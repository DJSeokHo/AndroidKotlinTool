package com.swein.androidkotlintool.main.examples.pixelcolorpicker

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.swein.androidkotlintool.R

class PixelColorPickerExampleActivity : AppCompatActivity() {

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    private val viewColor: View by lazy {
        findViewById(R.id.viewColor)
    }

    private lateinit var bitmap: Bitmap

    private var xRatioForBitmap = 1f
    private var yRatioForBitmap = 1f

    private var red = 0
    private var green = 0
    private var blue = 0
    private var alpha = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pixel_color_picker_example)

        bitmap = BitmapFactory.decodeResource(this.resources, R.drawable.coding_with_cat)
        imageView.setImageResource(R.drawable.coding_with_cat)

        imageView.post {

            xRatioForBitmap = bitmap.width.toFloat() / imageView.width.toFloat()
            yRatioForBitmap = bitmap.height.toFloat() / imageView.height.toFloat()

        }

        imageView.setOnTouchListener { _, motionEvent ->

            when (motionEvent.action) {

                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {

                    val touchXToBitmap: Float = motionEvent.x * xRatioForBitmap
                    val touchYToBitmap: Float = motionEvent.y * yRatioForBitmap

                    // make sure the touch event is inside bitmap
                    if (touchXToBitmap < 0 || touchYToBitmap < 0) {
                        return@setOnTouchListener true
                    }
                    if (touchXToBitmap > bitmap.width || touchYToBitmap > bitmap.height) {
                        return@setOnTouchListener true
                    }

                    // get bitmap
                    val pixel: Int = bitmap.getPixel(
                        touchXToBitmap.toInt(),
                        touchYToBitmap.toInt()
                    )

                    red = Color.red(pixel)
                    green = Color.green(pixel)
                    blue = Color.blue(pixel)
                    alpha = Color.alpha(pixel)

                    viewColor.setBackgroundColor(Color.argb(alpha, red, green, blue))
                }
            }

            true
        }
    }
}