package com.swein.androidkotlintool.main.examples.livedata.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreLiveDataViewModel: ViewModel() {

    val currentScore: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    init {
        currentScore.value = 0
    }

    fun setValue(value: Int) {
        // 您必须调用 setValue(T) 方法以从主线程更新 LiveData 对象。
        // 如果在工作器线程中执行代码，您可以改用 postValue(T) 方法来更新 LiveData 对象。
        currentScore.value = value
    }

    fun getValue(): Int {
        return currentScore.value ?: run {
            0
        }
    }
}