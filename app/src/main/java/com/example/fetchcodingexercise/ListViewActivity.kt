package com.example.fetchcodingexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchcodingexercise.databinding.ActivityListViewBinding

class ListViewActivity : AppCompatActivity() {
    private lateinit var listViewBinding: ActivityListViewBinding
    private val adapter: FetchRecyclerAdapter = FetchRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewBinding = ActivityListViewBinding.inflate(layoutInflater)
        val view = listViewBinding.root
        setContentView(view)
        setupRecycler()
    }

    override fun onStart() {
        super.onStart()
        val vm: MainViewModel by viewModels()
        vm.setup()
        setupSubs(vm)
        listViewBinding.defaultSort.setOnClickListener { vm.changeSorting(SortChoice.DEFAULT) }
        listViewBinding.nameSort.setOnClickListener { vm.changeSorting(SortChoice.NAME) }
        listViewBinding.idSort.setOnClickListener { vm.changeSorting(SortChoice.LIST_ID) }
    }

    private fun setupRecycler() {
        listViewBinding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        listViewBinding.recyclerView.adapter = adapter
    }

    private fun setupSubs(viewModel: MainViewModel) {
        //setup subscriptions
        viewModel.viewState
            .observe(this) {
                if (it.isInErrorState) {
                    //simple error flow cause happy path assumption
                    Toast.makeText(
                        this,
                        "Something went wrong, try restarting the app",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (it.isLoading) {

                    } else {
                        adapter.updateUsers(it.sortedListOfUsers)
                    }
                }
            }
    }
}
