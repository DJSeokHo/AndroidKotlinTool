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

class CoroutineDemoActivity : AppCompatActivity(), CoroutineScope {

    companion object {
        private const val TAG = "CoroutineDemoActivity"
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

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

            launch(Dispatchers.Main) {

                val time = measureTimeMillis {

                    val a1 = async {
                        getData("test1")
                    }

                    val a2 = async {
                        getData("test2")
                    }

                    val a3 = async {
                        getData("test3")
                    }

                    val a4 = async {
                        getData("test4")
                    }

                    val a5 = async {
                        getData("test5")
                    }

                    ILog.debug(TAG, "a1 ${a1.await()}")
                    ILog.debug(TAG, "===========================================")
                    ILog.debug(TAG, "a2 ${a2.await()}")
                    ILog.debug(TAG, "===========================================")
                    ILog.debug(TAG, "a3 ${a3.await()}")
                    ILog.debug(TAG, "===========================================")
                    ILog.debug(TAG, "a4 ${a4.await()}")
                    ILog.debug(TAG, "===========================================")
                    ILog.debug(TAG, "a5 ${a5.await()}")

                    constraintLayout.setBackgroundColor(Color.CYAN)
                }

                ILog.debug(TAG, "time $time")
            }

        }

    }


}