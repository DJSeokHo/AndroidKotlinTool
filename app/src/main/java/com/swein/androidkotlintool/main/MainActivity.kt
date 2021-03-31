package com.swein.androidkotlintool.main

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.constants.Constants
import com.swein.androidkotlintool.framework.module.basicpermission.BasicPermissionActivity
import com.swein.androidkotlintool.framework.module.basicpermission.PermissionManager
import com.swein.androidkotlintool.framework.module.basicpermission.RequestPermission
import com.swein.androidkotlintool.framework.module.volley.VolleyModule
import com.swein.androidkotlintool.framework.util.activity.ActivityUtil
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.screen.ScreenUtil
import com.swein.androidkotlintool.main.moduledemo.ModuleDemoActivity
import com.swein.androidkotlintool.template.bottomtab.activity.TabHostActivity
import com.swein.androidkotlintool.template.handlerthread.HandlerThreadTemplateActivity
import com.swein.androidkotlintool.template.list.SHListActivity
import com.swein.androidkotlintool.template.memeberjoin.MemberJoinTemplateActivity
import com.swein.androidkotlintool.template.mvp.mvploginexample.view.MVPLoginActivity
import com.swein.androidkotlintool.template.viewpagerfragment.activity.ViewPagerActivity

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ScreenUtil.setTitleBarColor(this, Color.parseColor(Constants.APP_BASIC_THEME_COLOR))
        findView()
        setListener()

//        ActivityUtil.startNewActivityWithoutFinish(this, CameraDemoActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, SHCameraPhotoFragmentDemoActivity::class.java)

        test()
    }

    @RequestPermission(permissionCode = PermissionManager.PERMISSION_REQUEST_CAMERA_CODE)
    private fun test() {

        if (PermissionManager.getInstance().requestPermission(
                this,
                true,
                PermissionManager.PERMISSION_REQUEST_CAMERA_CODE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {

//            ActivityUtil.startNewActivityWithoutFinish(this, SHCameraPhotoFragmentDemoActivity::class.java)
//            ActivityUtil.startNewActivityWithoutFinish(this, CameraDemoActivity::class.java)
//            ActivityUtil.startNewActivityWithoutFinish(this, BlurMaskActivity::class.java)
            ActivityUtil.startNewActivityWithoutFinish(this, MVPLoginActivity::class.java)
        }
    }

    private fun findView() {
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
