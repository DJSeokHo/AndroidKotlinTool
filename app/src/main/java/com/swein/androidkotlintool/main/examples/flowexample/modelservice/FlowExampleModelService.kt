package com.swein.androidkotlintool.main.examples.flowexample.modelservice

import com.swein.androidkotlintool.framework.utility.debug.ILog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FlowExampleModelService {

    suspend fun getTempDataFromDummyServer(offset: Int, limit: Int): MutableList<String> = withContext(
        Dispatchers.IO) {

        ILog.debug("!!! fetch data on ", Thread.currentThread().name)
        val list = mutableListOf<String>()

        for(i in offset until (offset + limit)) {

            list.add("content $i")
        }

        return@withContext list
    }

}