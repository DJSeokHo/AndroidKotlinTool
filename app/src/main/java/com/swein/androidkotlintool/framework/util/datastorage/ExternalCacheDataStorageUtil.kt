package com.swein.androidkotlintool.framework.util.datastorage

import android.content.Context
import android.os.Environment
import androidx.core.content.ContextCompat
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.androiddatastorage.AndroidDataStorageExampleActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object ExternalCacheDataStorageUtil {

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    fun saveTextFileIntoExternalCacheStorage(context: Context, fileName: String, fileContent: String, vararg folders: String) {

        if (!isExternalStorageWritable()) {
            return
        }

        var folder = context.externalCacheDir

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

        val fileOutputStream = FileOutputStream(file)
        val byteArray = fileContent.toByteArray(charset("UTF-8"))
        fileOutputStream.write(byteArray, 0, byteArray.size)
        fileOutputStream.close()
    }

    fun loadTextFileContentFromExternalCacheStorage(context: Context, fileName: String, vararg folders: String): String {

        if (!isExternalStorageReadable()) {
            return ""
        }

        var folder = context.externalCacheDir

        for (i in folders.indices) {

            folder = File(folder, folders[i])

            if (!folder.exists()) {

                return ""
            }
        }

        val file = File(folder, fileName)
        if (!file.exists()) {
            return ""
        }

        val fileInputStream = FileInputStream(file)
        val byteArray = fileInputStream.readBytes()
        return String(byteArray, charset("UTF-8"))
    }

    /**
     * just file' name list
     */
    fun getFileNameListFromFolderInExternalCacheStorage(context: Context, vararg folders: String): List<String> {

        if (!isExternalStorageReadable()) {
            return listOf()
        }

        var folder = context.externalCacheDir

        for (i in folders.indices) {

            folder = File(folder, folders[i])

            if (!folder.exists()) {

                return listOf()
            }
        }

        folder?.let {
            return it.list().toList()
        } ?: run {
            return listOf()
        }
    }

    /**
     * file's path list
     */
    fun getFilePathListFromFolderInExternalCacheStorage(context: Context, vararg folders: String): List<String> {

        if (!isExternalStorageReadable()) {
            return listOf()
        }

        var folder = context.externalCacheDir

        for (i in folders.indices) {

            folder = File(folder, folders[i])

            if (!folder.exists()) {

                return listOf()
            }
        }

        folder?.let {

            val filePathList = mutableListOf<String>()
            val fileNameList = it.list()
            for (i in fileNameList.indices) {
                filePathList.add("${it.absolutePath}/${fileNameList[i]}")
            }

            return filePathList

        }

        return listOf()
    }


    fun deleteFileFromFolderInExternalCacheStorage(context: Context, fileName: String, vararg folders: String): Boolean {

        if (!isExternalStorageReadable()) {
            return false
        }

        if (fileName == "") {
            return false
        }

        var folder = context.externalCacheDir

        for (i in folders.indices) {

            folder = File(folder, folders[i])

            if (!folder.exists()) {
                return false
            }
        }

        val file = File(folder, fileName)
        if (file.exists()) {
            return file.delete()
        }

        return false
    }

    /**
     * if folder has file in it
     * then can not delete folder
     */
    fun deleteFolderInExternalCacheStorage(context: Context, vararg folders: String): Boolean {

        if (!isExternalStorageReadable()) {
            return false
        }

        if (folders.isEmpty()) {
            return false
        }

        var folder = context.externalCacheDir

        for (i in folders.indices) {

            folder = File(folder, folders[i])

            if (!folder.exists()) {
                return false
            }
        }

        folder?.let {

            if (it.exists()) {
                return it.delete()
            }
        }

        return false
    }
}