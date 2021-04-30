package com.swein.androidkotlintool.framework.module.firebase.demo.service

import com.swein.androidkotlintool.framework.module.firebase.clouddatabase.CloudDBManager
import com.swein.androidkotlintool.framework.module.firebase.demo.model.MemberModel

object MemberModelService {

    fun createMember(memberModel: MemberModel) {
        CloudDBManager.insert(MemberModel.CLOUD_DB_PATH, memberModel.to())
    }

}