package com.swein.androidkotlintool.framework.module.room.example.database

import android.content.Context
import androidx.room.Room
import com.swein.androidkotlintool.framework.module.room.example.model.entity.IdeaInfoEntity

object DatabaseManager {

    lateinit var database: IdeaInfoDataBase
    fun init(context: Context, name: String) {
        database = Room.databaseBuilder(context, IdeaInfoDataBase::class.java, name).build()
    }

    suspend fun insert(ideaInfoEntity: IdeaInfoEntity) {
        return database.ideaInfoDao().insertIdeaInfo(ideaInfoEntity)
    }

    suspend fun deleteAll() {
        return database.ideaInfoDao().deleteAll()
    }

    suspend fun loadNew(): IdeaInfoEntity? {
        return database.ideaInfoDao().loadNewIdeaInfo()
    }
}