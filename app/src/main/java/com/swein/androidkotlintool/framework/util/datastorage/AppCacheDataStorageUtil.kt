package com.swein.androidkotlintool.framework.util.datastorage

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object AppCacheDataStorageUtil {

    fun saveTextFileIntoAppCacheStorage(context: Context, fileName: String, fileContent: String, vararg folders: String) {

        var folder = context.cacheDir

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

    fun loadTextFileContentFromAppCacheStorage(context: Context, fileName: String, vararg folders: String): String {

        var folder = context.cacheDir

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
    fun getFileNameListFromFolderInAppCacheStorage(context: Context, vararg folders: String): List<String> {

        var folder = context.cacheDir

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
    fun getFilePathListFromFolderInAppCacheStorage(context: Context, vararg folders: String): List<String> {

        var folder = context.cacheDir

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

    fun deleteFileFromFolderInAppCacheStorage(context: Context, fileName: String, vararg folders: String): Boolean {

        var folder = context.cacheDir

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
    fun deleteFolderInAppCacheStorage(context: Context, vararg folders: String): Boolean {

        var folder = context.cacheDir

        for (i in folders.indices) {

            folder = File(folder, folders[i])

            if (!folder.exists()) {
                return false
            }
        }

        if (folder.exists()) {
            return folder.delete()
        }

        return false
    }
}