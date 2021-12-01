package com.swein.androidkotlintool.main.examples.livedata.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreLiveDataViewModel: ViewModel() {

    val currentScore: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val currentUser: MutableLiveData<UserScoreInfo> by lazy {
        MutableLiveData<UserScoreInfo>()
    }

    val currentUserList: MutableLiveData<MutableList<UserScoreInfo>> by lazy {
        MutableLiveData<MutableList<UserScoreInfo>>()
    }

    init {
        currentScore.value = 0
        currentUserList.value = mutableListOf()
    }

    fun setValue(value: Int) {
        // 您必须调用 setValue(T) 方法以从主线程更新 LiveData 对象。
        // 如果在工作器线程中执行代码，您可以改用 postValue(T) 方法来更新 LiveData 对象。
        currentScore.value = value
//        currentScore.postValue(value)
    }

    fun getValue(): Int {
        return currentScore.value ?: run {
            0
        }
    }

    fun setUser(name: String, info: String) {

        val item = UserScoreInfo().apply {
            this.name = name
            this.info = info
        }

        currentUser.value = item

        currentUserList.value?.add(item)
        currentUserList.value = currentUserList.value
    }
}

class UserScoreInfo {
    var name = ""
    var info = ""
}