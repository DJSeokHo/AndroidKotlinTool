package com.swein.androidkotlintool.framework.module.room.demo.dao

import androidx.room.*
import com.swein.androidkotlintool.framework.module.room.demo.entity.NameAndAgeInfo
import com.swein.androidkotlintool.framework.module.room.demo.entity.UserInfo

/**
 * DAO：包含用于访问数据库的方法。
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM USER_TABLE")
    fun getAll(): List<UserInfo>

    @Query("SELECT * FROM USER_TABLE WHERE UID IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<UserInfo>

    @Query("SELECT * FROM USER_TABLE WHERE AGE > :minAge")
    fun loadAllUsersOlderThan(minAge: Int): Array<UserInfo>

//    @Query("SELECT * FROM USER_TABLE WHERE AGE > :minAge AND AGE < :maxAge")
    @Query("SELECT * FROM USER_TABLE WHERE AGE BETWEEN :minAge AND :maxAge")
    fun loadAllUsersBetweenAges(minAge: Int, maxAge: Int): Array<UserInfo>

    @Query("SELECT * FROM USER_TABLE WHERE NAME LIKE :name LIMIT 1")
    fun findByName(name: String): UserInfo

    /**
     * 大多数情况下，您只需获取实体的几个字段。例如，您的界面可能仅显示用户的名字和姓氏，而不是用户的每一条详细信息。
     * 通过仅提取应用界面中显示的列，您可以节省宝贵的资源，并且您的查询也能更快完成。
     */
    @Query("SELECT NAME, AGE FROM USER_TABLE")
    fun loadNameAgeAgeOnly(): Array<NameAndAgeInfo>

    @Query("SELECT * FROM USER_TABLE WHERE NAME IN (:regions)")
    fun loadUserFromNameRegion(regions: Array<String>): Array<UserInfo>

    @Insert
    fun insertAll(vararg userInfoArray: UserInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserInfo(userInfo: UserInfo)

//    @Delete
//    fun delete(userInfo: UserInfo): Int
//
//    @Delete // 虽然通常没有必要，但是您可以让此方法返回一个 int 值，以指示从数据库中删除的行数。
//    fun deleteUsers(vararg userInfoArray: UserInfo): Int

    @Update // 虽然通常没有必要，但是您可以让此方法返回一个 int 值，以指示从数据库中删除的行数。
    fun updateUsers(vararg userInfoArray: UserInfo)
}