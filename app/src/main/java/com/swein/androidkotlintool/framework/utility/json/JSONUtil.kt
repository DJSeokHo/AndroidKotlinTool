package com.swein.androidkotlintool.framework.utility.json

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.HashMap

class JSONUtil {

    @Throws(JSONException::class)
    fun createJSONObjectWithList(keyList: List<String>, list: List<*>): JSONObject {
        val jsonObject = JSONObject()

        for (i in keyList.indices) {
            jsonObject.put(keyList[i], list[i])
        }

        return jsonObject
    }

    fun createJSONObjectWithMap(map: Map<String, Any>): JSONObject {

        return JSONObject(map)
    }

    fun jsonObjectListToJSonString(list: List<*>): String {
        return JSONArray(list).toString()
    }

    fun jsonArrayToJSonString(jsonArray: JSONArray): String {
        return jsonArray.toString()
    }

    fun jsonStringToMap(string: String): Map<String, String>? {

        val jsonArray: JSONArray
        var hashMap: MutableMap<String, String>? = null

        try {

            jsonArray = JSONArray(string)
            hashMap = HashMap()

            for (i in 0 until jsonArray.length()) {

                val j = jsonArray.optJSONObject(i)
                val it = j.keys()

                while (it.hasNext()) {
                    val n = it.next().toString()
                    hashMap[n] = j.getString(n)
                }

            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return hashMap
    }

    fun jsonArrayToMap(jsonArray: JSONArray): Map<String, String>? {

        var hashMap: MutableMap<String, String>? = null

        try {

            hashMap = HashMap()

            for (i in 0 until jsonArray.length()) {

                val j = jsonArray.optJSONObject(i)
                val it = j.keys()

                while (it.hasNext()) {
                    val n = it.next().toString()
                    hashMap[n] = j.getString(n)
                }

            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return hashMap
    }

    fun jsonArrayToMapForJSONFileFromXML(jsonArray: JSONArray, key: String, value: String): Map<String, String>? {

        var hashMap: MutableMap<String, String>? = null

        try {
            hashMap = HashMap()
            for (i in 0 until jsonArray.length()) {
                hashMap[jsonArray.getJSONObject(i).getString(key)] = jsonArray.getJSONObject(i).getString(value)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return hashMap
    }
}