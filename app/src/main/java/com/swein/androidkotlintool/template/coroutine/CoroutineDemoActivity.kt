package com.swein.androidkotlintool.template.coroutine

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.okhttp.OKHttpWrapper
import com.swein.androidkotlintool.framework.util.okhttp.OKHttpWrapperDelegate
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil
import kotlinx.coroutines.*
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

class CoroutineDemoActivity : BaseCoroutineActivity() {

    companion object {
        private const val TAG = "CoroutineDemoActivity"
    }

    private lateinit var constraintLayout: ConstraintLayout

    private suspend fun getData(name: String) = withContext(Dispatchers.IO) {
        val response = OKHttpWrapper.requestGet("https://httpbin.org/get?name=$name")
        OKHttpWrapper.getStringResponse(response, "response empty $name")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_demo)

        constraintLayout = findViewById(R.id.constraintLayout)

        findViewById<Button>(R.id.buttonGet).setOnClickListener {

//            launch(Dispatchers.Main) {
//
//                val time = measureTimeMillis {
//
//                    val a1 = async {
//                        getData("test1")
//                    }
//
//                    val a2 = async {
//                        getData("test2")
//                    }
//
//                    val a3 = async {
//                        getData("test3")
//                    }
//
//                    ILog.debug(TAG, "a1 ${a1.await()}")
//                    ILog.debug(TAG, "===========================================")
//                    ILog.debug(TAG, "a2 ${a2.await()}")
//                    ILog.debug(TAG, "===========================================")
//                    ILog.debug(TAG, "a3 ${a3.await()}")
//
//                    constraintLayout.setBackgroundColor(Color.CYAN)
//                }
//
//                ILog.debug(TAG, "time ${time}ms")
//            }

//            GlobalScope.launch {
//
//                val time = measureTimeMillis {
//
//                    val data1 = getData1()
//                    val data2 = getData2()
//
//                    ILog.debug(TAG, "$data1 $data2")
//                }
//
//                ILog.debug(TAG, "time ${time}ms")
//            }

//            GlobalScope.launch(Dispatchers.IO) {
//
//                val time = measureTimeMillis {
//
//                    val data1 = async {
//                        getData1()
//                    }
//                    val data2 = async {
//                        getData2()
//                    }
//
//                    ILog.debug(TAG, "${data1.await()} ${data2.await()}")
//                }
//
//                ILog.debug(TAG, "time ${time}ms")
//            }

            GlobalScope.launch(Dispatchers.Main) {

                val time = measureTimeMillis {

                    val data1 = async {
                        getData1()
                    }
                    val data2 = async {
                        getData2()
                    }

                    ILog.debug(TAG, "${data1.await()} ${data2.await()}")
                }

                constraintLayout.setBackgroundColor(Color.CYAN)
                ILog.debug(TAG, "time ${time}ms")
            }
        }
    }

//    private suspend fun getData1(): String {
//        delay(1000)
//        return "data1"
//    }
//
//    private suspend fun getData2(): String {
//        delay(600)
//        return "data2"
//    }

    private suspend fun getData1() = withContext(Dispatchers.IO) {
        delay(1000)
        return@withContext "data1"
    }

    private suspend fun getData2() = withContext(Dispatchers.IO) {
        delay(600)
        return@withContext "data2"
    }
}