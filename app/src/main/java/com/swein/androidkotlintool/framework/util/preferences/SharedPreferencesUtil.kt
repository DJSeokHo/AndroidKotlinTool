package com.swein.androidkotlintool.framework.util.preferences

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import java.lang.Exception

class SharedPreferencesUtil {

    companion object {
        private const val KEY = "com.swein.androidkotlintool"

        /**
         *  MODE_PRIVATE: can only edit by this app
         */
        fun putValue(context: Context, key: String, value: String)
        {
            val pref: SharedPreferences = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
            val editor: SharedPreferences.Editor  = pref.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun putValue(context: Context, key: String, value: Int)
        {
            val pref: SharedPreferences = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
            val editor: SharedPreferences.Editor  = pref.edit()
            editor.putInt(key, value)
            editor.apply()
        }

        fun putValue(context: Context, key: String, value: Boolean)
        {
            val pref: SharedPreferences = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
            val editor: SharedPreferences.Editor  = pref.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        fun getValue(context: Context, key: String, dftValue: Boolean): Boolean
        {
            val pref: SharedPreferences = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
            try {
                return pref.getBoolean(key, dftValue)
            }
            catch (e: Exception) {
                return dftValue
            }
        }

        fun getValue(context: Context, key: String, dftValue: String): String?
        {
            val pref: SharedPreferences = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
            try {
                return pref.getString(key, dftValue)
            }
            catch (e: Exception) {
                return dftValue
            }
        }

        fun getValue(context: Context, key: String, dftValue: Int): Int
        {
            val pref: SharedPreferences = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
            try {
                return pref.getInt(key, dftValue)
            }
            catch (e: Exception) {
                return dftValue
            }
        }

        fun isContainValue(context: Context, key: String): Boolean
        {
            val pref: SharedPreferences = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)

            return pref.contains(key)
        }

        fun getAllKeyValue(context: Context): MutableMap<String, *>?
        {
            val pref: SharedPreferences = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
            return pref.all
        }

        @SuppressLint("CommitPrefEdits")
        fun clearSharedPreferencesData(context: Context)
        {
            val pref: SharedPreferences = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
            val editor: SharedPreferences.Editor  = pref.edit()
            editor.clear()
        }

        @SuppressLint("CommitPrefEdits")
        fun removeValue(context: Context, key: String)
        {
            val pref: SharedPreferences = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
            val editor: SharedPreferences.Editor  = pref.edit()
            editor.remove(key)
        }
    }
    
}