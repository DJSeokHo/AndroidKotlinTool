package com.swein.androidkotlintool.advanceexamples.threads

import com.swein.androidkotlintool.framework.utility.debug.ILog

class ThreadWaitWithJoinDemo: TestDemo {

    private var name = ""

    private fun initString() {
        synchronized(this) {
            name = "Coding with cat"
        }
    }

    private fun printString() {
        synchronized(this) {
            ILog.debug("??", name)
        }
    }

    override fun runTest() {

        val thread2 = Thread {

            Thread.sleep(2000)
            initString()

        }
        thread2.start()

        val thread1 = Thread {

            Thread.sleep(1000)

            // thread1 先执行完，准备打印，但是初始化还没完成，没有值，怎么办？那必须等2执行完再打印
            thread2.join()
            printString()
        }
        thread1.start()
    }
}