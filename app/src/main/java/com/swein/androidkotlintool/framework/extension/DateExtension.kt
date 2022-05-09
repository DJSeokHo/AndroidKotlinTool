package kr.co.dotv365.android.framework.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date.toDateString(pattern: String): String = try {
    SimpleDateFormat(pattern).format(this)
    }
    catch (e: Exception) {
        e.printStackTrace()
        SimpleDateFormat(pattern).format(Date())
    }