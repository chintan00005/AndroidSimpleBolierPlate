package com.example.mvvmexample.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClass {

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder().baseUrl("https://api.jsonbin.io").addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun getApi():PersonApi{
        return  getRetrofit().create(PersonApi::class.java)
    }
}