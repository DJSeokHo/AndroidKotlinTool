package com.swein.androidkotlintool.framework.util.device

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Service
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Vibrator
import android.provider.MediaStore
import android.telephony.TelephonyManager
import android.text.format.DateFormat
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.drawToBitmap
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*


object DeviceUtil {

    fun screenCapture(activity: Activity, showToast: Boolean = false, toastText: String = ""): String {

        try {

            val fileName = "USHome_${DateFormat.format("yyyyMMddHHmmss", Date())}.jpg"
//            val bitmap = activity.window.decorView.rootView.drawToBitmap(Bitmap.Config.ARGB_8888)

            activity.window.decorView.rootView.isDrawingCacheEnabled = true
            val bitmap: Bitmap = Bitmap.createBitmap(activity.window.decorView.rootView.drawingCache)
            activity.window.decorView.rootView.isDrawingCacheEnabled = false

            var outputStream: OutputStream? = null

            val screenShotsDir = File("${Environment.DIRECTORY_PICTURES}/Screenshots")
            if (!screenShotsDir.exists()) {
                screenShotsDir.mkdir()
            }

            val cameraDir = File("${Environment.DIRECTORY_DCIM}/Camera")
            if (!cameraDir.exists()) {
                cameraDir.mkdir()
            }

            val path = "${Environment.DIRECTORY_PICTURES}/Screenshots"
//            val path = "${Environment.DIRECTORY_DCIM}/Camera"

            // For devices running android >= Q
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // getting the contentResolver
                activity.contentResolver?.also { resolver ->

                    // Content resolver will process the contentvalues
                    val contentValues = ContentValues().apply {
                        // putting file information in content values
                        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, path)
                    }

                    // Inserting the contentValues to contentResolver and getting the Uri
                    val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                    // Opening an outputstream with the Uri that we got
                    outputStream = imageUri?.let {
                        resolver.openOutputStream(it)
                    }
                }
            }
            else {

                val image = File(path, fileName)
                outputStream = FileOutputStream(image)
            }

            outputStream?.use {

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                if (showToast) {

                    if (toastText == "") {
                        Toast.makeText(activity , "$path/$fileName" , Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(activity , toastText , Toast.LENGTH_SHORT).show()
                    }
                }
            }

            return "$path/$fileName"

        } catch (e: Throwable) {

            e.printStackTrace()
            return ""
        }
    }

    fun screenCapture(view: View, showToast: Boolean = false, toastText: String = ""): String {
        try {

            val fileName = "USHome_${DateFormat.format("yyyyMMddHHmmss", Date())}.jpg"

            view.isDrawingCacheEnabled = true
            val bitmap: Bitmap = Bitmap.createBitmap(view.drawingCache)
            view.isDrawingCacheEnabled = false

//            val bitmap = view.drawToBitmap(Bitmap.Config.ARGB_8888)

            var outputStream: OutputStream? = null

            val screenShotsDir = File("${Environment.DIRECTORY_PICTURES}/Screenshots")
            if (!screenShotsDir.exists()) {
                screenShotsDir.mkdir()
            }

            val cameraDir = File("${Environment.DIRECTORY_DCIM}/Camera")
            if (!cameraDir.exists()) {
                cameraDir.mkdir()
            }

            val path = "${Environment.DIRECTORY_PICTURES}/Screenshots"
//            val path = "${Environment.DIRECTORY_DCIM}/Camera"

            // For devices running android >= Q
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // getting the contentResolver
                view.context.contentResolver?.also { resolver ->

                    // Content resolver will process the contentvalues
                    val contentValues = ContentValues().apply {
                        // putting file information in content values
                        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, path)
                    }

                    // Inserting the contentValues to contentResolver and getting the Uri
                    val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                    outputStream = imageUri?.let {
                        resolver.openOutputStream(it)
                    }
                }
            }
            else {

                val image = File(path, fileName)
                outputStream = FileOutputStream(image)
            }

            outputStream?.use {
                // Finally writing the bitmap to the output stream that we opened
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                if (showToast) {

                    if (toastText == "") {
                        Toast.makeText(view.context , "$path/$fileName" , Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(view.context , toastText , Toast.LENGTH_SHORT).show()
                    }
                }
            }

            return "$path/$fileName"

        } catch (e: Throwable) {
            // Several error may come out with file handling or OOM
            e.printStackTrace()
            return ""
        }
    }

    fun screenCapture(view: View): Uri? {
        try {

            val fileName = "USHome_${DateFormat.format("yyyyMMddHHmmss", Date())}.jpg"

            view.isDrawingCacheEnabled = true
            val bitmap: Bitmap = Bitmap.createBitmap(view.drawingCache)
            view.isDrawingCacheEnabled = false

//            val bitmap = view.drawToBitmap(Bitmap.Config.ARGB_8888)

            var outputStream: OutputStream? = null

            val screenShotsDir = File("${Environment.DIRECTORY_PICTURES}/Screenshots")
            if (!screenShotsDir.exists()) {
                screenShotsDir.mkdir()
            }

            val cameraDir = File("${Environment.DIRECTORY_DCIM}/Camera")
            if (!cameraDir.exists()) {
                cameraDir.mkdir()
            }

            val path = "${Environment.DIRECTORY_PICTURES}/Screenshots"
//            val path = "${Environment.DIRECTORY_DCIM}/Camera"

            var imageUri: Uri? = null
            // For devices running android >= Q
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // getting the contentResolver
                view.context.contentResolver?.also { resolver ->

                    // Content resolver will process the contentvalues
                    val contentValues = ContentValues().apply {
                        // putting file information in content values
                        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, path)
                    }

                    // Inserting the contentValues to contentResolver and getting the Uri
                    imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                    outputStream = imageUri?.let {
                        resolver.openOutputStream(it)
                    }
                }
            }
            else {

                val image = File(path, fileName)
                outputStream = FileOutputStream(image)
                imageUri = Uri.fromFile(image)
            }

            outputStream?.use {
                // Finally writing the bitmap to the output stream that we opened
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)

            }

            return imageUri

        } catch (e: Throwable) {
            // Several error may come out with file handling or OOM
            e.printStackTrace()
            return null
        }
    }

    private fun loadBitmapFromView(v: View): Bitmap {
        val b = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        v.draw(c)
        return b
    }

    fun createPdf(viewList: List<View>, defaultSizeView: View): Uri? {

        try {

            val document = PdfDocument()

            // init pdf as A4 size paper
            // PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(defaultSizeView.getWidth(), defaultSizeView.getWidth() * 297/210, 1).create();

            // init pdf as view size
            val pageInfo = PageInfo.Builder(defaultSizeView.width, defaultSizeView.height, 1).create()
            var page: PdfDocument.Page
            for (view in viewList) {
                page = document.startPage(pageInfo)
                view.draw(page.canvas)
                document.finishPage(page)
            }

            val fileName = "USHome_${DateFormat.format("yyyyMMddHHmmss", Date())}.pdf"

            var outputStream: OutputStream? = null

            val fileDir = File("${Environment.DIRECTORY_DOCUMENTS}/USHomePDF")
            if (!fileDir.exists()) {
                fileDir.mkdir()
            }

            var pdfUri: Uri? = null

            val path = "${Environment.DIRECTORY_DOCUMENTS}/USHomePDF"

            // For devices running android >= Q
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // getting the contentResolver
                defaultSizeView.context.contentResolver.also { resolver ->

                    // Content resolver will process the contentvalues
                    val contentValues = ContentValues().apply {
                        // putting file information in content values
                        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                        put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, path)
                    }

                    val contentUri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
                    contentUri?.let { it ->
                        pdfUri = resolver.insert(it, contentValues)
                        pdfUri?.let { uri ->
                            val parcelFileDescriptor = resolver.openFileDescriptor(uri, "w")
                            outputStream = FileOutputStream(parcelFileDescriptor?.fileDescriptor)
                            resolver.openOutputStream(uri)

                        }
                    }

                }

            }
            else {

                val file = File(path, fileName)
                outputStream = FileOutputStream(file)
                document.writeTo(outputStream)

                pdfUri = Uri.fromFile(file)
            }

            outputStream?.use {
                // Finally writing the bitmap to the output stream that we opened
                document.writeTo(it)
                it.close()

                return pdfUri
            }

            return null

        } catch (e: Throwable) {
            // Several error may come out with file handling or OOM
            e.printStackTrace()
            return null
        }

    }

    /**
     * add this to Application.onCreate
     *
     * StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
     *
     * StrictMode.setVmPolicy(builder.build());
     *
     * builder.detectFileUriExposure();
     */
    fun shareImageFile(
        context: Context?,
        imagePath: String?,
        text: String?,
        subject: String?,
        chooserText: String?
    ) {
        val imageUri = Uri.fromFile(File(imagePath))
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
        shareIntent.type = "image/jpeg"
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context?.startActivity(Intent.createChooser(shareIntent, chooserText))
    }

    fun shareImageFile(
        context: Context?,
        imageUri: Uri?,
        text: String?,
        subject: String?,
        chooserText: String?
    ) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
        shareIntent.type = "image/jpeg"
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context?.startActivity(Intent.createChooser(shareIntent, chooserText))
    }

    fun sharePDFFile(
        context: Context?,
        fileUri: Uri?,
        text: String?,
        subject: String?,
        chooserText: String?
    ) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
        shareIntent.type = "application/pdf"
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context?.startActivity(Intent.createChooser(shareIntent, chooserText))
    }

    fun shareText(context: Context?, text: String?, subject: String?, chooserText: String?) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        context?.startActivity(Intent.createChooser(intent, chooserText))
    }

    fun vibrate(activity: Activity?, milliseconds: Long) {
        val vibrator = activity?.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator?
        vibrator?.let {
            if (it.hasVibrator()) {
                it.vibrate(milliseconds)
            }
        }
    }

    fun callWithDialog(context: Context?, phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context?.startActivity(intent)
    }

    fun getScreenCenterX(context: Context?): Float {
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        val displayMetrics = DisplayMetrics()
        if (windowManager == null) {
            return 0f
        }
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels * 0.5f
    }

    fun getScreenCenterY(context: Context?): Float {
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        val displayMetrics = DisplayMetrics()
        if (windowManager == null) {
            return 0f
        }
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels * 0.5f
    }

    @SuppressLint("HardwareIds")
    fun getNativePhoneNumber(context: Context?): String? {

        context?.let {

            val telephonyManager = it.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
            var nativePhoneNumber: String? = "N/A"
            if (ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_PHONE_NUMBERS
                ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return "NO PERMISSION"
            }
            nativePhoneNumber = telephonyManager?.line1Number
            if (nativePhoneNumber != null) {
                if (nativePhoneNumber.startsWith("+82")) {
                    nativePhoneNumber = nativePhoneNumber.replace("+82", "0")
                    return nativePhoneNumber
                }
            } else {
                return null
            }

        }

        return null
    }

    fun copyText(context: Context?, label: String?, text: String?) {
        val clipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clipData = ClipData.newPlainText(label, text)
        clipboardManager?.setPrimaryClip(clipData)
    }
}