package com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.dynamicfeatures.Constants
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.model.MemberModel
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.service.MemberModelService
import com.swein.androidkotlintool.framework.module.firebase.storage.FirebaseStorageManager
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class MemberViewModelState {

    data class RegisterSuccessfully(val memberModel: MemberModel): MemberViewModelState()
    data class SignInSuccessfully(val memberModel: MemberModel): MemberViewModelState()
    data class UpdateSuccessfully(val memberModel: MemberModel): MemberViewModelState()
    data class DeleteSuccessfully(val uuId: String): MemberViewModelState()
    data class CheckIDExistsSuccessfully(val id: String): MemberViewModelState()
    data class AutoSignInSuccessfully(val memberModel: MemberModel): MemberViewModelState()

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

    fun register(imageUri: Uri, imageFileName: String, memberModel: MemberModel) = viewModelScope.launch {

        _memberViewModelState.value = MemberViewModelState.Loading

        try {
            coroutineScope {

                val uploadImage = async {
                    MemberModelService.uploadImageFile(imageUri)
                }

                val imageUrl = uploadImage.await()
                val imagePath = "${FirebaseStorageManager.MEMBER_IMAGE_FOLDER}imageFileName"

                memberModel.profileImageUrl = imageUrl
                memberModel.profileImageFileCloudPath = imagePath

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

                _memberViewModelState.value = MemberViewModelState.SignInSuccessfully(memberModel)
            }
        }
        catch (e: Exception) {
            _memberViewModelState.value = MemberViewModelState.Error(e.message)
        }
    }

    fun modify(imageUri: Uri?, memberModel: MemberModel) = viewModelScope.launch {

        _memberViewModelState.value = MemberViewModelState.Loading

        try {
            coroutineScope {

                imageUri?.let {

                    val uploadImage = async {
                        MemberModelService.uploadImageFile(imageUri)
                    }

                    val imageUrl = uploadImage.await()
                    memberModel.profileImageUrl = imageUrl
                }

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

    fun isIdExists(id: String) = viewModelScope.launch {

        _memberViewModelState.value = MemberViewModelState.Loading

        try {
            coroutineScope {

                val signIn = async {
                    MemberModelService.isIdExists(id)
                }

                val querySnapshot = signIn.await()


                if (querySnapshot.documents.isNotEmpty()) {
                    _memberViewModelState.value = MemberViewModelState.CheckIDExistsSuccessfully(id)
                }
                else {
                    _memberViewModelState.value = MemberViewModelState.CheckIDExistsSuccessfully("")
                }
            }
        }
        catch (e: Exception) {
            _memberViewModelState.value = MemberViewModelState.Error(e.message)
        }
    }

    fun autoSign(uuId: String) = viewModelScope.launch {
        _memberViewModelState.value = MemberViewModelState.Loading

        try {
            coroutineScope {

                val signIn = async {
                    MemberModelService.autoSignIn(uuId)
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

                _memberViewModelState.value = MemberViewModelState.AutoSignInSuccessfully(memberModel)
            }
        }
        catch (e: Exception) {
            _memberViewModelState.value = MemberViewModelState.Error(e.message)
        }
    }
}
