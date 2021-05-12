package com.swein.androidkotlintool.framework.util.datastorage

import android.content.Context
import android.os.Environment
import androidx.core.content.ContextCompat
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.androiddatastorage.AndroidDataStorageExampleActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object ExternalDataStorageUtil {

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    fun saveTextFileIntoExternalStorage(context: Context, fileName: String, fileContent: String, vararg folders: String) {

        if (!isExternalStorageWritable()) {
            return
        }

        val externalStorageVolumes: Array<out File> = ContextCompat.getExternalFilesDirs(context, null)
        if (externalStorageVolumes.isEmpty()) {
            return
        }

        var primaryExternalStorage = externalStorageVolumes[0]

        for (i in folders.indices) {

            primaryExternalStorage = File(primaryExternalStorage, folders[i])

            if (!primaryExternalStorage.exists()) {

                primaryExternalStorage.mkdir()
            }
        }

        val file = File(primaryExternalStorage, fileName)
        if (!file.exists()) {
            file.createNewFile()
        }

        val fileOutputStream = FileOutputStream(file)
        val byteArray = fileContent.toByteArray(charset("UTF-8"))
        fileOutputStream.write(byteArray, 0, byteArray.size)
        fileOutputStream.close()
    }

    fun loadTextFileContentFromExternalStorage(context: Context, fileName: String, vararg folders: String): String {

        if (!isExternalStorageReadable()) {
            return ""
        }

        val externalStorageVolumes: Array<out File> = ContextCompat.getExternalFilesDirs(context, null)
        if (externalStorageVolumes.isEmpty()) {
            return ""
        }

        var primaryExternalStorage = externalStorageVolumes[0]

        for (i in folders.indices) {

            primaryExternalStorage = File(primaryExternalStorage, folders[i])

            if (!primaryExternalStorage.exists()) {

                return ""
            }
        }

        val file = File(primaryExternalStorage, fileName)
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
    fun getFileNameListFromFolderInExternalStorage(context: Context, vararg folders: String): List<String> {

        if (!isExternalStorageReadable()) {
            return listOf()
        }

        val externalStorageVolumes: Array<out File> = ContextCompat.getExternalFilesDirs(context, null)
        if (externalStorageVolumes.isEmpty()) {
            return listOf()
        }

        var primaryExternalStorage = externalStorageVolumes[0]

        for (i in folders.indices) {

            primaryExternalStorage = File(primaryExternalStorage, folders[i])

            if (!primaryExternalStorage.exists()) {

                return listOf()
            }
        }

        return primaryExternalStorage.list().toList()
    }

    /**
     * file's path list
     */
    fun getFilePathListFromFolderInExternalStorage(context: Context, vararg folders: String): List<String> {

        if (!isExternalStorageReadable()) {
            return listOf()
        }

        val externalStorageVolumes: Array<out File> = ContextCompat.getExternalFilesDirs(context, null)
        if (externalStorageVolumes.isEmpty()) {
            return listOf()
        }

        var primaryExternalStorage = externalStorageVolumes[0]

        for (i in folders.indices) {

            primaryExternalStorage = File(primaryExternalStorage, folders[i])

            if (!primaryExternalStorage.exists()) {

                return listOf()
            }
        }

        val filePathList = mutableListOf<String>()
        val fileNameList = primaryExternalStorage.list()
        for (i in fileNameList.indices) {
            filePathList.add("${primaryExternalStorage.absolutePath}/${fileNameList[i]}")
        }

        return filePathList
    }


    fun deleteFileFromFolderInExternalStorage(context: Context, fileName: String, vararg folders: String): Boolean {

        if (!isExternalStorageReadable()) {
            return false
        }

        val externalStorageVolumes: Array<out File> = ContextCompat.getExternalFilesDirs(context, null)
        if (externalStorageVolumes.isEmpty()) {
            return false
        }

        if (fileName == "") {
            return false
        }

        var primaryExternalStorage = externalStorageVolumes[0]

        for (i in folders.indices) {

            primaryExternalStorage = File(primaryExternalStorage, folders[i])

            if (!primaryExternalStorage.exists()) {
                return false
            }
        }

        val file = File(primaryExternalStorage, fileName)
        if (file.exists()) {
            return file.delete()
        }

        return false
    }

    /**
     * if folder has file in it
     * then can not delete folder
     */
    fun deleteFolderInExternalStorage(context: Context, vararg folders: String): Boolean {

        if (!isExternalStorageReadable()) {
            return false
        }

        val externalStorageVolumes: Array<out File> = ContextCompat.getExternalFilesDirs(context, null)
        if (externalStorageVolumes.isEmpty()) {
            return false
        }

        if (folders.isEmpty()) {
            return false
        }

        var primaryExternalStorage = externalStorageVolumes[0]

        for (i in folders.indices) {

            primaryExternalStorage = File(primaryExternalStorage, folders[i])

            if (!primaryExternalStorage.exists()) {
                return false
            }
        }

        if (primaryExternalStorage.exists()) {
            return primaryExternalStorage.delete()
        }

        return false
    }
}