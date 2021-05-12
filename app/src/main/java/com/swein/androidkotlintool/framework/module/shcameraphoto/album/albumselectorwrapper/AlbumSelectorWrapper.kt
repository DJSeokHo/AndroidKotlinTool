package com.swein.androidkotlintool.framework.module.shcameraphoto.album.albumselectorwrapper

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.swein.androidkotlintool.framework.module.shcameraphoto.album.albumselectorwrapper.bean.AlbumSelectorItemBean

object AlbumSelectorWrapper {

    @RequiresApi(Build.VERSION_CODES.O)
    fun scanImageFile(context: Context, offset: Int, limit: Int,
                      onSuccess: (albumSelectorItemBeanList: MutableList<AlbumSelectorItemBean>) -> Unit, onError: () -> Unit) {
        val albumSelectorItemBeanList: MutableList<AlbumSelectorItemBean> = mutableListOf()

        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            // storage error
            onError()
            return
        }

        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val contentResolver = context.contentResolver

        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID
        )
//            val projection = null
        val selection = "${MediaStore.Images.Media.MIME_TYPE} = ? or ${MediaStore.Images.Media.MIME_TYPE} = ? or ${MediaStore.Images.Media.MIME_TYPE} = ?"
        val selectionArgs = arrayOf("image/jpeg", "image/jpg", "image/png")

        contentResolver.query(

            uri, projection,
            Bundle().apply {
                // Limit & Offset
                putInt(ContentResolver.QUERY_ARG_LIMIT, limit)
                putInt(ContentResolver.QUERY_ARG_OFFSET, offset)
                // Sort function
                putString(ContentResolver.QUERY_ARG_SORT_COLUMNS, MediaStore.Files.FileColumns.DATE_ADDED)
                putInt(ContentResolver.QUERY_ARG_SORT_DIRECTION, ContentResolver.QUERY_SORT_DIRECTION_DESCENDING)

                // Selection
                putString(ContentResolver.QUERY_ARG_SQL_SELECTION, selection)
                putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, selectionArgs)
            }, null

        )?.use { cursor ->

            while (cursor.moveToNext()) {

                val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID))
                val imageUrl = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                val albumSelectorItemBean  = AlbumSelectorItemBean()

                albumSelectorItemBean.imageUri = imageUrl
                albumSelectorItemBeanList.add(albumSelectorItemBean)
            }

            cursor.close()

            onSuccess(albumSelectorItemBeanList)

        }
    }
}