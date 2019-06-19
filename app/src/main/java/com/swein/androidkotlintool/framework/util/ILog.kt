package com.swein.androidkotlintool.framework.util

/**
 * object 说明是一个静态方法类
 */
object ILog {

    private val TAG: String = "ILog ======> "

    fun debug(tag: String, content: Any?) {
        println("$TAG $tag $content")
    }

}