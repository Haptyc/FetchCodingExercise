package com.example.fetchcodingexercise

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList

class MainViewModel : ViewModel() {
    val viewState: MutableLiveData<ScreenState> = MutableLiveData(ScreenState())
    val vs: ScreenState
        get() = viewState.value!!

    private fun getUserData() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = FetchRetrofitApi.FETCH_API_SERVICE.getInfo()
            withContext(Dispatchers.Main) {
                viewState.value = vs.copy(
                    isLoading = true
                )
                val tempVs = vs.copy(
                    isLoading = false
                )
                if (response.isSuccessful) {
                    val items = response.body()
                    if (items != null) {
                        viewState.value = tempVs.copy(
                            isInErrorState = false,
                            listOfUsers = items,
                            sortedListOfUsers = sortItems(items)
                        )
                    } else {
                        viewState.value = tempVs.copy(isInErrorState = true)
                    }
                } else {
                    viewState.value = tempVs.copy(isInErrorState = true)
                }
            }
        }
    }

    private fun sortItems(
        items: List<FetchUser>,
        sortingBy: SortChoice = SortChoice.DEFAULT
    ): List<FetchUser> {
        val filtered = items.filter { !it.name.isNullOrBlank() }
        val tempList = mutableListOf<FetchUser>()
        when (sortingBy) {
            SortChoice.DEFAULT -> {
                val groupedData = filtered.groupBy {
                    it.listId
                }
                groupedData.keys
                    .sorted()
                    .forEach { listId ->
                        tempList.addAll(groupedData[listId]!!.sortedBy { user -> user.name })
                    }
            }
            SortChoice.LIST_ID -> {
                tempList.addAll(
                    filtered.sortedBy { it.listId }
                )
            }
            SortChoice.NAME -> {
                tempList.addAll(
                    filtered.sortedBy { it.name }
                )
            }
        }

        return tempList
    }

    //could do more fancy setup but easier to do this way for a small project
    fun setup() {
        //simple app, onlycall getUserData
        getUserData()
    }

    fun changeSorting(sortChoice: SortChoice) {
        viewState.value = vs.copy(sortedListOfUsers = sortItems(vs.listOfUsers, sortChoice))
    }
}

enum class SortChoice {
    DEFAULT,
    NAME,
    LIST_ID
}

data class ScreenState(
    val listOfUsers: List<FetchUser> = listOf(),
    val sortedListOfUsers: List<FetchUser> = listOf(),
    val isInErrorState: Boolean = false,
    val isLoading: Boolean = true
)
