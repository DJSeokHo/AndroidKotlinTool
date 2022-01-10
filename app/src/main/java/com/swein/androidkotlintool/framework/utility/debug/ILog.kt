package com.swein.androidkotlintool.framework.utility.debug

import android.util.Log

/**
 * object 说明是一个静态方法类
 */
object ILog {

    private const val TAG: String = "ILog ======> "

    fun debug(tag: String, content: Any?) {
        Log.d(TAG, "$tag $content")
    }

}