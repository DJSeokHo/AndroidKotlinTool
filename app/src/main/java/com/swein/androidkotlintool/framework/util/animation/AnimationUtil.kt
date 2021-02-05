package com.swein.androidkotlintool.framework.util.animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import com.swein.androidkotlintool.R

class AnimationUtil {

    companion object {

        fun show(context: Context?): Animation? {
            return AnimationUtils.loadAnimation(context, R.anim.fade_in)
        }

        fun hide(context: Context?): Animation? {
            return AnimationUtils.loadAnimation(context, R.anim.fade_out)
        }

        fun scrollViewSmoothScrollToY(
            scrollView: ScrollView,
            scrollToY: Int,
            duration: Int,
            animatorListener: Animator.AnimatorListener?
        ) {
            val objectAnimator = ObjectAnimator.ofInt(scrollView, "scrollY", scrollToY)
            if (null != animatorListener) {
                objectAnimator.addListener(animatorListener)
            }
            objectAnimator.setDuration(duration.toLong()).start()
        }

        fun scrollViewSmoothScrollToX(
            scrollView: HorizontalScrollView,
            scrollToX: Int,
            duration: Int,
            animatorListener: Animator.AnimatorListener?
        ) {
            val objectAnimator = ObjectAnimator.ofInt(scrollView, "scrollX", scrollToX)
            if (null != animatorListener) {
                objectAnimator.addListener(animatorListener)
            }
            objectAnimator.setDuration(duration.toLong()).start()
        }

        fun scrollViewSmoothScrollToX(
            scrollView: ScrollView,
            scrollToX: Int,
            duration: Int,
            animatorListener: Animator.AnimatorListener?
        ) {
            val objectAnimator = ObjectAnimator.ofInt(scrollView, "scrollX", scrollToX)
            if (null != animatorListener) {
                objectAnimator.addListener(animatorListener)
            }
            objectAnimator.setDuration(duration.toLong()).start()
        }

        fun translateViewSmoothToX(view: View, x: Float, duration: Int, animatorListener: Animator.AnimatorListener?) {
            val objectAnimator = ObjectAnimator.ofFloat(view, "translationX", x)
            if (null != animatorListener) {
                objectAnimator.addListener(animatorListener)
            }
            objectAnimator.setDuration(duration.toLong()).start()
        }

        fun translateViewSmoothToY(view: View, y: Float, duration: Int, animatorListener: Animator.AnimatorListener?) {
            val objectAnimator = ObjectAnimator.ofFloat(view, "translationY", y)
            if (null != animatorListener) {
                objectAnimator.addListener(animatorListener)
            }
            objectAnimator.setDuration(duration.toLong()).start()
        }

        fun setViewToPositionX(view: View, x: Float, duration: Int, animatorListener: Animator.AnimatorListener?) {
            val objectAnimator = ObjectAnimator.ofFloat(view, "x", x)
            if (null != animatorListener) {
                objectAnimator.addListener(animatorListener)
            }
            objectAnimator.setDuration(duration.toLong()).start()
        }

        fun setViewToPositionY(view: View, y: Float, duration: Int, animatorListener: Animator.AnimatorListener?) {
            val objectAnimator = ObjectAnimator.ofFloat(view, "y", y)
            if (null != animatorListener) {
                objectAnimator.addListener(animatorListener)
            }
            objectAnimator.setDuration(duration.toLong()).start()
        }

        fun setViewRotation(
            view: View,
            duration: Int,
            animatorListener: Animator.AnimatorListener?,
            fromAngle: Float,
            toAngle: Float
        ) {
            val objectAnimator = ObjectAnimator.ofFloat(view, "rotation", fromAngle, toAngle)
            if (null != animatorListener) {
                objectAnimator.addListener(animatorListener)
            }
            objectAnimator.setDuration(duration.toLong()).start()
        }


        /*
        animation set element
     */
        fun createAnimationSetElement(param: String, value: Float): PropertyValuesHolder {
            /*
            translationX
            translationY

            scrollX
            scrollY
         */
            return PropertyValuesHolder.ofFloat(param, value)
        }

        fun viewAnimationSet(view: View, duration: Int, vararg propertyValuesHolders: PropertyValuesHolder) {

            ObjectAnimator.ofPropertyValuesHolder(view, *propertyValuesHolders).setDuration(duration.toLong()).start()

        }
        /*
        animation set element
     */

        fun shakeView(context: Context, view: View) {
            val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
            view.startAnimation(shake)
        }
    }

}