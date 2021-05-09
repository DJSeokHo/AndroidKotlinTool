package com.swein.androidkotlintool.framework.module.room.demo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.swein.androidkotlintool.framework.module.room.demo.dao.UserDao
import com.swein.androidkotlintool.framework.module.room.demo.entity.UserInfo

@Database(entities = [UserInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}