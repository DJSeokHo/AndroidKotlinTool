package com.swein.androidkotlintool.main.examples.rotatingcirclemenu

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.rotatingcirclemenu.circlemenu.CircleMenuLayout
import com.swein.androidkotlintool.main.examples.rotatingcirclemenu.circlemenu.item.CircleMenuItemView
import com.swein.androidkotlintool.main.examples.rotatingcirclemenu.circlemenu.model.CircleMenuModel

class RotatingCircleMenuExampleActivity : AppCompatActivity() {

    private val circleMenuLayout: CircleMenuLayout by lazy {
        findViewById(R.id.circleMenuLayout)
    }

//    private lateinit var mCircleMenuLayoutOld: CircleMenuLayoutOld
//
//    private val mItemTexts = listOf(
//        "安全中心 ", "特色服务", "投资理财",
//        "转账汇款", "我的账户", "信用卡"
//    )
//    private val mItemImgs = listOf(
//        R.drawable.home_mbank_1_normal,
//        R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
//        R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
//        R.drawable.home_mbank_6_normal
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotating_circle_menu_example)

        circleMenuLayout.post {
            for (i in 0 until 6) {

                CircleMenuItemView(this, CircleMenuModel(R.mipmap.ti_profile, "menu$i")).also {

                    circleMenuLayout.addView(it)

                }
            }
        }

//        mCircleMenuLayoutOld = findViewById<View>(R.id.id_menulayout) as CircleMenuLayoutOld
//        mCircleMenuLayoutOld.setMenuItemIconsAndTexts(mItemImgs, mItemTexts)
//
//        mCircleMenuLayoutOld.setOnMenuItemClickListener(object : CircleMenuLayoutOld.OnMenuItemClickListener {
//            override fun itemClick(view: View?, pos: Int) {
//                Toast.makeText(
//                    this@RotatingCircleMenuExampleActivity, mItemTexts[pos],
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//            override fun itemCenterClick(view: View?) {
//                Toast.makeText(
//                    this@RotatingCircleMenuExampleActivity,
//                    "you can do something just like ccb  ",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        })
    }
}