package com.swein.androidkotlintool.template.handlerthread

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.widget.TextView
import com.swein.androidkotlintool.R

class HandlerThreadTemplateActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "HandlerThreadTemplateActivity"
    }

    private var textView: TextView? = null

    private var backgroundHandlerThread: HandlerThread? = null
    private var backgroundHandler: Handler? = null
    private var uiHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler_thread_template)

        textView = findViewById(R.id.textView)


        // create ui handler
        uiHandler = Handler(object: Handler.Callback {
            override fun handleMessage(msg: Message?): Boolean {
                when (msg!!.what) {
                    1 -> {
                        textView?.text = "haha " + msg.obj
                    }
                }
                return false
            }
        })

        // create background handler thread
        backgroundHandlerThread = HandlerThread("HandlerThread")
        backgroundHandlerThread?.start()


        // create background handler
        // and set looper of handlerThread
        // into background handler
        backgroundHandler = Handler(backgroundHandlerThread?.looper, object: Handler.Callback {

            var count: Int = 0

            override fun handleMessage(msg: Message?): Boolean {
                // get the message from background handler
                // and
                // send message to uiHandler
                Message.obtain(uiHandler, 1, ++count).sendToTarget()
                return true
            }
        })

        Thread(Runnable {
           for(i in 0 until 10) {
               // send message to looper of background handler in handlerThread
               backgroundHandler?.sendEmptyMessage(1)
               Thread.sleep(1000)
           }
        }).start()
    }

    override fun onDestroy() {
        backgroundHandlerThread?.quitSafely()
        super.onDestroy()
    }
}
