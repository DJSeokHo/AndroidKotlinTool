package com.swein.androidkotlintool.main.jetpackexample.coroutineincustomview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CoroutineCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs), CoroutineScope {

    private val viewLifeCycleScope = CoroutineScope(coroutineContext)

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        job.cancel()
    }

    fun doSomething() {
        viewLifeCycleScope.launch {

        }
    }
}