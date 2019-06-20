package com.swein.androidkotlintool.console.basicgrammar

import com.swein.androidkotlintool.framework.util.ILog

class BasicIterator(stringList: List<String>) {

    companion object {
        const private val TAG: String = "BasicIterator"
    }

    private val stringList: List<String>? = stringList

    fun iterator() {

        if(stringList == null) {
            return
        }

        for(string in stringList) {
            ILog.debug(TAG, string)
        }
    }

    fun iteratorWithIndex() {

        if(stringList == null) {
            return
        }

        for(index in stringList.indices) {
            ILog.debug(TAG, "$index - ${stringList[index]}")
        }
    }

    fun iteratorForeach() {

        if(stringList == null) {
            return
        }

        stringList.forEach(
            fun(string: String) {
                ILog.debug(TAG, string)
            }
        )
    }

    fun whileMethod() {

        if(stringList == null) {
            return
        }

        var index: Int = 0

        while (index < stringList.size) {
            ILog.debug(TAG, "$index - ${stringList[index]}")
            index++
        }
    }

    fun doWhileMethod() {

        if(stringList == null) {
            return
        }

        var index: Int = 0

        do {
            ILog.debug(TAG, "$index - ${stringList[index]}")
            index++
        }
        while (index < stringList.size)
    }

    override fun toString(): String {
        return super.toString()
    }
}

fun main(args: Array<String>) {

    val stringList: List<String> = listOf("a", "b", "c")
    val basicIterator: BasicIterator = BasicIterator(stringList)

    basicIterator.iterator()
    basicIterator.iteratorWithIndex()
    basicIterator.whileMethod()
    basicIterator.doWhileMethod()
    basicIterator.iteratorForeach()
}