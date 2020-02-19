package com.swein.androidkotlintool.framework.util.okhttp

import com.swein.androidkotlintool.framework.util.log.ILog
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import java.io.IOException

object OKHttpWrapper {

    private const val TAG = "OKHttpWrapper"

    interface OKHttpWrapperDelegate {
        fun onFailure(call: Call, e: IOException)
        fun onResponse(call: Call, response: Response)
    }

    fun getInstance(): OKHttpWrapper {
        return this
    }

    private var okHttpClient: OkHttpClient? = null

    fun cancelCall(call: Call) {
        if(!call.isCanceled()) {
            call.cancel()
        }
    }

    fun clear() {
        if(okHttpClient != null) {
            okHttpClient = null
        }
    }

    fun getStringResponse(response: Response): String? {
        ILog.debug(
            TAG,
            "onResponse: $response"
        )
        val responseBody = response.body ?: return ""
        return responseBody.string()
    }

    fun getStringResponseWithCustomHeader(response: Response): String? {
        ILog.debug(
            TAG,
            "onResponse: $response"
        )
        val responseBody = response.body ?: return ""
        return responseBody.string()
    }

    fun requestPostJSONWithHeader(url: String, header: HashMap<String, String>, jsonObject: JSONObject, okHttpWrapperDelegate: OKHttpWrapperDelegate) {

        if(okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val builder: Request.Builder = Request.Builder()

        header.forEach {
            builder.addHeader(it.key, it.value)
        }

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = RequestBody.create(mediaType, jsonObject.toString())

        val request = builder.post(requestBody).url(url).build()
        val call = okHttpClient!!.newCall(request)

        // auto thread
        call.enqueue(object: Callback {

            override fun onFailure(call: Call, e: IOException) {
                okHttpWrapperDelegate.onFailure(call, e)
            }

            override fun onResponse(call: Call, response: Response) {
                okHttpWrapperDelegate.onResponse(call, response)
            }

        })

    }
}