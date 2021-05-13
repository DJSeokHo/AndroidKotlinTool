package com.swein.androidkotlintool.framework.util.datastorage

import android.content.Context
import android.os.Environment
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception

object SHDataStorageUtil {

    enum class StorageType {
        APP_INNER_DATA, APP_INNER_CACHE, EXTERNAL_DATA, EXTERNAL_CACHE
    }

    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    fun createFile(context: Context, storageType: StorageType, fileName: String, vararg folders: String): File {

        // select root folder
        var folder: File = selectFolder(context, storageType)

        // create child folder
        for (i in folders.indices) {

            folder = File(folder, folders[i])

            if (!folder.exists()) {

                folder.mkdir()
            }
        }

        // create file
        val file = File(folder, fileName)
        if (!file.exists()) {
            file.createNewFile()
        }

        return file
    }

    fun storeTextIntoFile(context: Context, storageType: StorageType, fileName: String, fileContent: String, vararg folders: String): Boolean {

        try {

            // select root folder
            var folder: File = selectFolder(context, storageType)

            // create child folder
            for (i in folders.indices) {

                folder = File(folder, folders[i])

                if (!folder.exists()) {

                    folder.mkdir()
                }
            }

            // create file
            val file = File(folder, fileName)
            if (!file.exists()) {
                file.createNewFile()
            }

            // write file
            val fileOutputStream = FileOutputStream(file)
            val byteArray = fileContent.toByteArray(charset("UTF-8"))
            fileOutputStream.write(byteArray, 0, byteArray.size)
            fileOutputStream.close()

            return true
        }
        catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun loadTextFromFile(context: Context, storageType: StorageType, fileName: String, vararg folders: String): String {

        // select root folder
        var folder: File = selectFolder(context, storageType)

        // find folder
        for (i in folders.indices) {

            folder = File(folder, folders[i])

            if (!folder.exists()) {

                return ""
            }
        }

        // find file
        val file = File(folder, fileName)
        if (!file.exists()) {
            return ""
        }

        // get file's content
        val fileInputStream = FileInputStream(file)
        val byteArray = fileInputStream.readBytes()
        return String(byteArray, charset("UTF-8"))
    }

    /**
     * just file' name list
     */
    fun getFileNameListFromFolder(context: Context, storageType: StorageType, vararg folders: String): List<String> {

        // select root folder
        var folder: File = selectFolder(context, storageType)

        // get child
        for (i in folders.indices) {

            folder = File(folder, folders[i])

            if (!folder.exists()) {

                return listOf()
            }
        }

        return (folder.list() as Array<String>).toList()
    }

    /**
     * file's path list
     */
    fun getFilePathListFromFolder(context: Context, storageType: StorageType, vararg folders: String): List<String> {

        // select root folder
        var folder: File = selectFolder(context, storageType)

        // get child
        for (i in folders.indices) {

            folder = File(folder, folders[i])

            if (!folder.exists()) {

                return listOf()
            }
        }

        val filePathList = mutableListOf<String>()
        val fileNameList = folder.list() as Array<String>
        for (i in fileNameList.indices) {
            filePathList.add("${folder.absolutePath}/${fileNameList[i]}")
        }

        return filePathList
    }

    fun deleteFileFromFolder(context: Context, storageType: StorageType, fileName: String, vararg folders: String): Boolean {

        // select root folder
        var folder: File = selectFolder(context, storageType)

        // find file
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
    fun deleteFolder(context: Context, storageType: StorageType, vararg folders: String): Boolean {

        // select root folder
        var folder: File = selectFolder(context, storageType)

        // find folder
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

    private fun selectFolder(context: Context, storageType: StorageType): File {

        return when (storageType) {
            StorageType.APP_INNER_DATA -> {
                context.filesDir
            }
            StorageType.APP_INNER_CACHE -> {
                context.cacheDir
            }
            StorageType.EXTERNAL_DATA -> {

                if (!isExternalStorageWritable()) {
                    context.filesDir
                } else {
                    val externalStorageVolumes: Array<out File> =
                        ContextCompat.getExternalFilesDirs(context, null)
                    if (externalStorageVolumes.isEmpty()) {
                        context.filesDir
                    } else {
                        // primaryExternalStorage
                        externalStorageVolumes[0]
                    }
                }
            }
            StorageType.EXTERNAL_CACHE -> {

                if (!isExternalStorageWritable()) {
                    context.filesDir
                } else {
                    context.externalCacheDir ?: context.cacheDir
                }
            }
        }
    }
}