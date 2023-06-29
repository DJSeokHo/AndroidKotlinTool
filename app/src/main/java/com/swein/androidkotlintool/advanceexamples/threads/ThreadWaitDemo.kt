package com.swein.androidkotlintool.advanceexamples.threads

import com.swein.androidkotlintool.framework.utility.debug.ILog

class ThreadWaitDemo: TestDemo {

    private var name = ""

    private var monitor = Object()

    private fun initString() {

        synchronized(monitor) {
            name = "Coding with cat"
            // 3. 有值了，通知排队的来准备执行。注意，两个monitor必须一致
            monitor.notifyAll()

        }
    }

    private fun printString() {
        // 2. 所以这里需要判断，name 不为空字符串时才打印
        // 但是这样写就有问题了，会进入死锁。程序进来 printString，拿到了锁，但是有while，这是字符串一直是空，while会一直执行，无法结束。
        // 所以永远无法初始化，因为 printString 有锁，初始化无法执行。怎么办呢？
        synchronized(monitor) {

            while (name == "") {
                // 3. 先释放锁，去排队，等name有值了再回来。
                monitor.wait()
            }

        }

        ILog.debug("??", name)
    }

    override fun runTest() {

        val thread1 = Thread {

            Thread.sleep(1000)

            printString()

        }
        thread1.start()

        Thread {

            Thread.sleep(2000)
            // 1. 理论上 初始化和打印值都是耗时操作，也有可能打印比初始化先执行，但这样就明显不对了。
            initString()

        }.start()

        // 4. 假设我想让这个打印在执行完第一个线程的 printString() 后再打印，怎么办？
        thread1.join() // 这样的话主线程就会等thread1执行完成后再继续。
        ILog.debug("???", "good")
    }
}