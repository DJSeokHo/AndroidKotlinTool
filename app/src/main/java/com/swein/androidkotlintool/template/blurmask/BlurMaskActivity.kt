package com.swein.androidkotlintool.template.blurmask

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.display.DisplayUtil
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil


class BlurMaskActivity : FragmentActivity() {

    private lateinit var imageView: ImageView
    private lateinit var imageViewCrop: ImageView
    private lateinit var frameLayoutRoot: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blur_mask)

        findView()
        setImage()
    }

    private fun findView() {
        frameLayoutRoot = findViewById(R.id.frameLayoutRoot)
        imageView = findViewById(R.id.imageView)
        imageViewCrop = findViewById(R.id.imageViewCrop)
    }

    private fun setImage() {

        imageView.post {

            ThreadUtil.startThread {

                val imageUrl = "https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F37%2F2019%2F06%2F12170406%2Fmodern-home-exterior-gray-scheme-792ab713.jpg"

                val futureTarget: FutureTarget<Bitmap> = Glide.with(this)
                    .asBitmap()
                    .load(imageUrl)
                    .submit(imageView.width, imageView.height)

                val bitmap = futureTarget.get()


                ThreadUtil.startUIThread(0) {
                    imageView.setImageBitmap(bitmap)

                    imageViewCrop.post {

                        var cropBitmap = Bitmap.createBitmap(
                            imageView.width,
                            imageView.height,
                            Bitmap.Config.ARGB_8888
                        )
                        val canvas = Canvas(cropBitmap)
                        imageView.draw(canvas)

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