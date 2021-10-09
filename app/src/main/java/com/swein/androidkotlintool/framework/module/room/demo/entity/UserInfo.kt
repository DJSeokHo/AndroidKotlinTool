package com.swein.androidkotlintool.framework.module.room.demo.entity

import androidx.room.*

/**
 * Entity：表示数据库中的表。
 */

/*
如果您的应用需要通过全文搜索 (FTS) 快速访问数据库信息，请使用虚拟表（使用 FTS3 或 FTS4 SQLite 扩展模块）为您的实体提供支持。
如需使用 Room 2.1.0 及更高版本中提供的这项功能，请将 @Fts3 或 @Fts4 注释添加到给定实体
Use `@Fts3` only if your app has strict disk space requirements or if you
require compatibility with an older SQLite version.
 */
//@Fts4
//@Entity // 默认类名作为表名
@Entity(tableName = "USER_TABLE")
//@Entity(primaryKeys = ["uid", "name"])
data class UserInfo(

//    // 启用 FTS 的表始终使用 INTEGER 类型的主键且列名称为“rowid”。如果是由 FTS 表支持的实体定义主键，则必须使用相应的类型和列名称。
//    @PrimaryKey @ColumnInfo(name = "rowid") val id: Int,

    @PrimaryKey
    @ColumnInfo(name = "UID")
    var uid: String = "",

    @ColumnInfo(name = "NAME")
    var name: String = "",

    @ColumnInfo(name = "AGE")
    var age: Int = 0,

    @ColumnInfo(name = "ADDRESS")
    var address: String = "",

    // Room 会为实体中定义的每个字段创建一个列。如果某个实体中有您不想保留的字段，则可以使用 @Ignore 为这些字段添加注释
//    @Ignore
//    val imageBitmap: Bitmap?,

    /**
     * 例如，您的 User 类可以包含一个 Address 类型的字段，它表示名为 street、city、state 和 postCode 的字段的组合。
     * 若要在表中单独存储组合列，请在 User 类（带有 @Embedded 注释）中添加 Address 字段
     * 然后，表示 User 对象的表将包含具有以下名称的列：uid、name、age, address, street, state, city 和 post_code。
     */
    @Embedded
    var addressInfo: AddressInfo = AddressInfo()

)

/**
如果实体继承了父实体的字段，则使用 @Entity 属性的 ignoredColumns 属性通常会更容易：

open class User {
    var picture: Bitmap? = null
}

@Entity(ignoredColumns = arrayOf("picture"))
data class RemoteUser(
    @PrimaryKey val id: Int,
    val hasVpn: Boolean
) : User()
*/