package com.example.mvvmexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmexample.databinding.ActivityMainBinding
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.coroutines.yield
import java.lang.Exception
import kotlin.coroutines.resume

class MainActivity : AppCompatActivity() {

    private var viewModel: MyViewModel? = null
    private var activityMainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val application = application as MainApplication

//        CoroutineScope(Dispatchers.IO).async {
//           val def1 = listOf(async { print1() }, async { print2() })
//            val def2 = async { print2() }
//            val def3 = async { print1() }
//
//            withContext(Dispatchers.Main)
//                {
//                    def1.awaitAll().forEach { it ->
//                        activityMainBinding?.btnTemp?.text=it.toString()
//                    }
//
//                }
//
//            Log.e("Result 1", def1.awaitAll().toString())
//            Log.e("Result 2", awaitAll(def2,def3).toString())
////            withContext(Dispatchers.Default){
////                print2()
////                delay(10000)
////                withContext(Dispatchers.Main)
////                {
////                    activityMainBinding?.btnTemp?.text="Temp"
////                }
////            }
//
//        }
        
        try {


            val minJob = CoroutineScope(Dispatchers.Main).launch {

                try {
                    val job1 = CoroutineScope(Dispatchers.Main).launch {
                        try {
                            //     performNetworkCall()

//                        var i = 0
//                        while (i < 50000 && isActive) {
//        ensureActive()
//                           // yield()
//                            delay(50)
//                            Log.e("I value ", "" + i)
//                            i++
//
//
//                        }

                           val job = withTimeoutOrNull(10000) {
                                Log.e("Timeout ", "Started")
                                var i =1
                                repeat(10)
                                {
                                    delay(1000)
                                    Log.e("I ",""+i)
                                    i++
                                }
                                delay(50000)
                                Log.e("Timeout ", "Ended")
                            }
                            Log.e("withTimeout ", ""+job)
                        } catch (e: CancellationException) {
                            Log.e("Coroutine ", "Inside Cancelled launch")
                            e.printStackTrace()
                        } finally {
                            withContext(NonCancellable) {
                                // delay(15000)
                                Log.e("Coroutine ", " Finally ")
                            }
                        }
                    }
                   // delay(1000)
                   // job1.cancel()
                    //    job1.join()

                    //job1.await()
                    Log.e("Coroutine ", " Inside Completed")
                } catch (e: CancellationException) {
                    Log.e("Coroutine ", " Inside Cancelled await")
                    e.printStackTrace()
                }


            }

            //   minJob.cancel()
        } catch (e: CancellationException) {
            e.printStackTrace()
            Log.e("Coroutine ", " Outside Cancelled await")
        } finally {
            Log.e("Coroutine ", " Outside Finally")
        }

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(application.getRepo()!!)
        )[MyViewModel::class.java]
        viewModel?.liveDataFromServer?.observe(this) {
            when (it) {
                is Sealed.Loading -> {

                }

                is Sealed.Result -> {
                    activityMainBinding?.quote = it.data.toString()
                }

                is Sealed.Error -> {
                    Toast.makeText(this, it.errorMessage.toString(), Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    suspend fun performNetworkCall(): String {
        return suspendCancellableCoroutine { continuation ->

            continuation.invokeOnCancellation {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(10000)
                    Log.e("performNetworkCall ", " Finally ")
                }

            }
            GlobalScope.launch {
                Log.e("Before", " Fetching Data")
                val data = print1()
                //yield()
                Log.e("After ", "Fetching Data")
                delay(10000)
                continuation.resume(data) {
                    CoroutineScope(Dispatchers.Main).launch {
                        //delay(10000)
                        Log.e("performNetworkCall Callback ", " Finally ")
                    }

                } // Hey, resume here! I've got the data!
            }
        }
    }

    suspend fun print1(): String {
        delay(20000)
        return "Server 1"
    }

    suspend fun print2(): String {
        delay(5000)
        return "Server 2"
    }

    fun onClick(view: View) {
        viewModel?.getQuote()
    }
}