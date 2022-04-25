package com.swein.androidkotlintool.main.examples.lottieexample

import android.animation.Animator
import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SoundEffectConstants
import com.airbnb.lottie.LottieAnimationView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog

class LottieExampleActivity : AppCompatActivity() {

    private val lottieAnimationViewCamera: LottieAnimationView by lazy {
        findViewById(R.id.lottieAnimationViewCamera)
    }

    private val lottieAnimationViewMenu: LottieAnimationView by lazy {
        findViewById(R.id.lottieAnimationViewMenu)
    }

    private val lottieAnimationViewHeart: LottieAnimationView by lazy {
        findViewById(R.id.lottieAnimationViewHeart)
    }

    private val lottieAnimationViewNotification: LottieAnimationView by lazy {
        findViewById(R.id.lottieAnimationViewNotification)
    }

    private var isMenuOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie_example)

//        lottieAnimationViewCamera.setAnimation(R.raw.animate_camera)
//
//        lottieAnimationViewCamera.setOnClickListener {
//            lottieAnimationViewCamera.playAnimation()
//        }

        lottieAnimationViewHeart.setAnimation(R.raw.heart)
        lottieAnimationViewHeart.setOnClickListener {
            lottieAnimationViewHeart.playAnimation()
        }

        lottieAnimationViewNotification.setAnimation(R.raw.notification)
        lottieAnimationViewNotification.setOnClickListener {
            lottieAnimationViewNotification.playAnimation()
        }

//        lottieAnimationViewCamera.addAnimatorListener(object : Animator.AnimatorListener {
//            override fun onAnimationStart(p0: Animator?) {
//                ILog.debug("???", "onAnimationStart")
//            }
//
//            override fun onAnimationEnd(p0: Animator?) {
//                ILog.debug("???", "onAnimationEnd")
//            }
//
//            override fun onAnimationCancel(p0: Animator?) {
//                ILog.debug("???", "onAnimationCancel")
//            }
//
//            override fun onAnimationRepeat(p0: Animator?) {
//                ILog.debug("???", "onAnimationRepeat")
//            }
//
//        })


//        lottieAnimationViewMenu.addLottieOnCompositionLoadedListener { lottieComposition ->
//            ILog.debug("???", "${lottieComposition.durationFrames}")
//
//            val half = (lottieComposition.durationFrames * 0.5).toInt()
//
//            lottieAnimationViewMenu.setOnClickListener {
//
//                if (isMenuOpen) {
//                    lottieAnimationViewMenu.setMinFrame(half)
//                    lottieAnimationViewMenu.setMaxFrame(lottieComposition.durationFrames.toInt())
//
//                }
//                else {
//                    lottieAnimationViewMenu.setMinFrame(0)
//                    lottieAnimationViewMenu.setMaxFrame(half)
//                }
//
//                isMenuOpen = !isMenuOpen
//
//                lottieAnimationViewMenu.playAnimation()
//            }
//
//        }
//
//        lottieAnimationViewMenu.setAnimation(R.raw.animate_menu)

    }
}