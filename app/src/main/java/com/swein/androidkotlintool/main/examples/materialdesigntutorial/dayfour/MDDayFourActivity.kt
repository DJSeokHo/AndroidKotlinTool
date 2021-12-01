package com.swein.androidkotlintool.main.examples.materialdesigntutorial.dayfour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.toast.ToastUtility

class MDDayFourActivity : AppCompatActivity() {

    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.bottomNavigationView)
    }

    private val floatingActionButton: FloatingActionButton by lazy {
        findViewById(R.id.floatingActionButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mdday_four)

        floatingActionButton.setOnClickListener {
            ToastUtility.showCustomShortToastNormal(this, "center button click")

            bottomNavigationView.post {
                // 0 is home , 1 is discover, 2 is center floating action button spacer, 3 is notification, 4 is profile
                setBadgeNumber(3, 68)
            }

        }

        bottomNavigationView.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.home -> {
                    ToastUtility.showCustomShortToastNormal(this, "home button click")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.discovery -> {
                    ToastUtility.showCustomShortToastNormal(this, "discovery button click")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.notification -> {
                    ToastUtility.showCustomShortToastNormal(this, "notification button click")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    ToastUtility.showCustomShortToastNormal(this, "profile button click")
                    return@setOnNavigationItemSelectedListener true
                }
            }

            false
        }
    }

    private fun setBadgeNumber(index: Int, count: Int) {

        val menuView = bottomNavigationView.getChildAt(0) as BottomNavigationMenuView
        val itemView = menuView.getChildAt(index) as BottomNavigationItemView
        val badgeNumberView = LayoutInflater.from(this).inflate(R.layout.view_holder_badge_number, menuView, false)
        itemView.addView(badgeNumberView)
        val textViewBadgeNumber = badgeNumberView.findViewById<TextView>(R.id.textViewBadgeNumber)
        textViewBadgeNumber.text = count.toString()
        textViewBadgeNumber.visibility = if (count > 0) View.VISIBLE else View.GONE
    }
}