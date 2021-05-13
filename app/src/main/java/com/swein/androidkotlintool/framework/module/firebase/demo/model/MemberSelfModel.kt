package com.swein.androidkotlintool.framework.module.firebase.demo.model

import com.swein.androidkotlintool.framework.util.preferences.SharedPreferencesUtil

object MemberSelfModel {

    var uuId = ""

    var memberModel: MemberModel? = null
    var shopModel: ShopModel? = null

    var isLogin = false

    fun initWhenAppStart() {
        uuId = SharedPreferencesUtil.getValue("uuId", "")
    }

    fun parsingMemberModel(documentId: String = "", map: MutableMap<String, Any>) {

        if (documentId != "") {

            if (memberModel == null) {
                memberModel = MemberModel()
            }

            memberModel?.documentId = documentId
        }

        memberModel?.parsing(map)

        SharedPreferencesUtil.putValue("uuId", memberModel!!.uuId)
    }

    fun parsingShopModel(documentId: String = "", map: MutableMap<String, Any>) {

        if (documentId != "") {

            if (shopModel == null) {
                shopModel = ShopModel()
            }

            shopModel?.documentId = documentId
        }

        shopModel?.parsing(map)

    }

    fun clear() {

        SharedPreferencesUtil.putValue("uuId", "")

        uuId = ""
        isLogin = false

        memberModel = null
        shopModel = null
    }
}