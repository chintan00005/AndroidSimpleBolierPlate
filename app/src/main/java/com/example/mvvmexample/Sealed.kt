package com.example.mvvmexample

sealed class Sealed<T>(val data: T? = null, val errorMessage: String? = null) {
    class Loading<T> : Sealed<T>()
    class Result<T>(data: T?) : Sealed<T>(data = data)
    class Error<T>(errorMessage: String?) : Sealed<T>(errorMessage = errorMessage)
}