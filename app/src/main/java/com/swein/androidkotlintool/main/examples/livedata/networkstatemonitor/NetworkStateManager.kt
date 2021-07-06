package com.swein.androidkotlintool.main.examples.livedata.networkstatemonitor

import androidx.lifecycle.MutableLiveData
import com.swein.androidkotlintool.main.examples.livedata.networkstatemonitor.receiver.NetworkConnectionReceiver

object NetworkStateManager {

    val networkConnectionReceiver = NetworkConnectionReceiver({ type ->
        networkType.value = type
    }, { isConnected ->
        networkState.value = isConnected
    })

    val networkType: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val networkState: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
}