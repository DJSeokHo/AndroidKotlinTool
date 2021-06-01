package com.swein.androidkotlintool.main.examples.mvvmrecyclerviewexample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil
import com.swein.androidkotlintool.main.examples.mvvmrecyclerviewexample.adapter.item.MVVMExampleItemModel

class MVVMRecyclerViewExampleViewModel: ViewModel() {

    val list: MutableLiveData<MutableList<MVVMExampleItemModel>> by lazy {
        MutableLiveData<MutableList<MVVMExampleItemModel>>()
    }

    init {
        list.value = mutableListOf()
    }

    fun reload(limit: Int, start: Runnable, finish: Runnable) {

        start.run()

        ThreadUtil.startThread {
            list.value?.clear()

            Thread.sleep(1500)
            val listFromServer = getTempDataFromDummyServer(0, limit)
            list.value?.addAll(listFromServer)

            ThreadUtil.startUIThread(0) {
                finish.run()
                list.value = list.value
            }
        }

    }

    fun loadMore(offset: Int, limit: Int, start: Runnable, finish: Runnable) {

        start.run()

        ThreadUtil.startThread {

            Thread.sleep(1000)
            val listFromServer = getTempDataFromDummyServer(offset, limit)
            list.value?.addAll(listFromServer)

            ThreadUtil.startUIThread(0) {
                finish.run()
                list.value = list.value
            }
        }
    }

    private fun getTempDataFromDummyServer(offset: Int, limit: Int): MutableList<MVVMExampleItemModel> {
        val list: MutableList<MVVMExampleItemModel> = mutableListOf()

        var mvvmExampleItemModel: MVVMExampleItemModel
        for(i in offset until (offset + limit)) {
            mvvmExampleItemModel = MVVMExampleItemModel()

            if (i % 2 == 0) {
                mvvmExampleItemModel.imageUrl = "https://lh3.googleusercontent.com/proxy/YxXW71Gq927UQyQe6WAVZyUhJGK7X83dWguZw2WXfHOIRAjLTltJuRBz2d5d3SlnXVtBRH-z1fk7DJr1Di-hwQSEiAJvXmyFPt-LJYCtceWZ29vxnQ"
            }
            else {
                mvvmExampleItemModel.imageUrl = "https://cdn.pixabay.com/photo/2018/05/19/16/52/persian-cat-3413792_960_720.jpg"
            }
            mvvmExampleItemModel.index = i
            mvvmExampleItemModel.content = "content $i"
            list.add(mvvmExampleItemModel)
        }

        return list
    }

}