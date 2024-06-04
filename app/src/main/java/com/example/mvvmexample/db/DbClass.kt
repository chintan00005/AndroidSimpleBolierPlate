package com.example.mvvmexample.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Quote::class], version = 1)
abstract class DbClass : RoomDatabase() {


    abstract fun getDao(): DataDao

    companion object {

        @Volatile
        var INSTANCE: DbClass? = null

        fun getDb(context: Context): DbClass? {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context, DbClass::class.java, "quotes")
                        .build()
                }
            }
            return INSTANCE
        }
    }
}