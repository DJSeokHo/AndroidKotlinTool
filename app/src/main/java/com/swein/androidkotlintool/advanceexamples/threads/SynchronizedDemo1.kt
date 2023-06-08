package com.swein.androidkotlintool.advanceexamples.threads

import com.swein.androidkotlintool.framework.utility.debug.ILog
import java.lang.Exception

class SynchronizedDemo1: TestDemo {

    @Volatile
    private var running = true

    private fun stop() {
        running = false
    }

    override fun runTest() {

        Thread {

            ILog.debug("???", "thread start")

            // 守护线程
            while (running) {

            }

            ILog.debug("???", "thread stop")
        }.start()

        try {
            Thread.sleep(1000) // 卡界面1秒
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

        stop()
    }
}