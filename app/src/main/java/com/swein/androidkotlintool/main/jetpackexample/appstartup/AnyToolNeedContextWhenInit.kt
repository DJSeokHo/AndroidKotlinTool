package com.swein.androidkotlintool.main.jetpackexample.appstartup

import android.annotation.SuppressLint
import android.content.Context
import com.swein.androidkotlintool.framework.util.log.ILog

@SuppressLint("StaticFieldLeak")
object AnyToolNeedContextWhenInit {

    private const val TAG = "AnyToolNeedContextWhenInit"
    private var context: Context? = null

    fun init(context: Context) {
        AnyToolNeedContextWhenInit.context = context
    }
}