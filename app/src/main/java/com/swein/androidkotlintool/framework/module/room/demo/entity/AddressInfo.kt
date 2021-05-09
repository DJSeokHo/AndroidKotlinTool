package com.swein.androidkotlintool.framework.module.room.demo.entity

import androidx.room.ColumnInfo

data class AddressInfo(

    @ColumnInfo(name = "STREET")
    val street: String,

    @ColumnInfo(name = "CITY")
    val city: String,

    @ColumnInfo(name = "POST_CODE")
    val postCode: String
) {
    constructor(): this("", "", "")
}