package com.swein.androidkotlintool.main.examples.dragindicatorexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.swein.androidkotlintool.R

class DragIndicatorExampleActivity : AppCompatActivity() {

//    private val verticalDragAbleIndicatorView: VerticalDragAbleIndicatorView by lazy {
//        findViewById(R.id.verticalDragAbleIndicatorView)
//    }

    private val verticalDragAbleIndicatorWithoutMarginView: VerticalDragAbleIndicatorWithoutMarginView by lazy {
        findViewById(R.id.verticalDragAbleIndicatorWithoutMarginView)
    }

    private val textView: TextView by lazy {
        findViewById(R.id.textView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag_indicator_example)

//        verticalDragAbleIndicatorView.initData(
//            itemList = mutableListOf<VerticalDragAbleIndicatorView.ItemModel>().apply {
//                for (i in 0 until 9) {
//                    add(VerticalDragAbleIndicatorView.ItemModel())
//                }
//            },
//            itemWidthDp = 40f,
//            itemHeightDp = 40f,
//            indicatorRadiusDp = 20f,
//            topMarginDp = 80f,
//            indicatorDefaultPosition = -1,
//            onItemSelected = {
//
//                textView.text = if (it.title == "") {
//                    "#"
//                }
//                else {
//                    it.title
//                }
//            }
//        )

        verticalDragAbleIndicatorWithoutMarginView.initData(
            itemList = mutableListOf<VerticalDragAbleIndicatorWithoutMarginView.ItemModel>().apply {
                for (i in 0 until 9) {
                    add(VerticalDragAbleIndicatorWithoutMarginView.ItemModel())
                }
            },
            itemWidthDp = 40f,
            itemHeightDp = 40f,
            indicatorRadiusDp = 20f,
            indicatorDefaultPosition = 3,
            onItemSelected = {

                textView.text = it.title
            }
        )
    }
}