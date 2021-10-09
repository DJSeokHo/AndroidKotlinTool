package com.swein.androidkotlintool.framework.module.room.demo.entity

import androidx.room.ColumnInfo

data class AddressInfo(

    @ColumnInfo(name = "STREET")
    var street: String = "",

    @ColumnInfo(name = "CITY")
    var city: String = "",

    @ColumnInfo(name = "POST_CODE")
    var postCode: String = ""
)