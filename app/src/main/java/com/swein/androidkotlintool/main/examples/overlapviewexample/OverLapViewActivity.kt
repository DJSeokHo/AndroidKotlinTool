package com.swein.androidkotlintool.main.examples.overlapviewexample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.ShapeAppearanceModel
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.display.DisplayUtility

class OverLapViewActivity : AppCompatActivity() {

    private val imageView1: ShapeableImageView by lazy {
        findViewById(R.id.imageView1)
    }

    private val imageView2: ShapeableImageView by lazy {
        findViewById(R.id.imageView2)
    }

    private val imageView3: ShapeableImageView by lazy {
        findViewById(R.id.imageView3)
    }

    private val imageView4: ShapeableImageView by lazy {
        findViewById(R.id.imageView4)
    }

    private val imageView5: ShapeableImageView by lazy {
        findViewById(R.id.imageView5)
    }


    private val linearLayout: LinearLayout by lazy {
        findViewById(R.id.linearLayout)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_over_lap_view)

        staticWay()

        dynamicWay()
    }

    private fun dynamicWay() {

        linearLayout.removeAllViews()

        val itemCount = 25

        for (i in 0 until itemCount) {

            OverLapItemView(this).apply {

                if (i == itemCount - 1) {
                    setText("10+")
                }
                else {
                    setImageResource(R.drawable.coding_with_cat)
                }

                linearLayout.addView(this)
            }
        }

        // must set margin one by one after all items have been added to their parent!!
        for (i in 0 until linearLayout.childCount) {

            val child = linearLayout.getChildAt(i)

            if (child is OverLapItemView) {

                if (i != itemCount - 1) {
                    child.overlapToEnd(-15f)
                }

            }
        }
    }


    private fun staticWay() {

        imageView1.post{

            imageView1.shapeAppearanceModel = ShapeAppearanceModel().toBuilder()
                .setAllCornerSizes(RelativeCornerSize(0.5f))
                .build()

        }

        imageView2.post{

            imageView2.shapeAppearanceModel = ShapeAppearanceModel().toBuilder()
                .setAllCornerSizes(RelativeCornerSize(0.5f))
                .build()

        }

        imageView3.post{

            imageView3.shapeAppearanceModel = ShapeAppearanceModel().toBuilder()
                .setAllCornerSizes(RelativeCornerSize(0.5f))
                .build()

        }

        imageView4.post{

            imageView4.shapeAppearanceModel = ShapeAppearanceModel().toBuilder()
                .setAllCornerSizes(RelativeCornerSize(0.5f))
                .build()

        }

        imageView5.post{

            imageView5.shapeAppearanceModel = ShapeAppearanceModel().toBuilder()
                .setAllCornerSizes(RelativeCornerSize(0.5f))
                .build()

        }

    }
}