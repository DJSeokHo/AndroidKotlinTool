package com.swein.androidkotlintool.framework.utility.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GsonUtil {

    companion object {

        fun createGsonStringWithObject(obj: Any): String {
            return Gson().toJson(obj)
        }

        fun createPrettyGsonStringWithObject(obj: Any): String {
            val gsonBuilder = GsonBuilder()
            gsonBuilder.setPrettyPrinting()
            val gson = gsonBuilder.create()
            return gson.toJson(obj)
        }

        /**
         * if value has Date Type
         */
        fun createDateAnalysisGsonStringWithObject(obj: Any): String {
            val gsonBuilder = GsonBuilder()
            gsonBuilder.setPrettyPrinting()
            gsonBuilder.setDateFormat("yyyy-MM-dd")
            val gson = gsonBuilder.create()
            return gson.toJson(obj)
        }
    }
}