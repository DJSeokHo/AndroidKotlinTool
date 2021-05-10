package com.swein.androidkotlintool.framework.module.firebase.demo.model

class ProductModel {

    companion object {

        const val CLOUD_DB_PATH = "PRODUCT_TABLE"

        const val UUID_KEY = "UUID"
        const val SHOP_MODEL_KEY = "SHOP_MODEL"
        const val AREA_KEY = "AREA" // same as ShopModel's area
        const val NAME_KEY = "NAME" // 제품 이름
        const val IMAGE_URL_KEY = "IMAGE_URL" // 제품 이미지
        const val IMAGE_FILE_CLOUD_PATH_KEY = "IMAGE_FILE_CLOUD_PATH" // 제품 이미지
        const val INFO_KEY = "INFO" // 제품 정보
        const val PRICE_KEY = "PRICE" // 제품 가격
        const val SALE_TIME_KEY = "SALE_TIME" // 판매 시간
        const val INVENTORY_KEY = "INVENTORY" // 제고 숫
        const val PICKER_LIST_KEY = "PICKER_LIST" // 예약 방문자 숫
        const val CREATE_DATE_KEY = "CREATE_DATE"
        const val MODIFY_DATE_KEY = "MODIFY_DATE"
        const val CREATE_BY_KEY = "CREATE_BY"
        const val MODIFY_BY_KEY = "MODIFY_BY"
    }

    var documentId = ""

    var uuId = ""
    var shopModel: ShopModel? = null
    var area = ""
    var name = ""
    var imageUrl = ""
    var imageFileCloudPath = ""
    var info = ""
    var price = 0.0
    var saleTime = ""
    var inventory = 0L
    var pickerList = mutableListOf<MutableMap<String, Any>>()
    var createDate = ""
    var modifyDate = ""
    var createBy = ""
    var modifyBy = ""

    fun to(): MutableMap<String, Any> {

        val map = mutableMapOf<String, Any>()

        map[UUID_KEY] = uuId
        map[SHOP_MODEL_KEY] = shopModel!!.to()
        map[AREA_KEY] = area
        map[NAME_KEY] = name
        map[IMAGE_URL_KEY] = imageUrl
        map[IMAGE_FILE_CLOUD_PATH_KEY] = imageFileCloudPath
        map[INFO_KEY] = info
        map[PRICE_KEY] = price
        map[SALE_TIME_KEY] = saleTime
        map[INVENTORY_KEY] = inventory
        map[PICKER_LIST_KEY] = pickerList
        map[CREATE_DATE_KEY] = createDate
        map[MODIFY_DATE_KEY] = modifyDate
        map[CREATE_BY_KEY] = createBy
        map[MODIFY_BY_KEY] = modifyBy

        return map
    }

    fun parsing(map: MutableMap<String, Any>) {
        uuId = map[UUID_KEY] as String
        area = map[AREA_KEY] as String
        name = map[NAME_KEY] as String
        imageUrl = map[IMAGE_URL_KEY] as String
        imageFileCloudPath = map[IMAGE_FILE_CLOUD_PATH_KEY] as String
        info = map[INFO_KEY] as String
        price = map[PRICE_KEY] as Double
        saleTime = map[SALE_TIME_KEY] as String
        inventory = map[INVENTORY_KEY] as Long
        pickerList = map[PICKER_LIST_KEY] as MutableList<MutableMap<String, Any>>
        createDate = map[CREATE_DATE_KEY] as String
        modifyDate = map[MODIFY_DATE_KEY] as String
        createBy = map[CREATE_BY_KEY] as String
        modifyBy = map[MODIFY_BY_KEY] as String

        shopModel = ShopModel().apply {
            this.parsing(map[SHOP_MODEL_KEY] as MutableMap<String, Any>)
        }
    }
}