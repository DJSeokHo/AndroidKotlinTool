package com.swein.androidkotlintool.main.examples.circlemenu

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog

class CircleMenuExampleActivity : AppCompatActivity() {

    private lateinit var imageButtonCenter: ImageButton

    private lateinit var constraintLayoutMenu: ConstraintLayout
    private lateinit var menu1: LinearLayout
    private lateinit var menu2: LinearLayout
    private lateinit var menu3: LinearLayout
    private lateinit var menu4: LinearLayout
    private lateinit var menu5: LinearLayout
    private lateinit var menu6: LinearLayout
    private lateinit var menu7: LinearLayout
    private lateinit var menu8: LinearLayout

    // size of list is as same as size of menuTitleList
    private var list = mutableListOf<LinearLayout>()
    private var menuTitleList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_menu_example)

        findView()
        setListener()
    }

    private fun findView() {

        imageButtonCenter = findViewById(R.id.imageButtonCenter)
        constraintLayoutMenu = findViewById(R.id.constraintLayoutMenu)
        menu1 = findViewById(R.id.menu1)
        menu2 = findViewById(R.id.menu2)
        menu3 = findViewById(R.id.menu3)
        menu4 = findViewById(R.id.menu4)
        menu5 = findViewById(R.id.menu5)
        menu6 = findViewById(R.id.menu6)
        menu7 = findViewById(R.id.menu7)
        menu8 = findViewById(R.id.menu8)

        list.add(menu1)
        list.add(menu2)
        list.add(menu3)
        list.add(menu4)
        list.add(menu5)
        list.add(menu6)
        list.add(menu7)
        list.add(menu8)

        menuTitleList.add("Post")
        menuTitleList.add("Camera")
        menuTitleList.add("Chatting")
        menuTitleList.add("Map")
        menuTitleList.add("Profile")
        menuTitleList.add("Search")
        menuTitleList.add("Setting")
        menuTitleList.add("Support")
    }

    private fun setListener() {
        imageButtonCenter.setOnClickListener {
            toggleCenterMenu()
        }

        val listener = createTouchListener()
        constraintLayoutMenu.setOnTouchListener(listener)

        for (view in list) {
            view.setOnTouchListener(listener)
        }
    }

    private fun createTouchListener(): View.OnTouchListener {

        return object: View.OnTouchListener {

            var x: Float = 0f
            var isTouched = false

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {

                        event.let {
                            x = it.x
                        }

                        return if (v is LinearLayout || v is ConstraintLayout) {
                            isTouched = true
                            true
                        }
                        else {
                            isTouched = false
                            false
                        }

                    }
                    MotionEvent.ACTION_MOVE -> {
                        return true
                    }
                    MotionEvent.ACTION_UP -> {

                        val result = event.x - x

                        when {
                            result > 100 -> {
                                ILog.debug("??", "right")

                                for (linearLayout in list) {
                                    startRotate(linearLayout, false)
                                }
                            }
                            result < -100 -> {
                                ILog.debug("??", "left")

                                for (linearLayout in list) {
                                    startRotate(linearLayout, true)
                                }
                            }
                            else -> {
                                ILog.debug("??", "just touch")

                                if (v is LinearLayout) {
                                    toggleClick(v)
                                }
                            }
                        }

                        return false
                    }
                    else -> {
                        return false
                    }
                }

                return false
            }

        }
    }

    private fun toggleClick(currentView: LinearLayout) {

        for (i in 0 until list.size) {
            if (currentView.id == list[i].id) {
                Toast.makeText(this, "click menu ${menuTitleList[i]}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startRotate(currentView: LinearLayout, isLeft: Boolean) {

        val layoutParams = currentView.layoutParams as ConstraintLayout.LayoutParams
        val currentAngle = layoutParams.circleAngle

        val targetAngle = currentAngle + if (isLeft) {
            -45
        }
        else {
            45
        }

        val angleAnimator = ValueAnimator.ofFloat(currentAngle, targetAngle)
        angleAnimator.addUpdateListener {

            layoutParams.circleAngle = it.animatedValue as Float
            currentView.layoutParams = layoutParams
        }

        angleAnimator.duration = 400
        angleAnimator.interpolator = AnticipateOvershootInterpolator()

        angleAnimator.start()

    }

    private fun toggleCenterMenu() {

        // you can add your animation when visibility value changed
        // for example: fade in, fade out
        if (constraintLayoutMenu.visibility == View.VISIBLE) {
            constraintLayoutMenu.visibility = View.GONE
        }
        else {
            constraintLayoutMenu.visibility = View.VISIBLE
        }
    }
}