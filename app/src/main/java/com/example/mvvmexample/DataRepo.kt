package com.example.mvvmexample

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmexample.api.PersonApi
import com.example.mvvmexample.api.QuoteApi
import com.example.mvvmexample.db.DataDao
import com.example.mvvmexample.db.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

class DataRepo(private val dao: DataDao, private val api: PersonApi, private val context: Context) {

    private val _mutableLiveData = MutableLiveData<Sealed<List<QuoteApi>>>()
    val data: LiveData<Sealed<List<QuoteApi>>>
        get() = _mutableLiveData


    suspend fun getQuotesFromApi(category: String = "android") {
        if (UtilClass.isNetworkAvailable(context)) {
            val response = api.getQuote("\$..tweets[?(@.category==\"$category\")]")
            if (response.isSuccessful) {
                val list = mutableListOf<Quote>()
                withContext(Dispatchers.Default) {
                    response.body()?.let {
                        it.forEach { value ->
                            list.add(Quote(0, value.text, value.category))
                        }
                    }
                }
                dao.insert(list)
                _mutableLiveData.postValue(Sealed.Result(response.body()))
            }
            else
            {
                _mutableLiveData.postValue(Sealed.Error("Api Error"))
            }
        } else {
            val result = dao.getQuotes()
            val list = mutableListOf<QuoteApi>()
            withContext(Dispatchers.Default) {
                result.let {
                    it.forEach { value ->
                        list.add(QuoteApi(value.author, value.text))
                    }
                }
            }
            _mutableLiveData.postValue(Sealed.Result(list))
        }
    }

    suspend fun getQuotesFromApiBackground(category: String = "android") {
        if (UtilClass.isNetworkAvailable(context)) {
            val response = api.getQuote("\$..tweets[?(@.category==\"$category\")]")
            if (response.isSuccessful) {
                val list = mutableListOf<Quote>()
                withContext(Dispatchers.Default) {
                    response.body()?.let {
                        it.forEach { value ->
                            list.add(Quote(0, value.text, value.category))
                        }
                    }
                }
                dao.insert(list)
            }
        }
    }
}