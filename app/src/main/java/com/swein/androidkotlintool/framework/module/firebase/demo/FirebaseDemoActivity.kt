package com.swein.androidkotlintool.framework.module.firebase.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import autoLoginWhenAppStartExample
import com.google.firebase.firestore.DocumentReference
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.firebase.cloudstorage.CloudStorageManager
import com.swein.androidkotlintool.framework.module.firebase.demo.model.MemberSelfModel
import com.swein.androidkotlintool.framework.module.firebase.demo.model.ShopModel
import com.swein.androidkotlintool.framework.module.firebase.demo.service.MemberModelService
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.preferences.SharedPreferencesUtil
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil
import deleteProductExample
import getProductDetailExample
import getProductListExample
import loginByKakaoExample
import loginBySecretTokenKeyExample
import modifyBusinessExample
import registerBusinessExample
import registerExample
import requestShopInfoExample
import updateMemberProfileExample
import updateProductExample
import uploadProductExample

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

        ThreadUtil.startUIThread(1000) {
//            getProductListExample()
            getProductDetailExample()
//            deleteProductExample("ddNd9a4SkEly5BVFNJQ9")
        }
    }
}