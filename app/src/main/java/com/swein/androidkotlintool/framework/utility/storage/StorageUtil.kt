package com.swein.androidkotlintool.framework.utility.storage

import android.os.Environment
import org.json.JSONObject
import java.io.*

class StorageUtil {

    /**
     * add these permission at manifests first
     *
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
     * <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
     */

    companion object {

        fun deleteDir(pPath: String) {
            val dir = File(pPath)
            deleteDirWithFile(dir)
        }

        fun deleteDirWithFile(dir: File?) {

            if (dir == null || !dir.exists() || !dir.isDirectory) {
                return
            }

            for (file in dir.listFiles()!!) {

                if (file.isFile) {
                    file.delete()
                } else if (file.isDirectory) {
                    deleteDirWithFile(file)
                }
            }
            dir.delete()
        }

        fun createExternalStorageDirectoryFolder(folderName: String, rootPath: String): String {

            val fileDirectory = File("$rootPath/$folderName")

            if (!fileDirectory.exists()) {
                fileDirectory.mkdir()
            }

            return "$rootPath/$folderName"

        }

        fun writeExternalStorageDirectoryFileWithContentAddtion(filePath: String, fileName: String, content: String) {

            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {

                val file = File(filePath + fileName)

                if (!file.exists()) {

                    try {
                        file.createNewFile()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }

                try {
                    var randomAccessFile: RandomAccessFile? = null
                    randomAccessFile = RandomAccessFile(file, "rw")
                    randomAccessFile.seek(file.length())
                    randomAccessFile.write(content.toByteArray())
                    randomAccessFile.close()
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        fun writeExternalStorageDirectoryFileWithContent(filePath: String, fileName: String, content: String) {

            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {

                val file = File(filePath + fileName)

                if (!file.exists()) {

                    try {
                        file.createNewFile()
                    }
                    catch (e: IOException) {
                        e.printStackTrace()
                    }

                }

                try {
                    val fileOutputStream = FileOutputStream(file)
                    fileOutputStream.write(content.toByteArray())
                    fileOutputStream.close()
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        fun writeExternalStorageDirectoryFileWithJSONObject(
            filePath: String,
            fileName: String,
            jsonObject: JSONObject
        ) {

            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {

                val file = File(filePath, fileName)

                if (!file.exists()) {
                    try {
                        file.createNewFile()
                    }
                    catch (e: IOException) {
                        e.printStackTrace()
                    }

                }

                try {
                    val fileOutputStream = FileOutputStream(file)
                    fileOutputStream.write(jsonObject.toString().toByteArray(charset("UTF-8")))
                    fileOutputStream.close()
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun readExternalStorageDirectoryFile(filePath: String, fileName: String): String {

            val fileInputStream: FileInputStream
            val stringBuilder = StringBuilder("")

            try {

                fileInputStream = FileInputStream(filePath + fileName)
                val bufferedReader = BufferedReader(InputStreamReader(fileInputStream))

                val string: String
                string = bufferedReader.readLine()
                while (string != null) {
                    stringBuilder.append(string)
                }

                bufferedReader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return stringBuilder.toString()

        }

        fun readExternalStorageDirectoryFileJSONObject(filePath: String, fileName: String): JSONObject? {

            var jsonObject: JSONObject? = null

            try {

                val file = File(filePath, fileName)
                val fileInputStream = FileInputStream(file)
                val inputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader = BufferedReader(inputStreamReader)

                val line: String
                val stringBuilder = StringBuilder()

                line = bufferedReader.readLine()

                while (line != null) {
                    stringBuilder.append(line)
                }

                fileInputStream.close()
                inputStreamReader.close()
                bufferedReader.close()

                jsonObject = JSONObject(stringBuilder.toString())

            }
            catch (e: Exception) {
                e.printStackTrace()
            }

            return jsonObject
        }
    }
}