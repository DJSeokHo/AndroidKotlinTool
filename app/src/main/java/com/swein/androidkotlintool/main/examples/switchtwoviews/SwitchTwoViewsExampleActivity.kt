package com.swein.androidkotlintool.main.examples.switchtwoviews

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R

class SwitchTwoViewsExampleActivity: AppCompatActivity() {

    private val button: Button by lazy {
        findViewById(R.id.button)
    }


    private val viewRed: View by lazy {
        findViewById(R.id.viewRed)
    }


    private val viewBlue: View by lazy {
        findViewById(R.id.viewBlue)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_two_views_example)

        button.setOnClickListener {

            val blueX = viewBlue.x
            val blueY = viewBlue.y
            val blueZ = viewBlue.z
            val blueSX = viewBlue.scaleX
            val blueSY = viewBlue.scaleY

            val redX = viewRed.x
            val redY = viewRed.y
            val redZ = viewRed.z
            val redSX = viewRed.scaleX
            val redSY = viewRed.scaleY

            viewRed.animate().x(blueX)
            viewRed.animate().y(blueY)
            viewRed.animate().z(blueZ)
            viewRed.animate().scaleX(blueSX)
            viewRed.animate().scaleY(blueSY)

            viewBlue.animate().x(redX)
            viewBlue.animate().y(redY)
            viewBlue.animate().z(redZ)
            viewBlue.animate().scaleX(redSX)
            viewBlue.animate().scaleY(redSY)
        }
    }


}