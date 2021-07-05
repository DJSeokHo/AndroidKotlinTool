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


class ArcSlidingMenuActivity : AppCompatActivity() {

    private val textView1: TextView by lazy {
        findViewById(R.id.textView1)
    }

    private val textView2: TextView by lazy {
        findViewById(R.id.textView2)
    }

    private val textView3: TextView by lazy {
        findViewById(R.id.textView3)
    }

    private val textView4: TextView by lazy {
        findViewById(R.id.textView4)
    }

    private val textView5: TextView by lazy {
        findViewById(R.id.textView5)
    }

    private val imageButtonCenter: ImageButton by lazy {
        findViewById(R.id.imageButtonCenter)
    }

    private val constraintLayout: ConstraintLayout by lazy {
        findViewById(R.id.constraintLayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arc_sliding_menu)

        MenuAnimatorController.initData(this, textView1, textView2, textView3, textView4, textView5)

        textView1.setOnClickListener {

            val step = MenuAnimatorController.getStep(it)
            val rotateDirection = MenuAnimatorController.getRotateDirection(it)
            ILog.debug("???", "$step ${rotateDirection.name}")
            startAnimation(rotateDirection, step)

        }

        textView2.setOnClickListener {

            val step = MenuAnimatorController.getStep(it)
            val rotateDirection = MenuAnimatorController.getRotateDirection(it)
            ILog.debug("???", "$step ${rotateDirection.name}")
            startAnimation(rotateDirection, step)

        }

        textView3.setOnClickListener {

            val step = MenuAnimatorController.getStep(it)
            val rotateDirection = MenuAnimatorController.getRotateDirection(it)
            ILog.debug("???", "$step ${rotateDirection.name}")
            startAnimation(rotateDirection, step)

        }

        textView4.setOnClickListener {

            val step = MenuAnimatorController.getStep(it)
            val rotateDirection = MenuAnimatorController.getRotateDirection(it)
            ILog.debug("???", "$step ${rotateDirection.name}")
            startAnimation(rotateDirection, step)

        }

        textView5.setOnClickListener {

            val step = MenuAnimatorController.getStep(it)
            val rotateDirection = MenuAnimatorController.getRotateDirection(it)
            ILog.debug("???", "$step ${rotateDirection.name}")
            startAnimation(rotateDirection, step)

        }

        imageButtonCenter.setOnClickListener {


        }

        constraintLayout.setOnTouchListener(object: View.OnTouchListener {

            var x: Float = 0f

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        event?.let {
                            x = it.x
                        }
                        return false
                    }
                    MotionEvent.ACTION_MOVE -> {
                        return true
                    }
                    MotionEvent.ACTION_UP -> {

                        val result = event.x - x
                        ILog.debug("???", "$result")

                        val rotateDirection = when {
                            result > 0 -> {
                                RotateDirection.RIGHT
                            }
                            result < 0 -> {
                                RotateDirection.LEFT
                            }
                            else -> {
                                RotateDirection.NONE
                            }
                        }

                        startAnimation(rotateDirection, 1)

                        return false
                    }
                    else -> {
                        return false
                    }
                }

                return false
            }

        })
    }

    private fun startAnimation(rotateDirection: RotateDirection, step: Int, duration: Long = 200) {

        if (step == 2) {
            MenuAnimatorController.rotate(rotateDirection, step, duration) {
                MenuAnimatorController.rotate(rotateDirection, step - 1, duration)
            }
        }
        else if (step == 1) {
            MenuAnimatorController.rotate(rotateDirection, step, duration)
        }

    }
}