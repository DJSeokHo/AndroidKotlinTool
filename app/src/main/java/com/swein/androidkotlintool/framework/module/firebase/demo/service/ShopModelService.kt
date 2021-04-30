package com.swein.androidkotlintool.framework.module.firebase.demo.service

import com.swein.androidkotlintool.framework.module.firebase.clouddatabase.CloudDBManager
import com.swein.androidkotlintool.framework.module.firebase.demo.model.ShopModel

object ShopModelService {

    fun createShop(shopModel: ShopModel) {
        CloudDBManager.insert(ShopModel.CLOUD_DB_PATH, shopModel.to())
    }

}