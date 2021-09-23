package com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.service

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.CloudFireStoreWrapper
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.model.MemberModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object MemberModelService {

    private const val TAG = "MemberModelService"

    suspend fun register(memberModel: MemberModel): Void = withContext(Dispatchers.IO) {
        return@withContext CloudFireStoreWrapper.replace(MemberModel.CLOUD_FIRE_STORE_PATH, memberModel.uuId, memberModel.to())
    }

    suspend fun signIn(id: String, password: String): QuerySnapshot = withContext(Dispatchers.IO) {
        val map = mutableMapOf<String, Any>()
        map[MemberModel.ID_KEY] = id
        map[MemberModel.PASSWORD_KEY] = password
        return@withContext CloudFireStoreWrapper.select(MemberModel.CLOUD_FIRE_STORE_PATH, map)
    }

//    fun checkIsMemberExist(provider: String, providerId: String,
//                           onSuccessResponse: (list: MutableList<MutableMap<String, Any>>, documentIdList: MutableList<String>, documentSnapshot: DocumentSnapshot?) -> Unit,
//                           onErrorResponse: (e: Exception?) -> Unit, onEmptyResponse: () -> Unit) {
//        ThreadUtility.startThread {
//
//            ILog.debug(TAG, "checkIsMemberExist")
//            val conditionMap = mutableMapOf<String, Any>()
//            conditionMap[MemberModel.PROVIDER_ID_KEY] = providerId
//            conditionMap[MemberModel.PROVIDER_KEY] = provider
//
//            CloudDBManager.select(MemberModel.CLOUD_DB_PATH, limit = 1, conditionMap = conditionMap, delegate = object : CloudDBManager.SelectDelegate {
//                override fun onSuccess(
//                    list: MutableList<MutableMap<String, Any>>,
//                    documentIdList: MutableList<String>,
//                    documentSnapshot: DocumentSnapshot?
//                ) {
//                    onSuccessResponse(list, documentIdList, documentSnapshot)
//                }
//
//                override fun onFailure(e: Exception?) {
//                    onErrorResponse(e)
//                }
//
//                override fun onEmpty() {
//                    onEmptyResponse()
//                }
//
//            })
//        }
//    }
//
//    fun registerMember(provider: String, providerId: String, nickname: String, email: String, pushToken: String, profileImagePath: String,
//                       onSuccessResponse: (documentReference: DocumentReference?, map: MutableMap<String, Any>) -> Unit,
//                       onErrorResponse: (e: Exception?) -> Unit) {
//
//        ThreadUtility.startThread {
//
//            ILog.debug(TAG, "registerMember")
//
//            val uuId = UUIDUtil.getUUIDString()
//            val profileImageFileCloudPath = "${CloudStorageManager.MEMBER_PROFILE_IMAGE_FOLDER}member_${provider}_${uuId}.jpg"
//            CloudStorageManager.uploadImage(CloudStorageManager.FILE_STORAGE_DOMAIN, profileImagePath,
//                profileImageFileCloudPath,
//                CloudStorageManager.CloudStorageFileType.FILE, { imageUrl ->
//                    createMemberModel(uuId, provider, providerId, nickname, email, pushToken, imageUrl, profileImageFileCloudPath, onSuccessResponse, onErrorResponse)
//                }, {
//                    onErrorResponse(null)
//                    ILog.debug(TAG, "onFailure")
//                })
//        }
//
//    }
//    private fun createMemberModel(uuId: String, provider: String, providerId: String, nickname: String, email: String, pushToken: String,
//                                  profileImageUrl: String, profileImageFileCloudPath: String,
//                                  onSuccessResponse: (documentReference: DocumentReference?, map: MutableMap<String, Any>) -> Unit,
//                                  onErrorResponse: (e: Exception?) -> Unit) {
//
//        val memberModel = MemberModel()
//
//        memberModel.uuId = uuId
//        memberModel.loginSecretToken = ""
//        memberModel.memberId = ""
//        memberModel.email = email
//        memberModel.providerId = providerId
//        memberModel.provider = provider
//        memberModel.phone = ""
//        memberModel.name = ""
//        memberModel.birth = ""
//        memberModel.gender = -1 // 1: male, 0: female
//        memberModel.nickname = nickname
//        memberModel.profileImageUrl = profileImageUrl
//        memberModel.profileImageFileCloudPath = profileImageFileCloudPath
//        memberModel.pushToken = pushToken
//
//        val date = DateUtility.getCurrentDateTimeString()
//        memberModel.createDate = date
//        memberModel.modifyDate = date
//        memberModel.createBy = uuId
//        memberModel.modifyBy = uuId
//
//        CloudDBManager.insert(MemberModel.CLOUD_DB_PATH, memberModel.to(), object : CloudDBManager.InsertDelegate {
//            override fun onSuccess(
//                documentReference: DocumentReference?,
//                map: MutableMap<String, Any>
//            ) {
//                onSuccessResponse(documentReference, map)
//            }
//
//            override fun onFailure(e: Exception?) {
//                onErrorResponse(e)
//            }
//
//        })
//    }
//
//
//    fun loginSNS(provider: String, providerId: String,
//                 onSuccessResponse: (list: MutableList<MutableMap<String, Any>>, documentIdList: MutableList<String>, documentSnapshot: DocumentSnapshot?) -> Unit,
//              onErrorResponse: (e: Exception?) -> Unit, onEmptyResponse: () -> Unit) {
//        ThreadUtility.startThread {
//
//            ILog.debug(TAG, "login")
//            val conditionMap = mutableMapOf<String, Any>()
//            conditionMap[MemberModel.PROVIDER_ID_KEY] = providerId
//            conditionMap[MemberModel.PROVIDER_KEY] = provider
//
//            CloudDBManager.select(MemberModel.CLOUD_DB_PATH, limit = 1, conditionMap = conditionMap, delegate = object : CloudDBManager.SelectDelegate {
//                override fun onSuccess(
//                    list: MutableList<MutableMap<String, Any>>,
//                    documentIdList: MutableList<String>,
//                    documentSnapshot: DocumentSnapshot?
//                ) {
//                    onSuccessResponse(list, documentIdList, documentSnapshot)
//                }
//
//                override fun onFailure(e: Exception?) {
//                    onErrorResponse(e)
//                }
//
//                override fun onEmpty() {
//                    onEmptyResponse()
//                }
//            })
//        }
//    }
//
//    fun loginSecretToken(secretTokenKey: String, nickname: String,
//                         onSuccessResponse: (list: MutableList<MutableMap<String, Any>>, documentIdList: MutableList<String>, documentSnapshot: DocumentSnapshot?) -> Unit,
//              onErrorResponse: (e: Exception?) -> Unit, onEmptyResponse: () -> Unit) {
//        ThreadUtility.startThread {
//
//            ILog.debug(TAG, "login")
//            val conditionMap = mutableMapOf<String, Any>()
//            conditionMap[MemberModel.LOGIN_SECRET_TOKEN_KEY] = secretTokenKey
//            conditionMap[MemberModel.NICKNAME_KEY] = nickname
//
//            CloudDBManager.select(MemberModel.CLOUD_DB_PATH, limit = 1, conditionMap = conditionMap, delegate = object : CloudDBManager.SelectDelegate {
//                override fun onSuccess(
//                    list: MutableList<MutableMap<String, Any>>,
//                    documentIdList: MutableList<String>,
//                    documentSnapshot: DocumentSnapshot?
//                ) {
//                    onSuccessResponse(list, documentIdList, documentSnapshot)
//                }
//
//                override fun onFailure(e: Exception?) {
//                    onErrorResponse(e)
//                }
//
//                override fun onEmpty() {
//                    onEmptyResponse()
//                }
//
//            })
//
//        }
//    }
//
//    fun requestMemberInfo(uuId: String,
//                          onSuccessResponse: (list: MutableList<MutableMap<String, Any>>, documentIdList: MutableList<String>, documentSnapshot: DocumentSnapshot?) -> Unit,
//                          onErrorResponse: (e: Exception?) -> Unit, onEmptyResponse: () -> Unit) {
//
//        ThreadUtility.startThread {
//
//            ILog.debug(TAG, "requestMemberInfo")
//
//            val conditionMap = mutableMapOf<String, Any>()
//            conditionMap[MemberModel.UUID_KEY] = uuId
//            CloudDBManager.select(MemberModel.CLOUD_DB_PATH, limit = 1, conditionMap = conditionMap, delegate = object : CloudDBManager.SelectDelegate {
//                override fun onSuccess(
//                    list: MutableList<MutableMap<String, Any>>,
//                    documentIdList: MutableList<String>,
//                    documentSnapshot: DocumentSnapshot?
//                ) {
//                    onSuccessResponse(list, documentIdList, documentSnapshot)
//                }
//
//                override fun onFailure(e: Exception?) {
//                    onErrorResponse(e)
//                }
//
//                override fun onEmpty() {
//                    onEmptyResponse()
//                }
//
//            })
//        }
//    }
//
//    fun updateMemberProfile(memberSelfModel: MemberSelfModel,
//                            onSuccessResponse: (map: MutableMap<String, Any>) -> Unit,
//                            onErrorResponse: (e: Exception?) -> Unit) {
//
//        memberSelfModel.memberModel?.let {
//
//            ThreadUtility.startThread {
//
//                ILog.debug(TAG, "updateMemberProfile")
//
//                it.modifyDate = DateUtility.getCurrentDateTimeString()
//
//                CloudDBManager.update(MemberModel.CLOUD_DB_PATH, it.documentId, it.to(), object : CloudDBManager.UpdateDelegate {
//                    override fun onSuccess(documentId: String, map: MutableMap<String, Any>) {
//                        onSuccessResponse(map)
//                    }
//
//                    override fun onFailure(e: Exception?) {
//                        onErrorResponse(e)
//                    }
//
//                })
//
//            }
//
//        }
//
//    }

}