package com.swein.androidkotlintool.framework.extension

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.setCurrentItem(
    item: Int,
    duration: Long,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    pagePxWidth: Int = width // Default value taken from getWidth() from ViewPager2 view
) {

    val pxToDrag: Int = pagePxWidth * (item - currentItem)
    val animator = ValueAnimator.ofInt(0, pxToDrag)
    var previousValue = 0

    animator.addUpdateListener { valueAnimator ->
        val currentValue = valueAnimator.animatedValue as Int
        val currentPxToDrag = (currentValue - previousValue).toFloat()
        fakeDragBy(-currentPxToDrag)
        previousValue = currentValue
    }

    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator) {
            beginFakeDrag()
        }

        override fun onAnimationEnd(p0: Animator) {
            endFakeDrag()
        }

        override fun onAnimationCancel(p0: Animator) {

        }

        override fun onAnimationRepeat(p0: Animator) {

        }
    })

    animator.interpolator = interpolator
    animator.duration = duration
    animator.start()
}