package com.swein.androidkotlintool.main.examples.flowexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.flowexample.modelservice.FlowExampleModelService
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class FlowExampleState {

    data class Reload(val list: List<String>): FlowExampleState()
    data class LoadMore(val list: List<String>): FlowExampleState()
    object None: FlowExampleState()
    object Loading: FlowExampleState()
}

class FlowExampleViewModel: ViewModel() {

    private val _flowExampleUIState = MutableStateFlow<FlowExampleState>(FlowExampleState.None)
    val flowExampleUIState: StateFlow<FlowExampleState> = _flowExampleUIState

    var testValue = 0

    fun load(offset: Int = 0, limit: Int = 10) = viewModelScope.launch {

        ILog.debug("!!! start load", Thread.currentThread().name)
        _flowExampleUIState.value = FlowExampleState.Loading

        val result = async {
            delay(1000)
            FlowExampleModelService.getTempDataFromDummyServer(offset, limit)
        }

//        val resultList = listOf(     // fetch two docs at the same time
//            async {  },  // async returns a result for the first doc
//            async {  }   // async returns a result for the second doc
//        )
//        resultList.awaitAll()        // use awaitAll to wait for both network requests

        if (offset == 0) {
            _flowExampleUIState.value = FlowExampleState.Reload(result.await())
        }
        else {
            _flowExampleUIState.value = FlowExampleState.LoadMore(result.await())
        }
    }

}