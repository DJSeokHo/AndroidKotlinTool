package com.swein.androidkotlintool.framework.module.shcameraphoto.album.albumselectorwrapper

import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import com.swein.androidkotlintool.framework.module.shcameraphoto.album.albumselectorwrapper.bean.AlbumFolderItemBean
import com.swein.androidkotlintool.framework.module.shcameraphoto.album.albumselectorwrapper.bean.AlbumSelectorItemBean
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil.Companion.startThread
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil.Companion.startUIThread
import java.io.File
import java.util.*

object AlbumSelectorWrapper {

    interface AlbumSelectorWrapperDelegate {
        fun onSuccess(albumFolderItemBeanList: MutableList<AlbumFolderItemBean>, albumSelectorItemBeanList: MutableList<AlbumSelectorItemBean>)
        fun onError()
    }

    fun scanImageFile(context: Context, albumSelectorWrapperDelegate: AlbumSelectorWrapperDelegate) {
        val albumFolderItemBeanList: MutableList<AlbumFolderItemBean> = mutableListOf()
        val albumSelectorItemBeanList: MutableList<AlbumSelectorItemBean> = mutableListOf()

        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            // storage error
            albumSelectorWrapperDelegate.onError()
            return
        }

        startThread {
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val contentResolver = context.contentResolver
            contentResolver.query(
                uri,
                null,
                MediaStore.Images.Media.MIME_TYPE + " = ? or " +
                        MediaStore.Images.Media.MIME_TYPE + " = ? or " +
                        MediaStore.Images.Media.MIME_TYPE + " = ? ",
                arrayOf("image/jpeg", "image/jpg", "image/png"),
                MediaStore.Images.Media.DATE_MODIFIED
            )?.use {

                val dirPaths: MutableSet<String> =
                    HashSet()
                val count = it.count
                var parentFile: File
                for (i in count - 1 downTo 0) {
                    it.moveToPosition(i)
                    val path = it.getString(it.getColumnIndex(MediaStore.Images.Media.DATA))
                    var dirPath: String
                    var dirName: String?

                    parentFile = File(path).parentFile

                    if (parentFile == null) {
                        // if path is top, than have no parent folder
                        dirPath = ""
                        dirName = ""
                    }
                    else {
                        dirPath = parentFile.absolutePath
                        dirName = parentFile.name
                    }

                    if (i == count - 1) {
                        val albumFolderItemBean = AlbumFolderItemBean()
                        albumFolderItemBean.dirName = ""
                        albumFolderItemBean.dirPath = ""
                        albumFolderItemBean.isAll = true
                        albumFolderItemBean.isSelected = true
                        albumFolderItemBeanList.add(albumFolderItemBean)
                    }

                    val albumSelectorItemBean = AlbumSelectorItemBean()
                    albumSelectorItemBean.dirPath = dirPath
                    albumSelectorItemBean.filePath = path
                    albumSelectorItemBean.isSelected = false
                    albumSelectorItemBeanList.add(albumSelectorItemBean)

                    if (dirPath == "") {
                        continue
                    }

                    var albumFolderItemBean: AlbumFolderItemBean

                    if (dirPaths.contains(dirPath)) {
                        continue
                    }
                    else {
                        dirPaths.add(dirPath)
                        albumFolderItemBean = AlbumFolderItemBean()
                        albumFolderItemBean.dirPath = dirPath
                        albumFolderItemBean.dirName = dirName!!
                        albumFolderItemBean.isAll = false
                        albumFolderItemBean.isSelected = false
                        albumFolderItemBeanList.add(albumFolderItemBean)
                    }

                    if (parentFile.list() == null) {
                        continue
                    }

                    albumFolderItemBean.size =
                        parentFile.list { file: File?, s: String ->
                            s.endsWith(
                                ".jpg"
                            ) || s.endsWith(".jpeg") || s.endsWith(".png")
                        }.size
                }

                it.close()
                startUIThread(0) {
                    albumSelectorWrapperDelegate.onSuccess(
                        albumFolderItemBeanList,
                        albumSelectorItemBeanList
                    )
                }

            } ?: return@startThread

        }
    }
}