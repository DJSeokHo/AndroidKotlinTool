package com.swein.androidkotlintool.main

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.constants.Constants
import com.swein.androidkotlintool.framework.module.basicpermission.BasicPermissionActivity
import com.swein.androidkotlintool.framework.module.basicpermission.PermissionManager
import com.swein.androidkotlintool.framework.module.basicpermission.RequestPermission
import com.swein.androidkotlintool.framework.module.volley.VolleyModule
import com.swein.androidkotlintool.framework.util.activity.ActivityUtil
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.screen.ScreenUtil
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil
import com.swein.androidkotlintool.main.examples.fashionshoppingdetail.FashionShoppingDetailActivity
import com.swein.androidkotlintool.main.jetpackexample.datastore.DataStoreManager
import com.swein.androidkotlintool.main.moduledemo.ModuleDemoActivity
import com.swein.androidkotlintool.template.bottomtab.activity.TabHostActivity
import com.swein.androidkotlintool.template.handlerthread.HandlerThreadTemplateActivity
import com.swein.androidkotlintool.template.list.SHListActivity
import com.swein.androidkotlintool.template.memeberjoin.MemberJoinTemplateActivity
import com.swein.androidkotlintool.template.mvp.mvploginexample.view.MVPLoginActivity
import com.swein.androidkotlintool.template.viewpagerfragment.activity.ViewPagerActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

class MainActivity : BasicPermissionActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private var buttonModuleDemo: Button? = null
    private var buttonLoginTemplate: Button? = null
    private var buttonListTemplate: Button? = null
    private var buttonTabHost: Button? = null
    private var buttonViewPager: Button? = null
    private var buttonHandlerThread: Button? = null

    private lateinit var frameLayoutRoot: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ScreenUtil.setTitleBarColor(this, Color.parseColor(Constants.APP_BASIC_THEME_COLOR))
        findView()
        setListener()

//        GlobalScope.launch(Dispatchers.IO) {
//            ILog.debug(TAG, "Hello")
//
//            delay(1000)
//
//            launch(Dispatchers.Main) {
//                ILog.debug(TAG, "World")
//                Toast.makeText(this@MainActivity, "haha", Toast.LENGTH_SHORT).show()
//                frameLayoutRoot.setBackgroundColor(Color.BLUE)
//            }
//        }

//        GlobalScope.launch(Dispatchers.IO) {
//            ILog.debug(TAG, "Hello")
//
//            delay(1000)
//
//            launch(Dispatchers.Main) {
//                ILog.debug(TAG, "World")
//                Toast.makeText(this@MainActivity, "haha", Toast.LENGTH_SHORT).show()
//                frameLayoutRoot.setBackgroundColor(Color.BLUE)
//            }
//        }



//        coroutineScope.launch(Dispatchers.Main) {
//            val data =  withContext(Dispatchers.IO) {
//                getDataFromServer(dataId)
//            }
//            updateView(data)
//        }
//
//        coroutineScope.launch(Dispatchers.Main) {
//            val dataOne =  withContext(Dispatchers.IO) {
//                getDataFromServer(dataOneId)
//            }
//            val dataTwo =  withContext(Dispatchers.IO) {
//                getDataFromServer(dataTwoId)
//            }
//
//            val data = mergeDatas(dataOne, dataTwo)
//            updateView(data)
//        }

//        GlobalScope.launch(Dispatchers.Main) {
////            val data = getData(1)
////            updateView(data)
//            frameLayoutRoot.setBackgroundColor(Color.BLACK)
//            ILog.debug(TAG, "go go")
//
//            getData(1)
//            getData(2)
//            getData(3)
//
//            ILog.debug(TAG, "ok ok")
//            frameLayoutRoot.setBackgroundColor(Color.BLUE)
//        }

        GlobalScope.launch(Dispatchers.IO) {

            ILog.debug(TAG, "save")
            DataStoreManager.saveValue(this@MainActivity, "123", "aaa")

            ILog.debug(TAG, "get")
            val value = DataStoreManager.getStringValue(this@MainActivity, "123", "123")

            ILog.debug(TAG, "value $value")
        }

    }

    private fun getDataFromServer(dataId: Int) {
        ILog.debug(TAG, "get data $dataId")
    }

    @RequestPermission(permissionCode = PermissionManager.PERMISSION_REQUEST_CAMERA_CODE)
    private fun test() {

        if (PermissionManager.getInstance().requestPermission(
                this,
                true,
                PermissionManager.PERMISSION_REQUEST_CAMERA_CODE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

//            ActivityUtil.startNewActivityWithoutFinish(this, SHCameraPhotoFragmentDemoActivity::class.java)
//            ActivityUtil.startNewActivityWithoutFinish(this, CameraDemoActivity::class.java)
//            ActivityUtil.startNewActivityWithoutFinish(this, BlurMaskActivity::class.java)
//            ActivityUtil.startNewActivityWithoutFinish(this, MVPLoginActivity::class.java)
//            ActivityUtil.startNewActivityWithoutFinish(this, FashionShoppingDetailActivity::class.java)

        }
    }

    private fun findView() {
        frameLayoutRoot = findViewById(R.id.frameLayoutRoot)
        buttonModuleDemo = findViewById(R.id.buttonModuleDemo)
        buttonLoginTemplate = findViewById(R.id.buttonLoginTemplate)
        buttonListTemplate = findViewById(R.id.buttonListTemplate)
        buttonTabHost = findViewById(R.id.buttonTabHost)
        buttonViewPager = findViewById(R.id.buttonViewPager)
        buttonHandlerThread = findViewById(R.id.buttonHandlerThread)
    }

    private fun setListener() {

        buttonModuleDemo?.let {
            it.setOnClickListener {
                ActivityUtil.startNewActivityWithoutFinish(this, ModuleDemoActivity::class.java)
            }
        }

        buttonLoginTemplate?.let {
            it.setOnClickListener {
                ActivityUtil.startNewActivityWithoutFinish(this, MemberJoinTemplateActivity::class.java)
            }
        }

        buttonListTemplate?.let {
            it.setOnClickListener {
                ActivityUtil.startNewActivityWithoutFinish(this, SHListActivity::class.java)
            }
        }

        buttonTabHost?.let {
            it.setOnClickListener {
                ActivityUtil.startNewActivityWithoutFinish(this, TabHostActivity::class.java)
            }
        }

        buttonViewPager?.let {
            it.setOnClickListener {
                ActivityUtil.startNewActivityWithoutFinish(this, ViewPagerActivity::class.java)
            }
        }

        buttonHandlerThread?.let {
            it.setOnClickListener {
                ActivityUtil.startNewActivityWithoutFinish(this, HandlerThreadTemplateActivity::class.java)
            }
        }
    }

    override fun onBackPressed() {
        VolleyModule.getInstance().close()
        finish()
    }

    override fun onDestroy() {
        ILog.debug(TAG, "onDestroy")
        super.onDestroy()
    }

    protected fun finalize() {
        // finalization logic
        ILog.debug(TAG, "finalize")
    }
}
