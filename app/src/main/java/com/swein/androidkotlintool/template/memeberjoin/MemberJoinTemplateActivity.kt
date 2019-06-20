package com.swein.androidkotlintool.template.memeberjoin

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.constants.Constants
import com.swein.androidkotlintool.framework.util.activity.ActivityUtil
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.screen.ScreenUtil
import com.swein.androidkotlintool.template.memeberjoin.login.LoginTemplateFragment
import com.swein.androidkotlintool.template.navigationbar.NavigationBarTemplate

class MemberJoinTemplateActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MemberJoinTemplateActivity"
    }

    private var progressFrameLayout: FrameLayout? = null
    private var frameLayoutNavigationContainer: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_join_template)

        ScreenUtil.setTitleBarColor(this, Color.parseColor(Constants.APP_BASIC_THEME_COLOR))

        initData()
        findView()
        initNavigationBar()
        updateView()

    }

    private fun initData() {

    }

    private fun findView() {
        progressFrameLayout = findViewById(R.id.progressFrameLayout)
        frameLayoutNavigationContainer = findViewById(R.id.frameLayoutNavigationContainer)
    }

    private fun initNavigationBar() {

        frameLayoutNavigationContainer?.addView(

            NavigationBarTemplate(this).setDelegate(object: NavigationBarTemplate.NavigationBarTemplateDelegate {

                override fun onLeftButtonClick() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onRightButtonClick() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            }).setTitle("Login").hideRightButton().hideLeftButton().getView()
        )
    }

    private fun updateView() {
        val loginTemplateFragment = LoginTemplateFragment()
        loginTemplateFragment.setDelegate(object: LoginTemplateFragment.LoginTemplateFragmentDelegate {
            override fun onJoin() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onProcess() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onSuccess() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFailed() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        } )

        ActivityUtil.addFragment(this, R.id.frameLayoutBodyContainer, loginTemplateFragment)
    }

    private fun showProgress() {
        progressFrameLayout?.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressFrameLayout?.visibility = View.GONE
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    protected fun finalize() {
        ILog.debug(TAG, "finalize")
    }
}
