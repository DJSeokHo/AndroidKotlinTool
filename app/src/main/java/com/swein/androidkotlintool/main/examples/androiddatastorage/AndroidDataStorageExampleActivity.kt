package com.swein.androidkotlintool.main.examples.androiddatastorage

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.storage.StorageManager
import android.text.format.DateFormat
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.datastorage.AppCacheDataStorageUtil
import com.swein.androidkotlintool.framework.util.datastorage.AppInnerDataStorageUtil
import com.swein.androidkotlintool.framework.util.datastorage.ExternalDataStorageUtil
import com.swein.androidkotlintool.framework.util.datastorage.MediaDataStorageUtil
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil
import java.io.File
import java.util.*

class AndroidDataStorageExampleActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "AndroidDataStorageExampleActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_data_storage_example)

//        ILog.debug(TAG, ExternalDataStorageUtil.isExternalStorageWritable())
//        ILog.debug(TAG, ExternalDataStorageUtil.isExternalStorageReadable())
//
//
//        /*
//        如需访问其他位置，请调用 ContextCompat.getExternalFilesDirs()。如代码段中所示，返回数组中的第一个元素被视为主外部存储卷。除非该卷已满或不可用，否则请使用该卷。
//         */
//        val externalStorageVolumes: Array<out File> = ContextCompat.getExternalFilesDirs(applicationContext, null)
//        for (volume in externalStorageVolumes) {
//            ILog.debug(TAG, volume.absolutePath)
//            ILog.debug(TAG, volume.isFile)
//            ILog.debug(TAG, volume.isDirectory)
//        }
//
//        val primaryExternalStorage = externalStorageVolumes[0]
//        ILog.debug(TAG, primaryExternalStorage.absolutePath)
//        ILog.debug(TAG, primaryExternalStorage.isFile)
//        ILog.debug(TAG, primaryExternalStorage.isDirectory)
//        ILog.debug(TAG, primaryExternalStorage.list().size)
//
//        ILog.debug(TAG, externalCacheDir)
//        ILog.debug(TAG, getExternalFilesDirs(Environment.DIRECTORY_DCIM).size)
//        ILog.debug(TAG, getExternalFilesDirs(Environment.DIRECTORY_PICTURES).size)
//        ILog.debug(TAG, getExternalFilesDirs(Environment.DIRECTORY_DCIM)[0])
//        ILog.debug(TAG, getExternalFilesDirs(Environment.DIRECTORY_PICTURES)[0])
//
//        val content = "有时，分配内部存储分区作为外部存储空间的设备也会提供 SD 卡插槽。这意味着设备具有多个可能包含外部存储空间的物理卷，因此您需要选择用于应用专属存储空间的物理卷。\n" +
//                "\n" +
//                "如需访问其他位置，请调用 ContextCompat.getExternalFilesDirs()。如代码段中所示，返回数组中的第一个元素被视为主外部存储卷。除非该卷已满或不可用，否则请使用该卷。"
//        ExternalDataStorageUtil.saveTextFileIntoExternalStorage(this, "test", content, "1", "2", "3")
//        ILog.debug(TAG, ExternalDataStorageUtil.loadTextFileContentFromExternalStorage(this, "test", "1", "2", "3"))
//
//        ILog.debug(TAG, "log file name list ===========")
//        val fileNameList = ExternalDataStorageUtil.getFileNameListFromFolderInExternalStorage(this)
//        for (fileName in fileNameList) {
//            ILog.debug(TAG, fileName)
//        }
//
//        ILog.debug(TAG, "log file path list ===========")
//        val filePathList = ExternalDataStorageUtil.getFilePathListFromFolderInExternalStorage(this)
//        for (filePath in filePathList) {
//            val file = File(filePath)
//            ILog.debug(TAG, file.absolutePath)
//            ILog.debug(TAG, file.isFile)
//            ILog.debug(TAG, file.isDirectory)
//        }

        ThreadUtil.startThread {

            ThreadUtil.startUIThread(3000) {
                val fileName = "test_${DateFormat.format("yyyyMMddHHmmss", Date())}.jpg"
                val bitmap = window.decorView.rootView.drawToBitmap(Bitmap.Config.ARGB_8888)
                MediaDataStorageUtil.saveBitmapToPicturesDirectory(this, bitmap, fileName)
            }
        }

        ILog.debug(TAG, "log file name list ===========")
        val fileNameList = MediaDataStorageUtil.getFileNameListFromFolderInPicturesDirectory(this)
        for (fileName in fileNameList) {
            ILog.debug(TAG, fileName)
        }

        ILog.debug(TAG, "log file path list ===========")
        val filePathList = MediaDataStorageUtil.getFilePathListFromFolderInPicturesDirectory(this)
        for (filePath in filePathList) {
            val file = File(filePath)
            ILog.debug(TAG, file.absolutePath)
            ILog.debug(TAG, file.isFile)
            ILog.debug(TAG, file.isDirectory)
        }



    }

}