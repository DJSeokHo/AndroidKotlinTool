//package com.swein.androidkotlintool.framework.module.room.example.controller.db
//
//import com.swein.androidkotlintool.framework.module.room.example.database.DatabaseManager
//import com.swein.androidkotlintool.framework.module.room.example.model.IdeaModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//
//object DBController {
//
//    suspend fun insert(ideaModel: IdeaModel) = withContext(Dispatchers.IO) {
//        // insert on io thread
//        return@withContext DatabaseManager.insert(ideaModel.toDataClass())
//    }
//
////    suspend fun deleteAll() = withContext(Dispatchers.IO) {
////        return@withContext DatabaseManager.deleteAll()
////    }
//
//    suspend fun loadLatest() = withContext(Dispatchers.IO) {
////        return@withContext DatabaseManager.loadNew()
//    }
//}