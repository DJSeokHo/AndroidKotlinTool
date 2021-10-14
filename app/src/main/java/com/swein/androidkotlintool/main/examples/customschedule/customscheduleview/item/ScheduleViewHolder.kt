package com.swein.androidkotlintool.main.examples.customschedule.customscheduleview.item

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.date.DateUtility
import com.swein.androidkotlintool.framework.util.display.DisplayUtility
import com.swein.androidkotlintool.framework.util.log.ILog
import java.util.*
import kotlin.math.floor

@SuppressLint("ViewConstructor")
class ScheduleViewHolder(
    context: Context,
    var scheduleModel: ScheduleModel,
    private val parentWidth: Int,
    private val unitHeight: Int,
    var startPositionY: Int,
    backgroundColor: Int = Color.TRANSPARENT
): LinearLayout(context) {

    private lateinit var textViewTitle: TextView
    private lateinit var textViewMember: TextView

    var positionY: Int = 0

    // column == -1 means view has not on layout yet
    var column: Int = -1

    init {

        inflate(context, R.layout.view_holder_schedule, this)

        setBackgroundColor(backgroundColor)

        initView()

        findView()

        updateView()
    }

    private fun findView() {

        textViewTitle = findViewById(R.id.textViewTitle)
        textViewMember = findViewById(R.id.textViewMember)
    }

    private fun initView() {

        val width = floor(parentWidth.toDouble() / 3.0).toInt()

        val minutes = DateUtility.minutesBetween(scheduleModel.startDateTime, scheduleModel.endDateTime)
//        ILog.debug("model", "minutes is $minutes")
        val height = floor(unitHeight.toDouble() * (minutes / 30)).toInt()
//        ILog.debug("model", "width is ${this.width} height is ${this.height}")

        val calendar = Calendar.getInstance()
        calendar.time = scheduleModel.startDateTime
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val hourPosition = hour * DisplayUtility.dipToPx(context, 42f)
        val minutePosition = (minute.toDouble() / 60 * DisplayUtility.dipToPx(context, 42f)).toInt()
        ILog.debug("????", "$hourPosition + $minutePosition")
        positionY = hourPosition + minutePosition
        positionY -= startPositionY
        ILog.debug(">>>>", "hour is $hour, minute is $minute")
        ILog.debug(">>>>", "positionY is $positionY because start startPositionY is $startPositionY")


        this.layoutParams = ViewGroup.LayoutParams(width, height)
    }

    fun updateView() {

        textViewTitle.text = scheduleModel.title
        textViewMember.text = scheduleModel.member

    }
}