package com.swein.androidkotlintool.framework.util.okhttp

import com.swein.androidkotlintool.framework.util.log.ILog
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.lang.Exception

/**
 *
 * After Android 9.0
 * OKHttp can not access http
 *
 * so add this in AndroidManifest.xml between the <application></application>
 *
 * <uses-library android:name="org.apache.http.legacy" android:required="false"/>
 *
 * and add android:usesCleartextTraffic="true" in the <application>
 */

private var okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

object OKHttpWrapper {

    const val TAG = "OKHttpWrapper"

    fun cancelCall(call: Call) {
        if (!call.isCanceled()) {
            call.cancel()
        }
    }

    fun getStringResponse(response: Response, defaultResponse: String = ""): String {
        ILog.debug(TAG, "onResponse: $response")
        val responseBody = response.body

        responseBody?.let {
            return try {
                it.string()
            } catch (e: Exception) {
                "exception ${e.message.toString()}"
            }

        } ?: run {
            return defaultResponse
        }
    }

    /**
     * for callback
     */
    fun requestGet(
        url: String,
        header: MutableMap<String, String>? = null,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val request = builder.get().url(url).build()
        val call = okHttpClient.newCall(request)

        // auto  thread
        call.execute()
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpWrapperDelegate.onFailure(call, e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                okHttpWrapperDelegate.onResponse(call, response)
            }
        })
    }

    /**
     * for coroutine
     */
    fun requestGet(
        url: String,
        header: MutableMap<String, String>? = null
    ): Response {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val request = builder.get().url(url).build()
        val call = okHttpClient.newCall(request)

        val response = call.execute()
        cancelCall(call)
        return response
    }

    /**
     * for callback
     */
    fun requestPost(
        url: String,
        header: MutableMap<String, String>? = null,
        formData: MutableMap<String, String>? = null,
        fileList: MutableList<String>? = null,
        fileNameList: MutableList<String>? = null,
        fileKey: String = "",
        jsonObject: JSONObject? = null,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val requestBody: RequestBody
        // if form data
        if (formData != null || fileList != null || fileNameList != null) {

            val multipartBodyBuilder: MultipartBody.Builder = MultipartBody.Builder()
            multipartBodyBuilder.setType(MultipartBody.FORM)

            formData?.let {
                ILog.debug(TAG, "add form data")
                for ((key, value) in formData) {
                    ILog.debug(TAG, "$key $value")
                    multipartBodyBuilder.addFormDataPart(key, value)
                }
            }

            if (fileList != null && fileNameList != null) {
                ILog.debug(TAG, "add files")
                var file: File
                for (i in fileList.indices) {
                    ILog.debug(TAG, fileNameList[i])
                    val mediaType: MediaType = if (fileList[i].endsWith("png")) {
                        "image/png".toMediaType()
                    } else {
                        "image/jpeg".toMediaType()
                    }
                    file = File(fileList[i])
                    multipartBodyBuilder.addFormDataPart(fileKey, fileNameList[i], RequestBody.create(mediaType, file))
                }
            }

            requestBody = multipartBodyBuilder.build()
        }
        else {

            val mediaType: MediaType = "application/x-www-form-urlencoded; charset=utf-8".toMediaType()

            // if jsonObject
            requestBody = if (jsonObject == null) {
                RequestBody.create(mediaType, "")
            }
            else {
                RequestBody.create(mediaType, jsonObject.toString())
            }
        }

        val request = builder.post(requestBody).url(url).build()
        val call = okHttpClient.newCall(request)

        // auto  thread
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpWrapperDelegate.onFailure(call, e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                okHttpWrapperDelegate.onResponse(call, response)
            }
        })
    }

    /**
     * for coroutine
     */
    fun requestPost(
        url: String,
        header: MutableMap<String, String>? = null,
        formData: MutableMap<String, String>? = null,
        fileList: MutableList<String>? = null,
        fileNameList: MutableList<String>? = null,
        fileKey: String = "",
        jsonObject: JSONObject? = null
    ): Response {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val requestBody: RequestBody
        // if form data
        if (formData != null || fileList != null || fileNameList != null) {

            val multipartBodyBuilder: MultipartBody.Builder = MultipartBody.Builder()
            multipartBodyBuilder.setType(MultipartBody.FORM)

            formData?.let {
                ILog.debug(TAG, "add form data")
                for ((key, value) in formData) {
                    ILog.debug(TAG, "$key $value")
                    multipartBodyBuilder.addFormDataPart(key, value)
                }
            }

            if (fileList != null && fileNameList != null) {
                ILog.debug(TAG, "add files")
                var file: File
                for (i in fileList.indices) {
                    ILog.debug(TAG, fileNameList[i])
                    val mediaType: MediaType = if (fileList[i].endsWith("png")) {
                        "image/png".toMediaType()
                    } else {
                        "image/jpeg".toMediaType()
                    }
                    file = File(fileList[i])
                    multipartBodyBuilder.addFormDataPart(fileKey, fileNameList[i], RequestBody.create(mediaType, file))
                }
            }

            requestBody = multipartBodyBuilder.build()
        }
        else {

            val mediaType: MediaType = "application/x-www-form-urlencoded; charset=utf-8".toMediaType()

            // if jsonObject
            requestBody = if (jsonObject == null) {
                RequestBody.create(mediaType, "")
            }
            else {
                RequestBody.create(mediaType, jsonObject.toString())
            }
        }

        val request = builder.post(requestBody).url(url).build()
        val call = okHttpClient.newCall(request)

        val response = call.execute()
        cancelCall(call)
        return response
    }

    /**
     * for callback
     */
    fun requestPut(
        url: String,
        header: MutableMap<String, String>? = null,
        formData: MutableMap<String, String>? = null,
        fileList: MutableList<String>? = null,
        fileNameList: MutableList<String>? = null,
        fileKey: String = "",
        jsonObject: JSONObject? = null,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val requestBody: RequestBody
        // if form data
        if (formData != null || fileList != null || fileNameList != null) {

            val multipartBodyBuilder: MultipartBody.Builder = MultipartBody.Builder()
            multipartBodyBuilder.setType(MultipartBody.FORM)

            formData?.let {
                ILog.debug(TAG, "add form data")
                for ((key, value) in formData) {
                    ILog.debug(TAG, "$key $value")
                    multipartBodyBuilder.addFormDataPart(key, value)
                }
            }

            if (fileList != null && fileNameList != null) {
                ILog.debug(TAG, "add files")
                var file: File
                for (i in fileList.indices) {
                    ILog.debug(TAG, fileNameList[i])
                    val mediaType: MediaType = if (fileList[i].endsWith("png")) {
                        "image/png".toMediaType()
                    } else {
                        "image/jpeg".toMediaType()
                    }
                    file = File(fileList[i])
                    multipartBodyBuilder.addFormDataPart(fileKey, fileNameList[i], RequestBody.create(mediaType, file))
                }
            }

            requestBody = multipartBodyBuilder.build()
        }
        else {

            val mediaType: MediaType = "application/x-www-form-urlencoded; charset=utf-8".toMediaType()

            // if jsonObject
            requestBody = if (jsonObject == null) {
                RequestBody.create(mediaType, "")
            }
            else {
                RequestBody.create(mediaType, jsonObject.toString())
            }
        }

        val request = builder.put(requestBody).url(url).build()
        val call = okHttpClient.newCall(request)

        // auto  thread
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpWrapperDelegate.onFailure(call, e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                okHttpWrapperDelegate.onResponse(call, response)
            }
        })
    }

    /**
     * for coroutine
     */
    fun requestPut(
        url: String,
        header: MutableMap<String, String>? = null,
        formData: MutableMap<String, String>? = null,
        fileList: MutableList<String>? = null,
        fileNameList: MutableList<String>? = null,
        fileKey: String = "",
        jsonObject: JSONObject? = null
    ): Response {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val requestBody: RequestBody
        // if form data
        if (formData != null || fileList != null || fileNameList != null) {

            val multipartBodyBuilder: MultipartBody.Builder = MultipartBody.Builder()
            multipartBodyBuilder.setType(MultipartBody.FORM)

            formData?.let {
                ILog.debug(TAG, "add form data")
                for ((key, value) in formData) {
                    ILog.debug(TAG, "$key $value")
                    multipartBodyBuilder.addFormDataPart(key, value)
                }
            }

            if (fileList != null && fileNameList != null) {
                ILog.debug(TAG, "add files")
                var file: File
                for (i in fileList.indices) {
                    ILog.debug(TAG, fileNameList[i])
                    val mediaType: MediaType = if (fileList[i].endsWith("png")) {
                        "image/png".toMediaType()
                    } else {
                        "image/jpeg".toMediaType()
                    }
                    file = File(fileList[i])
                    multipartBodyBuilder.addFormDataPart(fileKey, fileNameList[i], RequestBody.create(mediaType, file))
                }
            }

            requestBody = multipartBodyBuilder.build()
        }
        else {

            val mediaType: MediaType = "application/x-www-form-urlencoded; charset=utf-8".toMediaType()

            // if jsonObject
            requestBody = if (jsonObject == null) {
                RequestBody.create(mediaType, "")
            }
            else {
                RequestBody.create(mediaType, jsonObject.toString())
            }
        }

        val request = builder.put(requestBody).url(url).build()
        val call = okHttpClient.newCall(request)

        val response = call.execute()
        cancelCall(call)
        return response
    }

    /**
     * for callback
     */
    fun requestDelete(
        url: String,
        header: MutableMap<String, String>? = null,
        formData: MutableMap<String, String>? = null,
        fileList: MutableList<String>? = null,
        fileNameList: MutableList<String>? = null,
        fileKey: String = "",
        jsonObject: JSONObject? = null,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val requestBody: RequestBody
        // if form data
        if (formData != null || fileList != null || fileNameList != null) {

            val multipartBodyBuilder: MultipartBody.Builder = MultipartBody.Builder()
            multipartBodyBuilder.setType(MultipartBody.FORM)

            formData?.let {
                ILog.debug(TAG, "add form data")
                for ((key, value) in formData) {
                    ILog.debug(TAG, "$key $value")
                    multipartBodyBuilder.addFormDataPart(key, value)
                }
            }

            if (fileList != null && fileNameList != null) {
                ILog.debug(TAG, "add files")
                var file: File
                for (i in fileList.indices) {
                    ILog.debug(TAG, fileNameList[i])
                    val mediaType: MediaType = if (fileList[i].endsWith("png")) {
                        "image/png".toMediaType()
                    } else {
                        "image/jpeg".toMediaType()
                    }
                    file = File(fileList[i])
                    multipartBodyBuilder.addFormDataPart(fileKey, fileNameList[i], RequestBody.create(mediaType, file))
                }
            }

            requestBody = multipartBodyBuilder.build()
        }
        else {

            val mediaType: MediaType = "application/x-www-form-urlencoded; charset=utf-8".toMediaType()

            // if jsonObject
            requestBody = if (jsonObject == null) {
                RequestBody.create(mediaType, "")
            }
            else {
                RequestBody.create(mediaType, jsonObject.toString())
            }
        }

        val request = builder.delete(requestBody).url(url).build()
        val call = okHttpClient.newCall(request)

        // auto  thread
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpWrapperDelegate.onFailure(call, e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                okHttpWrapperDelegate.onResponse(call, response)
            }
        })
    }

    /**
     * for coroutine
     */
    fun requestDelete(
        url: String,
        header: MutableMap<String, String>? = null,
        formData: MutableMap<String, String>? = null,
        fileList: MutableList<String>? = null,
        fileNameList: MutableList<String>? = null,
        fileKey: String = "",
        jsonObject: JSONObject? = null
    ): Response {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val requestBody: RequestBody
        // if form data
        if (formData != null || fileList != null || fileNameList != null) {

            val multipartBodyBuilder: MultipartBody.Builder = MultipartBody.Builder()
            multipartBodyBuilder.setType(MultipartBody.FORM)

            formData?.let {
                ILog.debug(TAG, "add form data")
                for ((key, value) in formData) {
                    ILog.debug(TAG, "$key $value")
                    multipartBodyBuilder.addFormDataPart(key, value)
                }
            }

            if (fileList != null && fileNameList != null) {
                ILog.debug(TAG, "add files")
                var file: File
                for (i in fileList.indices) {
                    ILog.debug(TAG, fileNameList[i])
                    val mediaType: MediaType = if (fileList[i].endsWith("png")) {
                        "image/png".toMediaType()
                    } else {
                        "image/jpeg".toMediaType()
                    }
                    file = File(fileList[i])
                    multipartBodyBuilder.addFormDataPart(fileKey, fileNameList[i], RequestBody.create(mediaType, file))
                }
            }

            requestBody = multipartBodyBuilder.build()
        }
        else {

            val mediaType: MediaType = "application/x-www-form-urlencoded; charset=utf-8".toMediaType()

            // if jsonObject
            requestBody = if (jsonObject == null) {
                RequestBody.create(mediaType, "")
            }
            else {
                RequestBody.create(mediaType, jsonObject.toString())
            }
        }

        val request = builder.delete(requestBody).url(url).build()
        val call = okHttpClient.newCall(request)

        val response = call.execute()
        cancelCall(call)
        return response
    }
}