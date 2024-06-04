package com.example.mvvmexample

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyWork(private val context: Context, workerParameters: WorkerParameters) : Worker(context,workerParameters){
    override fun doWork(): Result {

        CoroutineScope(Dispatchers.IO).launch {
            (context as MainApplication).getRepo()?.getQuotesFromApiBackground()
        }

        return Result.success()
    }
}