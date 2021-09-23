package com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.model.MemberModel
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.service.MemberModelService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class MemberViewModelState {

//    data class Reload(val memberModel: MemberModel): MemberViewModelState()
    data class RegisterSuccessfully(val memberModel: MemberModel): MemberViewModelState()
    data class SignSuccessfully(val memberModel: MemberModel): MemberViewModelState()
    object Empty: MemberViewModelState()
    data class Error(val message: String?): MemberViewModelState()
    object None: MemberViewModelState()
    object Loading: MemberViewModelState()
}

class MemberViewModel: ViewModel() {

    companion object {
        private const val TAG = "MemberViewModel"
    }

    private val _memberViewModelState = MutableStateFlow<MemberViewModelState>(MemberViewModelState.None)
    val memberViewModelState: StateFlow<MemberViewModelState> = _memberViewModelState

    fun register(memberModel: MemberModel) = viewModelScope.launch {

        _memberViewModelState.value = MemberViewModelState.Loading

        try {
            coroutineScope {

                val register = async {
                    MemberModelService.register(memberModel)
                }

                register.await()

                _memberViewModelState.value = MemberViewModelState.RegisterSuccessfully(memberModel)
            }
        }
        catch (e: Exception) {
            _memberViewModelState.value = MemberViewModelState.Error(e.message)
        }
    }

    fun signIn(id: String, password: String) = viewModelScope.launch {

        _memberViewModelState.value = MemberViewModelState.Loading

        try {
            coroutineScope {

                val signIn = async {
                    MemberModelService.signIn(id, password)
                }

                val querySnapshot = signIn.await()

                val list = mutableListOf<MutableMap<String, Any>>()
                for (document in querySnapshot.documents) {
                    list.add(document.data as MutableMap<String, Any>)
                }

                if (list.isEmpty()) {
                    _memberViewModelState.value = MemberViewModelState.Empty
                    return@coroutineScope
                }

                val memberModel = MemberModel()
                memberModel.parsing(list[0])

                _memberViewModelState.value = MemberViewModelState.SignSuccessfully(memberModel)
            }
        }
        catch (e: Exception) {
            _memberViewModelState.value = MemberViewModelState.Error(e.message)
        }
    }

//    fun reload(
//        fieldName: String = "ALL",
//        keyWord: String = "",
//        levKey: String = "",
//        catKey: String = "0",
//        orderBy: String = "0",
//        offset: Int = 0,
//        size: Int = 20
//    ) = viewModelScope.launch {
//
//        _homeViewModelState.value = HomeViewModelState.Loading
//
//        try {
//            coroutineScope {
//
//                val mainListResult = async {
//                    HomeService.mainList(fieldName, keyWord, levKey, catKey, orderBy, offset, size)
//                }
//
//                val mainTopListResult = async {
//                    HomeService.mainTopList()
//                }
//
//                val resultMainList = mainListResult.await()
//                val resultMainTopList = mainTopListResult.await()
//
//                _homeViewModelState.value = HomeViewModelState.Reload(resultMainList, resultMainTopList)
//
//            }
//        }
//        catch (e: Exception) {
//            _homeViewModelState.value = HomeViewModelState.Error(e.message)
//        }
//
//    }

}
