package com.swein.androidkotlintool.main.examples.customanimation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import com.swein.androidkotlintool.R

/**
 * AccelerateDecelerateInterpolator 先加速再减速。这是默认的 Interpolator
 * LinearInterpolator 匀速
 * AccelerateInterpolator 持续加速
 * DecelerateInterpolator 持续减速直到 0
 * AnticipateInterpolator 先回拉一下再进行正常动画轨迹。效果看起来有点像投掷物体或跳跃等动作前的蓄力 如果是放大动画，那么就是先缩小一下再放大；其他类型的动画同理
 * OvershootInterpolator 动画会超过目标值一些，然后再弹回来。效果看起来有点像你一屁股坐在沙发上后又被弹起来一点的感觉
 *
 * AnticipateOvershootInterpolator 上面这两个的结合版：开始前回拉，最后超过一些然后回弹
 * BounceInterpolator 在目标值处弹跳。有点像玻璃球掉在地板上的效果
 * CycleInterpolator 这个也是一个正弦 / 余弦曲线，不过它和 AccelerateDecelerateInterpolator 的区别是，它可以自定义曲线的周期，所以动画可以不到终点就结束，也可以到达终点后回弹，回弹的次数由曲线的周期决定，曲线的周期由 CycleInterpolator() 构造方法的参数决定
 */
class CustomAnimationExampleActivity : AppCompatActivity() {

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    private val view: View by lazy {
        findViewById(R.id.view)
    }

    private val customCircleProgressView: CustomCircleProgressView  by lazy {
        CustomCircleProgressView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_animation_example)

        findViewById<Button>(R.id.button).setOnClickListener {
//            testObjectAnimator()
            testViewAnimator()
            val colorStart = getColor(R.color.red)
            val colorFinish = getColor(R.color.green)
            val animator = ObjectAnimator.ofArgb(view, "backgroundColor", colorStart, colorFinish).setDuration(500)
            animator.start()

        }

        findViewById<FrameLayout>(R.id.container).addView(customCircleProgressView)
    }

    private fun testObjectAnimator() {
//        val animator = ObjectAnimator.ofFloat(imageView, "translationX", 500f).setDuration(500) // 目标值
//        val animator = ObjectAnimator.ofFloat(imageView, "translationX", 0f, 500f).setDuration(500) // 起始值 目标值
//        val animator = ObjectAnimator.ofFloat(imageView, "translationX", 0f, 100f, 300f, 450f, 500f).setDuration(500) // 起始值 中间值。。。 目标值
//        animator.start()

        val animator = ObjectAnimator.ofFloat(customCircleProgressView, "progressValue", 0f, 100f).setDuration(1000)
        animator.interpolator = LinearInterpolator()
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {

            }

            override fun onAnimationEnd(p0: Animator) {
            }

            override fun onAnimationCancel(p0: Animator) {
            }

            override fun onAnimationRepeat(p0: Animator) {
            }

        })
        animator.start()
    }

    private fun testViewAnimator() {

        imageView.animate().translationX(100f) //平移到指定位置
        imageView.animate().translationXBy(100f) //现在位置基础上平移指定像素距离
        imageView.animate().rotationBy(90f).translationXBy(100f)
//            .setInterpolator(AccelerateDecelerateInterpolator()) // 速度模型 AccelerateDecelerateInterpolator() 默认, LinearInterpolator()
//            .setInterpolator(AnticipateOvershootInterpolator()) // 速度模型 AccelerateDecelerateInterpolator() 默认, LinearInterpolator()
            .setInterpolator(BounceInterpolator()) // 速度模型 AccelerateDecelerateInterpolator() 默认, LinearInterpolator()
            .setDuration(500).setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {

                }

                override fun onAnimationEnd(p0: Animator) {
                }

                override fun onAnimationCancel(p0: Animator) {
                }

                override fun onAnimationRepeat(p0: Animator) {
                }

            })
    }
}