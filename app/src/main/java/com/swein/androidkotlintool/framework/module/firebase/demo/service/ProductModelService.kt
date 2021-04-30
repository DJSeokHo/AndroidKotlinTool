package com.swein.androidkotlintool.framework.module.firebase.demo.service

import com.swein.androidkotlintool.framework.module.firebase.clouddatabase.CloudDBManager
import com.swein.androidkotlintool.framework.module.firebase.demo.model.ProductModel

object ProductModelService {

    fun createProduct(productModel: ProductModel) {
        CloudDBManager.insert(ProductModel.CLOUD_DB_PATH, productModel.to())
    }

    fun updateProduct(documentId: String, productModel: ProductModel) {
        CloudDBManager.update(ProductModel.CLOUD_DB_PATH, documentId, productModel.to())
    }

    fun deleteProduct(documentId: String) {
        CloudDBManager.delete(ProductModel.CLOUD_DB_PATH, documentId)
    }
}