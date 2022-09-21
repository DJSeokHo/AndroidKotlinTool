package com.swein.androidkotlintool.main.examples.imagesimilaritycameraexample

import android.Manifest
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.hardware.display.DisplayManager
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.window.layout.WindowMetricsCalculator
import com.google.common.util.concurrent.ListenableFuture
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.bitmap.BitmapUtil
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.framework.utility.glide.SHGlide
import com.swein.androidkotlintool.framework.utility.window.WindowUtility
import com.swein.androidkotlintool.main.examples.permissionexample.PermissionManager
import com.swein.androidkotlintool.main.examples.systemphotopicker.SystemPhotoPickManager
import java.io.File
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class ImageSimilarityCameraActivity : AppCompatActivity() {

    private val permissionManager = PermissionManager(this)
    private val systemPhotoPickManager = SystemPhotoPickManager(this)

    companion object {
        private const val TAG = "ImageSimilarityCameraActivity"

        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
    }

    private lateinit var imageViewCamera: ImageView
    private lateinit var imageViewSample: ImageView
    private lateinit var textViewSimilarity: TextView

    private lateinit var previewView: PreviewView
    private lateinit var preview: Preview
    private lateinit var camera: Camera
    private lateinit var cameraProvider: ProcessCameraProvider

    private var imageAnalysis: ImageAnalysis? = null

    private var lensFacing = CameraSelector.LENS_FACING_BACK
    private var displayId: Int = -1

    /** Blocking camera operations are performed using this executor */
    private var cameraExecutor = Executors.newSingleThreadExecutor()
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private var imageCapture = ImageCapture.Builder()
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
        .build()

    /**
     * add android:configChanges="keyboardHidden|orientation|screenSize"
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        bindCameraUseCases()
    }

    private var bitmapSample: Bitmap? = null
    private var rotation = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_similarity_camera)

        permissionManager.requestPermission(
            permissionDialogTitle = "title",
            permissionDialogMessage = "message",
            permissionDialogPositiveButtonTitle = "ok",
            permissions = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE),
            runnableAfterPermissionGranted = {
            },
            runnableAfterPermissionNotGranted = {
                finish()
            }
        )

        WindowUtility.toggleFullScreen(this)

        findView()
        setListener()

        initCamera(
            onLensCheck = { front: Boolean, back: Boolean ->
                if (!front && !back) {
                    finish()
                }

            }
        )

    }

    private fun findView() {
        previewView = findViewById(R.id.previewView)
        imageViewCamera = findViewById(R.id.imageViewCamera)
        imageViewSample = findViewById(R.id.imageViewSample)
        textViewSimilarity = findViewById(R.id.textViewSimilarity)
    }

    private fun setListener() {
        imageViewSample.setOnClickListener {

//            systemPhotoPickManager.takePictureWithBitmap {
//                bitmapSample = it
//                SHGlide.setImage(imageViewSample, bitmap = bitmapSample)
//            }

            val file = File.createTempFile(
                "temp_image",
                ".jpg",
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            )

            val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()
            imageCapture.takePicture(outputFileOptions, cameraExecutor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(error: ImageCaptureException)
                    {
                        ILog.debug(TAG, error.message.toString())
                    }
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        // insert your code here.

                        val tempBitmap = BitmapFactory.decodeFile(file.absolutePath)

                        val matrix = Matrix()
                        matrix.postRotate(rotation)
                        bitmapSample = Bitmap.createBitmap(
                            tempBitmap, 0, 0, tempBitmap.width, tempBitmap.height,
                            matrix, true
                        )

                        imageViewSample.post {
                            SHGlide.setImage(imageViewSample, bitmap = bitmapSample)
                        }

                    }
                })
        }

        imageViewSample.setOnLongClickListener {

            systemPhotoPickManager.selectPathPicture {
                bitmapSample = BitmapFactory.decodeFile(it)
                SHGlide.setImage(imageViewSample, bitmap = bitmapSample)
            }

            true
        }
    }

    private fun initCamera(onLensCheck: (front: Boolean, back: Boolean) -> Unit) {

        previewView.post {

            displayId = previewView.display.displayId

            cameraProviderFuture = ProcessCameraProvider.getInstance(this)
            cameraProviderFuture.addListener({

                cameraProvider = cameraProviderFuture.get()

                onLensCheck(
                    cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA),
                    cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)
                )

                (getSystemService(Context.DISPLAY_SERVICE) as DisplayManager).registerDisplayListener(object : DisplayManager.DisplayListener {
                    override fun onDisplayAdded(displayId: Int) = Unit
                    override fun onDisplayRemoved(displayId: Int) = Unit

                    override fun onDisplayChanged(displayId: Int) {

                        if (displayId == this@ImageSimilarityCameraActivity.displayId) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                display?.let {
                                    imageAnalysis?.targetRotation = it.rotation
                                }
                            }

                        }
                    }
                }, null)

                initImageCapture()
                bindCameraUseCases()

            }, ContextCompat.getMainExecutor(this))
        }
    }

    private fun initImageCapture() {

        imageCapture.targetRotation
    }

    private fun bindCameraUseCases() {

        cameraProvider.unbindAll()

        val screenAspectRatio = calculateScreenAspectRatio()
        val rotation = previewView.display.rotation
        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        preview = Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()


        imageAnalysis = ImageAnalysis.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()
            .also {
                startSimilarity(it)
            }

        try {
            // A variable number of use-cases can be passed here -
            // camera provides access to CameraControl & CameraInfo
            camera = cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                imageCapture,
                preview,
                imageAnalysis
            )

            // Attach the viewfinder's surface provider to preview use case
            preview.setSurfaceProvider(previewView.surfaceProvider)
        }
        catch (e: Exception) {
            e.printStackTrace()
            ILog.debug(TAG, "Use case binding failed ${e.message}")
        }
    }

    private fun calculateScreenAspectRatio(): Int {

        val windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val currentBounds = windowMetrics.bounds
        ILog.debug(TAG, "Screen metrics: ${currentBounds.width()} x ${currentBounds.height()}")

        val screenAspectRatio = aspectRatio(currentBounds.width(), currentBounds.height())
        ILog.debug(TAG, "Preview aspect ratio: $screenAspectRatio")

        return screenAspectRatio
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    private fun startSimilarity(imageAnalysis: ImageAnalysis) {

        imageAnalysis.setAnalyzer(cameraExecutor, ISNormalImageRealTimeAnalyzer { bitmap: Bitmap, degree: Int ->

            rotation = degree.toFloat()

            try {

                if (bitmapSample == null) {
//                    ILog.debug(TAG, "bitmapSample is null")
                    return@ISNormalImageRealTimeAnalyzer
                }

                ILog.debug(TAG, "bitmapSample ${bitmapSample?.width} ${bitmapSample?.height}")

                val bitmapForShowing = BitmapUtil.rotate(bitmap, degree)
                imageViewCamera.post {
                    imageViewCamera.setImageBitmap(bitmapForShowing)
                }

                var cameraBitmap = bitmapForShowing
                cameraBitmap = ThumbnailUtils.extractThumbnail(cameraBitmap, 8, 8)
                bitmapSample = ThumbnailUtils.extractThumbnail(bitmapSample, 8, 8)

                cameraBitmap = convertGreyImg(cameraBitmap)
                bitmapSample = convertGreyImg(bitmapSample!!)

                val avgC = getAvg(cameraBitmap)
                val avgS = getAvg(bitmapSample!!)

                val binaryC = getBinary(cameraBitmap, avgC)
                val binaryS = getBinary(bitmapSample!!, avgS)

                val hexC = binaryString2hexString(binaryC)
                val hexS = binaryString2hexString(binaryS)

                val similarity = similarity(hexC, hexS)
//                val difference = difference(hexC, hexS)

                textViewSimilarity.post {
                    textViewSimilarity.text = "${similarity}%"
                }
            }
            catch (e: Exception) {
                ILog.debug(TAG, e.message)
                e.printStackTrace()
            }
        })
    }

    /**
     * 比较相似度 > 60 就行
     */
    private fun similarity(s1: String, s2: String): Int {
        val s1s = s1.toCharArray()
        val s2s = s2.toCharArray()
        var diffNum = 100
        for (i in s1s.indices) {
            if (s1s[i] != s2s[i]) {
                diffNum--
            }
        }

        if (diffNum < 0) {
            diffNum = 0
        }

        return diffNum
    }

    private fun difference(s1: String, s2: String): Int {
        val s1s = s1.toCharArray()
        val s2s = s2.toCharArray()
        var diffNum = 0
        for (i in s1s.indices) {
            if (s1s[i] != s2s[i]) {
                diffNum++
            }
        }

        return diffNum
    }

    /**
     * 简化色彩
     */
    private fun convertGreyImg(img: Bitmap): Bitmap {
        val width = img.width //获取位图的宽
        val height = img.height //获取位图的高
        val pixels = IntArray(width * height) //通过位图的大小创建像素点数组
        img.getPixels(pixels, 0, width, 0, 0, width, height)
        val alpha = 0xFF shl 24
        for (i in 0 until height) {
            for (j in 0 until width) {
                val original = pixels[width * i + j]
                val red = (original and 0x00FF0000) shr 16
                val green = (original and 0x0000FF00) shr 8
                val blue = original and 0x000000FF
                var grey = (red.toFloat() * 0.3 + green.toFloat() * 0.59 + blue.toFloat() * 0.11).toInt()
                grey = alpha or (grey shl 16) or (grey shl 8) or grey
                pixels[width * i + j] = grey
            }
        }
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        result.setPixels(pixels, 0, width, 0, 0, width, height)
        return result
    }

    /**
     * 计算平均值
     */
    private fun getAvg(img: Bitmap): Int {
        val width = img.width
        val height = img.height
        val pixels = IntArray(width * height)
        img.getPixels(pixels, 0, width, 0, 0, width, height)
        var avgPixel = 0
        for (pixel in pixels) {
            avgPixel += pixel
        }
        return avgPixel / pixels.size
    }

    /**
     * 比较像素的灰度
     */
    private fun getBinary(img: Bitmap, average: Int): String {
        val sb = StringBuilder()
        val width = img.width
        val height = img.height
        val pixels = IntArray(width * height)
        img.getPixels(pixels, 0, width, 0, 0, width, height)
        for (i in 0 until height) {
            for (j in 0 until width) {
                val original = pixels[width * i + j]
                if (original >= average) {
                    pixels[width * i + j] = 1
                } else {
                    pixels[width * i + j] = 0
                }
                sb.append(pixels[width * i + j])
            }
        }
        return sb.toString()
    }

    /**
     * 计算哈希值
     */
    private fun binaryString2hexString(bString: String): String {
        if (bString == "" || bString.length % 8 != 0) {
            return ""
        }

        val stringBuild = StringBuilder()

        var iTmp: Int
        for (i in bString.indices step 4) {
            iTmp = 0
            for (j in 0 until 4) {
                iTmp += (bString.substring(i + j, i + j + 1).toInt()) shl (4 - j - 1)
            }
            stringBuild.append(Integer.toHexString(iTmp))
        }
        return stringBuild.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        bitmapSample = null
    }
}