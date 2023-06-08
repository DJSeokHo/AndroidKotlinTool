package com.swein.androidkotlintool.advanceexamples.threads

import com.swein.androidkotlintool.framework.utility.debug.ILog
import java.lang.Exception
import java.util.concurrent.atomic.AtomicInteger

class SynchronizedDemo2Good: TestDemo {

    private var count: AtomicInteger = AtomicInteger(0) // 让count的操作具有原子性和同步性

    private fun count() {
//        count.incrementAndGet() // ++count
        count.getAndIncrement() // count++
    }

    override fun runTest() {
        /*
        这里2个线程对一个值各加100万，结果应该是200万，但是实际上不是，因为线程同步性问题。
        这里加 @Volatile 也不对，因为 count++ 不是一个原子操作，因为他是分成两步的，原子操作是指一个不可再拆分的操作。这里的++是语法糖。

        val temp = count + 1
        count = temp

        不是一个原子操作。所以@Volatile会失效。
        切换线程只要在一个原子操作完成后，随时可以切换，所以有可能temp还没来得及赋值，就被切了，所以会导致结果不正确。

        怎么办呢？把count()函数变成一个原子性函数不就行了。
         */
        Thread {
            for (i in 0 until 1_000_000) {
                count()
            }
            ILog.debug("???", "final good count from 1: $count")
        }.start()

        Thread {
            for (i in 0 until 1_000_000) {
                count()
            }
            ILog.debug("???", "final good count from 2: $count")
        }.start()
    }
}