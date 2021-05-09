package com.swein.androidkotlintool.framework.module.room.demo.entity

import androidx.room.ColumnInfo

data class NameAndAgeInfo(

    @ColumnInfo(name = "NAME")
    val name: String,

    @ColumnInfo(name = "AGE")
    val age: Int
)