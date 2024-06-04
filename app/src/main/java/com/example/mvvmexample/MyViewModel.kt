package com.example.mvvmexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmexample.api.QuoteApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(val repo: DataRepo) : ViewModel() {

    val liveDataFromServer : LiveData<Sealed<List<QuoteApi>>>
        get() = repo.data

    fun getQuote() {
        viewModelScope.launch(Dispatchers.IO){
            repo.getQuotesFromApi()
        }
    }

}