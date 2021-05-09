package com.swein.androidkotlintool.framework.module.room.demo.relationdata

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface RelationDAOExample {

    @Transaction
    @Query("SELECT * FROM USER_TABLE")
    fun getUsersAndLibraries(): List<UserAndLibrary>

    @Transaction
    @Query("SELECT * FROM USER_TABLE")
    fun getUsersWithPlaylists(): List<UserWithPlaylists>

}