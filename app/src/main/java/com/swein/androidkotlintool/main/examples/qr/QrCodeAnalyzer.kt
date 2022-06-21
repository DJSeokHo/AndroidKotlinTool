package com.swein.androidkotlintool.main.examples.qr

import android.graphics.ImageFormat
import android.os.Build
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.swein.androidkotlintool.framework.utility.debug.ILog
import java.nio.ByteBuffer

private fun ByteBuffer.toByteArray(): ByteArray {
    rewind()
    val data = ByteArray(remaining())
    get(data)
    return data
}

class QrCodeAnalyzer(private val onQrCodesDetected: (qrCode: Result) -> Unit
) : ImageAnalysis.Analyzer {

    companion object {
        private const val TAG = "QrCodeAnalyzer"
    }

    private val yuvFormats = mutableListOf(ImageFormat.YUV_420_888)

    private var finish: Boolean = false

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            yuvFormats.addAll(listOf(ImageFormat.YUV_422_888, ImageFormat.YUV_444_888))
        }
    }

    private val multiFormatReader = MultiFormatReader().apply {
        val map = mapOf(
                DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE)
        )
        setHints(map)
    }

    override fun analyze(image: ImageProxy) {

        if(finish) {
            return
        }

        // We are using YUV format because, ImageProxy internally uses ImageReader to get the image
        // by default ImageReader uses YUV format unless changed.
        if (image.format !in yuvFormats) {
            return
        }

        val data = image.planes[0].buffer.toByteArray()

        val source = PlanarYUVLuminanceSource(
                data,
                image.width,
                image.height,
                0,
                0,
                image.width,
                image.height,
                false
        )

        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
        try {
            // Whenever reader fails to detect a QR code in image
            // it throws NotFoundException
            val result = multiFormatReader.decode(binaryBitmap)
            onQrCodesDetected(result)
            ILog.debug(TAG, "finish Expected YUV, now = ${image.format}")
            finish = true
        }
        catch (e: NotFoundException) {
            e.printStackTrace()
        }
        finally {
            multiFormatReader.reset()
        }
        image.close()
    }
}
