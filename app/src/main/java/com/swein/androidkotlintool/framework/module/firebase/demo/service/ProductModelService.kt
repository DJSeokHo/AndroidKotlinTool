package com.swein.androidkotlintool.framework.module.firebase.demo.service

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.swein.androidkotlintool.framework.module.firebase.clouddatabase.CloudDBManager
import com.swein.androidkotlintool.framework.module.firebase.demo.model.ProductModel
import com.swein.androidkotlintool.framework.util.log.ILog

object ProductModelService {

    private const val TAG = "ProductModelService"

    fun uploadProduct() {

    }

    fun modifyProduct() {

    }

    fun deleteProduct() {

    }

    fun requestProductList() {

    }

    fun requestProductDetail() {

    }


//    fun createProduct(productModel: ProductModel) {
//        CloudDBManager.insert(
//            ProductModel.CLOUD_DB_PATH,
//            productModel.to(),
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
//
//    fun updateProduct(documentId: String, productModel: ProductModel) {
//        CloudDBManager.update(ProductModel.CLOUD_DB_PATH, documentId, productModel.to(), object : CloudDBManager.UpdateDelegate {
//            override fun onSuccess(map: MutableMap<String, Any>) {
//                ILog.debug(TAG, "$map")
//            }
//
//            override fun onFailure(e: Exception?) {
//                ILog.debug(TAG, "${e?.message}")
//            }
//
//        })
//    }
//
//    fun deleteProduct(documentId: String) {
//        CloudDBManager.delete(ProductModel.CLOUD_DB_PATH, documentId, object : CloudDBManager.DeleteDelegate {
//            override fun onSuccess(documentId: String) {
//                ILog.debug(TAG, documentId)
//            }
//
//            override fun onFailure(e: Exception?) {
//                ILog.debug(TAG, "${e?.message}")
//            }
//
//        })
//    }
//
//    fun selectProductList(offset: DocumentSnapshot? = null, limit: Long = 10, conditionMap: MutableMap<String, Any>? = null) {
//
//        CloudDBManager.select(collectionPath = ProductModel.CLOUD_DB_PATH, offset = offset, limit = limit, orderByCol = ProductModel.MODIFY_DATE_KEY,
//            conditionMap = conditionMap,
//            delegate = object : CloudDBManager.SelectDelegate {
//            override fun onSuccess(
//                list: MutableList<MutableMap<String, Any>>,
//                documentSnapshot: DocumentSnapshot?
//            ) {
//                ILog.debug(TAG, "last is ${documentSnapshot?.data}")
//
//                for (item in list) {
//                    ILog.debug(TAG, "$item")
//                }
//            }
//
//            override fun onFailure(e: Exception?) {
//                ILog.debug(TAG, "${e?.message}")
//            }
//
//            override fun onEmpty() {
//                ILog.debug(TAG, "empty")
//            }
//
//        })
//    }
//
//    fun selectProduct(uuId: String) {
//        val mutableMap = mutableMapOf<String, Any>()
//        mutableMap[ProductModel.UUID_KEY] = uuId
//
//        CloudDBManager.select(collectionPath = ProductModel.CLOUD_DB_PATH, limit = 1, orderByCol = ProductModel.MODIFY_DATE_KEY, conditionMap = mutableMap, delegate = object : CloudDBManager.SelectDelegate {
//            override fun onSuccess(
//                list: MutableList<MutableMap<String, Any>>,
//                documentSnapshot: DocumentSnapshot?
//            ) {
//                ILog.debug(TAG, "last is ${documentSnapshot?.data}")
//
//                for (item in list) {
//                    ILog.debug(TAG, "$item")
//                }
//            }
//
//            override fun onFailure(e: Exception?) {
//                ILog.debug(TAG, "${e?.message}")
//            }
//
//            override fun onEmpty() {
//                ILog.debug(TAG, "empty")
//            }
//
//        })
//    }
}