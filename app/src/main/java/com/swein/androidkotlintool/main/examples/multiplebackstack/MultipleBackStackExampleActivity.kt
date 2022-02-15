package com.swein.androidkotlintool.main.examples.multiplebackstack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.multiplebackstack.bottomnavigation.CustomBottomNavigationView

class MultipleBackStackExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_back_stack_example)

        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {

        CustomBottomNavigationView(
            context = this,
            onHomeClick = {

            },
            onSearchClick = {

            },
            onProfileClick = {

            }
        ).also {
            findViewById<FrameLayout>(R.id.frameLayoutBottomNavigationContainer).addView(it)
        }

    }
}