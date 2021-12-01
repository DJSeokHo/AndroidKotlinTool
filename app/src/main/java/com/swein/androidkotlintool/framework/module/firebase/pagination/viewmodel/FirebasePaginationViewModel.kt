package com.swein.androidkotlintool.framework.module.firebase.pagination.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import com.swein.androidkotlintool.framework.module.firebase.pagination.model.FirebasePaginationItemModel
import com.swein.androidkotlintool.framework.module.firebase.pagination.service.FirebasePaginationService
import com.swein.androidkotlintool.framework.utility.debug.ILog
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class FirebasePaginationViewModelState {

    data class ReloadSuccessfully(val offset: DocumentSnapshot, val list: List<FirebasePaginationItemModel>): FirebasePaginationViewModelState()
    data class LoadMoreSuccessfully(val offset: DocumentSnapshot, val list: List<FirebasePaginationItemModel>): FirebasePaginationViewModelState()

    object Empty: FirebasePaginationViewModelState()
    data class Error(val message: String?): FirebasePaginationViewModelState()
    object Loading: FirebasePaginationViewModelState()

    object None: FirebasePaginationViewModelState()
}

class FirebasePaginationViewModel: ViewModel() {

    private val _viewModelState = MutableStateFlow<FirebasePaginationViewModelState>(FirebasePaginationViewModelState.None)
    val viewModelState: StateFlow<FirebasePaginationViewModelState> = _viewModelState

    fun reload(orderBy: String, limit: Long, tag: String = "") = viewModelScope.launch {

        _viewModelState.value = FirebasePaginationViewModelState.Loading

        try {
            coroutineScope {

                val reload = async {
                    FirebasePaginationService.fetch(orderBy, null, limit, tag)
                }

                val querySnapshot = reload.await()

                ILog.debug("???", "reload ${querySnapshot.documents.size}")
                val list = mutableListOf<MutableMap<String, Any>>()
                for (document in querySnapshot.documents) {
                    list.add(document.data as MutableMap<String, Any>)
                }

                if (list.isEmpty()) {
                    _viewModelState.value = FirebasePaginationViewModelState.Empty
                    return@coroutineScope
                }

                val tempList = mutableListOf<FirebasePaginationItemModel>()
                var firebasePaginationItemModel: FirebasePaginationItemModel
                for (map in list) {
                    firebasePaginationItemModel = FirebasePaginationItemModel()
                    firebasePaginationItemModel.parsing(map)
                    tempList.add(firebasePaginationItemModel)
                }

                _viewModelState.value = FirebasePaginationViewModelState.ReloadSuccessfully(querySnapshot.documents.last(), tempList)
            }
        }
        catch (e: Exception) {
            _viewModelState.value = FirebasePaginationViewModelState.Error(e.message)
        }
    }

    fun loadMore(orderBy: String, offset: DocumentSnapshot? = null, limit: Long, tag: String = "") = viewModelScope.launch {

        _viewModelState.value = FirebasePaginationViewModelState.Loading

        try {
            coroutineScope {

                val loadMore = async {
                    FirebasePaginationService.fetch(orderBy, offset, limit, tag)
                }

                val querySnapshot = loadMore.await()

                ILog.debug("???", "loadMore ${querySnapshot.documents.size}")

                val list = mutableListOf<MutableMap<String, Any>>()
                for (document in querySnapshot.documents) {
                    list.add(document.data as MutableMap<String, Any>)
                }

                if (list.isEmpty()) {
                    _viewModelState.value = FirebasePaginationViewModelState.Empty
                    return@coroutineScope
                }

                val tempList = mutableListOf<FirebasePaginationItemModel>()
                var firebasePaginationItemModel: FirebasePaginationItemModel
                for (map in list) {
                    firebasePaginationItemModel = FirebasePaginationItemModel()
                    firebasePaginationItemModel.parsing(map)
                    tempList.add(firebasePaginationItemModel)
                }

                _viewModelState.value = FirebasePaginationViewModelState.LoadMoreSuccessfully(querySnapshot.documents.last(), tempList)
            }
        }
        catch (e: Exception) {
            _viewModelState.value = FirebasePaginationViewModelState.Error(e.message)
        }
    }
}
