package com.swein.androidkotlintool.advanceexamples.threads

import com.swein.androidkotlintool.framework.utility.debug.ILog

class ThreadInteractionDemo: TestDemo {

    override fun runTest() {

        val thread = Thread {

            for (i in 0 until 1_000_000) {

                if (Thread.interrupted()) {
                    return@Thread
                }
                ILog.debug("???", i)
            }
        }

        thread.start()

        Thread.sleep(1000)

//        thread.stop() // 强行中断，无法控制，不推荐
        thread.interrupt() // 可以让线程配合中断

    }
}