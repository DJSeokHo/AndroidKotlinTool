package com.swein.androidkotlintool.framework.utility.bitmap

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import com.swein.androidkotlintool.framework.utility.debug.ILog
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

        fun decodeSampledBitmapFromResource(
            res: Resources,
            resId: Int,
            reqWidth: Int,
            reqHeight: Int
        ): Bitmap {
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

        private fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
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

        fun getBitmapWithoutOOM(file: String): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = false
            options.inPreferredConfig = Bitmap.Config.RGB_565
            options.inDither = true
            return BitmapFactory.decodeFile(file, options)
        }

        fun adjustPhotoRotation(bitmap: Bitmap, orientationDegree: Float): Bitmap {

            val matrix = Matrix()
            matrix.setRotate(
                orientationDegree, bitmap.width.toFloat() / 2,
                bitmap.height.toFloat() / 2
            )
            val targetX: Float
            val targetY: Float
            if (orientationDegree == 90f) {
                targetX = bitmap.height.toFloat()
                targetY = 0f
            }
            else {
                targetX = bitmap.height.toFloat()
                targetY = bitmap.width.toFloat()
            }


            val values = FloatArray(9)
            matrix.getValues(values)


            val x1 = values[Matrix.MTRANS_X]
            val y1 = values[Matrix.MTRANS_Y]


            matrix.postTranslate(targetX - x1, targetY - y1)


            val canvasBitmap = Bitmap.createBitmap(
                bitmap.height, bitmap.width,
                Bitmap.Config.ARGB_8888
            )


            val paint = Paint()
            val canvas = Canvas(canvasBitmap)
            canvas.drawBitmap(bitmap, matrix, paint)


            return canvasBitmap
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

        fun compressImageWithFilePath(filePath: String, targetMB: Int, degree: Int) {
            var image: Bitmap = BitmapFactory.decodeFile(filePath)
            image = rotate(image, degree)

            try {

                val file = File(filePath)
                val length = file.length()

                val fileSizeInKB = (length / 1024).toString().toDouble()
                val fileSizeInMB = (fileSizeInKB / 1024).toString().toDouble()

                var quality = 100
                if(fileSizeInMB > targetMB) {
                    quality = ((targetMB / fileSizeInMB) * 100).toInt()
                }

                val fileOutputStream = FileOutputStream(filePath)
                image.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream)
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }

        fun compressImageWithFilePath(filePath: String, targetMB: Int) {
            var image: Bitmap = BitmapFactory.decodeFile(filePath)

            val exif = ExifInterface(filePath)
            val exifOrientation: Int = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
            )
            val exifDegree: Int = exifOrientationToDegrees(exifOrientation)

            ILog.debug("???", "$exifDegree")
            image = rotate(image, exifDegree)

            try {

                val file = File(filePath)
                val length = file.length()

                val fileSizeInKB = (length / 1024).toString().toDouble()
                val fileSizeInMB = (fileSizeInKB / 1024).toString().toDouble()

                var quality = 100
                if(fileSizeInMB > targetMB) {
                    quality = ((targetMB / fileSizeInMB) * 100).toInt()
                }

                val fileOutputStream = FileOutputStream(filePath)
                image.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream)
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }

        fun readPictureDegree(path: String): Int {
            var degree = 0
            try {
                val exifInterface = ExifInterface(path)
                val orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return degree
        }

        fun rotateImageWithoutStore(file: String, angle: Int, scaleRate: Float): Bitmap {
            var originalBitmap = getBitmapWithoutOOM(file)
            if (scaleRate < 1.0f) {
                originalBitmap = getScaleBitmap(
                    originalBitmap,
                    (originalBitmap.width * scaleRate).toInt(),
                    (originalBitmap.height * scaleRate).toInt()
                )
            }
            var returnBitmap: Bitmap? = null
            val matrix = Matrix()
            matrix.postRotate(angle.toFloat())
            try {
                returnBitmap = Bitmap.createBitmap(
                    originalBitmap,
                    0,
                    0,
                    originalBitmap.width,
                    originalBitmap.height,
                    matrix,
                    true
                )
            } catch (e: OutOfMemoryError) {
                e.printStackTrace()
            }
            if (returnBitmap == null) {
                returnBitmap = originalBitmap
            }


//        saveBitmapToJpeg(returnBitmap, tempFileName, 90);
            return returnBitmap
        }

        private fun exifOrientationToDegrees(exifOrientation: Int): Int {
            return when (exifOrientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> {
                    90
                }
                ExifInterface.ORIENTATION_ROTATE_180 -> {
                    180
                }
                ExifInterface.ORIENTATION_ROTATE_270 -> {
                    270
                }
                else -> 0
            }
        }

        fun rotate(b: Bitmap, degrees: Int): Bitmap {
            var bitmap = b
            if (degrees != 0) {
                val m = Matrix()
                m.setRotate(
                    degrees.toFloat(), bitmap.width.toFloat() / 2,
                    bitmap.height.toFloat() / 2
                )
                try {
                    val converted = Bitmap.createBitmap(
                        bitmap, 0, 0,
                        bitmap.width, bitmap.height, m, true
                    )
                    if (bitmap != converted) {
                        bitmap.recycle()
                        bitmap = converted
                    }
                } catch (ex: OutOfMemoryError) {
                    // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
                }
            }
            return bitmap
        }

    }
}