package com.swein.androidkotlintool.framework.util.storage

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import com.swein.androidkotlintool.framework.util.log.ILog
import java.io.*
import java.nio.channels.FileChannel
import java.text.SimpleDateFormat

class FileIOUtil {

    companion object {
        fun getPathFromUri(context: Context, uri: Uri): String {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor!!.moveToNext()
            val path = cursor!!.getString(cursor!!.getColumnIndex("_data"))
            cursor!!.close()
            return path
        }


        fun getOutputMediaFileUri(type: Int): Uri {
            return Uri.fromFile(getOutputMediaFile(type))
        }

        private fun getOutputMediaFile(type: Int): File? {
            // To be safe, you should check that the SDCard is mounted
            // using Environment.getExternalStorageState() before doing this.

            val mediaStorageDir = File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM
                ), "Camera"
            )
            // This location works best if you want the created images to be shared
            // between applications and persist after your app has been uninstalled.

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null
                }
            }

            // Create a media file name
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(java.util.Date())
            val mediaFile: File
            if (type == 1) {
                mediaFile = File(
                    mediaStorageDir.path + File.separator +
                            "" + timeStamp + ".jpg"
                )
            } else {
                return null
            }

            return mediaFile
        }

        /**
         * get image file with path
         * @param path
         * @return
         */
        fun getFileFromPath(path: String): File {

            return File(path)

        }

        /**
         * get path of file
         * @param file
         * @return
         */
        fun getPathFromImageFile(file: File): String {

            return file.absolutePath

        }

        /**
         * copy file of directory (function A + function B used)
         * @param fromPath
         * @param toPath
         */
        fun DirectoryFileCopy(fromPath: String, toPath: String) {
            DirectoryCopy(fromPath, toPath)
        }

        @Throws(Exception::class)
        private fun getByte(context: Context, uri: Uri?): ByteArray? {

            val buf: ByteArray
            var videoBytes: ByteArray? = null

            if (uri != null) {

                val byteArrayOutputStream = ByteArrayOutputStream()
                val fis = getFileInputStream(context, uri)

                buf = ByteArray(1024)
                val n: Int = fis!!.read(buf)
                while (-1 != n) {
                    byteArrayOutputStream.write(buf, 0, n)
                }

                videoBytes = byteArrayOutputStream.toByteArray()
            }
            return videoBytes
        }

        /**
         * file to byte
         * @param file
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun getByte(file: File?): ByteArray? {
            var bytes: ByteArray? = null
            if (file != null) {
                val inputStream = FileInputStream(file)
                val length = file.length().toInt()
                if (length > Integer.MAX_VALUE) {
                    Log.e(FileIOUtil::class.java.name, "this file is max")
                    return null
                }
                bytes = ByteArray(length)
                var offset = 0
                val numRead = inputStream.read(bytes, offset, bytes.size - offset)

                while (offset < bytes.size && numRead >= 0) {
                    offset += numRead
                }

                if (offset < bytes.size) {
                    ILog.debug(FileIOUtil::class.java.name, "file length is error")
                    return null
                }
                inputStream.close()
            }
            return bytes
        }

        /**
         * function A
         * @param fromPath
         * @param toPath
         * @return
         */
        fun DirectoryCopy(fromPath: String, toPath: String): Int {
            //target dir
            val currentFiles: Array<File>?
            val root = File(fromPath)

            //check SD card and File
            //not exists than return out
            if (!root.exists()) {
                return -1
            }

            //if exists than get
            currentFiles = root.listFiles()

            //target dir
            val targetDir = File(toPath)

            //create dir
            if (!targetDir.exists()) {

                targetDir.mkdirs()

            }

            for (i in currentFiles!!.indices) {
                if (currentFiles!![i].isDirectory) {
                    //if sub directory then enter
                    DirectoryCopy(currentFiles!![i].path + "/", toPath + currentFiles!![i].name + "/")

                } else {
                    //if file then copy
                    copySdcardFile(currentFiles!![i].path, toPath + currentFiles!![i].name)

                }
            }
            return 0
        }

        /**
         * function B
         * @param fromPath
         * @param toPath
         * @return
         */
        fun copySdcardFile(fromPath: String, toPath: String): Int {

            try {
                val fosfrom = FileInputStream(fromPath)
                val fosto = FileOutputStream(toPath)
                val bt = ByteArray(1024)
                val c: Int = fosfrom.read(bt)

                while (c > 0) {

                    fosto.write(bt, 0, c)

                }

                fosfrom.close()
                fosto.close()
                return 0

            } catch (ex: Exception) {

                return -1

            }

        }

        @Throws(IOException::class)
        fun copyFileUsingFileChannels(source: File, dest: File) {
            var inputChannel: FileChannel? = null
            var outputChannel: FileChannel? = null
            try {
                inputChannel = FileInputStream(source).channel
                outputChannel = FileOutputStream(dest).channel
                outputChannel!!.transferFrom(inputChannel, 0, inputChannel!!.size())
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (inputChannel != null) {
                    inputChannel!!.close()
                }
                if (outputChannel != null) {
                    outputChannel!!.close()
                }
            }
        }

        fun getUriFromFile(file: File): Uri {
            return Uri.fromFile(file)
        }


        @Throws(IOException::class)
        fun copyFileToUriUsingFileChannels(context: Context, source: File, dest: Uri) {

            val outputFileDescriptor = context.contentResolver.openFileDescriptor(dest, "w")!!.fileDescriptor

            var inputChannel: FileChannel? = null
            var outputChannel: FileChannel? = null
            try {
                inputChannel = FileInputStream(source).channel
                outputChannel = FileOutputStream(outputFileDescriptor).channel

                outputChannel!!.transferFrom(inputChannel, 0, inputChannel!!.size())

            } finally {
                inputChannel!!.close()
                outputChannel!!.close()
            }
        }


        @Throws(IOException::class)
        fun copyUriToUriUsingFileChannels(context: Context, source: Uri, dest: Uri) {

            val inputFileDescriptor = context.contentResolver.openFileDescriptor(dest, "r")!!.fileDescriptor
            val outputFileDescriptor = context.contentResolver.openFileDescriptor(dest, "w")!!.fileDescriptor

            var inputChannel: FileChannel? = null
            var outputChannel: FileChannel? = null
            try {
                inputChannel = FileInputStream(inputFileDescriptor).channel
                outputChannel = FileOutputStream(outputFileDescriptor).channel

                outputChannel!!.transferFrom(inputChannel, 0, inputChannel!!.size())

            } finally {
                inputChannel!!.close()
                outputChannel!!.close()
            }
        }


        fun UriFromFileToFileDescriptor(context: Context, file: File): FileDescriptor? {

            var fileDescriptor: FileDescriptor? = null

            try {
                fileDescriptor = context.contentResolver.openFileDescriptor(Uri.fromFile(file), "r")!!.fileDescriptor
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }


            return fileDescriptor
        }

        private fun getFileOutputStream(context: Context, uri: Uri): FileOutputStream? {
            try {
                return FileOutputStream(context.contentResolver.openFileDescriptor(uri, "w")!!.fileDescriptor)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        fun getFileInputStream(context: Context, uri: Uri): FileInputStream? {
            try {
                return FileInputStream(context.contentResolver.openFileDescriptor(uri, "r")!!.fileDescriptor)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }


        /**
         * get path from uri what head is "content://......."
         * @param context
         * @param uri
         * @return
         */
        fun getPathFromContentUri(context: Context, uri: Uri): String? {

            val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

            // DocumentProvider
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                    // ExternalStorageProvider
                    if (isExternalStorageDocument(uri)) {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split((":").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val type = split[0]

                        if ("primary".equals(type, ignoreCase = true)) {
                            return (Environment.getExternalStorageDirectory()).toString() + "/" + split[1]
                        }

                        // TODO handle non-primary volumes
                    } else if (isDownloadsDocument(uri)) {

                        val id = DocumentsContract.getDocumentId(uri)
                        val contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                        )

                        return getDataColumn(context, contentUri, null, null)
                    } else if (isMediaDocument(uri)) {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split((":").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val type = split[0]

                        var contentUri: Uri? = null
                        if ("image" == type) {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        } else if ("video" == type) {
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        } else if ("audio" == type) {
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        }

                        val selection = "_id=?"
                        val selectionArgs = arrayOf(split[1])

                        return getDataColumn(context, contentUri, selection, selectionArgs)
                    }// MediaProvider
                    // DownloadsProvider
                } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {
                    return getDataColumn(context, uri, null, null)
                } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
                    return uri.path
                }// File
                // MediaStore (and general)
            }

            return null
        }

        /**
         * Get the value of the data column for this Uri. This is useful for
         * MediaStore Uris, and other file-based ContentProviders.
         *
         * @param context The context.
         * @param uri The Uri to query.
         * @param selection (Optional) Filter used in the query.
         * @param selectionArgs (Optional) Selection arguments used in the query.
         * @return The value of the _data column, which is typically a file path.
         */
        fun getDataColumn(
            context: Context, uri: Uri?, selection: String?,
            selectionArgs: Array<String>?
        ): String? {

            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)

            try {
                cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
                if (cursor != null && cursor!!.moveToFirst()) {
                    val column_index = cursor!!.getColumnIndexOrThrow(column)
                    return cursor!!.getString(column_index)
                }
            } finally {
                if (cursor != null)
                    cursor!!.close()
            }
            return null
        }


        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }


        fun getParcelFileDescriptor(context: Context, uri: Uri): ParcelFileDescriptor? {
            var parcelFileDescriptor: ParcelFileDescriptor? = null
            try {
                parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "rw")
                return parcelFileDescriptor
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

            return null
        }

        @Throws(IOException::class)
        fun getTextFileFromDir(dirPath: String) {

            val dir = File(dirPath)
            val files = dir.listFiles(object : FilenameFilter {
                override fun accept(dir: File, name: String): Boolean {
                    return name.endsWith(".txt")
                }
            }) ?: return

            for (i in files!!.indices) {

                if (files!![i].isDirectory) {

                    getTextFileFromDir(files!![i].absolutePath)

                } else {

                    val strFileName = files!![i].absolutePath.toLowerCase()

                    println(strFileName)

                    val result = StringBuilder()
                    try {

                        val br = BufferedReader(FileReader(strFileName))
                        val s: String = br.readLine()

                        while (s != null) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                                result.append(System.lineSeparator() + s)

                            }

                        }

                        br.close()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    println(result.toString())
                }
            }
        }

    }
}