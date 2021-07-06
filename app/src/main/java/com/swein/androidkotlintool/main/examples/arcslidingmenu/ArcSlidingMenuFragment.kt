package com.swein.androidkotlintool.main.examples.arcslidingmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.livedata.LiveDataDemoFragment


class ArcSlidingMenuFragment : Fragment() {

    companion object {

        const val TAG = "ArcSlidingMenuFragment"

        @JvmStatic
        fun newInstance() =
            ArcSlidingMenuFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private lateinit var rootView: View

    private val frameLayoutRoot: FrameLayout by lazy {
        rootView.findViewById(R.id.frameLayoutRoot)
    }

    private val imageView: ImageView by lazy {
        rootView.findViewById(R.id.imageView)
    }

    private val textViewTitle: TextView by lazy {
        rootView.findViewById(R.id.textViewTitle)
    }

    private val textViewSubTitle: TextView by lazy {
        rootView.findViewById(R.id.textViewSubTitle)
    }

    private val constraintLayout: ConstraintLayout by lazy {
        rootView.findViewById(R.id.constraintLayout)
    }

    private val imageButtonSupportCenter: ImageButton by lazy {
        rootView.findViewById(R.id.imageButtonSupportCenter)
    }
    private val imageButtonClientCenter: ImageButton by lazy {
        rootView.findViewById(R.id.imageButtonClientCenter)
    }
    private val imageButtonEvent: ImageButton by lazy {
        rootView.findViewById(R.id.imageButtonEvent)
    }
    private val imageButtonBJCollection: ImageButton by lazy {
        rootView.findViewById(R.id.imageButtonBJCollection)
    }
    private val imageButtonCommunity: ImageButton by lazy {
        rootView.findViewById(R.id.imageButtonCommunity)
    }

    private val textViewSupportCenter: ImageButton by lazy {
        rootView.findViewById(R.id.textViewSupportCenter)
    }
    private val textViewClientCenter: ImageButton by lazy {
        rootView.findViewById(R.id.textViewClientCenter)
    }
    private val textViewEvent: ImageButton by lazy {
        rootView.findViewById(R.id.textViewEvent)
    }
    private val textViewBJCollection: ImageButton by lazy {
        rootView.findViewById(R.id.textViewBJCollection)
    }
    private val textViewCommunity: ImageButton by lazy {
        rootView.findViewById(R.id.textViewCommunity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_arc_sliding_menu, container, false)
        MenuAnimatorController.initData(rootView.context, imageButtonSupportCenter, imageButtonClientCenter, imageButtonEvent, imageButtonCommunity, imageButtonBJCollection)
        setListener()
        return rootView
    }

    private fun setListener() {

        frameLayoutRoot.setOnClickListener {
            ILog.debug(TAG, "root click!!")
        }

        val listener = createTouchListener()
        constraintLayout.setOnTouchListener(listener)
        imageButtonSupportCenter.setOnTouchListener(listener)
        imageButtonClientCenter.setOnTouchListener(listener)
        imageButtonEvent.setOnTouchListener(listener)
        imageButtonBJCollection.setOnTouchListener(listener)
        imageButtonCommunity.setOnTouchListener(listener)
    }

    private fun createTouchListener(): View.OnTouchListener {

        return object: View.OnTouchListener {

            var x: Float = 0f
            var isClick = false

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {

                        event.let {
                            x = it.x
                        }

                        return if (v is ImageButton || v is TextView) {
                            isClick = true
                            true
                        }
                        else {
                            isClick = false
                            false
                        }

                    }
                    MotionEvent.ACTION_MOVE -> {
                        return true
                    }
                    MotionEvent.ACTION_UP -> {

                        val result = event.x - x

                        val rotateDirection = when {
                            result > 100 -> {
                                RotateDirection.RIGHT
                            }
                            result < -100 -> {
                                RotateDirection.LEFT
                            }
                            else -> {
                                RotateDirection.NONE
                            }
                        }

                        if (isClick) {

                            if (rotateDirection == RotateDirection.NONE) {

                                v?.let {

                                    val step = MenuAnimatorController.getStep(it)
                                    val direction = MenuAnimatorController.getRotateDirection(it)
                                    ILog.debug("???", "$step ${direction.name}")
                                    startAnimation(direction, step)
                                }

                            }
                            else {
                                startAnimation(rotateDirection, 1)
                            }

                            isClick = false
                        }
                        else {
                            startAnimation(rotateDirection, 1)
                        }

                        return false
                    }
                    else -> {
                        return false
                    }
                }

                return false
            }

        }
    }

    private fun startAnimation(rotateDirection: RotateDirection, step: Int, duration: Long = 200) {

        if (step == 2) {
            MenuAnimatorController.rotate(rotateDirection, step, duration, onRepeat = {
                MenuAnimatorController.rotate(rotateDirection, step - 1, duration, onFinish = {

                })
            })
        }
        else if (step == 1) {
            MenuAnimatorController.rotate(rotateDirection, step, duration, onFinish = {

            })
        }

    }
}