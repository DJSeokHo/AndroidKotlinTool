package com.swein.androidkotlintool.framework.module.room.example.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "IDEA_INFO_TABLE")
data class IdeaInfoEntity(

    @PrimaryKey
    @ColumnInfo(name = "UUID")
    val uuId: String,

    @ColumnInfo(name = "NAME")
    val name: String,

    @ColumnInfo(name = "TITLE")
    val title: String,

    @ColumnInfo(name = "INFO")
    val info: String,

    @ColumnInfo(name = "DATE")
    val date: String
) {
}
