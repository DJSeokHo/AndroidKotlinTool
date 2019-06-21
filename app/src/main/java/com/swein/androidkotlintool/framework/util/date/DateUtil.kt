package com.swein.androidkotlintool.framework.util.date

import android.annotation.SuppressLint
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {

        fun getCurrentDateTimeString(): String {
            val calendar: Calendar = Calendar.getInstance()
            return "${calendar.get(Calendar.YEAR)}-${(calendar.get(Calendar.MONTH) + 1)}-${calendar.get(Calendar.DAY_OF_MONTH)} ${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}:${calendar.get(Calendar.SECOND)}"
        }

        @SuppressLint("SimpleDateFormat")
        fun getDayOfWeekOfDateTime(date: String): String {
            val weekDays: List<String> = listOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")

            val cal: Calendar = Calendar.getInstance()

            try {
                cal.time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date)

                var w: Int = cal.get(Calendar.DAY_OF_WEEK) - 1
                if (w < 0) {
                    w = 0
                }

                return weekDays[w]
            }
            catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }

    }
}