package com.swein.androidkotlintool.main.examples.onbackpresseddispatcherexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import com.swein.androidkotlintool.R

class OnBackPressedDispatcherExampleActivity : AppCompatActivity() {

    private val button: Button by lazy {
        findViewById(R.id.button)
    }

    private var fragmentIndex = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_back_pressed_dispatcher_example)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("???", "Activity onBackPressed")
                finish()
            }
        })

        button.setOnClickListener {

            OnBackPressedDispatcherExampleFragment.add(this, fragmentIndex)

            fragmentIndex++
        }
    }
}