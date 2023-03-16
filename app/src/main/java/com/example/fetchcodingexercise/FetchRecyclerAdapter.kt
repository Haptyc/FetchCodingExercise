package com.example.fetchcodingexercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchcodingexercise.databinding.SingleViewBinding

class FetchRecyclerAdapter : RecyclerView.Adapter<FetchRecyclerVH>() {
    private val users: MutableList<FetchUser> = mutableListOf()

    fun updateUsers(u: List<FetchUser>) {
        users.clear()
        users.addAll(u)
        //easy approach for simple app no diff required
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FetchRecyclerVH {
        return FetchRecyclerVH(SingleViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: FetchRecyclerVH, position: Int) {
        holder.onBind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }
}

class FetchRecyclerVH(private val listBinding: SingleViewBinding) : RecyclerView.ViewHolder(listBinding.root) {

    fun onBind(data: FetchUser) {
        listBinding.listId.text = data.listId.toString()
        listBinding.listName.text = data.name
    }
}