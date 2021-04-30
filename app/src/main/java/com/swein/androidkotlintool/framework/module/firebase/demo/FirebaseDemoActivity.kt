package com.swein.androidkotlintool.framework.module.firebase.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil

class FirebaseDemoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_firebase_demo)

        ThreadUtil.startThread {
//            createMemberTest()
//            createShopTest()
//            createProductTest()
            updateProductTest()
//            deleteProductTest()
        }
    }
}