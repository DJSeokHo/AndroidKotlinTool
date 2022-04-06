package com.swein.androidkotlintool.main.examples.rotatingcirclemenu

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.rotatingcirclemenu.circlemenu.CircleMenuLayout
import com.swein.androidkotlintool.main.examples.rotatingcirclemenu.circlemenu.item.CircleMenuItemView
import com.swein.androidkotlintool.main.examples.rotatingcirclemenu.circlemenu.model.CircleMenuModel

class RotatingCircleMenuExampleActivity : AppCompatActivity() {

    private val circleMenuLayout: CircleMenuLayout by lazy {
        findViewById(R.id.circleMenuLayout)
    }

    private val cardView: CardView by lazy {
        findViewById(R.id.cardView)
    }

    private val imageButton: ImageButton by lazy {
        findViewById(R.id.imageButton)
    }

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    private val textView: TextView by lazy {
        findViewById(R.id.textView)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotating_circle_menu_example)

        circleMenuLayout.initView(R.drawable.circle_menu_bg, 300f)

        circleMenuLayout.post {

            CircleMenuItemView(this, CircleMenuModel(R.mipmap.ti_cm_post, "Post"), onItemClick = {
                imageView.setImageResource(it.imageResource)
                textView.text = it.title
            }).also {

                circleMenuLayout.addView(it)

            }

            CircleMenuItemView(this, CircleMenuModel(R.mipmap.ti_cm_camera, "Camera"), onItemClick = {
                imageView.setImageResource(it.imageResource)
                textView.text = it.title
            }).also {

                circleMenuLayout.addView(it)

            }

            CircleMenuItemView(this, CircleMenuModel(R.mipmap.ti_cm_chatting, "Chatting"), onItemClick = {
                imageView.setImageResource(it.imageResource)
                textView.text = it.title
            }).also {

                circleMenuLayout.addView(it)

            }

            CircleMenuItemView(this, CircleMenuModel(R.mipmap.ti_cm_map, "Map"), onItemClick = {
                imageView.setImageResource(it.imageResource)
                textView.text = it.title
            }).also {

                circleMenuLayout.addView(it)

            }

            CircleMenuItemView(this, CircleMenuModel(R.mipmap.ti_cm_profile, "Profile"), onItemClick = {
                imageView.setImageResource(it.imageResource)
                textView.text = it.title
            }).also {

                circleMenuLayout.addView(it)

            }

            CircleMenuItemView(this, CircleMenuModel(R.mipmap.ti_cm_search, "Search"), onItemClick = {
                imageView.setImageResource(it.imageResource)
                textView.text = it.title
            }).also {

                circleMenuLayout.addView(it)

            }

        }

        imageButton.setOnClickListener {

            cardView.visibility = if (cardView.visibility == View.VISIBLE) {
                View.INVISIBLE
            }
            else {
                View.VISIBLE
            }
        }
    }
}