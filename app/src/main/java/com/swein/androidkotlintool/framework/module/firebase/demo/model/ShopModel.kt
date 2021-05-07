package com.swein.androidkotlintool.framework.module.firebase.demo.model

class ShopModel {

    companion object {

        const val AREA_1 = "분당구"
        const val AREA_2 = "수정구"
        const val AREA_3 = "중원구"

        const val CLOUD_DB_PATH = "SHOP_TABLE"

        const val UUID_KEY = "UUID"
        const val OWNER_UUID_KEY = "OWNER_UUID"
        const val BUSINESS_NUMBER_KEY = "BUSINESS_NUMBER"
        const val NAME_KEY = "NAME"
        const val SHOP_NAME_KEY = "SHOP_NAME"
        const val BUSINESS_NAME_KEY = "BUSINESS_NAME"
        const val CONTACT_KEY = "CONTACT"
        const val AREA_KEY = "AREA"
        const val DETAIL_ADDRESS_KEY = "DETAIL_ADDRESS"
        const val LAT_KEY = "LAT"
        const val LNG_KEY = "LNG"
        const val INFO_KEY = "INFO"
        const val BUSINESS_HOUR_KEY = "BUSINESS_HOUR"
        const val SHOP_IMAGE_URL_KEY = "SHOP_IMAGE_URL"
        const val SHOP_IMAGE_FILE_CLOUD_PATH_KEY = "SHOP_IMAGE_FILE_CLOUD_PATH"
        const val BUSINESS_IMAGE_URL_KEY = "BUSINESS_IMAGE_URL"
        const val BUSINESS_IMAGE_FILE_CLOUD_PATH_KEY = "BUSINESS_IMAGE_FILE_CLOUD_PATH"
        const val READY_FOR_SALE_KEY = "READY_FOR_SALE"
        const val IS_OPEN_KEY = "IS_OPEN"
        const val CREATE_DATE_KEY = "CREATE_DATE"
        const val MODIFY_DATE_KEY = "MODIFY_DATE"
        const val CREATE_BY_KEY = "CREATE_BY"
        const val MODIFY_BY_KEY = "MODIFY_BY"
    }

    var documentId = ""

    var uuId = ""
    var ownerUuId = ""
    var businessNumber = ""
    var name = "" // 가게주인 이름
    var shopName = "" // 가게 이름
    var businessName = "" // 상호명
    var contact = ""
    var area = ""
    var detailAddress = ""
    var lat = 0.0
    var lng = 0.0
    var info = ""
    var businessHour = ""
    var shopImageUrl = "" // 가게이미지
    var shopImageFileCloudPath = ""
    var businessImageUrl = "" // 사업자 등록증 이미지
    var businessImageFileCloudPath = ""
    var readyForSale = 0L // 0: 심사중, 1: 정상, -1: 임시 금지
    var isOpen = 0L
    var createDate = ""
    var modifyDate = ""
    var createBy = ""
    var modifyBy = ""

    fun to(): MutableMap<String, Any> {

        val map = mutableMapOf<String, Any>()

        map[UUID_KEY] = uuId
        map[OWNER_UUID_KEY] = ownerUuId
        map[BUSINESS_NUMBER_KEY] = businessNumber
        map[NAME_KEY] = name
        map[SHOP_NAME_KEY] = shopName
        map[BUSINESS_NAME_KEY] = businessName
        map[CONTACT_KEY] = contact
        map[AREA_KEY] = area
        map[DETAIL_ADDRESS_KEY] = detailAddress
        map[LAT_KEY] = lat
        map[LNG_KEY] = lng
        map[INFO_KEY] = info
        map[BUSINESS_HOUR_KEY] = businessHour
        map[SHOP_IMAGE_URL_KEY] = shopImageUrl
        map[SHOP_IMAGE_FILE_CLOUD_PATH_KEY] = shopImageFileCloudPath
        map[BUSINESS_IMAGE_URL_KEY] = businessImageUrl
        map[BUSINESS_IMAGE_FILE_CLOUD_PATH_KEY] = businessImageFileCloudPath
        map[READY_FOR_SALE_KEY] = readyForSale
        map[IS_OPEN_KEY] = isOpen
        map[CREATE_DATE_KEY] = createDate
        map[MODIFY_DATE_KEY] = modifyDate
        map[CREATE_BY_KEY] = createBy
        map[MODIFY_BY_KEY] = modifyBy

        return map
    }

    fun parsing(map: MutableMap<String, Any>) {
        uuId = map[UUID_KEY] as String
        ownerUuId = map[OWNER_UUID_KEY] as String
        businessNumber = map[BUSINESS_HOUR_KEY] as String
        name = map[NAME_KEY] as String
        shopName = map[SHOP_NAME_KEY] as String
        businessName = map[BUSINESS_NAME_KEY] as String
        contact = map[CONTACT_KEY] as String
        area = map[AREA_KEY] as String
        detailAddress = map[DETAIL_ADDRESS_KEY] as String
        lat = map[LAT_KEY] as Double
        lng = map[LNG_KEY] as Double
        info = map[INFO_KEY] as String
        businessNumber = map[BUSINESS_NUMBER_KEY] as String
        shopImageUrl = map[SHOP_IMAGE_URL_KEY] as String
        shopImageFileCloudPath = map[SHOP_IMAGE_FILE_CLOUD_PATH_KEY] as String
        businessImageUrl = map[BUSINESS_IMAGE_URL_KEY] as String
        businessImageFileCloudPath = map[BUSINESS_IMAGE_FILE_CLOUD_PATH_KEY] as String
        readyForSale = map[READY_FOR_SALE_KEY] as Long
        isOpen = map[IS_OPEN_KEY] as Long
        createDate = map[CREATE_DATE_KEY] as String
        modifyDate = map[MODIFY_DATE_KEY] as String
        createBy = map[CREATE_BY_KEY] as String
        modifyBy = map[MODIFY_BY_KEY] as String
    }
}