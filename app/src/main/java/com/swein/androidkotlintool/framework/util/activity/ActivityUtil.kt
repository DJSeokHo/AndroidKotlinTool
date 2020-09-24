package com.swein.androidkotlintool.framework.util.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


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

        fun addFragmentWithTAG(activity: FragmentActivity, containerViewId: Int, fragment: Fragment, tag: String) {
            if(fragment.isAdded) {
                return
            }

            val transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
            transaction.add(containerViewId, fragment, tag).commit()
        }

        fun hideFragment(activity: FragmentActivity, fragment: Fragment) {
            val transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
            transaction.hide(fragment).commit()
        }

        fun hideFragment(fragmentManager: FragmentManager, fragment: Fragment) {
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.hide(fragment).commit()
        }

        fun showFragment(activity: FragmentActivity, fragment: Fragment) {
            val transaction: FragmentTransaction  = activity.supportFragmentManager.beginTransaction()
            transaction.show(fragment).commit()
        }

        fun showFragment(fragmentManager: FragmentManager, fragment: Fragment) {
            val transaction: FragmentTransaction  = fragmentManager.beginTransaction()
            transaction.show(fragment).commit()
        }
    }

}