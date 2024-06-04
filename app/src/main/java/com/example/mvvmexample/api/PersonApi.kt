package com.example.mvvmexample.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface PersonApi {

    @GET("/v3/b/6639e581e41b4d34e4f00236?meta=false")
    suspend fun getQuote(@Header("X-JSON-Path") category: String): Response<List<QuoteApi>>
}