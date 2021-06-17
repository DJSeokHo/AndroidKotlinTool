package com.swein.androidkotlintool.framework.module.room.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.room.demo.database.AppDatabase
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.thread.ThreadUtility


/**
Room 包含 3 个主要组件：

数据库：包含数据库持有者，并作为应用已保留的持久关系型数据的底层连接的主要接入点。

使用 @Database 注释的类应满足以下条件：

是扩展 RoomDatabase 的抽象类。
在注释中添加与数据库关联的实体列表。
包含具有 0 个参数且返回使用 @Dao 注释的类的抽象方法。
在运行时，您可以通过调用 Room.databaseBuilder() 或 Room.inMemoryDatabaseBuilder() 获取 Database 的实例。

Entity：表示数据库中的表。

DAO：包含用于访问数据库的方法。
 */
class RoomDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_demo)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "DEMO_DB").build()

        ILog.debug("???", "${db.isOpen}")
        ILog.debug("???", "${db.openHelper.databaseName}")

        val currentDBPath = getDatabasePath("DEMO_DB").absolutePath
        ILog.debug("???", currentDBPath)

        ThreadUtility.startThread {

//            db.userDao().insertUserInfo(UserInfo("asd234", "name bbb", 20, "aaddrrreeessss bbb",
//                AddressInfo("street bbb", "city bbb", "post code 654321")))

            val userList = db.userDao().getAll()

            for(userInfo in userList) {
                ILog.debug("???", userInfo.toString())
            }
        }


//        copyFile()
    }

//    private fun copyFile() {
//        try {
//            val sd: File = Environment.getExternalStorageDirectory()
//            val data: File = Environment.getDataDirectory()
//            if (sd.canWrite()) {
//                val currentDBPath = getDatabasePath("DEMO_DB").absolutePath
//                val backupDBPath = "DEMO_DB"
//                //previous wrong  code
//                // **File currentDB = new File(data,currentDBPath);**
//                // correct code
//                val currentDB = File(currentDBPath)
//                val backupDB = File(sd, backupDBPath)
//                if (currentDB.exists()) {
//                    val src: FileChannel = FileInputStream(currentDB).channel
//                    val dst: FileChannel = FileOutputStream(backupDB).channel
//                    dst.transferFrom(src, 0, src.size())
//                    src.close()
//                    dst.close()
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
}