package com.swein.androidkotlintool.main.examples.customschedule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.customschedule.fragment.ScheduleFragment

class CustomScheduleExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_schedule_example)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, ScheduleFragment.newInstance(), ScheduleFragment.TAG).commitAllowingStateLoss()
        }
    }


}