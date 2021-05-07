package com.swein.androidkotlintool.framework.util.preferences

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.swein.androidkotlintool.BuildConfig

class SharedPreferencesUtil {

    companion object {
        private const val KEY = BuildConfig.APPLICATION_ID

        private lateinit var sharedPreferences: SharedPreferences

        /**
         * put this before main activity start
         */
        fun init(context: Context) {
            sharedPreferences = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
        }

        fun putValue(key: String, value: String) {
            sharedPreferences.edit().putString(key, value).apply()
        }

        fun putValue(key: String, value: Int) {
            sharedPreferences.edit().putInt(key, value).apply()
        }

        fun putValue(key: String, value: Long) {
            sharedPreferences.edit().putLong(key, value).apply()
        }

        fun putValue(key: String, value: Float) {
            sharedPreferences.edit().putFloat(key, value).apply()
        }

        fun putValue(key: String, value: Boolean) {
            sharedPreferences.edit().putBoolean(key, value).apply()
        }

        fun getValue(key: String, dftValue: Boolean): Boolean {
            return sharedPreferences.getBoolean(key, dftValue)
        }

        fun getValue(key: String, dftValue: String): String {
            return sharedPreferences.getString(key, dftValue)!!
        }

        fun getValue(key: String, dftValue: Int): Int {
            return sharedPreferences.getInt(key, dftValue)
        }

        fun getValue(key: String, dftValue: Long): Long {
            return sharedPreferences.getLong(key, dftValue)
        }

        fun getValue(key: String, dftValue: Float): Float {
            return sharedPreferences.getFloat(key, dftValue)
        }

        fun isContainValue(key: String): Boolean {
            return sharedPreferences.contains(key)
        }

        fun getAllKeyValue(): Map<*, *> {
            return sharedPreferences.all
        }

        fun clearSharedPreferencesData() {
            sharedPreferences.edit().clear().apply()
        }

        fun removeValue(key: String) {
            sharedPreferences.edit().remove(key).apply()
        }
    }
    
}