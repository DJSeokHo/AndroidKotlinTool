package com.swein.androidkotlintool.main.examples.rxkotlinlite.subscriber

/**
 * U: upper
 */
interface Observer<U> {
    fun onNext(event: U)
    fun onReady() { }
    fun onCompleted() { }
    fun onError(throwable: Throwable) { }
}