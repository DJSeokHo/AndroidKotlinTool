package com.swein.androidkotlintool.main.examples.shapeableimageviewexample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CutCornerTreatment
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.display.DisplayUtility

class ShapeAbleImageViewExampleActivity : AppCompatActivity() {

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
    private val imageView6: ShapeableImageView by lazy {
        findViewById(R.id.imageView6)
    }
    private val imageView7: ShapeableImageView by lazy {
        findViewById(R.id.imageView7)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shape_able_image_view_example)

        // circle
        imageView1.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCornerSizes(RelativeCornerSize(0.5f))
            .build()

        // cut
        imageView2.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(CutCornerTreatment())
            .setAllCornerSizes(dipToPx(this, 10f))
            .build()

        // rhombus
        imageView3.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(CutCornerTreatment())
            .setAllCornerSizes(RelativeCornerSize(0.5f))
            .build()

        // fan-shaped
        imageView4.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopRightCorner(RoundedCornerTreatment())
            .setTopRightCornerSize(RelativeCornerSize(1f))
            .build()

        // double fan-shaped
        imageView5.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopLeftCorner(RoundedCornerTreatment())
            .setTopRightCorner(RoundedCornerTreatment())
            .setTopLeftCornerSize(RelativeCornerSize(0.6f))
            .setTopRightCornerSize(RelativeCornerSize(0.6f))
            .build()

        // water drop
        imageView6.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setTopLeftCornerSize(RelativeCornerSize(0.8f))
            .setTopRightCornerSize(RelativeCornerSize(0.8f))
            .setBottomLeftCornerSize(dipToPx(this, 30f))
            .setBottomRightCornerSize(dipToPx(this, 30f))
            .build()

        // leaf
        imageView7.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopRightCorner(RoundedCornerTreatment())
            .setBottomLeftCorner(RoundedCornerTreatment())
            .setTopRightCornerSize(RelativeCornerSize(0.6f))
            .setBottomLeftCornerSize(RelativeCornerSize(0.6f))
            .build()
    }

    fun dipToPx(context: Context, dipValue: Float): Float {
        return dipValue * context.resources.displayMetrics.density
    }
}