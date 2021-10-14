package com.swein.androidkotlintool.main.examples.customschedule.customscheduleview.item

import java.text.SimpleDateFormat
import java.util.*

data class ScheduleModel(
    val title: String,
    val member: String,
    val startDateTimeString: String,
    var endDateTimeString: String
) {
    var startDateTime: Date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDateTimeString)
    var endDateTime: Date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDateTimeString)
}