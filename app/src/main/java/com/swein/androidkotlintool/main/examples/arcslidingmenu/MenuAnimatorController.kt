package com.swein.androidkotlintool.main.examples.arcslidingmenu

import android.animation.ValueAnimator
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.swein.androidkotlintool.framework.util.display.DisplayUtil


class MenuAnimationInfoData {
    var radius: Float = -1f
    var angle: Float = -1f
    var size: Int = -1
}

enum class RotateDirection {
    NONE, LEFT, RIGHT
}

object MenuAnimatorController {

    private val positionList = mutableListOf<MenuAnimationInfoData>()
    private val viewList = mutableListOf<View>()

    private var isAnimationFinished = true

    private val handler = Handler(Looper.getMainLooper())

    fun initData(context: Context, vararg views: View) {

        viewList.clear()

        for (view in views) {
            viewList.add(view)
        }

        // 0 65 90 270 295
        positionList.clear()

        val position0 = MenuAnimationInfoData().apply {
            this.radius = DisplayUtil.dipToPx(context, 70f).toFloat()
            this.angle = 0f
            this.size = DisplayUtil.dipToPx(context, 60f)
        }
        positionList.add(position0)

        val position65 = MenuAnimationInfoData().apply {
            this.radius = DisplayUtil.dipToPx(context, 80f).toFloat()
            this.angle = 65f
            this.size = DisplayUtil.dipToPx(context, 40f)
        }
        positionList.add(position65)

        val position90 = MenuAnimationInfoData().apply {
            this.radius = DisplayUtil.dipToPx(context, 130f).toFloat()
            this.angle = 90f
            this.size = DisplayUtil.dipToPx(context, 40f)
        }
        positionList.add(position90)

        val position270 = MenuAnimationInfoData().apply {
            this.radius = DisplayUtil.dipToPx(context, 130f).toFloat()
            this.angle = 270f
            this.size = DisplayUtil.dipToPx(context, 40f)
        }
        positionList.add(position270)

        val position295 = MenuAnimationInfoData().apply {
            this.radius = DisplayUtil.dipToPx(context, 80f).toFloat()
            this.angle = 295f
            this.size = DisplayUtil.dipToPx(context, 40f)
        }
        positionList.add(position295)
    }

    fun rotate(rotateDirection: RotateDirection, step: Int, duration: Long, onRepeat: (() -> Unit)? = null, onFinish: (() -> Unit)? = null) {

        if (rotateDirection == RotateDirection.NONE) {
            return
        }

        if (step == 0) {
            return
        }

        if (!isAnimationFinished) {
            return
        }

        isAnimationFinished = false

        var startData: MenuAnimationInfoData
        var endData: MenuAnimationInfoData

        for (i in 0 until viewList.size) {

            val currentView = viewList[i]

            startData = getStartPosition(currentView)
            endData = getEndPosition(currentView, rotateDirection)

            startAnimation(startData, endData, duration, {
                val layoutParams = currentView.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.circleRadius = it.toInt()
                currentView.layoutParams = layoutParams
            }, {
                val layoutParams = currentView.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.circleAngle = it
                currentView.layoutParams = layoutParams
            }, {
                val layoutParams = currentView.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.width = it
                layoutParams.height = it
                currentView.layoutParams = layoutParams
            })

            resetToStartPosition(currentView, rotateDirection, duration)
        }

        handler.postDelayed({

            isAnimationFinished = true

            if (step > 1) {
                onRepeat?.let {
                    it()
                }
            }
            else if (step == 1) {
                onFinish?.let {
                    it()
                }
            }

        }, duration + 50)

    }

    fun getStep(viewClicked: View): Int {

        val viewClickedLayoutParams = viewClicked.layoutParams as ConstraintLayout.LayoutParams
        return if (viewClickedLayoutParams.circleAngle == 65f || viewClickedLayoutParams.circleAngle == 295f) {
            1
        }
        else if (viewClickedLayoutParams.circleAngle == 90f || viewClickedLayoutParams.circleAngle == 270f) {
            2
        }
        else {
            0
        }
    }

    fun getRotateDirection(viewClicked: View): RotateDirection {
        val viewClickedLayoutParams = viewClicked.layoutParams as ConstraintLayout.LayoutParams
        return if (viewClickedLayoutParams.circleAngle == 65f || viewClickedLayoutParams.circleAngle == 90f) {
            RotateDirection.LEFT
        }
        else if (viewClickedLayoutParams.circleAngle == 270f || viewClickedLayoutParams.circleAngle == 295f) {
            RotateDirection.RIGHT
        }
        else {
            RotateDirection.NONE
        }
    }

    private fun getPositionIndexOfView(view: View): Int {
        val layoutParams = view.layoutParams as ConstraintLayout.LayoutParams
        for (i in 0 until positionList.size) {
            if (layoutParams.circleAngle == positionList[i].angle && layoutParams.circleRadius.toFloat() == positionList[i].radius) {
                return i
            }
        }

        return -1
    }

    private fun getEndPosition(currentView: View, rotateDirection: RotateDirection): MenuAnimationInfoData {

        val currentViewPositionIndex = getPositionIndexOfView(currentView)
        val endData: MenuAnimationInfoData

        if (rotateDirection == RotateDirection.RIGHT) {
            if (currentViewPositionIndex == positionList.size - 1) {
                endData = MenuAnimationInfoData().apply {
                    this.radius = DisplayUtil.dipToPx(currentView.context, 70f).toFloat()
                    this.angle = 360f
                    this.size = DisplayUtil.dipToPx(currentView.context, 60f)
                }
            }
            else {
                endData = positionList[currentViewPositionIndex + 1]
            }
        }
        else if (rotateDirection == RotateDirection.LEFT) {
            if (currentViewPositionIndex == 0) {
                endData = MenuAnimationInfoData().apply {
                    this.radius = DisplayUtil.dipToPx(currentView.context, 80f).toFloat()
                    this.angle = -65f
                    this.size = DisplayUtil.dipToPx(currentView.context, 40f)
                }
            }
            else {
                endData = positionList[currentViewPositionIndex - 1]
            }
        }
        else {
            endData = MenuAnimationInfoData()
        }

        return endData
    }

    private fun getStartPosition(currentView: View): MenuAnimationInfoData {
        val currentViewPositionIndex = getPositionIndexOfView(currentView)
        return positionList[currentViewPositionIndex]
    }

    private fun resetToStartPosition(view: View, rotateDirection: RotateDirection, duration: Long) {

        view.postDelayed({

            if (rotateDirection == RotateDirection.RIGHT) {
                val layoutParams = view.layoutParams as ConstraintLayout.LayoutParams
                if (layoutParams.circleAngle == 360f) {
                    layoutParams.circleAngle = 0f
                }
                view.layoutParams = layoutParams
            }
            else if (rotateDirection == RotateDirection.LEFT) {
                val layoutParams = view.layoutParams as ConstraintLayout.LayoutParams
                if (layoutParams.circleAngle == -65f) {
                    layoutParams.circleAngle = 295f
                }
                view.layoutParams = layoutParams
            }

        }, duration + 30)
    }

    private fun startAnimation(startData: MenuAnimationInfoData, endData: MenuAnimationInfoData, duration: Long,
                               onRadiusUpdate: (Float) -> Unit,
                               onAngleAnimator: (Float) -> Unit,
                               onSizeAnimator: (Int) -> Unit
    ) {

        val radiusAnimator = ValueAnimator.ofFloat(startData.radius, endData.radius)
        val angleAnimator = ValueAnimator.ofFloat(startData.angle, endData.angle)
        val sizeAnimator = ValueAnimator.ofInt(startData.size, endData.size)

        radiusAnimator.addUpdateListener {
            onRadiusUpdate(it.animatedValue as Float)
        }

        angleAnimator.addUpdateListener {
            onAngleAnimator(it.animatedValue as Float)
        }

        sizeAnimator.addUpdateListener {
            onSizeAnimator(it.animatedValue as Int)
        }

        radiusAnimator.duration = duration
//        radiusAnimator.interpolator = DecelerateInterpolator()

        angleAnimator.duration = duration
//        angleAnimator.interpolator = DecelerateInterpolator()

        sizeAnimator.duration = duration
//        sizeAnimator.interpolator = DecelerateInterpolator()

        radiusAnimator.start()
        angleAnimator.start()
        sizeAnimator.start()
    }

}