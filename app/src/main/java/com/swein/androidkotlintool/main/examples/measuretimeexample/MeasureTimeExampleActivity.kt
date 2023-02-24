package com.swein.androidkotlintool.main.examples.measuretimeexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.swein.androidkotlintool.R
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MeasureTimeExampleActivity : AppCompatActivity() {

    private val buttonCommon: Button by lazy {
        findViewById(R.id.buttonCommon)
    }

    private val buttonSequential: Button by lazy {
        findViewById(R.id.buttonSequential)
    }

    private val buttonConcurrent: Button by lazy {
        findViewById(R.id.buttonConcurrent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measure_time_example)

        buttonCommon.setOnClickListener {

            measureTimeMillis {

                Log.d("???", "Start Common in Main Thread")

                val result = commonFunction()
                Log.d("???", "result is $result")

            }.also {
                Log.d("???", "Common completed in $it ms")
            }
        }

        buttonSequential.setOnClickListener {

            lifecycleScope.launch(Dispatchers.Main) {

                measureTimeMillis {

                    Log.d("???", "Start Sequential")

                    val one = someSuspendFunction()
                    val two = anotherSuspendFunction()

                    Log.d("???", "result is ${one + two}")

                }.also {
                    Log.d("???", "Sequential completed in $it ms")
                }
            }
        }

        buttonConcurrent.setOnClickListener {

            lifecycleScope.launch(Dispatchers.Main) {

                measureTimeMillis {

                    Log.d("???", "Start Concurrent")

                    val one = async(Dispatchers.IO) {
                        someSuspendFunction()
                    }

                    val two = async(Dispatchers.IO) {
                        anotherSuspendFunction()
                    }

                    Log.d("???", "result is ${one.await() + two.await()}")

                }.also {
                    Log.d("???", "Concurrent completed in $it ms")
                }

            }

        }
    }
}

fun commonFunction(): String {
    Thread.sleep(1200)
    return "OK"
}

suspend fun someSuspendFunction(): Int {
    delay(1000)
    return 3
}

suspend fun anotherSuspendFunction(): Int {
    delay(1500)
    return 5
}
