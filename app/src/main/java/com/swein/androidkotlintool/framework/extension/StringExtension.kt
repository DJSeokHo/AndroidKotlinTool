package kr.co.dotv365.android.framework.extension

import android.annotation.SuppressLint
import java.net.URLDecoder
import java.net.URLEncoder
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun String.toDate(pattern: String): Date = try {
        SimpleDateFormat(pattern).parse(this)!!
    }
    catch (e: Exception) {
        e.printStackTrace()
        Date()
    }

fun String.addComma(): String = DecimalFormat(",###").format(this.toDouble())


fun String.encodeUTF8(): String = URLEncoder.encode(this, "UTF-8")

fun String.decodeUTF8(): String = URLDecoder.decode(this, "UTF-8")