package com.swein.androidkotlintool.framework.util.datastorage

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


/**
从内部存储空间访问
对于每个应用，系统都会在内部存储空间中提供目录，应用可以在该存储空间中整理其文件。一个目录专为应用的持久性文件而设计，而另一个目录包含应用的缓存文件。您的应用不需要任何系统权限即可读取和写入这些目录中的文件。

其他应用无法访问存储在内部存储空间中的文件。这使得内部存储空间非常适合存储其他应用不应访问的应用数据。

但是，请注意，这些目录的空间通常比较小。在将应用专属文件写入内部存储空间之前，应用应查询设备上的可用空间。

访问持久性文件
应用的普通持久性文件位于您可以使用上下文对象的 filesDir 属性访问的目录中。此框架提供了多种方法帮助您在此目录中访问和存储文件。

访问和存储文件
您可以使用 File API 访问和存储文件：
 */
object AppInnerDataStorageUtil {

    fun saveTextFileIntoAppInnerStorage(context: Context, fileName: String, fileContent: String, vararg folders: String) {

        var folder = context.filesDir

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

    fun loadTextFileContentFromAppInnerStorage(context: Context, fileName: String, vararg folders: String): String {

        var folder = context.filesDir

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
    fun getFileNameListFromFolderInAppInnerStorage(context: Context, vararg folders: String): List<String> {

        var folder = context.filesDir

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
    fun getFilePathListFromFolderInAppInnerStorage(context: Context, vararg folders: String): List<String> {

        var folder = context.filesDir

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

    fun deleteFileFromFolderInAppInnerStorage(context: Context, fileName: String, vararg folders: String): Boolean {

        var folder = context.filesDir

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
    fun deleteFolderInAppInnerStorage(context: Context, vararg folders: String): Boolean {

        var folder = context.filesDir

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