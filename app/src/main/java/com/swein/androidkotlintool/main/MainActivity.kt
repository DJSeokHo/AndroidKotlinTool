package com.swein.androidkotlintool.main

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.graphics.Insets
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowInsets
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.app.Person
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.constants.Constants
import com.swein.androidkotlintool.framework.module.basicpermission.BasicPermissionActivity
import com.swein.androidkotlintool.framework.module.basicpermission.PermissionManager
import com.swein.androidkotlintool.framework.module.basicpermission.RequestPermission
import com.swein.androidkotlintool.framework.module.shcameraphoto.demo.SHCameraPhotoFragmentDemoActivity
import com.swein.androidkotlintool.framework.module.volley.VolleyModule
import com.swein.androidkotlintool.framework.util.activity.ActivityUtil
import com.swein.androidkotlintool.framework.util.display.DisplayUtil
import com.swein.androidkotlintool.framework.util.eventsplitshot.eventcenter.EventCenter
import com.swein.androidkotlintool.framework.util.eventsplitshot.subject.ESSArrows
import com.swein.androidkotlintool.framework.util.glide.SHGlide
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.screen.ScreenUtil
import com.swein.androidkotlintool.main.examples.livedata.LiveDataDemoActivity
import com.swein.androidkotlintool.main.examples.mvvmrecyclerviewexample.MVVMRecyclerViewExampleActivity
import com.swein.androidkotlintool.main.examples.viewmodel.ViewModelDemoActivity
import com.swein.androidkotlintool.main.examples.workmanager.WorkManagerDemoActivity
import com.swein.androidkotlintool.main.moduledemo.ModuleDemoActivity
import com.swein.androidkotlintool.template.bottomtab.activity.TabHostActivity
import com.swein.androidkotlintool.template.handlerthread.HandlerThreadTemplateActivity
import com.swein.androidkotlintool.template.list.SHListActivity
import com.swein.androidkotlintool.template.memeberjoin.MemberJoinTemplateActivity
import kotlinx.coroutines.*

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

    private lateinit var imageView1: ImageView
    private lateinit var imageView2: ImageView
    private lateinit var imageView3: ImageView
    private lateinit var imageView4: ImageView
    private lateinit var imageView5: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(this).inflate(R.layout.activity_main, null)
        setContentView(view)

        ScreenUtil.setTitleBarColor(this, Color.parseColor(Constants.APP_BASIC_THEME_COLOR))
        findView()
        setListener()

        EventCenter.addEventObserver(ESSArrows.SET_SELECTED_IMAGE_LIST, this, object : EventCenter.EventRunnable {
            override fun run(arrow: String, poster: Any, data: MutableMap<String, Any>?) {
                val list = data!!["list"]
                showImage(list as MutableList<String>)
            }
        })

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
//        ActivityUtil.startNewActivityWithoutFinish(this, RoomWithCoroutineAndStartUPExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, RoomWithCoroutineAndStartUPExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, SHCameraPhotoFragmentDemoActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, WorkManagerDemoActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, LiveDataDemoActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, ViewModelDemoActivity::class.java)
        ActivityUtil.startNewActivityWithoutFinish(this, MVVMRecyclerViewExampleActivity::class.java)

//        val list = mutableListOf<String>()
//        list.add("/data/user/0/com.swein.androidkotlintool/cache/cache_com_swein_androidkotlintool_0.jpg")
//        list.add("/data/user/0/com.swein.androidkotlintool/cache/cache_com_swein_androidkotlintool_1.jpg")
//        list.add("/data/user/0/com.swein.androidkotlintool/cache/cache_com_swein_androidkotlintool_2.jpg")
//
//        val file = File("/data/user/0/com.swein.androidkotlintool/cache/cache_com_swein_androidkotlintool_3.jpg")
//        ILog.debug(TAG, "?? ${file.exists()}")
//        file.delete()
//        ILog.debug(TAG, "?? ${file.exists()}")
//
//        val f = File("/data/user/0/com.swein.androidkotlintool/cache/cache_com_swein_androidkotlintool_3.jpg")
//        ILog.debug(TAG, "!!!!?? ${f.exists()}")
//        list.add("/data/user/0/com.swein.androidkotlintool/cache/cache_com_swein_androidkotlintool_3.jpg")
//        showImage(list)

        findViewById<Button>(R.id.buttonTest).setOnClickListener {
//            ActivityUtil.startNewActivityWithoutFinish(this, SHCameraPhotoFragmentDemoActivity::class.java)

        }

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

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

    private fun showImage(list: MutableList<String>) {

        imageView1.setImageBitmap(null)
        imageView2.setImageBitmap(null)
        imageView3.setImageBitmap(null)
        imageView4.setImageBitmap(null)
        imageView5.setImageBitmap(null)

        for (i in 0 until list.size) {
            when (i) {
                0 -> {
                    SHGlide.setImageFilePath(this, list[i], imageView1, null, 0, 0, 0f, 0f)
//                    imageView1.setImageBitmap(BitmapFactory.decodeFile(list[i]))
                }
                1 -> {
                    SHGlide.setImageFilePath(this, list[i], imageView2, null, 0, 0, 0f, 0f)
//                    imageView2.setImageBitmap(BitmapFactory.decodeFile(list[i]))
                }
                2 -> {
                    SHGlide.setImageFilePath(this, list[i], imageView3, null, 0, 0, 0f, 0f)
//                    imageView3.setImageBitmap(BitmapFactory.decodeFile(list[i]))
                }
                3 -> {
                    SHGlide.setImageFilePath(this, list[i], imageView4, null, 0, 0, 0f, 0f)
//                    imageView4.setImageBitmap(BitmapFactory.decodeFile(list[i]))
                }
                4 -> {
                    SHGlide.setImageFilePath(this, list[i], imageView5, null, 0, 0, 0f, 0f)
//                    imageView4.setImageBitmap(BitmapFactory.decodeFile(list[i]))
                }
            }
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

        imageView1 = findViewById(R.id.imageView1)
        imageView2 = findViewById(R.id.imageView2)
        imageView3 = findViewById(R.id.imageView3)
        imageView4 = findViewById(R.id.imageView4)
        imageView5 = findViewById(R.id.imageView5)
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
