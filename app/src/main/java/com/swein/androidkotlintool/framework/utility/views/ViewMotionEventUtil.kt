package com.swein.androidkotlintool.framework.utility.views

import android.view.MotionEvent
import android.view.VelocityTracker

class ViewMotionEventUtil {

    companion object {

        interface ViewMotionEventUtilDelegate {

            fun onVelocityResult(velocity: Int)

        }

        fun getVelocityX(
            motionEvent: MotionEvent,
            second: Int,
            viewMotionEventUtilDelegate: ViewMotionEventUtilDelegate
        ) {
            val velocityTracker = VelocityTracker.obtain()
            velocityTracker.addMovement(motionEvent)
            velocityTracker.computeCurrentVelocity(second * 1000)

            viewMotionEventUtilDelegate.onVelocityResult(velocityTracker.xVelocity.toInt())

            velocityTracker.clear()
            velocityTracker.recycle()
        }

        fun getVelocityY(
            motionEvent: MotionEvent,
            second: Int,
            viewMotionEventUtilDelegate: ViewMotionEventUtilDelegate
        ) {
            val velocityTracker = VelocityTracker.obtain()
            velocityTracker.addMovement(motionEvent)
            velocityTracker.computeCurrentVelocity(second * 1000)

            viewMotionEventUtilDelegate.onVelocityResult(velocityTracker.xVelocity.toInt())

            velocityTracker.clear()
            velocityTracker.recycle()
        }
    }
}