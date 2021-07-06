package com.swein.androidkotlintool.main.examples.arcslidingmenu

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.livedata.LiveDataDemoFragment


class ArcSlidingMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arc_sliding_menu)

        supportFragmentManager.beginTransaction().replace(R.id.container, ArcSlidingMenuFragment.newInstance(), ArcSlidingMenuFragment.TAG).commitAllowingStateLoss()

    }

    override fun onBackPressed() {

        supportFragmentManager.findFragmentByTag(ArcSlidingMenuFragment.TAG)?.let {
            supportFragmentManager.beginTransaction().remove(it).commitAllowingStateLoss()
            return
        }

        finish()
    }
}