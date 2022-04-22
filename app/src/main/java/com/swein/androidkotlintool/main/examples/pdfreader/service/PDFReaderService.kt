package com.swein.androidkotlintool.main.examples.pdfreader.service

import android.content.Context
import android.os.Environment
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.framework.utility.okhttp.OKHttpWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

object PDFReaderService {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

    private data class PDFReaderServiceResponse(val call: Call, val response: Response)

    private fun requestGet(
        url: String
    ): PDFReaderServiceResponse {

        val builder = Request.Builder()

        val request = builder.get().url(url).build()
        val call = okHttpClient.newCall(request)

        val result = call.execute()
        return PDFReaderServiceResponse(call, result)
    }

    private fun cancelCall(call: Call) {
        if (!call.isCanceled()) {
            call.cancel()
        }
    }

    private fun getFileResponse(context: Context, response: Response, onProgress: (progress: Int) -> Unit): File? {
        ILog.debug(OKHttpWrapper.TAG, "onResponse: $response")
        val responseBody = response.body

        responseBody?.let {
            return try {

                val pdfFilePath = createPdfFile(context)

                val inputStream = responseBody.byteStream()
                val outputStream = FileOutputStream(pdfFilePath)
                val target = it.contentLength()

                copyStream(inputStream, outputStream) { totalBytesCopied ->

                    ILog.debug("???", "$totalBytesCopied/${target}")
                    onProgress(((totalBytesCopied.toFloat() / target.toFloat()) * 100).toInt())

                }

//                inputStream.copyTo(outputStream) { totalBytesCopied: Long, bytesJustCopied: Int ->
//
//                    ILog.debug("???", "$totalBytesCopied/${target}")
//                    onProgress
//                }

                outputStream.flush()
                outputStream.close()
                inputStream.close()

                pdfFilePath

            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

        } ?: run {
            return null
        }
    }

    suspend fun pdf(context: Context, url: String, onProgress: (progress: Int) -> Unit): File? = withContext(Dispatchers.IO) {

        val coroutineResponse = requestGet(url)

        val responseFile = getFileResponse(context, coroutineResponse.response, onProgress)
        cancelCall(coroutineResponse.call)

        return@withContext responseFile
    }

    private fun copyStream(inputStream: InputStream, outputStream: OutputStream, onCopy: (totalBytesCopied: Long) -> Any) {

        var bytesCopied = 0L

        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var bytes = inputStream.read(buffer)

        while (bytes >= 0) {
            outputStream.write(buffer, 0, bytes)
            bytesCopied += bytes

            onCopy(bytesCopied)

            bytes = inputStream.read(buffer)
        }
    }

    private fun createPdfFile(context: Context, pdfName: String = "temp_pdf"): File {

        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        return File.createTempFile(
            pdfName,
            ".pdf",
            storageDir
        )
    }

}
