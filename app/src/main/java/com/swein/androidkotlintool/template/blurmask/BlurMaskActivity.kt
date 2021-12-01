package com.swein.androidkotlintool.template.blurmask

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.thread.ThreadUtility


class BlurMaskActivity : FragmentActivity() {

    private lateinit var imageViewBackground: ImageView
    private lateinit var imageViewCrop: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blur_mask)

        findView()
        setImage()
    }

    private fun findView() {
        imageViewBackground = findViewById(R.id.imageViewBackground)
        imageViewCrop = findViewById(R.id.imageViewCrop)
    }

    private fun setImage() {

        imageViewBackground.post {

            ThreadUtility.startThread {

                val imageUrl = "https://cdn.hipwallpaper.com/i/83/84/JVE4pu.jpg"

                val futureTarget: FutureTarget<Bitmap> = Glide.with(this)
                    .asBitmap()
                    .load(imageUrl)
                    .submit(imageViewBackground.width, imageViewBackground.height)

                val bitmap = futureTarget.get()


                ThreadUtility.startUIThread(0) {
                    imageViewBackground.setImageBitmap(bitmap)

                    imageViewCrop.post {

                        var cropBitmap = Bitmap.createBitmap(
                            imageViewBackground.width,
                            imageViewBackground.height,
                            Bitmap.Config.ARGB_8888
                        )
                        val canvas = Canvas(cropBitmap)
                        imageViewBackground.draw(canvas)

                        cropBitmap = Bitmap.createBitmap(cropBitmap, imageViewCrop.x.toInt(), imageViewCrop.y.toInt(), imageViewCrop.width, imageViewCrop.height)
                        imageViewCrop.setImageBitmap(rsBlur(this, cropBitmap))
                    }

                }

            }

        }

    }

    /**
     * 0 < radius <= 25
     */
    private fun rsBlur(context: Context, sourceBitmap: Bitmap, radius: Int = 25): Bitmap {

        val renderScript = RenderScript.create(context)

        val input = Allocation.createFromBitmap(renderScript, sourceBitmap)
        val output = Allocation.createTyped(renderScript, input.type)

        val scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))

        scriptIntrinsicBlur.setInput(input)

        val r = when {
            radius <= 0 -> {
                1
            }
            radius > 25 -> {
                25
            }
            else -> {
                radius
            }
        }

        scriptIntrinsicBlur.setRadius(r.toFloat())

        scriptIntrinsicBlur.forEach(output)

        output.copyTo(sourceBitmap)

        renderScript.destroy()

        return sourceBitmap
    }
}