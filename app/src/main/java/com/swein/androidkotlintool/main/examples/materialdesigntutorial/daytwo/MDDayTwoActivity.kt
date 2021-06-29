package com.swein.androidkotlintool.main.examples.materialdesigntutorial.daytwo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.swein.androidkotlintool.R

class MDDayTwoActivity : AppCompatActivity() {

    private val toolbar: Toolbar by lazy {
        findViewById(R.id.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mdday_two)

//        toolbar.overflowIcon = ContextCompat.getDrawable(this, R.drawable.menu)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.mipmap.ic_launcher)
        toolbar.title = "title"
        toolbar.setTitleTextColor(Color.parseColor("#333333"))
        toolbar.subtitle = "Sub title"
        toolbar.setSubtitleTextColor(Color.parseColor("#999999"))
        toolbar.inflateMenu(R.menu.toolbar_menu)

        toolbar.setNavigationOnClickListener {
            Toast.makeText(this@MDDayTwoActivity, "navigation click", Toast.LENGTH_SHORT).show()
        }

        toolbar.setOnMenuItemClickListener {

            when (it.itemId) {

                R.id.menuOne -> {
                    Toast.makeText(this@MDDayTwoActivity, "menu one", Toast.LENGTH_SHORT).show()
                }

                R.id.menuTwo -> {
                    Toast.makeText(this@MDDayTwoActivity, "menu two", Toast.LENGTH_SHORT).show()
                }

                R.id.menuThree -> {
                    Toast.makeText(this@MDDayTwoActivity, "menu three", Toast.LENGTH_SHORT).show()
                }
            }

            true
        }

    }
}