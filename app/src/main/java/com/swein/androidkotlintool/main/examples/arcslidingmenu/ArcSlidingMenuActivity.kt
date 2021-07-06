package com.swein.androidkotlintool.main.examples.arcslidingmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R

class ArcSlidingMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arc_sliding_menu)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ArcSlidingMenuFragment.newInstance(), ArcSlidingMenuFragment.TAG)
            .commitAllowingStateLoss()

    }

    override fun onBackPressed() {

        supportFragmentManager.findFragmentByTag(ArcSlidingMenuFragment.TAG)?.let {
            supportFragmentManager.beginTransaction().remove(it).commitAllowingStateLoss()
            return
        }

        finish()
    }
}