package com.swein.androidkotlintool.main.jetpackexample.appstartup

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object AnyToolNeedContextWhenInit {

    private const val TAG = "AnyToolNeedContextWhenInit"
    private var context: Context? = null

    fun init(context: Context) {
        AnyToolNeedContextWhenInit.context = context
    }
}