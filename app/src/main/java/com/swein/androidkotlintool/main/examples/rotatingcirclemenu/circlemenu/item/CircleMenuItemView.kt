package com.swein.androidkotlintool.main.examples.rotatingcirclemenu.circlemenu.item

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.rotatingcirclemenu.circlemenu.model.CircleMenuModel

@SuppressLint("ViewConstructor")
class CircleMenuItemView(
    context: Context,
    circleMenuModel: CircleMenuModel
): ConstraintLayout(context) {

    private var textView: TextView
    private var imageView: ImageView

    init {

        inflate(context, R.layout.circle_menu_item, this)

        textView = findViewById(R.id.textView)
        imageView = findViewById(R.id.imageView)

        imageView.setImageResource(circleMenuModel.imageResource)
        textView.text = circleMenuModel.title

        setOnClickListener {
            Toast.makeText(it.context, circleMenuModel.title, Toast.LENGTH_SHORT).show()
        }
    }
}