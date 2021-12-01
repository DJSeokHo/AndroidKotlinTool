package com.swein.androidkotlintool.main.examples.callbacktocoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import com.swein.androidkotlintool.R
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CallbackToCoroutineActivity : AppCompatActivity() {

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_callback_to_coroutine)

        imageView.setImageResource(R.drawable.coding_with_cat)

        // image view size callback type
//        imageView.post {
//            Log.d("???", "imageView size: ${imageView.width} ${imageView.height}")
//        }

        // image view size coroutine type
        lifecycleScope.launch {
            whenCreated {
                val sizeMap = imageViewTestCoroutine(imageView)
                Log.d("???", "${sizeMap["width"]} ${sizeMap["height"]}")
            }
        }

        // io test callback type
//        ioTest {
//            Log.d("???", "ioTest finish in ui thread")
//        }

        // io test coroutine type
        lifecycleScope.launch {
            whenCreated {
                Log.d("???", "start io test in coroutine")
                ioTestCoroutine()
                Log.d("???", "finish io test in ui thread")
            }
        }
    }

    private suspend fun ioTestCoroutine() {
        return suspendCoroutine { continuation ->
            ioTest {
                continuation.resume(Unit)
            }
        }
    }

    private suspend fun imageViewTestCoroutine(view: View): MutableMap<String, Int> {
        return suspendCoroutine { continuation ->
            view.post {
                val sizeMap = mutableMapOf<String, Int>()
                sizeMap["width"] = view.width
                sizeMap["height"] = view.height
                continuation.resume(sizeMap)
            }
        }
    }

    private val handler = Handler(Looper.getMainLooper())
    private fun ioTest(runnable: Runnable) {
        Thread().run {
            Log.d("???", "ioTest run")
            Thread.sleep(2500)
            handler.post(runnable)
        }
    }
}