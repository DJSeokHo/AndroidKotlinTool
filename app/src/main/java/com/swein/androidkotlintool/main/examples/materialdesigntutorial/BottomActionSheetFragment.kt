package com.swein.androidkotlintool.main.examples.materialdesigntutorial

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.swein.androidkotlintool.R

class BottomActionSheetFragment(private val isFullScreen: Boolean = false): BottomSheetDialogFragment() {

    companion object {
        const val TAG = "BottomActionSheetFragment"
    }

    private lateinit var rootView: View

    private lateinit var buttonSubscribe: Button
    private lateinit var buttonLike: Button
    private lateinit var imageButtonNotificationBell: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isFullScreen) {
            setStyle(STYLE_NORMAL, R.style.BottomSheetDialogBg)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_bottom_action_sheet, container)
        findView()
        setListener()
        return rootView
    }

    private fun findView() {
        buttonSubscribe = rootView.findViewById(R.id.buttonSubscribe)
        buttonLike = rootView.findViewById(R.id.buttonLike)
        imageButtonNotificationBell = rootView.findViewById(R.id.imageButtonNotificationBell)
    }

    private fun setListener() {
        buttonSubscribe.setOnClickListener {
            Toast.makeText(context, "subscribe", Toast.LENGTH_SHORT).show()
        }

        buttonLike.setOnClickListener {
            Toast.makeText(context, "like", Toast.LENGTH_SHORT).show()
        }

        imageButtonNotificationBell.setOnClickListener {
            Toast.makeText(context, "notification bell", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 设置默认全屏显示
     */
    override fun onStart() {
        super.onStart()

        if (isFullScreen) {

            //拿到系统的 bottom_sheet
            val view: FrameLayout = dialog?.findViewById(R.id.design_bottom_sheet)!!
            //设置view高度
            view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
//            view.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
//            view.layoutParams.height = 400
            //获取behavior
            val behavior = BottomSheetBehavior.from(view)
            //设置展开状态
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
//            behavior.peekHeight = 400


            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> {
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                        }
                        BottomSheetBehavior.STATE_DRAGGING -> {
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                        }
                        BottomSheetBehavior.STATE_HIDDEN -> {
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }

            })

        }

    }

    override fun onCancel(dialog: DialogInterface) {
        if (isFullScreen) {
            dialog.dismiss()
        }
        super.onCancel(dialog)
    }
}