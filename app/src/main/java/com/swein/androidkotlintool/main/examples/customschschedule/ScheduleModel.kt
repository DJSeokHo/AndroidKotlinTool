package com.swein.androidkotlintool.main.examples.customschschedule

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import com.swein.androidkotlintool.framework.util.date.DateUtility
import com.swein.androidkotlintool.framework.util.display.DisplayUtility
import com.swein.androidkotlintool.framework.util.log.ILog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

data class ScheduleModel(
    val title: String,
    val member: String,
    val startDateTimeString: String,
    var endDateTimeString: String
) {
    var startDateTime = Date()
    var endDateTime = Date()
    var width = 0
    var height = 0
    var columnIndex = 0
    var topY = 0

    fun init(parentWidth: Int, unitHeight: Int) {
        parsingData()
        calculateSize(parentWidth, unitHeight)
    }

    @SuppressLint("SimpleDateFormat")
    private fun parsingData() {
        this.startDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDateTimeString)
        this.endDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDateTimeString)
    }

    private fun calculateSize(parentWidth: Int, unitHeight: Int) {

        this.width = floor(parentWidth.toDouble() / 3.0).toInt()

        val minutes = DateUtility.minutesBetween(startDateTime, endDateTime)

//        ILog.debug("model", "minutes is $minutes")
        this.height = floor(unitHeight.toDouble() * (minutes / 30)).toInt()

//        ILog.debug("model", "width is ${this.width} height is ${this.height}")
    }

}