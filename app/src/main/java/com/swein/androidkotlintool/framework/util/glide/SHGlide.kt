package io.everyportable.framework.util.glide

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestOptions
import java.io.File

class SHGlide {

    companion object {

        fun setImageBitmap(context: Context?, url: String?, imageView: ImageView?, placeHolder: Drawable?, w: Int, h: Int, rate: Float, thumbnailSize: Float) {
            var width = w
            var height = h
            var requestBuilder = Glide.with(context!!).asBitmap().load(url).transition(BitmapTransitionOptions.withCrossFade())

            if (placeHolder != null) {
                requestBuilder = requestBuilder.placeholder(placeHolder)
            }

            if (width != 0 && height != 0) {
                if (rate != 0f) {
                    width = (width.toFloat() * rate).toInt()
                    height = (height.toFloat() * rate).toInt()
                }
                requestBuilder = requestBuilder.override(width, height)
            }

            if (thumbnailSize != 0f) {
                requestBuilder = requestBuilder.thumbnail(thumbnailSize)
            }

            requestBuilder.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView!!)
        }

        fun setImageBitmap(context: Context?, imageResource: Int, imageView: ImageView?, placeHolder: Drawable?, w: Int, h: Int, rate: Float, thumbnailSize: Float) {
            var width = w
            var height = h
            var requestBuilder = Glide.with(context!!).asBitmap().load(imageResource).transition(BitmapTransitionOptions.withCrossFade())

            if (placeHolder != null) {
                requestBuilder = requestBuilder.placeholder(placeHolder)
            }

            if (width != 0 && height != 0) {
                if (rate != 0f) {
                    width = (width.toFloat() * rate).toInt()
                    height = (height.toFloat() * rate).toInt()
                }
                requestBuilder = requestBuilder.override(width, height)
            }

            if (thumbnailSize != 0f) {
                requestBuilder = requestBuilder.thumbnail(thumbnailSize)
            }

            requestBuilder.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView!!)
        }

        fun setImageBitmap(context: Context?, file: File, imageView: ImageView?, placeHolder: Drawable?, w: Int, h: Int, rate: Float, thumbnailSize: Float) {
            var width = w
            var height = h
            var requestBuilder = Glide.with(context!!).asBitmap().load(file).transition(BitmapTransitionOptions.withCrossFade())

            if (placeHolder != null) {
                requestBuilder = requestBuilder.placeholder(placeHolder)
            }

            if (width != 0 && height != 0) {
                if (rate != 0f) {
                    width = (width.toFloat() * rate).toInt()
                    height = (height.toFloat() * rate).toInt()
                }
                requestBuilder = requestBuilder.override(width, height)
            }

            if (thumbnailSize != 0f) {
                requestBuilder = requestBuilder.thumbnail(thumbnailSize)
            }

            requestBuilder.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView!!)
        }

        fun setImageBitmap(context: Context?, imageUri: Uri, imageView: ImageView?, placeHolder: Drawable?, w: Int, h: Int, rate: Float, thumbnailSize: Float) {
            var width = w
            var height = h
            var requestBuilder = Glide.with(context!!).asBitmap().load(imageUri).transition(BitmapTransitionOptions.withCrossFade())

            if (placeHolder != null) {
                requestBuilder = requestBuilder.placeholder(placeHolder)
            }

            if (width != 0 && height != 0) {
                if (rate != 0f) {
                    width = (width.toFloat() * rate).toInt()
                    height = (height.toFloat() * rate).toInt()
                }
                requestBuilder = requestBuilder.override(width, height)
            }

            if (thumbnailSize != 0f) {
                requestBuilder = requestBuilder.thumbnail(thumbnailSize)
            }
            requestBuilder.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView!!)
        }

        fun setRoundedImageBitmap(context: Context?, url: String?, imageView: ImageView?, placeHolder: Drawable?, w: Int, h: Int, rate: Float, thumbnailSize: Float) {
            var width = w
            var height = h
            var requestBuilder = Glide.with(context!!).asBitmap().load(url).transition(BitmapTransitionOptions.withCrossFade())

            if (placeHolder != null) {
                requestBuilder = requestBuilder.placeholder(placeHolder)
            }

            if (width != 0 && height != 0) {
                if (rate != 0f) {
                    width = (width.toFloat() * rate).toInt()
                    height = (height.toFloat() * rate).toInt()
                }
                requestBuilder = requestBuilder.override(width, height)
            }

            if (thumbnailSize != 0f) {
                requestBuilder = requestBuilder.thumbnail(thumbnailSize)
            }

            requestBuilder.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(RequestOptions.circleCropTransform()).into(imageView!!)
        }
    }
}