package com.swein.androidkotlintool.main

import android.Manifest
import android.graphics.Color
import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowInsets
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.constants.Constants
import com.swein.androidkotlintool.framework.module.basicpermission.BasicPermissionActivity
import com.swein.androidkotlintool.framework.module.basicpermission.PermissionManager
import com.swein.androidkotlintool.framework.module.basicpermission.RequestPermission
import com.swein.androidkotlintool.framework.module.volley.VolleyModule
import com.swein.androidkotlintool.framework.utility.activity.ActivityUtil
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.framework.utility.display.DisplayUtility
import com.swein.androidkotlintool.framework.utility.eventsplitshot.eventcenter.EventCenter
import com.swein.androidkotlintool.framework.utility.eventsplitshot.subject.ESSArrows
import com.swein.androidkotlintool.framework.utility.glide.SHGlide
import com.swein.androidkotlintool.framework.utility.screen.ScreenUtil
import com.swein.androidkotlintool.main.examples.backgroundrunningwhitelist.BackgroundRunningWhiteListActivity
import com.swein.androidkotlintool.main.examples.customschedule.CustomScheduleExampleActivity
import com.swein.androidkotlintool.main.examples.dragindicatorexample.DragIndicatorExampleActivity
import com.swein.androidkotlintool.main.examples.lottieexample.LottieExampleActivity
import com.swein.androidkotlintool.main.examples.notificationexample.NotificationExampleActivity
import com.swein.androidkotlintool.main.examples.pdfreader.PDFReaderActivity
import com.swein.androidkotlintool.main.examples.switchtwoviews.SwitchTwoViewsExampleActivity
import com.swein.androidkotlintool.main.examples.unityadsexample.UnityAdsExampleActivity
import com.swein.androidkotlintool.main.moduledemo.ModuleDemoActivity
import com.swein.androidkotlintool.template.bottomtab.activity.TabHostActivity
import com.swein.androidkotlintool.template.handlerthread.HandlerThreadTemplateActivity
import com.swein.androidkotlintool.template.list.SHListActivity
import com.swein.androidkotlintool.template.memeberjoin.MemberJoinTemplateActivity

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
//        ActivityUtil.startNewActivityWithoutFinish(this, MVVMRecyclerViewExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, CustomViewExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, CustomAnimationExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, MaterialDesignBottomBarExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, CoordinatorLayoutExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, MDDayOneActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, MDDayTwoActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, MDDayThreeActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, ChartExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, MDDayFourActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, MDDayFiveActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, MDDaySixActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, MDDaySevenActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, ArcSlidingMenuActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, NetworkStateMonitorActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, RxKotlinLiteDemoActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, InfinityAutoScrollPaddingBannerExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, MultipleViewTypesRecyclerViewExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, MultipleBackStackExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, MultipleSelectionRecyclerViewExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, WebViewExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, FirebaseAuthPhoneActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, OTPVerificationCodeViewExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, FlowExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, SystemPhotoAndCropExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, SystemPhotoPickerExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, RecyclerViewWithSlideActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, UnityAdsExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, ExoPlayerExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, HtmlEditorExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, CloudFireStoreDemoActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, FirebaseStorageActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, CallbackToCoroutineActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, PagingExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, FirebaseDemoActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, YoutubePlayerExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, FirebasePaginationDemoActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, GroupRecyclerViewActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, CustomScheduleExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, ShapeAbleImageViewExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, KtorExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, CustomTabLayoutExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, SystemUIVisibilityActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, SceneTransitionAnimationExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, OverLapViewActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, SensorExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, LightSensorExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, RegularExpressionExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, CustomIntentExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, BroadcastExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, NotificationExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, AlarmManagerExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, AlarmDemoActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, InPersonSigningActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, GoogleMapExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, PatternLockExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, SendEmailExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, GoogleAdmobExampleActivity::class.java)
        //        ActivityUtil.startNewActivityWithoutFinish(this, CircleMenuExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, RecyclerViewWaterfallExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, RotatingCircleMenuExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, IGLikeListActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, RecyclerViewWithHeaderAndFooterExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, BackgroundRunningWhiteListActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, LottieExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, DragIndicatorExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, SwitchTwoViewsExampleActivity::class.java)
//        ActivityUtil.startNewActivityWithoutFinish(this, PDFReaderActivity::class.java)


//        SnackBarUtility.showSnackBar(frameLayoutRoot, "test")
//        SnackBarUtility.showSnackBar(frameLayoutRoot, "test", "click me") {
//            ILog.debug(TAG, "????")
//        }
//        SnackBarUtility.showSnackBar(frameLayoutRoot, "test", "click me", Color.BLACK, Color.YELLOW) {
//            ILog.debug(TAG, "????")
//        }
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
            ILog.debug(TAG, "${DisplayUtility.getScreenWidth(this)}")


            DisplayUtility.getRootViewInfo(view, object : DisplayUtility.DisplayRootViewInfoDelegate {
                override fun onInfo(
                    screenWidth: Int,
                    screenHeight: Int,
                    statusHeight: Int,
                    bottomHeight: Int,
                    keypadHeight: Int
                ) {
                    ILog.debug(TAG, "screenWidth $screenWidth ${DisplayUtility.getScreenWidth(this@MainActivity)} ${DisplayUtility.getScreenWidth(this@MainActivity)}")
                    ILog.debug(TAG, "screenHeight $screenHeight ${DisplayUtility.getScreenHeight(this@MainActivity)} ${DisplayUtility.getScreenHeight(this@MainActivity)}")
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
                    SHGlide.setImage(imageView1, url = list[i])
//                    imageView1.setImageBitmap(BitmapFactory.decodeFile(list[i]))
                }
                1 -> {
                    SHGlide.setImage(imageView2, url = list[i])
//                    imageView2.setImageBitmap(BitmapFactory.decodeFile(list[i]))
                }
                2 -> {
                    SHGlide.setImage(imageView3, url = list[i])
//                    imageView3.setImageBitmap(BitmapFactory.decodeFile(list[i]))
                }
                3 -> {
                    SHGlide.setImage(imageView4, url = list[i])
//                    imageView4.setImageBitmap(BitmapFactory.decodeFile(list[i]))
                }
                4 -> {
                    SHGlide.setImage(imageView5, url = list[i])
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
