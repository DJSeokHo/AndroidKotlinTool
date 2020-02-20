package com.swein.androidkotlintool.framework.util.push

import android.content.Context
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.okhttp.OKHttpWrapper
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil
import com.swein.androidkotlintool.framework.util.toast.ToastUtil
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

            OKHttpWrapper.getInstance().requestPostJSONWithHeader("https://fcm.googleapis.com/fcm/send", mutableMap, jsonObject, object: OKHttpWrapper.OKHttpWrapperDelegate {

                override fun onFailure(call: Call, e: IOException) {
                    OKHttpWrapper.getInstance().cancelCall(call)
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {

                    try {
                        val responseString = OKHttpWrapper.getInstance().getStringResponse(response)
                        ILog.debug(TAG, responseString)

                        responseString?.let {
                            val responseJSONObject: JSONObject = JSONObject(responseString)

                            val success = responseJSONObject.getInt("success")

                            (success > 0).let {
                                ThreadUtil.startUIThread(0, Runnable {
                                    ToastUtil.showShortToastNormal(context, successMessage)
                                })
                            }
                        }
                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                    }
                    finally {
                        OKHttpWrapper.getInstance().cancelCall(call)
                    }
                }
            })
        }
    }
}