package com.swein.androidkotlintool.main.examples.customschschedule

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.display.DisplayUtility
import com.swein.androidkotlintool.framework.util.log.ILog
import kotlin.math.floor

class CustomScheduleExampleActivity : AppCompatActivity() {

    private val list = mutableListOf<ScheduleModel>()

    private val customScheduleViewGroup: CustomScheduleViewGroup by lazy {
        findViewById(R.id.customScheduleViewGroup)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_schedule_example)

        customScheduleViewGroup.post {
            ILog.debug("???", "${customScheduleViewGroup.width} ${customScheduleViewGroup.height}")
            initData()

            var scheduleViewHolder: ScheduleViewHolder
            for (i in 0 until 3) {
                scheduleViewHolder = ScheduleViewHolder(this, list[i].width, list[i].height, list[i].title, list[i].member, Color.RED)
                customScheduleViewGroup.addView(scheduleViewHolder)
            }

        }
    }

    private fun initData() {

        val heightOfOneHour = DisplayUtility.dipToPx(this, 42f).toDouble()
        val unitHeight = floor(heightOfOneHour / 2.toDouble()).toInt()

        var scheduleModel = ScheduleModel("소통/음악", "dsad(test1)","2021-10-07 07:30:00", "2021-10-07 09:30:00")
        scheduleModel.init(customScheduleViewGroup.width, unitHeight)
        list.add(scheduleModel)

        scheduleModel = ScheduleModel("스포츠", "dgrfg(test2)","2021-10-07 09:30:00", "2021-10-07 13:00:00")
        scheduleModel.init(customScheduleViewGroup.width, unitHeight)
        list.add(scheduleModel)

        scheduleModel = ScheduleModel("게임", "rete(test3)","2021-10-07 11:00:00", "2021-10-07 13:00:00")
        scheduleModel.init(customScheduleViewGroup.width, unitHeight)
        list.add(scheduleModel)

        scheduleModel = ScheduleModel("모바일", "nngf(test4)","2021-10-07 13:00:00", "2021-10-07 16:00:00")
        scheduleModel.init(customScheduleViewGroup.width, unitHeight)
        list.add(scheduleModel)

        scheduleModel = ScheduleModel("모바일", "jy45y54(test5)","2021-10-07 14:30:00", "2021-10-07 16:30:00")
        scheduleModel.init(customScheduleViewGroup.width, unitHeight)
        list.add(scheduleModel)

        scheduleModel = ScheduleModel("모바일", "6ujngf(test6)","2021-10-07 14:00:00", "2021-10-07 17:00:00")
        scheduleModel.init(customScheduleViewGroup.width, unitHeight)
        list.add(scheduleModel)

        scheduleModel = ScheduleModel("신입", "kkk(test7)","2021-10-07 17:30:00", "2021-10-07 20:00:00")
        scheduleModel.init(customScheduleViewGroup.width, unitHeight)
        list.add(scheduleModel)

    }
}