package com.swein.androidkotlintool.advanceexamples.threads

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import java.lang.Exception
import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ThreadsExamplesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threads_examples)

//        thread()
//        threadWithRunnable()
//        executor()
//        callable()

//        SynchronizedDemo1().runTest()
        SynchronizedDemo2().runTest()
        SynchronizedDemo2Good().runTest()
    }

    private fun thread() {
        Thread {
            ILog.debug("???", "thread start")
        }.start()
    }

    private fun threadWithRunnable() {

        Runnable {
            ILog.debug("???", "thread with runnable")
        }.apply {

            Thread(this).start()
        }
    }

    private fun executor() {

//        val executor = Executors.newSingleThreadExecutor() // 一般不用
//        val executor = Executors.newFixedThreadPool() // 用于一大堆爆发型任务，比如批量处理 bitmap
//        val executor = Executors.newCachedThreadPool() // 常用
        val executor = ThreadPoolExecutor(5, 100, 5, TimeUnit.MINUTES, SynchronousQueue()) // 自定义

        Runnable {
            ILog.debug("???", "runnable with executor")
        }.apply {

            executor.execute(this)
            executor.execute(this)
            executor.execute(this)
        }.also {
            executor.shutdown() // 用完后关闭，那么正在执行和排队的不受影响。但是不会再接受新的线程进行排队了
//            executor.execute(it) // 这个就不会有效，因为已经shutdown了，不再处理新线程

//            executor.shutdownNow()
        }
    }

    /**
     * 有返回值的runnable
     */
    private fun callable() {

//        val executor = Executors.newSingleThreadExecutor() // 一般不用
//        val executor = Executors.newFixedThreadPool() // 用于一大堆爆发型任务，比如批量处理 bitmap
//        val executor = Executors.newCachedThreadPool() // 常用
        val executor = ThreadPoolExecutor(5, 100, 5, TimeUnit.MINUTES, SynchronousQueue()) // 自定义

        object : Callable<String> {

            override fun call(): String {

                try {
                    Thread.sleep(2500)
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
                ILog.debug("???", "runnable with executor")
                return "OK"
            }

        }.apply {

            val future: Future<String> = executor.submit(this)
            executor.shutdown() // 用完后关闭，那么正在执行和排队的不受影响。但是不会再接受新的线程进行排队了

            while (true) {

                if (future.isDone) {
                    ILog.debug("???", future.get())
                    break
                }
            }
        }
    }
}