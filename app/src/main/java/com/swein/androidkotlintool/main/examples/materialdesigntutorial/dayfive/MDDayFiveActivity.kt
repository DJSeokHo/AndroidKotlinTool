package com.swein.androidkotlintool.main.examples.materialdesigntutorial.dayfive

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.main.examples.materialdesigntutorial.BottomActionSheetFragment


class MDDayFiveActivity : AppCompatActivity() {

    private val bottomActionSheet: LinearLayout by lazy {
        findViewById(R.id.bottomActionSheet)
    }

    private var behavior: BottomSheetBehavior<LinearLayout>? = null

    private val bottomSheetCallback = object: BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            ILog.debug("???", "onStateChanged $newState")

            /*
            /** The bottom sheet is dragging. */
              public static final int STATE_DRAGGING = 1;

              /** The bottom sheet is settling. */
              public static final int STATE_SETTLING = 2;

              /** The bottom sheet is expanded. */
              public static final int STATE_EXPANDED = 3;

              /** The bottom sheet is collapsed. */
              public static final int STATE_COLLAPSED = 4;

              /** The bottom sheet is hidden. */
              public static final int STATE_HIDDEN = 5;

              /** The bottom sheet is half-expanded (used when mFitToContents is false). */
              public static final int STATE_HALF_EXPANDED = 6;
             */
            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                ILog.debug("???", "BottomSheetBehavior appear")
                imageView.setImageResource(R.mipmap.down_arrow)
            }
            else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                ILog.debug("???", "BottomSheetBehavior disappear")
                imageView.setImageResource(R.mipmap.up_arrow)
            }

        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {

        }
    }

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mdday_five)

        findViewById<Button>(R.id.buttonBottomActionSheet).setOnClickListener {

            behavior?.let {
                if (it.state == BottomSheetBehavior.STATE_EXPANDED) {
                    it.state = BottomSheetBehavior.STATE_COLLAPSED
                    imageView.setImageResource(R.mipmap.up_arrow)
                }
                else {
                    it.state = BottomSheetBehavior.STATE_EXPANDED
                    imageView.setImageResource(R.mipmap.down_arrow)
                }
            }
        }

        findViewById<Button>(R.id.buttonBottomActionSheetFragment).setOnClickListener {
            BottomActionSheetFragment(true).show(supportFragmentManager, BottomActionSheetFragment.TAG)
        }

        behavior = BottomSheetBehavior.from(bottomActionSheet)
        behavior!!.addBottomSheetCallback(bottomSheetCallback)
    }

    override fun onBackPressed() {

        behavior?.let {
            if (it.state == BottomSheetBehavior.STATE_EXPANDED) {
                it.state = BottomSheetBehavior.STATE_COLLAPSED
                imageView.setImageResource(R.mipmap.up_arrow)
                return
            }

            it.removeBottomSheetCallback(bottomSheetCallback)
        }

        finish()
    }
}