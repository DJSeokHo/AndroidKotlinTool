package com.swein.androidkotlintool.framework.module.location.model

import android.os.Build
import org.json.JSONObject
import java.lang.Exception

class LocationModel {

    var provider: String? = null

    /* 위도 */
    var latitude: Double? = null

    /* 경도 */
    var longitude: Double? = null

    /* 수평 정확도 */
    var accuracy: Float? = null

    /* 수직 정확도 (API > Android Oreo)*/
    var verticalAccuracy: Float? = null

    override fun toString(): String {

        val jsonObject = JSONObject()

        try {
            jsonObject.put("provider", provider)
            jsonObject.put("latitude", latitude)
            jsonObject.put("longitude", longitude)
            jsonObject.put("accuracy", accuracy)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                jsonObject.put("verticalAccuracy", verticalAccuracy)
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

        return super.toString()
    }

}