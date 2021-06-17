package com.swein.androidkotlintool.framework.module.firebase.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import autoLoginWhenAppStartExample
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.firebase.demo.model.MemberSelfModel
import com.swein.androidkotlintool.framework.util.preferences.SharedPreferencesUtil
import com.swein.androidkotlintool.framework.util.thread.ThreadUtility
import getProductDetailExample

class FirebaseDemoActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "FirebaseDemoActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_firebase_demo)
        SharedPreferencesUtil.init(this)
        MemberSelfModel.initWhenAppStart()

//        registerExample()
//        autoLoginWhenAppStartExample()
//        loginByKakaoExample()
//        loginBySecretTokenKeyExample()
//        updateMemberProfileExample()


//        registerBusinessExample()
//        requestShopInfoExample()

//        ThreadUtil.startUIThread(3000) {
//            modifyBusinessExample()
//        }


        autoLoginWhenAppStartExample()

//        ThreadUtil.startUIThread(1000) {
//            requestShopInfoExample()
//
//            ThreadUtil.startUIThread(1000) {
//                uploadProductExample()
//            }
//        }

        ThreadUtility.startUIThread(1000) {
//            getProductListExample()
            getProductDetailExample()
//            deleteProductExample("ddNd9a4SkEly5BVFNJQ9")
        }
    }
}