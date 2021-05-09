package com.swein.androidkotlintool.framework.module.room.demo.relationdata

import androidx.room.*

@Entity(tableName = "USER_TABLE")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "UUID")
    val uuId: String,

    @ColumnInfo(name = "NAME")
    val name: String,

    @ColumnInfo(name = "AGE")
    val age: Int
)

@Entity(tableName = "LIBRARY_TABLE")
data class Library(
    @PrimaryKey
    @ColumnInfo(name = "UUID")
    val uuId: String,

    @ColumnInfo(name = "OWNER_UUID")
    val ownerUuId: String
)

@Entity
data class Playlist(
    @PrimaryKey
    @ColumnInfo(name = "UUID")
    val uuid: String,

    @ColumnInfo(name = "CREATOR_UUID")
    val creatorUuId: Long,

    @ColumnInfo(name = "PLAY_LIST_NAME")
    val playlistName: String
)

/**
定义一对一关系
两个实体之间的一对一关系是指这样一种关系：父实体的每个实例都恰好对应于子实体的一个实例，反之亦然。

例如，假设有一个音乐在线播放应用，用户在该应用中具有一个属于自己的歌曲库。每个用户只有一个库，而且每个库恰好对应于一个用户。因此，User 实体和 Library 实体之间就应存在一种一对一的关系。

首先，为您的两个实体分别创建一个类。其中一个实体必须包含一个变量，且该变量是对另一个实体的主键的引用。
 */
data class UserAndLibrary(

    @Embedded
    val user: User,

    @Relation(parentColumn = "UUID", entityColumn = "OWNER_UUID")
    val library: Library
)

/**
定义一对多关系
两个实体之间的一对多关系是指这样一种关系：父实体的每个实例对应于子实体的零个或多个实例，但子实体的每个实例只能恰好对应于父实体的一个实例。

在音乐在线播放应用示例中，假设用户可以将其歌曲整理到播放列表中。每个用户可以创建任意数量的播放列表，但每个播放列表只能由一个用户创建。因此，User 实体和 Playlist 实体之间应存在一种一对多关系。

首先，为您的两个实体分别创建一个类。与上个示例中一样，子实体必须包含一个变量，且该变量是对父实体的主键的引用。
 */
data class UserWithPlaylists(
    @Embedded
    val user: User,

    @Relation(parentColumn = "UUID", entityColumn = "CREATOR_UUID")
    val playlists: List<Playlist>
)