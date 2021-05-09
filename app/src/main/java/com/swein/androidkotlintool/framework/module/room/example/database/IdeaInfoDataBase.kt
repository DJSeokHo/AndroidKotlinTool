package com.swein.androidkotlintool.framework.module.room.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.swein.androidkotlintool.framework.module.room.example.model.dao.IdeaInfoDao
import com.swein.androidkotlintool.framework.module.room.example.model.entity.IdeaInfoEntity

@Database(entities = [IdeaInfoEntity::class], version = 1)
abstract class IdeaInfoDataBase : RoomDatabase() {
    abstract fun ideaInfoDao(): IdeaInfoDao
}