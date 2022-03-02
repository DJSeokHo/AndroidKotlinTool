package com.swein.androidkotlintool.main.examples.patternlockexample

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R


class PatternLockExampleActivity : AppCompatActivity() {

    private val textView: TextView by lazy {
        findViewById(R.id.textView)
    }

    private val patternView: PatternView by lazy {
        findViewById(R.id.patternView)
    }

    private val testPattern1 = "abcdefghi"
    private val testPattern2 = "cfie"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pattern_lock_example)

        patternView.onPatternChanged = {
            textView.text = it
            textView.setTextColor(Color.BLACK)
        }

        patternView.onCheckPattern = {
            textView.text = it

            textView.setTextColor(
                when (it) {
                    testPattern1 -> {
                        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                        Color.GREEN
                    }
                    testPattern2 -> {
                        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                        Color.GREEN
                    }
                    else -> {
                        Toast.makeText(this, "try again", Toast.LENGTH_SHORT).show()
                        Color.RED
                    }
                }
            )
        }

        patternView.onVibrate = {
            vibrate()
        }

    }

    @SuppressLint("MissingPermission")
    private fun vibrate() {

        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            vibrator.let {
                if (it.hasVibrator()) {
                    it.vibrate(70)
                }
            }
        }
        else {
            val effect = VibrationEffect.createOneShot(
                70, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        }
    }
}