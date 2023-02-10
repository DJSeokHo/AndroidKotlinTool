package com.swein.androidkotlintool.main.examples.stopwatchexample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import com.swein.androidkotlintool.R
import java.text.SimpleDateFormat
import java.util.*

class StopWatchExampleActivity : AppCompatActivity() {

    // hour
    private val buttonHourPlus: Button by lazy {
        findViewById(R.id.buttonHourPlus)
    }
    private val textViewHour: TextView by lazy {
        findViewById(R.id.textViewHour)
    }
    private val buttonHourMinus: Button by lazy {
        findViewById(R.id.buttonHourMinus)
    }

    // minute
    private val buttonMinutePlus: Button by lazy {
        findViewById(R.id.buttonMinutePlus)
    }
    private val textViewMinute: TextView by lazy {
        findViewById(R.id.textViewMinute)
    }
    private val buttonMinuteMinus: Button by lazy {
        findViewById(R.id.buttonMinuteMinus)
    }

    // second
    private val buttonSecondPlus: Button by lazy {
        findViewById(R.id.buttonSecondPlus)
    }
    private val textViewSecond: TextView by lazy {
        findViewById(R.id.textViewSecond)
    }
    private val buttonSecondMinus: Button by lazy {
        findViewById(R.id.buttonSecondMinus)
    }

    private val buttonStart: Button by lazy {
        findViewById(R.id.buttonStart)
    }
    private val buttonReset: Button by lazy {
        findViewById(R.id.buttonReset)
    }

    private var hours = 0
    private var minutes = 0
    private var seconds = 0

    private var countDownTimer: CountDownTimer? = null
    private var isCounting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_watch_example)

        supportActionBar?.hide()

        buttonHourPlus.setOnClickListener {
            hours = plusTime(hours)
            updateUI()
        }
        buttonHourMinus.setOnClickListener {
            hours = minusTime(hours)
            updateUI()
        }

        buttonMinutePlus.setOnClickListener {
            minutes = plusTime(minutes)
            updateUI()
        }
        buttonMinuteMinus.setOnClickListener {
            minutes = minusTime(minutes)
            updateUI()
        }

        buttonSecondPlus.setOnClickListener {
            seconds = plusTime(seconds)
            updateUI()
        }
        buttonSecondMinus.setOnClickListener {
            seconds = minusTime(seconds)
            updateUI()
        }

        buttonStart.setOnClickListener {

            if (hours == 0 && minutes == 0 && seconds == 0) {
                return@setOnClickListener
            }

            if (isCounting) {
                stopCount()
            }
            else {
                startCount()
            }


        }
        buttonReset.setOnClickListener {

            reset()

        }
    }

    private fun plusTime(time: Int): Int {

        var tempTime = time

        tempTime += 1

        // maximum is 99
        if (tempTime >= 99) {
            tempTime = 99
        }

        return tempTime
    }

    private fun minusTime(time: Int): Int {
        var tempTime = time

        tempTime -= 1

        // minimum is 0
        if (tempTime <= 0) {
            tempTime = 0
        }

        return tempTime
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateUI() {

        val calendar = Calendar.getInstance()

        calendar.clear()
        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, seconds)

        val dateString = SimpleDateFormat("HH:mm:ss").format(calendar.time)

        val dates = dateString.split(":")

        textViewHour.text = dates[0]
        textViewMinute.text = dates[1]
        textViewSecond.text = dates[2]

        if (isCounting) {
            buttonStart.text = "Stop"
        }
        else {
            buttonStart.text = "Start"
        }
    }

    private fun updateTimeWithSeconds(totalSeconds: Int) {

        val calendar = Calendar.getInstance()

        calendar.clear()
        calendar.set(Calendar.SECOND, totalSeconds)

        hours = calendar.get(Calendar.HOUR_OF_DAY)
        minutes = calendar.get(Calendar.MINUTE)
        seconds = calendar.get(Calendar.SECOND)
    }

    private fun startCount() {

        if (countDownTimer != null) {
            return
        }

        val totalSecond = seconds + (60 * minutes) + (3600 * hours)

        // change UI every second
        countDownTimer = object : CountDownTimer((totalSecond.toLong() * 1000), 1000) {

            override fun onTick(millisUntilFinished: Long) {

                updateTimeWithSeconds(
//                    (millisUntilFinished * 0.001).toInt() // millisecond to second
                    (millisUntilFinished * 0.001).toInt() + 1 // millisecond to second, onTick will be toggle immediately, so
                    // we need plus 1 to make it looks like a real stop watch
                )

                updateUI()
            }

            override fun onFinish() {

                reset()
            }
        }

        countDownTimer?.start()
        isCounting = true
    }

    private fun stopCount() {
        countDownTimer?.cancel()
        countDownTimer = null
        isCounting = false

        updateUI()
    }

    private fun reset() {
        stopCount()

        hours = 0
        minutes = 0
        seconds = 0

        updateUI()
    }

    override fun onDestroy() {
        stopCount()
        super.onDestroy()
    }
}