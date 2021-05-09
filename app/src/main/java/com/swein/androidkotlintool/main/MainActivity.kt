package com.swein.androidkotlintool.main

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.graphics.Insets
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowInsets
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
import com.swein.androidkotlintool.framework.module.firebase.demo.FirebaseDemoActivity
import com.swein.androidkotlintool.framework.module.location.demo.LocationDemoActivity
import com.swein.androidkotlintool.framework.module.location.demo.LocationWithLifecycleDemoActivity
import com.swein.androidkotlintool.framework.module.room.demo.RoomDemoActivity
import com.swein.androidkotlintool.framework.module.room.example.RoomWithCoroutineAndStartUPExampleActivity
import com.swein.androidkotlintool.framework.module.volley.VolleyModule
import com.swein.androidkotlintool.framework.util.activity.ActivityUtil
import com.swein.androidkotlintool.framework.util.display.DisplayUtil
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.screen.ScreenUtil
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil
import com.swein.androidkotlintool.main.examples.customizecolorswitch.CustomizeColorSwitchActivity
import com.swein.androidkotlintool.main.jetpackexample.databinding.DataBindingExampleActivity
import com.swein.androidkotlintool.main.examples.fashionshoppingdetail.FashionShoppingDetailActivity
import com.swein.androidkotlintool.main.examples.permissionexample.PermissionExampleActivity
import com.swein.androidkotlintool.main.jetpackexample.lifecycle.LifecycleExampleActivity
import com.swein.androidkotlintool.main.jetpackexample.viewbinding.ViewBindingExampleActivity
import com.swein.androidkotlintool.main.jetpackexample.datastore.DataStoreManager
import com.swein.androidkotlintool.main.moduledemo.ModuleDemoActivity
import com.swein.androidkotlintool.template.bottomtab.activity.TabHostActivity
import com.swein.androidkotlintool.template.coroutine.CoroutineDemoActivity
import com.swein.androidkotlintool.template.handlerthread.HandlerThreadTemplateActivity
import com.swein.androidkotlintool.template.list.SHListActivity
import com.swein.androidkotlintool.template.memeberjoin.MemberJoinTemplateActivity
import com.swein.androidkotlintool.template.mvp.mvploginexample.view.MVPLoginActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

class MainActivity : BasicPermissionActivity() {

    class CloneableObject: Any(), Cloneable {

        var name = ""
        var list = mutableListOf<String>()
        var listTes = mutableListOf<Tes>()

        public override fun clone(): Any {
            return super.clone()
        }
    }

    class Tes {

        var number: Int

        constructor(number: Int) {
            this.number = number
        }

    }

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
        val view = LayoutInflater.from(this).inflate(R.layout.activity_main, null)
        setContentView(view)

        ScreenUtil.setTitleBarColor(this, Color.parseColor(Constants.APP_BASIC_THEME_COLOR))
        findView()
        setListener()

//        ActivityUtil.startNewActivityWithoutFinish(this, CoroutineDemoActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, ViewBindingExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, DataBindingExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, CustomizeColorSwitchActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, LifecycleExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, PermissionExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, FirebaseDemoActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, LocationDemoActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, LocationWithLifecycleDemoActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, RoomDemoActivity::class.java)
        ActivityUtil.startNewActivityWithoutFinish(this, RoomWithCoroutineAndStartUPExampleActivity::class.java)

        // clone test
        val obj1 = CloneableObject()
        obj1.name = "123"
        ILog.debug(TAG, "obj1 $obj1 ${obj1.name}")
        val obj2 = obj1
        ILog.debug(TAG, "obj2 $obj2 ${obj2.name}")

        val obj3 = CloneableObject()
        obj3.name = "hahaha"
        obj3.list.add("1")
        obj3.list.add("2")
        obj3.listTes.add(Tes(3))
        obj3.listTes.add(Tes(4))
        ILog.debug(TAG, "obj3 $obj3 ${obj3.name} ${obj3.list} ${obj3.listTes[0].number} ${obj3.listTes[1].number}")

        val obj4 = obj3.clone() as CloneableObject
        ILog.debug(TAG, "obj4 $obj4 ${obj4.name} ${obj4.list} ${obj4.listTes[0].number} ${obj4.listTes[1].number}")

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {

            val windowMetrics = windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            val width = windowMetrics.bounds.width() - insets.left - insets.right
            ILog.debug(TAG, "${windowMetrics.bounds.width()} - ${insets.left} - ${insets.right} $width")
            ILog.debug(TAG, "${DisplayUtil.getScreenWidthPx(this)}")


            DisplayUtil.getRootViewInfo(view, object : DisplayUtil.DisplayRootViewInfoDelegate {
                override fun onInfo(
                    screenWidth: Int,
                    screenHeight: Int,
                    statusHeight: Int,
                    bottomHeight: Int,
                    keypadHeight: Int
                ) {
                    ILog.debug(TAG, "screenWidth $screenWidth ${DisplayUtil.getScreenWidthPx(this@MainActivity)} ${DisplayUtil.getScreenWidth(this@MainActivity)}")
                    ILog.debug(TAG, "screenHeight $screenHeight ${DisplayUtil.getScreenHeightPx(this@MainActivity)} ${DisplayUtil.getScreenHeight(this@MainActivity)}")
                    ILog.debug(TAG, "statusHeight $statusHeight ${windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.statusBars())}")
                    ILog.debug(TAG, "bottomHeight $bottomHeight ${windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())}")
                }
            })
        }
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
//        ILog.debug(TAG, "onDestroy")
        super.onDestroy()
    }

    protected fun finalize() {
        // finalization logic
//        ILog.debug(TAG, "finalize")
    }
}
