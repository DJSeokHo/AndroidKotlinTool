package com.swein.androidkotlintool.framework.module.firebase.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.firebase.cloudstorage.CloudStorageManager
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil

class FirebaseDemoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_firebase_demo)

        ThreadUtil.startThread {

            CloudStorageManager.uploadImage("gs://androidkotlintool.appspot.com", "/storage/emulated/0/DCIM/Screenshots/Screenshot_20210413-191931_RecyclerViewExample.jpg",
                "Member/Profile/test.jpg")
//            selectProductList()
//            selectProduct("d718e7ec525343ecadebdd56987f407d")

//            val mutableMap = mutableMapOf<String, Any>()
//            mutableMap[ProductModel.AREA_KEY] 6= "분당구"
//            mutableMap[ProductModel.AREA_KEY] = "수정구"
//            mutableMap[ProductModel.AREA_KEY] = "중원구"

//            val likeMutableMap = mutableMapOf<String, Any>()
//            likeMutableMap[ProductModel.INFO_KEY] = "고고고"

//            selectProductList(mutableMap)

//            selectProductList()
//            createMemberTest()
//            createShopTest()
//            for (i in 0 until 30) {
//                createProductTest(i)
//                if (i % 3 == 0) {
//                    Thread.sleep(1700)
//                }
//                else {
//                    Thread.sleep(1200)
//
//                }
//            }

//            updateProductTest()
//            deleteProductTest()
        }
    }
}