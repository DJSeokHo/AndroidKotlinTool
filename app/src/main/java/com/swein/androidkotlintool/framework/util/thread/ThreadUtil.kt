package com.swein.androidkotlintool.framework.util.thread

import android.os.Handler
import android.os.Looper
import java.lang.Exception
import java.util.concurrent.Executors

class ThreadUtil {

    companion object {

        private val executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        private val handler = object: Handler(Looper.getMainLooper()) {

        }

        fun startThread(runnable: Runnable) {
            try {
                runnable.run()
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun startUIThread(delayMillis: Int, runnable: Runnable) {
            handler.postDelayed(runnable, delayMillis.toLong())
        }
    }

    protected fun finalize() {
        if (!executorService.isShutdown) {
            executorService.shutdown()
        }
    }
}