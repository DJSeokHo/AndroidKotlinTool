package com.swein.androidkotlintool.framework.module.firebase.demo.model

class ProductModel {

    companion object {

        const val CLOUD_DB_PATH = "PRODUCT_TABLE"

        const val UUID_KEY = "UUID"
        const val SHOP_UUID_KEY = "SHOP_UUID"
        const val NAME_KEY = "NAME"
        const val IMAGE_URL_KEY = "IMAGE_URL"
        const val INFO_KEY = "INFO"
        const val PRICE_KEY = "PRICE"
        const val SALE_TIME_KEY = "SALE_TIME"
        const val INVENTORY_KEY = "INVENTORY"
        const val PICKER_NUMBER_KEY = "PICKER_NUMBER"
        const val CREATE_DATE_KEY = "CREATE_DATE"
        const val MODIFY_DATE_KEY = "MODIFY_DATE"
        const val CREATE_BY_KEY = "CREATE_BY"
        const val MODIFY_BY_KEY = "MODIFY_BY"
    }

    var uuId = ""
    var shopUuId = ""
    var name = ""
    var imageUrl = ""
    var info = ""
    var price = 0.0
    var saleTime = ""
    var inventory = 0
    var pickerNumber = 0
    var createDate = ""
    var modifyDate = ""
    var createBy = ""
    var modifyBy = ""

    fun to(): MutableMap<String, Any> {

        val map = mutableMapOf<String, Any>()

        map[UUID_KEY] = uuId
        map[SHOP_UUID_KEY] = shopUuId
        map[NAME_KEY] = name
        map[IMAGE_URL_KEY] = imageUrl
        map[INFO_KEY] = info
        map[PRICE_KEY] = price
        map[SALE_TIME_KEY] = saleTime
        map[INVENTORY_KEY] = inventory
        map[PICKER_NUMBER_KEY] = pickerNumber
        map[CREATE_DATE_KEY] = createDate
        map[MODIFY_DATE_KEY] = modifyDate
        map[CREATE_BY_KEY] = createBy
        map[MODIFY_BY_KEY] = modifyBy

        return map
    }

    fun parsing(map: MutableMap<String, Any>) {
        uuId = map[UUID_KEY] as String
        shopUuId = map[SHOP_UUID_KEY] as String
        name = map[NAME_KEY] as String
        imageUrl = map[IMAGE_URL_KEY] as String
        info = map[INFO_KEY] as String
        price = map[PRICE_KEY] as Double
        saleTime = map[SALE_TIME_KEY] as String
        inventory = map[INVENTORY_KEY] as Int
        pickerNumber = map[PICKER_NUMBER_KEY] as Int
        createDate = map[CREATE_DATE_KEY] as String
        modifyDate = map[MODIFY_DATE_KEY] as String
        createBy = map[CREATE_BY_KEY] as String
        modifyBy = map[MODIFY_BY_KEY] as String
    }
}