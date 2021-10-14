package com.swein.androidkotlintool.main.examples.customschedule.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.date.DateUtility
import com.swein.androidkotlintool.framework.util.display.DisplayUtility
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.customschedule.ScheduleTimeViewHolder
import com.swein.androidkotlintool.main.examples.customschedule.customscheduleview.CustomScheduleViewGroup
import com.swein.androidkotlintool.main.examples.customschedule.customscheduleview.item.ScheduleModel
import com.swein.androidkotlintool.main.examples.customschedule.customscheduleview.item.ScheduleViewHolder
import kotlin.math.abs
import kotlin.math.floor


class ScheduleFragment : Fragment() {

    companion object {

        const val TAG = "ScheduleFragment"

        @JvmStatic
        fun newInstance() =
            ScheduleFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private val list = mutableListOf<ScheduleModel>()

    private lateinit var scrollView: ScrollView
    private lateinit var customScheduleViewGroup: CustomScheduleViewGroup
    private lateinit var linearLayoutTimeArea: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findView(view)
        initView()

        scrollView.setOnTouchListener(createTouchListener())
    }

    private fun findView(view: View) {
        scrollView = view.findViewById(R.id.scrollView)
        customScheduleViewGroup = view.findViewById(R.id.customScheduleViewGroup)
        linearLayoutTimeArea = view.findViewById(R.id.linearLayoutTimeArea)
    }

    private fun createTouchListener(): View.OnTouchListener {

        return object: View.OnTouchListener {

            var x: Float = 0f
            var y: Float = 0f

            var scrollDistance = 0f

            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {

                        event.let {
                            x = it.x
                            y = it.y
                        }

                        return false

                    }
                    MotionEvent.ACTION_MOVE -> {

                        scrollDistance = abs(event.y - y)
                        ILog.debug("???", "scrollDistance $scrollDistance")
                        return false
                    }
                    MotionEvent.ACTION_UP -> {

                        if (scrollDistance < 100) {

                            val result = event.x - x

                            when {
                                result > 100 -> {
                                    ILog.debug("???", "right")
                                }
                                result < -100 -> {
                                    ILog.debug("???", "left")
                                }
                                else -> {
                                    ILog.debug("???", "none")
                                }
                            }
                        }

                        scrollDistance = 0f
                        return false
                    }
                    else -> {
                        return false
                    }
                }

            }

        }
    }

    private fun initView() {

        initTimeArea()
        initScheduleArea()
    }

    private fun initTimeArea() {

        var startTimestamp = list[0].startDateTime.time
        var endTimestamp = list[0].endDateTime.time

        var startDate = list[0].startDateTime
        var endDate = list[0].endDateTime

        for (i in 0 until list.size) {
            if (startTimestamp > list[i].startDateTime.time) {
                startTimestamp = list[i].startDateTime.time
                startDate = list[i].startDateTime
            }

            if (endTimestamp < list[i].endDateTime.time) {
                endTimestamp = list[i].endDateTime.time
                endDate = list[i].endDateTime
            }
        }

        var startHour = DateUtility.getHour24(startDate)

        if (startHour > 0) {
            startHour -= 1
        }

        var endHour = DateUtility.getHour24(endDate)

        if (endHour < 23) {
            endHour += 1
        }
        else {
            endHour = 24
        }

        linearLayoutTimeArea.post {

            var scheduleTimeViewHolder: ScheduleTimeViewHolder
            for (i in startHour until endHour) {

                scheduleTimeViewHolder = ScheduleTimeViewHolder(linearLayoutTimeArea.context, if (i < 10) {
                    "0$i:00"
                }
                else {
                    "$i:00"
                })

                linearLayoutTimeArea.addView(scheduleTimeViewHolder)
            }
        }
    }

    private fun initScheduleArea() {

        customScheduleViewGroup.removeAllViews()

        var startTimestamp = list[0].startDateTime.time
        var endTimestamp = list[0].endDateTime.time

        var startDate = list[0].startDateTime
        var endDate = list[0].endDateTime

        for (i in 0 until list.size) {
            if (startTimestamp > list[i].startDateTime.time) {
                startTimestamp = list[i].startDateTime.time
                startDate = list[i].startDateTime
            }

            if (endTimestamp < list[i].endDateTime.time) {
                endTimestamp = list[i].endDateTime.time
                endDate = list[i].endDateTime
            }
        }

        var startHour = DateUtility.getHour24(startDate)
        val startMinute = DateUtility.getMinute(startDate)

        if (startHour > 0) {
            startHour -= 1
        }

        var endHour = DateUtility.getHour24(endDate)
        var endMinute = DateUtility.getMinute(endDate)

        if (endHour < 23) {
            endHour += 1
        }
        else {
            endHour = 24
            endMinute = 0
        }

//            ILog.debug("???", "total start time is $startHour:$startMinute")
//            ILog.debug("???", "total end time is $endHour:$endMinute")

        val diffHour = endHour - startHour
        val diffMinute = endMinute - startMinute

        val totalHeight = diffHour * DisplayUtility.dipToPx(customScheduleViewGroup.context, 42f) +
                (diffMinute.toDouble() / 60).toInt() * DisplayUtility.dipToPx(customScheduleViewGroup.context, 42f)
//            ILog.debug("???", "totalHeight is $totalHeight px")

        customScheduleViewGroup.post {

            val layoutParams = customScheduleViewGroup.layoutParams
            layoutParams.height = totalHeight
            customScheduleViewGroup.layoutParams = layoutParams

            val heightOfOneHour = DisplayUtility.dipToPx(customScheduleViewGroup.context, 42f).toDouble()
            val unitHeight = floor(heightOfOneHour / 2.toDouble()).toInt()

            val startPositionY = startHour * DisplayUtility.dipToPx(customScheduleViewGroup.context, 42f) +
                    (startMinute.toDouble() / 60).toInt() * DisplayUtility.dipToPx(customScheduleViewGroup.context, 42f)

            var scheduleViewHolder: ScheduleViewHolder
            for (i in 0 until list.size) {
                scheduleViewHolder = ScheduleViewHolder(customScheduleViewGroup.context, list[i], customScheduleViewGroup.width, unitHeight, startPositionY, if (i % 2 == 0) {
                    Color.BLUE
                }
                else {
                    Color.RED
                })
                customScheduleViewGroup.addView(scheduleViewHolder)
            }
        }
    }

    private fun initData() {

        list.clear()

        var scheduleModel = ScheduleModel("게임", "jonjkdb(test0)","2021-10-07 03:30:00", "2021-10-07 07:30:00")
        list.add(scheduleModel)

        scheduleModel = ScheduleModel("소통/음악", "dsad(test1)","2021-10-07 07:30:00", "2021-10-07 11:30:00")
        list.add(scheduleModel)

        scheduleModel = ScheduleModel("스포츠", "dgrfg(test2)","2021-10-07 06:00:00", "2021-10-07 10:30:00")
        list.add(scheduleModel)

        scheduleModel = ScheduleModel("게임", "rete(test3)","2021-10-07 8:00:00", "2021-10-07 13:00:00")
        list.add(scheduleModel)

        scheduleModel = ScheduleModel("모바일111", "nngf(test4)","2021-10-07 11:00:00", "2021-10-07 16:00:00")
        list.add(scheduleModel)

        scheduleModel = ScheduleModel("모바일222", "jy45y54(test5)","2021-10-07 14:30:00", "2021-10-07 16:30:00")
        list.add(scheduleModel)

        scheduleModel = ScheduleModel("모바일333", "6ujngf(test6)","2021-10-07 14:00:00", "2021-10-07 17:00:00")
        list.add(scheduleModel)

        scheduleModel = ScheduleModel("신입", "kkk(test7)","2021-10-07 17:30:00", "2021-10-07 23:00:00")
        list.add(scheduleModel)

        scheduleModel = ScheduleModel("게임", "erwwer(test8)","2021-10-07 18:30:00", "2021-10-07 21:00:00")
        list.add(scheduleModel)

        scheduleModel = ScheduleModel("스포츠", "uiuiyuyui(test9)","2021-10-07 20:30:00", "2021-10-07 23:30:00")
        list.add(scheduleModel)

    }
}