package com.swein.androidkotlintool.framework.util.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.R
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity





class ActivityUtil {

    companion object {

        fun startSystemIntent(packageContext: Context, intent: Intent) {
            packageContext.startActivity(intent)
        }

        fun startSystemIntentWithResultByRequestCode(activity: Activity, intent: Intent, requestCode: Int) {
            activity.startActivityForResult(intent, requestCode)
        }

        fun startNewActivityWithoutFinish(packageContext: Context, cls: Class<*>) {
            val intent = Intent(packageContext, cls)
            packageContext.startActivity(intent)
        }

        fun startNewActivityWithFinish(packageContext: Context, cls: Class<*>) {
            val intent = Intent(packageContext, cls)
            packageContext.startActivity(intent)
            (packageContext as Activity).finish()
        }

        fun addFragment(activity: FragmentActivity, containerViewId: Int, fragment: Fragment) {
            if (fragment.isAdded) {
                return
            }

            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.add(containerViewId, fragment).commit()
        }

    }

}