package com.swein.androidkotlintool.framework.extension

import android.annotation.SuppressLint
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
