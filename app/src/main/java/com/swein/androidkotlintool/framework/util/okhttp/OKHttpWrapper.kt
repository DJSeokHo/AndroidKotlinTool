package com.swein.androidkotlintool.framework.util.okhttp

import com.swein.androidkotlintool.framework.util.log.ILog
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.HashMap

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
object OKHttpWrapper {

    const val TAG = "OKHttpWrapper"

    private var okHttpClient: OkHttpClient? = null

    fun cancelCall(call: Call) {
        if (!call.isCanceled()) {
            call.cancel()
        }
    }

    fun getStringResponse(response: Response): String? {
        ILog.debug(TAG, "onResponse: $response")
        val responseBody = response.body ?: return ""
        return responseBody.string()
    }

    fun getStringResponseWithCustomHeader(response: Response): String? {
        ILog.debug(TAG, "onResponse: $response")
        val responseBody = response.body ?: return ""
        return responseBody.string()
    }

    fun requestGet(url: String?, okHttpWrapperDelegate: OKHttpWrapperDelegate) {
        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val builder = Request.Builder()
        val request = builder.get().url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostJSONWithHeader(url: String, header: MutableMap<String, String>, jsonObject: JSONObject, okHttpWrapperDelegate: OKHttpWrapperDelegate) {

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

    fun requestGetWithHeader(
        url: String?,
        header: HashMap<String?, String?>,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {
        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val builder = Request.Builder()
        for ((key, value) in header) {
            builder.addHeader(key!!, value!!)
        }

        val request = builder.get().url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestGetWithAuthorizationBearer(
        url: String?,
        okHttpWrapperDelegate: OKHttpWrapperDelegate,
        token: String
    ) {
        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }
        val builder = Request.Builder()
        val request = builder.get().url(url!!).addHeader(
            "Authorization",
            "Bearer $token"
        ).build()

        val call = okHttpClient!!.newCall(request)

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

    fun requestPostWithJSON(
        url: String?,
        okHttpWrapperDelegate: OKHttpWrapperDelegate,
        jsonObject: JSONObject
    ) {
        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody: RequestBody = jsonObject.toString().toRequestBody(mediaType)
        val builder = Request.Builder()
        val request = builder.url(url!!).post(requestBody).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPost(url: String?, okHttpWrapperDelegate: OKHttpWrapperDelegate) {
        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val mediaType: MediaType = "application/x-www-form-urlencoded; charset=utf-8".toMediaType()
        val jsonObject = JSONObject()
        val requestBody: RequestBody = jsonObject.toString().toRequestBody(mediaType)
        val builder = Request.Builder()
        val request = builder.url(url!!).post(requestBody).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostWithHeaderAndJSONBodyRaw(
        url: String?,
        header: HashMap<String?, String?>,
        jsonObject: JSONObject,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody: RequestBody = jsonObject.toString().toRequestBody(mediaType)
        val builder = Request.Builder()

        for ((key, value) in header) {
            builder.addHeader(key!!, value!!)
        }

        val request = builder.url(url!!).post(requestBody).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPatchWithHeaderAndJSONBodyRaw(
        url: String?,
        header: HashMap<String?, String?>,
        jsonObject: JSONObject,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody: RequestBody = jsonObject.toString().toRequestBody(mediaType)
        val builder = Request.Builder()

        for ((key, value) in header) {
            builder.addHeader(key!!, value!!)
        }

        val request = builder.url(url!!).patch(requestBody).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostWithHeader(
        url: String?,
        header: HashMap<String?, String?>,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val mediaType: MediaType = "application/x-www-form-urlencoded; charset=utf-8".toMediaType()
        val jsonObject = JSONObject()
        val requestBody: RequestBody = jsonObject.toString().toRequestBody(mediaType)
        val builder = Request.Builder()

        for ((key, value) in header) {
            builder.addHeader(key!!, value!!)
        }

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestDeleteWithHeader(
        url: String?,
        header: HashMap<String?, String?>,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val mediaType: MediaType = "application/x-www-form-urlencoded; charset=utf-8".toMediaType()
        val jsonObject = JSONObject()
        val requestBody: RequestBody = jsonObject.toString().toRequestBody(mediaType)
        val builder = Request.Builder()

        for ((key, value) in header) {
            builder.addHeader(key!!, value!!)
        }

        val request = builder.delete(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPutWithHeader(
        url: String?,
        header: HashMap<String?, String?>,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val mediaType: MediaType = "application/x-www-form-urlencoded; charset=utf-8".toMediaType()
        val jsonObject = JSONObject()
        val requestBody: RequestBody = jsonObject.toString().toRequestBody(mediaType)
        val builder = Request.Builder()

        for ((key, value) in header) {
            builder.addHeader(key!!, value!!)
        }

        val request = builder.put(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostImageFileWithOpenId(
        url: String?,
        fileName: String?,
        fileKey: String,
        file: File,
        openid: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }


        val body = file.asRequestBody("image/png".toMediaType())

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("openid", openid)
            .addFormDataPart(
                fileKey,
                fileName,
                body
            )
            .build()

        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostImageFile(
        url: String?,
        fileName: String?,
        fileKey: String,
        filePath: String,
        header: HashMap<String?, String?>,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val file = File(filePath)

        val body = file.asRequestBody("image/png".toMediaType())

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "profileImageFile",
                fileName,
                body
            )
            .build()

        val builder = Request.Builder()

        for ((key, value) in header) {
            builder.addHeader(key!!, value!!)
        }

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataId(
        url: String?,
        affiliateId: Int,
        header: HashMap<String?, String?>,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("affiliateId", affiliateId.toString())
            .build()
        val builder = Request.Builder()

        for ((key, value) in header) {
            builder.addHeader(key!!, value!!)
        }

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataMenu(
        url: String?,
        parent: String,
        code: String,
        load: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("parent", parent)
            .add("code", code)
            .add("load", load)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataRecordList(
        url: String?,
        openid: String,
        pageNum: String,
        pageSize: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("openid", openid)
            .add("pageNum", pageNum)
            .add("pageSize", pageSize)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataNickname(
        url: String?,
        id: String,
        nickname: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("id", id)
            .add("nickName", nickname)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataRent(
        url: String?,
        memberId: String,
        equipCode: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("memberId", memberId)
            .add("equipCode", equipCode)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataUnBindingCard(
        url: String?,
        openid: String, // 用户openid
        card_number: String, // 银行卡号
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("openid", openid)
            .add("card_number", card_number)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataSearchDevice(
        url: String?,
        codeString: String,
        sources: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("codeString", codeString)
            .add("sources", sources)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataChangePW(
        url: String?,
        openid: String,
        oldPassword: String,
        newPassword: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("openid", openid)
            .add("oldPassword", oldPassword)
            .add("newPassword", newPassword)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataResetPW(
        url: String?,
        email: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("email", email)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataFindEmail(
        url: String?,
        areaCode: String,
        phone: String,
        verifyCode: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("areaCode", areaCode)
            .add("phone", phone)
            .add("verifyCode", verifyCode)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestUnBindUserPhone(
        url: String?,
        ereaCode: String, phone: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("ereaCode", ereaCode)
            .add("phone", phone)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataIsBindOtherLogin(
        url: String?,
        ereaCode: String, phone: String, email: String, fb_responseId: String, naverId: String, kakaoId: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("ereaCode", ereaCode)
            .add("phone", phone)
            .add("email", email)
            .add("fb_responseId", fb_responseId)
            .add("naverId", naverId)
            .add("kakaoId", kakaoId)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataEmailPassword(
        url: String?,
        username: String,
        password: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("username", username)
            .add("password", password)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataNaverLogin(
        url: String?,
        email: String,
        naverId: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("email", email)
            .add("naverId", naverId)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataFacebookLogin(
        url: String?,
        email: String,
        fb_responseId: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("email", email)
            .add("fb_responseId", fb_responseId)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataKakaoLogin(
        url: String?,
        email: String,
        kakaoId: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("email", email)
            .add("kakaoId", kakaoId)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataUserInfo(
        url: String?,
        openId: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("openid", openId)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostFormDataRegisterEmail(
        url: String?,
        nickName: String,
        email: String,
        password: String,
        areaCode: String,
        phone: String,
        verifyCode: String,
        fb_responseId: String,
        naverId: String,
        kakaoId: String,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val requestBody: RequestBody = FormBody.Builder()
            .add("nickName", nickName)
            .add("email", email)
            .add("password", password)
            .add("ereaCode", areaCode) // ereaCode is not a error word, because server API is ereaCode
            .add("phone", phone)
            .add("verifyCode", verifyCode)
            .add("fb_responseId", fb_responseId)
            .add("naverId", naverId)
            .add("kakaoId", kakaoId)
            .build()
        val builder = Request.Builder()

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostWithMultipartFormDataJSONAndFiles(
        url: String?, header: HashMap<String?, String?>,
        affiliateId: String?, content: String?, rateTotal: String?, imageList: List<String>,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val multipartBodyBuilder = MultipartBody.Builder()
        multipartBodyBuilder.setType(MultipartBody.FORM)
        multipartBodyBuilder.addFormDataPart("affiliateId", affiliateId!!)
        multipartBodyBuilder.addFormDataPart("content", content!!)
        multipartBodyBuilder.addFormDataPart("rateTotal", rateTotal!!)

        var file: File
        for (i in imageList.indices) {
            val mediaType: MediaType =
                if (imageList[i].endsWith("png")) {
                    "image/png".toMediaType()
                }
                else {
                    "image/jpeg".toMediaType()
                }
            file = File(imageList[i])
            multipartBodyBuilder.addFormDataPart(
                "imageFile",
                file.name,
                RequestBody.create(mediaType, file)
            )
        }

        val requestBody: RequestBody = multipartBodyBuilder.build()
        val builder = Request.Builder()

        for ((key, value) in header) {
            builder.addHeader(key!!, value!!)
        }

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

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

    fun requestPostWithMultipartFormDataJSONAndFiles(
        url: String?,
        header: HashMap<String?, String?>,
        content: String?,
        rateTotal: String?,
        imageList: List<String>,
        imageWillKeepList: List<String?>,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val multipartBodyBuilder = MultipartBody.Builder()
        multipartBodyBuilder.setType(MultipartBody.FORM)
        multipartBodyBuilder.addFormDataPart("content", content!!)
        multipartBodyBuilder.addFormDataPart("rateTotal", rateTotal!!)

        if (imageWillKeepList.isEmpty()) {
            multipartBodyBuilder.addFormDataPart("imageUrl", "")
        }
        else {
            val stringBuilder = StringBuilder()
            for (i in imageWillKeepList.indices) {
                stringBuilder.append(imageWillKeepList[i])
                if (i < imageWillKeepList.size - 1) {
                    stringBuilder.append(",")
                }
            }
            ILog.debug(TAG, "?????/ $stringBuilder")
            multipartBodyBuilder.addFormDataPart("imageUrl", stringBuilder.toString())
        }

        var file: File
        for (i in imageList.indices) {
            val mediaType: MediaType =
                if (imageList[i].endsWith("png")) {
                    "image/png".toMediaType()
                }
                else {
                    "image/jpeg".toMediaType()
                }
            file = File(imageList[i])
            multipartBodyBuilder.addFormDataPart(
                "imageFile",
                file.name,
                RequestBody.create(mediaType, file)
            )
        }

        val requestBody: RequestBody = multipartBodyBuilder.build()
        val builder = Request.Builder()

        for ((key, value) in header) {
            builder.addHeader(key!!, value!!)
        }

        val request = builder.post(requestBody).url(url!!).build()
        val call = okHttpClient!!.newCall(request)

        // auto  thread
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpWrapperDelegate.onFailure(call, e)
            }

            override fun onResponse(call: Call, response: Response) {
                okHttpWrapperDelegate.onResponse(call, response)
            }
        })
    }


    /**
     * example:
     *
     * OKHttpWrapper.getInstance().requestDownloadFile("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564642051087&di=a5a0ecddbceeaf44e2b55d0439eaed44&imgtype=0&src=http%3A%2F%2Fen.pimg.jp%2F017%2F747%2F873%2F1%2F17747873.jpg",
     * new OKHttpWrapper.OKHttpWrapperDelegate() {
     * @Override
     * public void onFailure(@NotNull Call call, @NotNull IOException e) {
     * OKHttpWrapper.getInstance().cancelCall(call);
     * }
     *
     * @Override
     * public void onResponse(@NotNull Call call, @NotNull Response response) {
     * try {
     * OKHttpWrapper.getInstance().storageFileResponse(response, Environment.getExternalStorageDirectory().toString() + "/default.png");
     * }
     * catch (IOException e) {
     * e.printStackTrace();
     * }
     * finally {
     * OKHttpWrapper.getInstance().cancelCall(call);
     * }
     * }
     * });
     */
    fun requestDownloadFile(url: String?, okHttpWrapperDelegate: OKHttpWrapperDelegate) {
        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
            okHttpClient = builder.build()
        }

        val request = Request.Builder().url(url!!).build()
        val call = okHttpClient!!.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpWrapperDelegate.onFailure(call, e)
            }

            override fun onResponse(call: Call, response: Response) {
                okHttpWrapperDelegate.onResponse(call, response)
            }
        })
    }

    fun storageFileResponse(response: Response, fileName: String?) {
        val responseBody = response.body ?: return
        val file = File(fileName)

        if (!file.exists()) {
            file.createNewFile()
        }

        val buf = ByteArray(2048)
        var len = 0
        var fileOutputStream: FileOutputStream? = null
        var inputStream: InputStream? = null

        try {
            inputStream = responseBody.byteStream()
            fileOutputStream = FileOutputStream(file)
            while (inputStream.read(buf).also { len = it } != -1) {
                fileOutputStream.write(buf, 0, len)
            }
            fileOutputStream.flush()
        }
        catch (e: IOException) {
            e.printStackTrace()
        }
        finally {
            fileOutputStream?.close()
            inputStream?.close()
        }
    }

    fun clear() {
        if (okHttpClient != null) {
            okHttpClient = null
        }
    }
}