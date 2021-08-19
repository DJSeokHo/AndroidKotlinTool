package com.swein.androidkotlintool.main.examples.systemphotopicker

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import com.swein.androidkotlintool.BuildConfig
import com.swein.androidkotlintool.main.examples.permissionexample.PermissionManager
import java.io.File
import java.io.FileOutputStream
import java.util.*

class SystemPhotoPickManager(private val componentActivity: ComponentActivity) {

    private val takePicture: ActivityResultLauncher<Uri>
    private val selectPicture: ActivityResultLauncher<String>
    private val permissionManager: PermissionManager = PermissionManager(componentActivity)

    init {
        takePicture = registerTakePicture()
        selectPicture = registerSelectPicture()
    }

    private lateinit var selectedDelegate: (uri: Uri) -> Unit

    private var takePathDelegate: ((imagePath: String) -> Unit)? = null
    private var takeUriDelegate: ((uri: Uri) -> Unit)? = null
    private var takeBitmapDelegate: ((bitmap: Bitmap) -> Unit)? = null

    private var tempImageFilePath = ""
    private var tempImageUri: Uri? = null

    private var shouldCompress = false

    private fun registerTakePicture(): ActivityResultLauncher<Uri> {

        return componentActivity.registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->

            if (success) {

                takeUriDelegate?.let { takeUriDelegate ->
                    takeUriDelegate(tempImageUri!!)
                    return@registerForActivityResult
                }

                takePathDelegate?.let { takePathDelegate ->

                    if (shouldCompress) {
                        compressImage(tempImageFilePath)
                    }

                    takePathDelegate(tempImageFilePath)
                    return@registerForActivityResult
                }

                takeBitmapDelegate?.let { takeBitmapDelegate ->

                    val exif = ExifInterface(tempImageFilePath)
                    val exifOrientation: Int = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
                    )
                    val exifDegree: Int = exifOrientationToDegrees(exifOrientation)

                    takeBitmapDelegate(rotateImage(BitmapFactory.decodeStream(componentActivity.contentResolver.openInputStream(tempImageUri!!)), exifDegree.toFloat()))
                }

            }

        }
    }

    private fun registerSelectPicture(): ActivityResultLauncher<String> {

        return componentActivity.registerForActivityResult(ActivityResultContracts.GetContent()) {
            selectedDelegate(it)
        }
    }

    fun selectPicture(selectedDelegate: (uri: Uri) -> Unit) {

        permissionManager.requestPermission(
            "Permission",
            "permissions are necessary",
            "setting",
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        ) {
            this.selectedDelegate = selectedDelegate
            selectPicture.launch("image/*")
        }

    }

    fun takePictureWithFilePath(shouldCompress: Boolean = false, takePathDelegate: (imagePath: String) -> Unit) {

        permissionManager.requestPermission(
            "Permission",
            "permissions are necessary",
            "setting",
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        ) {
            this.shouldCompress = shouldCompress
            this.takePathDelegate = takePathDelegate

            tempImageUri = FileProvider.getUriForFile(
                componentActivity, BuildConfig.APPLICATION_ID + ".provider",
                createImageFile().also {
                    tempImageFilePath = it.absolutePath
                }
            )

            takePicture.launch(tempImageUri)
        }

    }

    fun takePictureWithUri(takeUriDelegate: (uri: Uri) -> Unit) {

        permissionManager.requestPermission(
            "Permission",
            "permissions are necessary",
            "setting",
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        ) {
            this.takeUriDelegate = takeUriDelegate

            tempImageUri = FileProvider.getUriForFile(
                componentActivity, BuildConfig.APPLICATION_ID + ".provider",
                createImageFile().also {
                    tempImageFilePath = it.absolutePath
                }
            )

            takePicture.launch(tempImageUri)
        }

    }

    fun takePictureWithBitmap(takeBitmapDelegate: ((bitmap: Bitmap) -> Unit)) {

        permissionManager.requestPermission(
            "Permission",
            "permissions are necessary",
            "setting",
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        ) {
            this.takeBitmapDelegate = takeBitmapDelegate

            tempImageUri = FileProvider.getUriForFile(
                componentActivity, BuildConfig.APPLICATION_ID + ".provider",
                createImageFile().also {
                    tempImageFilePath = it.absolutePath
                }
            )

            takePicture.launch(tempImageUri)
        }

    }

    private fun compressImage(filePath: String, targetMB: Double = 1.0) {
        var image: Bitmap = BitmapFactory.decodeFile(filePath)

        val exif = ExifInterface(filePath)
        val exifOrientation: Int = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
        )
        val exifDegree: Int = exifOrientationToDegrees(exifOrientation)

        image = rotateImage(image, exifDegree.toFloat())

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
            fileOutputStream.close()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
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

    private fun createImageFile(): File {
        // Create an image file name
        val storageDir = componentActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "temp_image",
            ".jpg",
            storageDir
        )
    }
}