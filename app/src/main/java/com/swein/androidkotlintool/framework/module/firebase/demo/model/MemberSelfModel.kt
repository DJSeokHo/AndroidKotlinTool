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

    fun parsingMemberModel(documentId: String, map: MutableMap<String, Any>) {

        if (memberModel == null) {
            memberModel = MemberModel()
        }

        memberModel!!.parsing(map)
        memberModel!!.documentId = documentId
        SharedPreferencesUtil.putValue("uuId", memberModel!!.uuId)
    }

    fun parsingMemberModel(map: MutableMap<String, Any>) {

        memberModel?.let {
            it.parsing(map)
            SharedPreferencesUtil.putValue("uuId", it.uuId)
        }
    }

    fun parsingShopModel(documentId: String, map: MutableMap<String, Any>) {

        if (shopModel == null) {
            shopModel = ShopModel()
        }

        shopModel!!.parsing(map)
        shopModel!!.documentId = documentId
    }

    fun parsingShopModel(map: MutableMap<String, Any>) {

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