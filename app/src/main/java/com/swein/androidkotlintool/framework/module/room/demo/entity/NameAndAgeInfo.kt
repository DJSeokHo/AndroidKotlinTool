package com.swein.androidkotlintool.framework.module.room.demo.entity

import androidx.room.ColumnInfo

data class NameAndAgeInfo(

    @ColumnInfo(name = "NAME")
    var name: String = "",

    @ColumnInfo(name = "AGE")
    var age: Int = 0
)