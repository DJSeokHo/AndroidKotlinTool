package com.swein.androidkotlintool.framework.module.shcameraphoto

import android.Manifest
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.display.DisplayManager
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.common.util.concurrent.ListenableFuture
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.basicpermission.PermissionManager
import com.swein.androidkotlintool.framework.module.basicpermission.RequestPermission
import com.swein.androidkotlintool.framework.util.bitmap.BitmapUtil
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.theme.ThemeUtil
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil
import kotlinx.android.synthetic.main.fragment_s_h_camera_photo.*
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class SHCameraPhotoFragment : Fragment() {

    companion object {

        const val TAG = "SHCameraPhotoFragment"
        private const val PHOTO_EXTENSION = ".jpg"

//        @JvmStatic
//        fun newInstance() =
//            SHCameraPhotoFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }

        @JvmStatic
        fun newInstance(): Fragment {
            return SHCameraPhotoFragment()
        }

        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
    }

    private lateinit var rootView: View

    private lateinit var imageButtonTake: ImageButton
    private lateinit var imageButtonSwitchCamera: ImageButton
    private lateinit var imageButtonFlash: ImageButton
    private lateinit var textViewAction: TextView
    private lateinit var imageView: ImageView
    private lateinit var frameLayoutProgress: FrameLayout

    private lateinit var previewView: PreviewView

    private lateinit var preview: Preview
    private lateinit var camera: Camera
    private lateinit var cameraProvider: ProcessCameraProvider
    private var lensFacing = CameraSelector.LENS_FACING_BACK
    private lateinit var imageCapture: ImageCapture
    private lateinit var imageAnalysis: ImageAnalysis
    private var flash = false

    private var selectedImageList = mutableListOf<String>()

    private val displayManager by lazy {
        requireContext().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    }
    private var displayId: Int = -1

    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit
        override fun onDisplayChanged(displayId: Int) = view?.let { view ->
            if (displayId == this@SHCameraPhotoFragment.displayId) {
                ILog.debug(TAG, "Rotation changed: ${view.display.rotation}")
                imageCapture.targetRotation = view.display.rotation
                imageAnalysis.targetRotation = view.display.rotation
            }
        } ?: Unit
    }

    /** Blocking camera operations are performed using this executor */
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    /**
     * add android:configChanges="keyboardHidden|orientation|screenSize"
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            // Nothing need to be done here
            ILog.debug(TAG, "ORIENTATION_LANDSCAPE")

        }
        else {
            ILog.debug(TAG, "ORIENTATION_PORTRAIT")
            // Nothing need to be done here
        }

        bindCameraUseCases()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            ThemeUtil.transparencyBar(it)
            ThemeUtil.hideSystemUi(it)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_s_h_camera_photo, container, false)
        initData()
        findView()
        setListener()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Wait for the views to be properly laid out
        previewView.post {

            // Keep track of the display in which this view is attached
            displayId = previewView.display.displayId

            // Set up the camera and its use cases
            activity?.let {
                updateView()
                initCamera(it)
            }
        }
    }

    private fun initData() {

        selectedImageList.clear()
    }

    private fun findView() {
        imageButtonTake = rootView.findViewById(R.id.imageButtonTake)
        imageButtonSwitchCamera = rootView.findViewById(R.id.imageButtonSwitchCamera)
        textViewAction = rootView.findViewById(R.id.textViewAction)
        imageView = rootView.findViewById(R.id.imageView)
        previewView = rootView.findViewById(R.id.previewView)
        imageButtonFlash = rootView.findViewById(R.id.imageButtonFlash)

        frameLayoutProgress = rootView.findViewById(R.id.frameLayoutProgress)
    }

    private fun setListener() {

        imageButtonFlash.setOnClickListener {

            flash = !flash

            imageCapture.flashMode = if(flash) {
                ImageCapture.FLASH_MODE_AUTO
            }
            else {
                ImageCapture.FLASH_MODE_OFF
            }

            updateView()
        }

        imageButtonSwitchCamera.setOnClickListener {

            lensFacing = if (CameraSelector.LENS_FACING_BACK == lensFacing) {
                CameraSelector.LENS_FACING_FRONT
            }
            else {
                CameraSelector.LENS_FACING_BACK
            }

            try {

                previewView.post {
                    bindCameraUseCases()
                }

            } catch (e: Exception) {
                // Do nothing
                e.printStackTrace()
            }
        }

        imageButtonTake.setOnClickListener {

            context?.let {

                showProgress()

                val photoFilePath = createFilePath(getOutputDirectory(it))
                val photoFile = File(photoFilePath)
                val metadata = ImageCapture.Metadata().apply {
                    // Mirror image when using the front camera
                    isReversedHorizontal = lensFacing == CameraSelector.LENS_FACING_FRONT
                }

                ILog.debug(TAG, photoFile.absolutePath)
                val outputFileOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
                    .setMetadata(metadata).build()

                imageCapture.takePicture(outputFileOptions, cameraExecutor, object : ImageCapture.OnImageSavedCallback {

                    override fun onError(error: ImageCaptureException)
                    {
                        ILog.debug(TAG, error.message)
                        error.printStackTrace()
                        ThreadUtil.startUIThread(0) {
                            hideProgress()
                        }
                    }

                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                        val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)
                        ILog.debug(TAG, savedUri)

                        ThreadUtil.startThread {

                            BitmapUtil.compressImageWithFilePath(photoFilePath, 1)
                            val bitmap = BitmapFactory.decodeFile(photoFilePath)

                            ThreadUtil.startUIThread(0) {
                                imageView.setImageBitmap(bitmap)
                                hideProgress()
                            }
                        }
                    }
                })
            }
        }

        textViewAction.setOnClickListener {

            if(textViewAction.text == getString(R.string.camera_cancel)) {
                activity?.finish()
            }

        }
    }

    private fun updateView() {
        if (flash) {
            imageButtonFlash.setImageResource(R.drawable.flash_auto)
        }
        else {
            imageButtonFlash.setImageResource(R.drawable.flash_off)
        }
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    @RequestPermission(permissionCode = PermissionManager.PERMISSION_REQUEST_CAMERA_CODE)
    private fun initCamera(activity: FragmentActivity) {

        if (PermissionManager.getInstance().requestPermission(activity, true, PermissionManager.PERMISSION_REQUEST_CAMERA_CODE, Manifest.permission.CAMERA)) {

            cameraProviderFuture = ProcessCameraProvider.getInstance(activity)
            cameraProviderFuture.addListener({

                initCamera()
                checkBackFrontCamera()
                bindCameraUseCases()

            }, ContextCompat.getMainExecutor(activity))

        }
    }

    private fun checkBackFrontCamera() {

        if(!hasBackCamera() && !hasFrontCamera()) {
            activity?.finish()
        }

        if(hasBackCamera() && hasFrontCamera()) {
            imageButtonSwitchCamera.visibility = View.VISIBLE
        }
        else {
            imageButtonSwitchCamera.visibility = View.GONE
        }
    }

    private fun initCamera() {

        ILog.debug(TAG, "initCamera")

        cameraProvider = cameraProviderFuture.get()
        cameraExecutor = Executors.newSingleThreadExecutor()
        displayManager.registerDisplayListener(displayListener, null)
    }

    private fun bindCameraUseCases() {

        // Get screen metrics used to setup camera for full screen resolution
        val metrics = DisplayMetrics().also { previewView.display.getRealMetrics(it) }
        ILog.debug(TAG, "Screen metrics: ${metrics.widthPixels} x ${metrics.heightPixels}")

        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
        ILog.debug(TAG, "Preview aspect ratio: $screenAspectRatio")

        val rotation = previewView.display.rotation

        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        preview = initPreview(screenAspectRatio, rotation)

        imageCapture = initImageCapture(screenAspectRatio, rotation)
//        imageCapture = initImageCapture(screenAspectRatio, rotation, ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)

        imageAnalysis = initImageAnalysis(screenAspectRatio, rotation)

        cameraProvider.unbindAll()

        try {
            // A variable number of use-cases can be passed here -
            // camera provides access to CameraControl & CameraInfo
            camera = cameraProvider.bindToLifecycle(
                this, cameraSelector, preview, imageCapture, imageAnalysis)

            // Attach the viewfinder's surface provider to preview use case
            preview.setSurfaceProvider(previewView.surfaceProvider)
        } catch (e: Exception) {
            ILog.debug(TAG, "Use case binding failed ${e.message}")
        }
    }

    private fun initPreview(screenAspectRatio: Int, rotation: Int): Preview {

        return Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()
    }

    private fun initImageCapture(screenAspectRatio: Int, rotation: Int): ImageCapture {

        val flashMode = if(flash) {
            ImageCapture.FLASH_MODE_AUTO
        }
        else {
            ImageCapture.FLASH_MODE_OFF
        }

        return ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
            .setFlashMode(flashMode)
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()
    }

    private fun initImageAnalysis(screenAspectRatio: Int, rotation: Int): ImageAnalysis {


        return ImageAnalysis.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, NormalImageRealTimeAnalyzer(object :
                    NormalImageRealTimeAnalyzer.NormalImageRealTimeAnalyzerDelegate {
                    override fun onBitmap(bitmap: Bitmap, degree: Int) {

//                context?.let {
//                    val photoFilePath = createFilePath(getOutputDirectory(it), PHOTO_EXTENSION)
//
//                    ILog.debug(TAG, photoFilePath)
//
//                    val bufferedOutputStream = BufferedOutputStream(FileOutputStream(File(photoFilePath)))
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream)
//
//                    BitmapUtil.compressImageWithFilePath(photoFilePath, 1, degree)
//
//                    ThreadUtil.startUIThread(0) {
//
//                        imageView.setImageBitmap(BitmapUtil.rotate(bitmap, degree))
//
//                    }
//                }

                    }
                }))
            }
    }

    private fun hasBackCamera(): Boolean {
        return cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)
    }

    private fun hasFrontCamera(): Boolean {
        return cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)
    }

    private fun getOutputDirectory(context: Context): File {
        val appContext = context.applicationContext
        val mediaDir = context.externalCacheDirs.firstOrNull()?.let {
            File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else appContext.filesDir
    }

    private fun createFilePath(baseFolder: File): String {

        return "${baseFolder}temp_image$PHOTO_EXTENSION"
    }

    private fun showProgress() {
        frameLayoutProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        frameLayoutProgress.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()

        cameraExecutor.shutdown()
        displayManager.unregisterDisplayListener(displayListener)
    }
}