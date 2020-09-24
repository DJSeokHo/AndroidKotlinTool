package com.swein.androidkotlintool.framework.module.camera

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Size
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
import com.swein.androidkotlintool.framework.util.log.ILog
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraDemoActivity : BasicPermissionActivity() {

    companion object {
        private const val TAG = "CameraDemoActivity"
    }

    private lateinit var previewView: PreviewView

    private lateinit var preview: Preview
    private lateinit var camera: Camera
    private lateinit var cameraProvider: ProcessCameraProvider

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
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }, ContextCompat.getMainExecutor(this))

        }

    }

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

        imageAnalysis.setAnalyzer(cameraExecutor, NormalImageRealTimeAnalyzer(object : NormalImageRealTimeAnalyzer.NormalImageRealTimeAnalyzerDelegate {
            override fun onBitmap(bitmap: Bitmap) {
                ILog.debug(TAG, "${bitmap.width} ${bitmap.height}")
            }

        }))

//        if (type == 0) {
//            imageAnalysis.setAnalyzer(cameraExecutor, QrCodeAnalyzer { result ->
//                previewView.post {
//                    ILog.debug(TAG, "scanned: " + result.text)
////                    ToastUtil.showCustomLongToastNormal(this, result.text)
//                    val mutableMap = mutableMapOf<String, Any>()
//                    mutableMap["qr"] = result.text
//                    EventCenter.sendEvent(ESSArrows.SCAN_QR_FINISHED, this, mutableMap)
//                    finish()
//                }
//            })
//        }
//        else if (type == 1) {
//            imageAnalysis.setAnalyzer(cameraExecutor, TextAnalyzer(object :
//                TextAnalyzer.TextAnalyzerDelegate {
//                override fun onTextDetected(result: String?) {
//                    ILog.debug(TAG, "scanned: $result")
//                    isCardNumberFinished = true
//                    toggleCardScanFinish(result!!, "", "")
//                }
//
//                override fun getRotation(): Int {
////                ILog.iLogDebug(TAG, "angle is " + angle)
//                    return camera.cameraInfo.sensorRotationDegrees
//                }
//
//                override fun onDateDetected(month: String?, year: String?) {
//                    isCardDateFinished = true
//                    toggleCardScanFinish("", year!!, month!!)
//                }
//            }))
//        }


        cameraProvider.unbindAll()
        camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
        preview.setSurfaceProvider(previewView.createSurfaceProvider())
    }

}