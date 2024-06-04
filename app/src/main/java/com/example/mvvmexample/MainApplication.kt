package com.example.mvvmexample

import android.annotation.SuppressLint
import android.app.Application
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.mvvmexample.api.RetrofitClass
import com.example.mvvmexample.db.DbClass
import java.util.concurrent.TimeUnit

class MainApplication : Application() {


    private var dataRepo: DataRepo?=null

    override fun onCreate() {
        super.onCreate()

         val dao = DbClass.getDb(this)
         dataRepo = DataRepo(dao!!.getDao(), RetrofitClass().getApi(), applicationContext)
        setWorkManager()
    }


    fun getRepo(): DataRepo? {
        return dataRepo
    }

    @SuppressLint("InvalidPeriodicWorkRequestInterval")
    private fun setWorkManager(){
        val constraint = androidx.work.Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest: PeriodicWorkRequest.Builder = PeriodicWorkRequest.Builder(MyWork::class.java,30, TimeUnit.SECONDS)
        workRequest.setConstraints(constraint)
       WorkManager.getInstance(this).enqueue(workRequest.build())
    }


}