package com.swein.androidkotlintool.framework.module.firebase.demo.service

import com.google.firebase.firestore.DocumentReference
import com.swein.androidkotlintool.framework.module.firebase.clouddatabase.CloudDBManager
import com.swein.androidkotlintool.framework.module.firebase.demo.model.ShopModel
import com.swein.androidkotlintool.framework.util.log.ILog

object ShopModelService {

    private const val TAG = "ShopModelService"

//    fun createShop(shopModel: ShopModel) {
//        CloudDBManager.insert(ShopModel.CLOUD_DB_PATH, shopModel.to(),
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

    fun requestShopDetail() {

    }

}