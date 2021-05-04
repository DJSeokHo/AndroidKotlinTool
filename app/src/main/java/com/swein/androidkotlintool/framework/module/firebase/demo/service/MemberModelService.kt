package com.swein.androidkotlintool.framework.module.firebase.demo.service

import com.google.firebase.firestore.DocumentReference
import com.swein.androidkotlintool.framework.module.firebase.clouddatabase.CloudDBManager
import com.swein.androidkotlintool.framework.module.firebase.demo.model.MemberModel
import com.swein.androidkotlintool.framework.util.log.ILog

object MemberModelService {

    private const val TAG = "MemberModelService"

//    fun createMember(memberModel: MemberModel) {
//        CloudDBManager.insert(MemberModel.CLOUD_DB_PATH, memberModel.to(),
//            object : CloudDBManager.InsertDelegate {
//                override fun onSuccess(
//                    documentReference: DocumentReference?,
//                    map: MutableMap<String, Any>
//                ) {
//                    ILog.debug(TAG, "$documentReference $map")
//                }
//
//                override fun onFailure(e: Exception?) {
//                    ILog.debug(TAG, "${e?.message}")
//                }
//            })
//    }

    fun registerMember() {

    }

    fun registerBusiness() {

    }

    fun login(providerId: String, provider: String) {

    }

    fun login(secretTokenKey: String) {

    }

    fun requestMemberProfile() {

    }

    fun modifyMemberProfile() {

    }

}