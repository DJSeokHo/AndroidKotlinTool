package com.swein.androidkotlintool.framework.module.firebase.demo.service

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.swein.androidkotlintool.framework.module.firebase.clouddatabase.CloudDBManager
import com.swein.androidkotlintool.framework.module.firebase.cloudstorage.CloudStorageManager
import com.swein.androidkotlintool.framework.module.firebase.demo.model.MemberSelfModel
import com.swein.androidkotlintool.framework.module.firebase.demo.model.ShopModel
import com.swein.androidkotlintool.framework.util.date.DateUtil
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil
import com.swein.androidkotlintool.framework.util.uuid.UUIDUtil

object ShopModelService {

    private const val TAG = "ShopModelService"

    fun checkIsShopExist(uuid: String, onSuccessResponse: (list: MutableList<MutableMap<String, Any>>, documentSnapshot: DocumentSnapshot?) -> Unit,
                         onErrorResponse: (e: Exception?) -> Unit, onEmptyResponse: () -> Unit) {
        ThreadUtil.startThread {

            ILog.debug(TAG, "checkIsShopExist")
            val conditionMap = mutableMapOf<String, Any>()
            conditionMap[ShopModel.OWNER_UUID_KEY] = uuid

            CloudDBManager.select(ShopModel.CLOUD_DB_PATH, limit = 1, conditionMap = conditionMap, delegate = object : CloudDBManager.SelectDelegate {
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

    fun registerBusiness(ownerUuId: String, businessNumber: String, name: String, shopName: String, businessName: String, contact: String, area: String, detailAddress: String,
                         lat: Double, lng: Double, info: String, businessHour: String, shopImagePath: String, businessImagePath: String,
                         onSuccessResponse: (documentReference: DocumentReference?, map: MutableMap<String, Any>) -> Unit,
                         onErrorResponse: (e: Exception?) -> Unit) {

        ThreadUtil.startThread {

            ILog.debug(TAG, "registerBusiness")

            val uuId = UUIDUtil.getUUIDString()
            val shopImageFileCloudPath = "${CloudStorageManager.SHOP_PROFILE_IMAGE_FOLDER}shop_${ownerUuId}_${uuId}.jpg"
            val businessImageFileCloudPath = "${CloudStorageManager.SHOP_BUSINESS_IMAGE_FOLDER}shop_${ownerUuId}_${uuId}.jpg"

            ILog.debug(TAG, "upload shop image")
            // upload shop image
            CloudStorageManager.uploadImage(
                CloudStorageManager.FILE_STORAGE_DOMAIN, shopImagePath,
                shopImageFileCloudPath,
                CloudStorageManager.CloudStorageFileType.FILE, { shopImageUrl ->

                    ILog.debug(TAG, "upload shop image success")

                    ILog.debug(TAG, "upload business image")
                    // upload business image after shop image uploaded
                    CloudStorageManager.uploadImage(
                        CloudStorageManager.FILE_STORAGE_DOMAIN, businessImagePath,
                        businessImageFileCloudPath,
                        CloudStorageManager.CloudStorageFileType.FILE, { businessImageUrl ->

                            ILog.debug(TAG, "upload business image success")
                            // shop image and business image uploaded
                            createShopModel(uuId, ownerUuId, businessNumber, name, shopName, businessName, contact, area, detailAddress, lat, lng, info, businessHour, shopImageUrl,
                                shopImageFileCloudPath, businessImageUrl, businessImageFileCloudPath, onSuccessResponse, onErrorResponse)

                        }, {
                            onErrorResponse(null)
                            ILog.debug(TAG, "onFailure")
                        })

                }, {
                    onErrorResponse(null)
                    ILog.debug(TAG, "onFailure")
                })

        }
    }
    private fun createShopModel(uuId: String, ownerUuId: String, businessNumber: String, name: String, shopName: String, businessName: String, contact: String, area: String, detailAddress: String,
                                lat: Double, lng: Double, info: String, businessHour: String, shopImageUrl: String, shopImageFileCloudPath: String,
                                businessImageUrl: String, businessImageFileCloudPath: String,
                                onSuccessResponse: (documentReference: DocumentReference?, map: MutableMap<String, Any>) -> Unit,
                                onErrorResponse: (e: Exception?) -> Unit) {

        ILog.debug(TAG, "createShopModel")

        val shopModel = ShopModel()
        shopModel.uuId = uuId
        shopModel.ownerUuId = ownerUuId
        shopModel.businessNumber = businessNumber
        shopModel.name = name
        shopModel.shopName = shopName
        shopModel.businessName = businessName
        shopModel.contact = contact
        shopModel.area = area
        shopModel.detailAddress = detailAddress
        shopModel.lat = lat
        shopModel.lng = lng
        shopModel.info = info
        shopModel.businessHour = businessHour
        shopModel.shopImageUrl = shopImageUrl // 가게 이미지
        shopModel.shopImageFileCloudPath = shopImageFileCloudPath // 가게 이미지 경록
        shopModel.businessImageUrl = businessImageUrl // 사업자 등록증 이미지
        shopModel.businessImageFileCloudPath = businessImageFileCloudPath // 사업자 등록증 이미지 경록
        shopModel.readyForSale = 0L
        shopModel.isOpen = 0L

        val date = DateUtil.getCurrentDateTimeString()
        shopModel.createDate = date
        shopModel.modifyDate = date
        shopModel.createBy = ownerUuId
        shopModel.modifyBy = ownerUuId

        CloudDBManager.insert(ShopModel.CLOUD_DB_PATH, shopModel.to(), object : CloudDBManager.InsertDelegate {
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

    fun requestShopInfo(ownerUuId: String, onSuccessResponse: (list: MutableList<MutableMap<String, Any>>, documentSnapshot: DocumentSnapshot?) -> Unit,
                        onErrorResponse: (e: Exception?) -> Unit, onEmptyResponse: () -> Unit) {

        ThreadUtil.startThread {

            ILog.debug(TAG, "requestShopInfo")

            val conditionMap = mutableMapOf<String, Any>()
            conditionMap[ShopModel.OWNER_UUID_KEY] = ownerUuId
            CloudDBManager.select(ShopModel.CLOUD_DB_PATH, limit = 1, conditionMap = conditionMap, delegate = object : CloudDBManager.SelectDelegate {
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

    // 가게정보 변경, 다시 심사
    fun modifyBusiness(memberSelfModel: MemberSelfModel, shopImagePath: String, businessImagePath: String,
                       onSuccessResponse: (map: MutableMap<String, Any>) -> Unit,
                       onErrorResponse: (e: Exception?) -> Unit) {

        memberSelfModel.shopModel?.let {

            ThreadUtil.startThread {

                if (shopImagePath == "" && businessImagePath == "") {

                    // no image change
                    ILog.debug(TAG, "no image change")
                    updateShopModel(memberSelfModel, it.shopImageUrl, it.businessImageUrl, onSuccessResponse, onErrorResponse)
                    return@startThread
                }

                if (shopImagePath != "" && businessImagePath == "") {

                    // change only shop image
                    ILog.debug(TAG, "change only shop image")
                    CloudStorageManager.uploadImage(CloudStorageManager.FILE_STORAGE_DOMAIN, shopImagePath,
                        it.shopImageFileCloudPath,
                        CloudStorageManager.CloudStorageFileType.FILE, { shopImageUrl ->

                            // shop image and business image uploaded
                            updateShopModel(memberSelfModel, shopImageUrl, it.businessImageUrl, onSuccessResponse, onErrorResponse)

                        }, {
                            onErrorResponse(null)
                            ILog.debug(TAG, "onFailure")
                        })

                    return@startThread
                }

                if (shopImagePath == "" && businessImagePath != "") {
                    // change only business image
                    ILog.debug(TAG, "change only business image")
                    CloudStorageManager.uploadImage(CloudStorageManager.FILE_STORAGE_DOMAIN, businessImagePath,
                        it.businessImageFileCloudPath,
                        CloudStorageManager.CloudStorageFileType.FILE, { businessImageUrl ->

                            // shop image and business image uploaded
                            updateShopModel(memberSelfModel, it.shopImageUrl, businessImageUrl, onSuccessResponse, onErrorResponse)

                        }, {
                            onErrorResponse(null)
                            ILog.debug(TAG, "onFailure")
                        })

                    return@startThread
                }

                // change shop and business image
                ILog.debug(TAG, "change shop and business image")
                // upload shop image
                CloudStorageManager.uploadImage(CloudStorageManager.FILE_STORAGE_DOMAIN, shopImagePath,
                    it.shopImageFileCloudPath,
                    CloudStorageManager.CloudStorageFileType.FILE, { shopImageUrl ->

                        // upload business image after shop image uploaded
                        CloudStorageManager.uploadImage(CloudStorageManager.FILE_STORAGE_DOMAIN, businessImagePath,
                            it.businessImageFileCloudPath,
                            CloudStorageManager.CloudStorageFileType.FILE, { businessImageUrl ->

                                // shop image and business image uploaded
                                updateShopModel(memberSelfModel, shopImageUrl, businessImageUrl, onSuccessResponse, onErrorResponse)

                            }, {
                                onErrorResponse(null)
                                ILog.debug(TAG, "onFailure")
                            })

                    }, {
                        onErrorResponse(null)
                        ILog.debug(TAG, "onFailure")
                    })

            }

        }
    }
    private fun updateShopModel(memberSelfModel: MemberSelfModel, shopImageUrl: String, businessImageUrl: String,
                                onSuccessResponse: (map: MutableMap<String, Any>) -> Unit,
                                onErrorResponse: (e: Exception?) -> Unit) {

        memberSelfModel.shopModel?.let {

            it.shopImageUrl = shopImageUrl // 가게 이미지
            it.businessImageUrl = businessImageUrl // 사업자 등록증 이미지
            it.readyForSale = 0L
            it.isOpen = 0L

            val date = DateUtil.getCurrentDateTimeString()
            it.modifyDate = date

            CloudDBManager.update(ShopModel.CLOUD_DB_PATH, it.documentId, it.to(), object : CloudDBManager.UpdateDelegate {

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