package com.swein.androidkotlintool.framework.utility.push

import android.content.Context
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.framework.utility.okhttp.OKHttpWrapper
import com.swein.androidkotlintool.framework.utility.okhttp.OKHttpWrapperDelegate
import com.swein.androidkotlintool.framework.utility.thread.ThreadUtility
import com.swein.androidkotlintool.framework.utility.toast.ToastUtility
import okhttp3.Call
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class PushUtil {

    companion object {

        private const val TAG = "PushUtil"

        fun sendPush(context: Context, pushToken: String, title: String, message: String, successMessage: String) {

            val mutableMap: MutableMap<String, String> = mutableMapOf()

            mutableMap["Authorization"] = "key=AAAAA4s3tzQ:APA91bEUioBJrE91b2QoBDPHcWEtk0yHDWkmyf4ynxV5dK9bxHcDEK3oXTtBkjHNw-Ngdjfw3ev5sW7ptvjuIDkEfcgBez9GSKZOUcFihVaRSzJGQlAWA7EJs51bZ1NtVjtI1uoN1TuO"
            mutableMap["Content-Type"] = "application/json"

            val jsonObject = JSONObject()
            jsonObject.put("to", pushToken)
            jsonObject.put("priority", "high")

            val data = JSONObject()
            data.put("title", title)
            data.put("message", message)

            jsonObject.put("data", data)

            OKHttpWrapper.requestPost("https://fcm.googleapis.com/fcm/send", mutableMap, jsonObject = jsonObject, okHttpWrapperDelegate = object:
                OKHttpWrapperDelegate {

                override fun onFailure(call: Call, e: IOException) {
                    OKHttpWrapper.cancelCall(call)
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {

                    try {
                        val responseString = OKHttpWrapper.getStringResponse(response)
                        ILog.debug(TAG, responseString)

                        responseString?.let {
                            val responseJSONObject: JSONObject = JSONObject(responseString)

                            val success = responseJSONObject.getInt("success")

                            (success > 0).let {
                                ThreadUtility.startUIThread(0, Runnable {
                                    ToastUtility.showShortToastNormal(context, successMessage)
                                })
                            }
                        }
                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                    }
                    finally {
                        OKHttpWrapper.cancelCall(call)
                    }
                }
            })
        }
    }
}