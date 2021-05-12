package com.swein.androidkotlintool.framework.util.datastorage

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import androidx.core.content.ContextCompat
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.androiddatastorage.AndroidDataStorageExampleActivity
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object MediaDataStorageUtil {

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    fun saveBitmapToPicturesDirectory(context: Context, bitmap: Bitmap, fileName: String, vararg folders: String) {

        if (!isExternalStorageWritable()) {
            return
        }

        val dirs = context.getExternalFilesDirs(Environment.DIRECTORY_PICTURES)
        if (dirs.isEmpty()) {
            return
        }

        var folder = dirs[0]

        for (i in folders.indices) {

            folder = File(folder, folders[i])

            if (!folder.exists()) {

                folder.mkdir()
            }
        }

        val file = File(folder, fileName)
        if (!file.exists()) {
            file.createNewFile()
        }

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)

        val fileOutputStream = FileOutputStream(file)
        val byteArray = byteArrayOutputStream.toByteArray()
        fileOutputStream.write(byteArray, 0, byteArray.size)
        fileOutputStream.close()

        MediaScannerConnection.scanFile(context, arrayOf(file.toString()), null, null)
    }


    /**
     * just file' name list
     */
    fun getFileNameListFromFolderInPicturesDirectory(context: Context, vararg folders: String): List<String> {

        if (!ExternalDataStorageUtil.isExternalStorageReadable()) {
            return listOf()
        }

        val dirs = context.getExternalFilesDirs(Environment.DIRECTORY_PICTURES)
        if (dirs.isEmpty()) {
            return listOf()
        }

        var folder = dirs[0]

        for (i in folders.indices) {

            folder = File(folder, folders[i])

            if (!folder.exists()) {

                return listOf()
            }
        }

        return folder.list().toList()
    }

    /**
     * file's path list
     */
    fun getFilePathListFromFolderInPicturesDirectory(context: Context, vararg folders: String): List<String> {

        if (!ExternalDataStorageUtil.isExternalStorageReadable()) {
            return listOf()
        }

        val dirs = context.getExternalFilesDirs(Environment.DIRECTORY_PICTURES)
        if (dirs.isEmpty()) {
            return listOf()
        }

        var folder = dirs[0]

        for (i in folders.indices) {

            folder = File(folder, folders[i])

            if (!folder.exists()) {

                return listOf()
            }
        }

        val filePathList = mutableListOf<String>()
        val fileNameList = folder.list()
        for (i in fileNameList.indices) {
            filePathList.add("${folder.absolutePath}/${fileNameList[i]}")
        }

        return filePathList
    }
}