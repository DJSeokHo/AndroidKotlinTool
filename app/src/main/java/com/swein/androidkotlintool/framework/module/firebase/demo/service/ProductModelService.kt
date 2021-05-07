package com.swein.androidkotlintool.framework.module.firebase.demo.service

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.swein.androidkotlintool.framework.module.firebase.clouddatabase.CloudDBManager
import com.swein.androidkotlintool.framework.module.firebase.cloudstorage.CloudStorageManager
import com.swein.androidkotlintool.framework.module.firebase.demo.model.MemberModel
import com.swein.androidkotlintool.framework.module.firebase.demo.model.ProductModel
import com.swein.androidkotlintool.framework.util.date.DateUtil
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil
import com.swein.androidkotlintool.framework.util.uuid.UUIDUtil

object ProductModelService {

    private const val TAG = "ProductModelService"

    fun uploadProduct(shopUuId: String, area: String, name: String,
                      info: String, price: Double, saleTime: String, inventory: Long,
                      productImagePath: String,
                      onSuccessResponse: (documentReference: DocumentReference?, map: MutableMap<String, Any>) -> Unit,
                      onErrorResponse: (e: Exception?) -> Unit) {

        ThreadUtil.startThread {

            ILog.debug(TAG, "uploadProduct")

            val uuId = UUIDUtil.getUUIDString()
            val productImageFileCloudPath = "${CloudStorageManager.PRODUCT_IMAGE_FOLDER}product_${uuId}.jpg"

            CloudStorageManager.uploadImage(
                CloudStorageManager.FILE_STORAGE_DOMAIN, productImagePath,
                productImageFileCloudPath,
                CloudStorageManager.CloudStorageFileType.FILE, { productImageUrl ->

                    createProductModel(uuId, shopUuId, area, name,
                        info, price, saleTime, inventory,
                        productImageUrl, productImageFileCloudPath, onSuccessResponse, onErrorResponse)

                }, {
                    onErrorResponse(null)
                    ILog.debug(TAG, "onFailure")
                })
        }
    }
    private fun createProductModel(uuId: String, shopUuId: String, area: String, name: String,
                                   info: String, price: Double, saleTime: String, inventory: Long,
                                   productImageUrl: String, productImageFileCloudPath: String,
                                   onSuccessResponse: (documentReference: DocumentReference?, map: MutableMap<String, Any>) -> Unit,
                                   onErrorResponse: (e: Exception?) -> Unit) {

        val productModel = ProductModel()

        productModel.uuId = uuId
        productModel.shopUuId = shopUuId
        productModel.area = area
        productModel.name = name
        productModel.imageUrl = productImageUrl
        productModel.imageFileCloudPath = productImageFileCloudPath
        productModel.info = info
        productModel.price = price
        productModel.saleTime = saleTime
        productModel.inventory = inventory
        productModel.pickerNumber = 0

        val date = DateUtil.getCurrentDateTimeString()

        productModel.createDate = date
        productModel.modifyDate = date
        productModel.createBy = uuId
        productModel.modifyBy = uuId

        CloudDBManager.insert(ProductModel.CLOUD_DB_PATH, productModel.to(), object : CloudDBManager.InsertDelegate {
            override fun onSuccess(
                documentReference: DocumentReference?,
                map: MutableMap<String, Any>
            ) {
                onSuccessResponse(documentReference, map)
            }

            override fun onFailure(e: Exception?) {
                onErrorResponse(e)
            }

        })
    }

    fun modifyProduct() {

    }

    fun deleteProduct() {

    }

    fun requestProductList(offset: DocumentSnapshot? = null, limit: Long = 10, area: String,
                           onSuccessResponse: (list: MutableList<MutableMap<String, Any>>, documentSnapshot: DocumentSnapshot?) -> Unit,
                           onErrorResponse: (e: Exception?) -> Unit,
                           onEmptyResponse: () -> Unit) {

        ThreadUtil.startThread {

            ILog.debug(TAG, "requestProductList")

            val conditionMap = mutableMapOf<String, Any>()
            conditionMap[ProductModel.AREA_KEY] = area

            CloudDBManager.select(collectionPath = ProductModel.CLOUD_DB_PATH,
                offset = offset, limit = limit,
                orderByCol = ProductModel.MODIFY_DATE_KEY, conditionMap = conditionMap, delegate = object : CloudDBManager.SelectDelegate {
                override fun onSuccess(
                    list: MutableList<MutableMap<String, Any>>,
                    documentSnapshot: DocumentSnapshot?
                ) {
                    onSuccessResponse(list, documentSnapshot)
                }

                override fun onFailure(e: Exception?) {
                    onErrorResponse(e)
                }

                override fun onEmpty() {
                    onEmptyResponse()
                }
            })
        }
    }

    fun requestProductDetail() {

    }

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