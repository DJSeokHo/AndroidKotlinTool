package com.swein.androidkotlintool.main.examples.qr

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.hardware.display.DisplayManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.window.layout.WindowMetricsCalculator
import com.google.common.util.concurrent.ListenableFuture
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.framework.utility.eventsplitshot.eventcenter.EventCenter
import com.swein.androidkotlintool.framework.utility.eventsplitshot.subject.ESSArrows
import com.swein.androidkotlintool.framework.utility.window.WindowUtility
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class QRScannerActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "QRScannerActivity"

        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
    }

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

    /**
     * add android:configChanges="keyboardHidden|orientation|screenSize"
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        bindCameraUseCases()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscanner)

        toggleFullScreen()

        findView()

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

                        if (displayId == this@QRScannerActivity.displayId) {
                            imageAnalysis?.targetRotation = display!!.rotation
                        }
                    }
                }, null)

                bindCameraUseCases()

            }, ContextCompat.getMainExecutor(this))
        }
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
                startScanQR(it)
            }

        try {
            // A variable number of use-cases can be passed here -
            // camera provides access to CameraControl & CameraInfo
            camera = cameraProvider.bindToLifecycle(
                this, cameraSelector,
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

    private fun toggleFullScreen() {

        WindowUtility.layoutFullScreen(this)
        WindowUtility.setStateBarToDarkTheme(this)
        WindowUtility.setStatusBarColor(this, Color.TRANSPARENT)
        WindowUtility.setNavigationBarColor(this, Color.TRANSPARENT)

    }

    private fun startScanQR(imageAnalysis: ImageAnalysis) {
        imageAnalysis.setAnalyzer(cameraExecutor, QrCodeAnalyzer { result ->
            previewView.post {
                ILog.debug(TAG, "scanned: " + result.text)
                val mutableMap = mutableMapOf<String, Any>()
                mutableMap["qr"] = result.text
                EventCenter.sendEvent(ESSArrows.SCAN_QR_FINISHED, this, mutableMap)
                finish()
            }
        })
    }
}