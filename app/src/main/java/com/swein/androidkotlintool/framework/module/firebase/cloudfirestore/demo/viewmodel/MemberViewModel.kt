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

    data class RegisterSuccessfully(val memberModel: MemberModel): MemberViewModelState()
    data class SignSuccessfully(val memberModel: MemberModel): MemberViewModelState()
    data class UpdateSuccessfully(val memberModel: MemberModel): MemberViewModelState()
    data class DeleteSuccessfully(val uuId: String): MemberViewModelState()

    object Empty: MemberViewModelState()
    data class Error(val message: String?): MemberViewModelState()
    object Loading: MemberViewModelState()

    object None: MemberViewModelState()
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

    fun modify(memberModel: MemberModel) = viewModelScope.launch {

        _memberViewModelState.value = MemberViewModelState.Loading

        try {
            coroutineScope {

                val modify = async {
                    MemberModelService.modify(memberModel)
                }

                modify.await()

                _memberViewModelState.value = MemberViewModelState.UpdateSuccessfully(memberModel)
            }
        }
        catch (e: Exception) {
            _memberViewModelState.value = MemberViewModelState.Error(e.message)
        }

    }

    fun delete(uuId: String) = viewModelScope.launch {

        _memberViewModelState.value = MemberViewModelState.Loading

        try {
            coroutineScope {

                val delete = async {
                    MemberModelService.delete(uuId)
                }

                delete.await()

                _memberViewModelState.value = MemberViewModelState.DeleteSuccessfully(uuId)
            }
        }
        catch (e: Exception) {
            _memberViewModelState.value = MemberViewModelState.Error(e.message)
        }

    }

}