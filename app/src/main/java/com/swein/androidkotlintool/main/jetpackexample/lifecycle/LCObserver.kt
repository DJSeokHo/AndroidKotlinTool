package com.swein.androidkotlintool.main.jetpackexample.lifecycle

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.swein.androidkotlintool.framework.util.log.ILog

class LCObserver(private val context: Context, private val lifeCycle: Lifecycle): LifecycleObserver {

    companion object {
        private const val TAG = "LCObserver"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        ILog.debug(TAG, "onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        ILog.debug(TAG, "onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        ILog.debug(TAG, "onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        ILog.debug(TAG, "onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        ILog.debug(TAG, "onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        ILog.debug(TAG, "onDestroy")
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
//    fun onAny() {
//        ILog.debug(TAG, "onAny")
//    }
}