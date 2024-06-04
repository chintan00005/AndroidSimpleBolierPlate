package com.example.mvvmexample.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface DataDao {
    @Insert
    suspend fun insert(quote: List<Quote>)
    @Update
    suspend fun update(quote: Quote)
    @Delete
    suspend fun delete(quote: Quote)

    @Query("SELECT * from QUOTE")
    fun getQuotes():List<Quote>
}