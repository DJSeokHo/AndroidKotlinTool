package com.swein.androidkotlintool.advanceexamples.customtouchevent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.swein.androidkotlintool.R

class CustomTouchEventExampleActivity : AppCompatActivity() {

    private val customTouchView: CustomTouchView by lazy {
        findViewById(R.id.customTouchView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_touch_event_example)

        customTouchView.setOnClickListener {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
        }
    }
}