package com.swein.androidkotlintool.main.examples.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.swein.androidkotlintool.R

class CustomViewExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view_example)

        findViewById<FrameLayout>(R.id.container).addView(TestViewOne(this))
//        findViewById<FrameLayout>(R.id.container).addView(TestViewTwo(this))
//        findViewById<FrameLayout>(R.id.container).addView(TestViewFour(this))
    }
}