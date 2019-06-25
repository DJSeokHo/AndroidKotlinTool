package com.swein.androidkotlintool.framework.util.bigmap

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class BitmapUtil {

    companion object {

        /**
         * must in thread
         *
         * @param urlString
         * @return
         */
        fun getBitmapFromUrl(urlString: String): Bitmap? {

            try {

                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input = connection.inputStream
                return BitmapFactory.decodeStream(input)
            } catch (e: IOException) {
                // Log exception
                e.printStackTrace()
                return null
            }

        }

        fun getBitmapFromDrawable(drawable: Drawable): Bitmap {
            val width = drawable.intrinsicWidth
            val height = drawable.intrinsicHeight
            val config =
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565// 取drawable的颜色格式
            val bitmap = Bitmap.createBitmap(width, height, config)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, width, height)
            drawable.draw(canvas)
            return bitmap
        }

        fun getBitmapFromDrawableResource(context: Context, resourceId: Int): Bitmap {
            val drawable = context.resources.getDrawable(resourceId)
            val width = drawable.intrinsicWidth
            val height = drawable.intrinsicHeight
            val config =
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565// 取drawable的颜色格式
            val bitmap = Bitmap.createBitmap(width, height, config)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, width, height)
            drawable.draw(canvas)
            return bitmap
        }

        fun getBitmapFromFile(file: File): Bitmap {

            val bitmap: Bitmap

            bitmap = BitmapFactory.decodeFile(file.absolutePath)

            return bitmap

        }

        fun getBitmapFromPath(path: String): Bitmap {
            val bitmap: Bitmap

            bitmap = BitmapFactory.decodeFile(path)

            return bitmap
        }


        fun bitmapToByte(bitmap: Bitmap): ByteArray {

            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)

            return byteArrayOutputStream.toByteArray()
        }

        fun decodeSampledBitmapFromResource(res: Resources, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(res, resId, options)
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeResource(res, resId, options)
        }

        fun decodeSampledBitmapFromFilePath(imagePath: String, reqWidth: Int, reqHeight: Int): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(imagePath, options)
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeFile(imagePath, options)
        }

        private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {
                val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
                val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }
            return inSampleSize
        }

        fun rotateImage(file: String, angle: Int) {

            val bounds = BitmapFactory.Options()
            bounds.inJustDecodeBounds = true
            BitmapFactory.decodeFile(file, bounds)

            val opts = BitmapFactory.Options()
            val bm = BitmapFactory.decodeFile(file, opts)
            var exif: ExifInterface? = null
            try {
                exif = ExifInterface(file)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val orientString = exif!!.getAttribute(ExifInterface.TAG_ORIENTATION)
            val orientation =
                if (orientString != null) Integer.parseInt(orientString) else ExifInterface.ORIENTATION_NORMAL


            // for OOM
            val ratio = 1.0f

            val matrix = Matrix()
            matrix.setRotate(angle.toFloat(), bm.width.toFloat() / 2, bm.height.toFloat() / 2)
            val rotatedBitmap = Bitmap.createBitmap(
                bm,
                0,
                0,
                (bounds.outWidth * ratio).toInt(),
                (bounds.outHeight * ratio).toInt(),
                matrix,
                true
            )
            saveBitmapToJpeg(rotatedBitmap, file)
        }


        fun rotateImage(file: String) {

            val bounds = BitmapFactory.Options()
            bounds.inJustDecodeBounds = true
            BitmapFactory.decodeFile(file, bounds)

            val opts = BitmapFactory.Options()
            val bm = BitmapFactory.decodeFile(file, opts)
            var exif: ExifInterface? = null
            try {
                exif = ExifInterface(file)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val orientString = exif!!.getAttribute(ExifInterface.TAG_ORIENTATION)
            val orientation =
                if (orientString != null) Integer.parseInt(orientString) else ExifInterface.ORIENTATION_NORMAL


            var rotationAngle = 0
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270

            // for OOM
            val ratio = 1.0f

            val matrix = Matrix()
            matrix.setRotate(rotationAngle.toFloat(), bm.width.toFloat() / 2, bm.height.toFloat() / 2)
            val rotatedBitmap = Bitmap.createBitmap(
                bm,
                0,
                0,
                (bounds.outWidth * ratio).toInt(),
                (bounds.outHeight * ratio).toInt(),
                matrix,
                true
            )
            saveBitmapToJpeg(rotatedBitmap, file)
        }

        fun saveBitmapToJpeg(bitmap: Bitmap, path: String) {
            var out: FileOutputStream? = null
            try {

                out = FileOutputStream(path)

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)

            } catch (exception: FileNotFoundException) {
                exception.printStackTrace()
            } finally {
                if (out != null)
                    try {
                        out.close()
                    } catch (e: IOException) {

                        e.printStackTrace()
                    }

            }
        }

        fun ResizeImages(path: String, quality: Int) {

            val photo = BitmapFactory.decodeFile(path)
            val bytes = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.JPEG, quality, bytes)

            val f = File(path)
            try {
                f.createNewFile()
                val fo = FileOutputStream(f)
                fo.write(bytes.toByteArray())
                fo.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        fun getScaleBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
            val width = bitmap.width
            val height = bitmap.height
            val scaleWidth = newWidth.toFloat() / width
            val scaleHeight = newHeight.toFloat() / height

            val matrix = Matrix()

            matrix.postScale(scaleWidth, scaleHeight)

            val resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false
            )
            bitmap.recycle()
            return resizedBitmap
        }

        fun getBitmapFromByte(data: ByteArray): Bitmap {
            return BitmapFactory.decodeByteArray(data, 0, data.size)
        }

    }
}