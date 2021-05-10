package com.swein.androidkotlintool.framework.module.firebase.demo.service

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.swein.androidkotlintool.framework.module.firebase.clouddatabase.CloudDBManager
import com.swein.androidkotlintool.framework.module.firebase.cloudstorage.CloudStorageManager
import com.swein.androidkotlintool.framework.module.firebase.demo.model.MemberModel
import com.swein.androidkotlintool.framework.module.firebase.demo.model.MemberSelfModel
import com.swein.androidkotlintool.framework.module.firebase.demo.model.ProductModel
import com.swein.androidkotlintool.framework.module.firebase.demo.model.ShopModel
import com.swein.androidkotlintool.framework.util.date.DateUtil
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil
import com.swein.androidkotlintool.framework.util.uuid.UUIDUtil

object ProductModelService {

    private const val TAG = "ProductModelService"

    fun uploadProduct(area: String, name: String,
                      info: String, price: Double, saleTime: String, inventory: Long,
                      productImagePath: String, shopModel: ShopModel,
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

                    createProductModel(uuId, area, name, info, price, saleTime, inventory,
                        productImageUrl, productImageFileCloudPath, shopModel, onSuccessResponse, onErrorResponse)

                }, {
                    onErrorResponse(null)
                    ILog.debug(TAG, "onFailure")
                })
        }
    }
    private fun createProductModel(uuId: String, area: String, name: String,
                                   info: String, price: Double, saleTime: String, inventory: Long,
                                   productImageUrl: String, productImageFileCloudPath: String, shopModel: ShopModel,
                                   onSuccessResponse: (documentReference: DocumentReference?, map: MutableMap<String, Any>) -> Unit,
                                   onErrorResponse: (e: Exception?) -> Unit) {

        val productModel = ProductModel()

        productModel.uuId = uuId
        productModel.area = area
        productModel.name = name
        productModel.imageUrl = productImageUrl
        productModel.imageFileCloudPath = productImageFileCloudPath
        productModel.info = info
        productModel.price = price
        productModel.saleTime = saleTime
        productModel.inventory = inventory
        productModel.pickerList.clear()

        val date = DateUtil.getCurrentDateTimeString()

        productModel.createDate = date
        productModel.modifyDate = date
        productModel.createBy = uuId
        productModel.modifyBy = uuId

        productModel.shopModel = shopModel

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

    fun updateProductModel(productModel: ProductModel, onSuccessResponse: (map: MutableMap<String, Any>) -> Unit,
                      onErrorResponse: (e: Exception?) -> Unit) {

        if (productModel.documentId == "") {
            ILog.debug(TAG, "need document id")
            return
        }

        ThreadUtil.startThread {

            ILog.debug(TAG, "updateProductModel ${productModel.documentId}")

            val date = DateUtil.getCurrentDateTimeString()
            productModel.modifyDate = date

            CloudDBManager.update(ProductModel.CLOUD_DB_PATH, productModel.documentId, productModel.to(), object : CloudDBManager.UpdateDelegate {

                override fun onSuccess(map: MutableMap<String, Any>) {
                    onSuccessResponse(map)
                }

                override fun onFailure(e: Exception?) {
                    onErrorResponse(e)
                }

            })

        }
    }

    fun deleteProduct(documentId: String, onSuccessResponse: (documentId: String) -> Unit,
                      onErrorResponse: (e: Exception?) -> Unit) {

        ThreadUtil.startThread {

            ILog.debug(TAG, "deleteProduct")

            CloudDBManager.delete(ProductModel.CLOUD_DB_PATH, documentId, object : CloudDBManager.DeleteDelegate {

                override fun onSuccess(documentId: String) {
                    onSuccessResponse(documentId)
                }

                override fun onFailure(e: Exception?) {
                    onErrorResponse(e)
                }

            })
        }
    }

    fun requestProductList(offset: DocumentSnapshot? = null, limit: Long = 10, area: String,
                           onSuccessResponse: (list: MutableList<MutableMap<String, Any>>, documentIdList: MutableList<String>, documentSnapshot: DocumentSnapshot?) -> Unit,
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
                    documentIdList: MutableList<String>,
                    documentSnapshot: DocumentSnapshot?
                ) {
                    onSuccessResponse(list, documentIdList, documentSnapshot)
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

    fun requestProductDetail(uuId: String,
                             onSuccessResponse: (list: MutableList<MutableMap<String, Any>>, documentIdList: MutableList<String>, documentSnapshot: DocumentSnapshot?) -> Unit,
                             onErrorResponse: (e: Exception?) -> Unit,
                             onEmptyResponse: () -> Unit) {

        ThreadUtil.startThread {

            ILog.debug(TAG, "requestProductDetail")

            val conditionMap = mutableMapOf<String, Any>()
            conditionMap[ProductModel.UUID_KEY] = uuId

            CloudDBManager.select(collectionPath = ProductModel.CLOUD_DB_PATH,
                limit = 1,
                orderByCol = ProductModel.MODIFY_DATE_KEY, conditionMap = conditionMap, delegate = object : CloudDBManager.SelectDelegate {
                    override fun onSuccess(
                        list: MutableList<MutableMap<String, Any>>,
                        documentIdList: MutableList<String>,
                        documentSnapshot: DocumentSnapshot?
                    ) {
                        onSuccessResponse(list, documentIdList, documentSnapshot)
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

    /**
     * uuId: 본인의 uuid
     * productModel: 픽업할 제품 object
     */
    fun requestPickup(productModel: ProductModel, onSuccessResponse: (map: MutableMap<String, Any>) -> Unit,
                      onErrorResponse: (e: Exception?) -> Unit) {

        MemberSelfModel.memberModel?.let {

            ThreadUtil.startThread {

                ILog.debug(TAG, "${it.uuId} requestPickup ${productModel.name}")

                val date = DateUtil.getCurrentDateTimeString()
                productModel.modifyDate = date

                var found = false

                for (picker in productModel.pickerList) {
                    if (picker[MemberModel.UUID_KEY] == it.uuId) {
                        found = true
                        break
                    }
                }

                if (!found) {
                    productModel.pickerList.add(it.to())
                }
                else {
                    // already request pick up, just return
                    ILog.debug(TAG, "already request pick up, just return")
                    onSuccessResponse(productModel.to())
                    return@startThread
                }

                CloudDBManager.update(ProductModel.CLOUD_DB_PATH, productModel.documentId, productModel.to(), object : CloudDBManager.UpdateDelegate {

                    override fun onSuccess(map: MutableMap<String, Any>) {
                        onSuccessResponse(map)
                    }

                    override fun onFailure(e: Exception?) {
                        onErrorResponse(e)
                    }

                })

            }

        }

    }

    fun saleFinished(productModel: ProductModel, onSuccessResponse: (map: MutableMap<String, Any>) -> Unit,
                     onErrorResponse: (e: Exception?) -> Unit) {

        ThreadUtil.startThread {

            ILog.debug(TAG, "saleFinished ${productModel.name}")

            val date = DateUtil.getCurrentDateTimeString()
            productModel.modifyDate = date

            productModel.inventory = 0

            CloudDBManager.update(ProductModel.CLOUD_DB_PATH, productModel.documentId, productModel.to(), object : CloudDBManager.UpdateDelegate {

                override fun onSuccess(map: MutableMap<String, Any>) {
                    onSuccessResponse(map)
                }

                override fun onFailure(e: Exception?) {
                    onErrorResponse(e)
                }

            })

        }

    }
}