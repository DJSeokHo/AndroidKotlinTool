package com.swein.androidkotlintool.console.basicgrammar

import com.swein.androidkotlintool.framework.util.log.ILog

class BasicCollections {

    companion object {
        private const val TAG: String = "BasicCollections"
    }

    fun listTest() {

        var mutableList: ArrayList<String>?

        mutableList = arrayListOf()
        for (i in 0..10) {
            mutableList.add(i.toString())
        }
        mutableList.forEach(fun(string: String) {
            ILog.debug(TAG, string)
        })

        val list: List<String> = listOf("1", "2", "3")
        mutableList = list.toMutableList() as ArrayList<String>
        mutableList.forEach(fun(string: String) {
            ILog.debug(TAG, string)
        })
    }

    fun mapTest() {

        var mutableMap: MutableMap<String, Any?>?

        mutableMap = mutableMapOf()
        for(i in 0..10) {
            mutableMap["$i"] = "haha $i"
        }

        ILog.debug(TAG, mutableMap)
        mutableMap.entries.forEach {
            ILog.debug(TAG, "${it.key} ${it.value as String}")
        }

        val map: Map<String, Any?> = mapOf(
            "0" to "haha 0",
            "1" to "haha 1",
            "2" to "haha 2")

        mutableMap = map.toMutableMap()
        ILog.debug(TAG, mutableMap)
        mutableMap.entries.forEach {
            ILog.debug(TAG, "${it.key} ${it.value as String}")
        }
    }
}

fun main() {
    val basicCollections = BasicCollections()
    basicCollections.listTest()
    basicCollections.mapTest()
}