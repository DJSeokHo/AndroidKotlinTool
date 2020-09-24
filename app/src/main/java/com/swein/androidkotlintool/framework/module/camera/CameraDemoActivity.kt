package com.swein.androidkotlintool.framework.module.camera

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Size
import android.widget.ImageView
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.basicpermission.BasicPermissionActivity
import com.swein.androidkotlintool.framework.module.basicpermission.PermissionManager
import com.swein.androidkotlintool.framework.module.basicpermission.RequestPermission
import com.swein.androidkotlintool.framework.util.bitmap.BitmapUtil
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil
import io.everyportable.framework.util.glide.SHGlide
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraDemoActivity : BasicPermissionActivity() {

    companion object {
        private const val TAG = "CameraDemoActivity"
        private const val PHOTO_EXTENSION = ".jpg"
    }

    private lateinit var previewView: PreviewView

    private lateinit var preview: Preview
    private lateinit var camera: Camera
    private lateinit var cameraProvider: ProcessCameraProvider

    private lateinit var imageView: ImageView

    /** Blocking camera operations are performed using this executor */
    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_demo)

        findView()
        initCamera()
    }

    private fun findView() {
        previewView = findViewById(R.id.previewView)
        imageView = findViewById(R.id.imageView)
    }

    @RequestPermission(permissionCode = PermissionManager.PERMISSION_REQUEST_CAMERA_CODE)
    private fun initCamera() {

        if (PermissionManager.getInstance().requestPermission(
                this, true, PermissionManager.PERMISSION_REQUEST_CAMERA_CODE,
                Manifest.permission.CAMERA
            )
        ) {

            cameraProviderFuture = ProcessCameraProvider.getInstance(this)
            cameraProviderFuture.addListener({
                try {
                    cameraProvider = cameraProviderFuture.get()
                    startCamera(cameraProvider)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, ContextCompat.getMainExecutor(this))

        }

    }

    private var isOK = false

    private fun startCamera(cameraProvider: ProcessCameraProvider) {
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        var width = previewView.width
        var height = previewView.height

        val size: Size
        if (width > 600) {
            val rate = 600f / width.toFloat()
            ILog.debug(TAG, "rate is $rate")
            width = (width.toFloat() * rate).toInt()
            height = (height.toFloat() * rate).toInt()
        }

        ILog.debug(TAG, "$width $height")
        size = Size(width, height)

        preview = Preview.Builder().setTargetResolution(size).build()
//        preview = Preview.Builder().build()

        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(size)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(cameraExecutor, NormalImageRealTimeAnalyzer(object :
            NormalImageRealTimeAnalyzer.NormalImageRealTimeAnalyzerDelegate {
            override fun onBitmap(bitmap: Bitmap, degree: Int) {

                val photoFilePath = createFilePath(getOutputDirectory(this@CameraDemoActivity), PHOTO_EXTENSION)

//                ILog.debug(TAG, photoFilePath)

                val bufferedOutputStream = BufferedOutputStream(FileOutputStream(File(photoFilePath)))
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream)

                BitmapUtil.compressImageWithFilePath(photoFilePath, 1, degree)

                val file = File(photoFilePath)

                ThreadUtil.startUIThread(0) {

                    if(!isOK) {
                        SHGlide.setImageBitmap(this@CameraDemoActivity, file, imageView, null, 0, 0, 0f, 0f)
                    }

                    imageView.setImageBitmap(BitmapUtil.rotate(bitmap, degree))

                    isOK = true

                }
            }
        }))

        cameraProvider.unbindAll()
        camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
        preview.setSurfaceProvider(previewView.createSurfaceProvider())
    }

    fun getOutputDirectory(context: Context): File {
        val appContext = context.applicationContext
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else appContext.filesDir
    }

    private fun createFilePath(baseFolder: File, extension: String): String {

        return "${baseFolder}temp_image$extension"
    }
}