package com.swein.androidkotlintool.framework.util.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.swein.androidkotlintool.R


class ActivityUtil {

    companion object {

        fun startSystemIntent(packageContext: Context, intent: Intent) {
            packageContext.startActivity(intent)
        }

        fun startSystemIntentWithResultByRequestCode(
            activity: Activity,
            intent: Intent,
            requestCode: Int
        ) {
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

        fun addFragmentWithTAG(
            activity: FragmentActivity,
            containerViewId: Int,
            fragment: Fragment,
            tag: String
        ) {
            if (fragment.isAdded) {
                return
            }

            val transaction: FragmentTransaction =
                activity.supportFragmentManager.beginTransaction()
            transaction.add(containerViewId, fragment, tag).commit()
        }

        fun addFragmentWithTAG(
            activity: FragmentActivity,
            containerViewId: Int,
            fragment: Fragment,
            tag: String?,
            animation: Boolean,
            rootFragment: Fragment?
        ) {
            if (fragment.isAdded) {
                return
            }
            val transaction = activity.supportFragmentManager.beginTransaction()
            if (animation) {
                transaction.setCustomAnimations(
                    R.anim.fragment_from_right,
                    R.anim.fragment_out_left,
                    R.anim.fragment_from_right,
                    R.anim.fragment_out_left
                )
                if (rootFragment != null) {
                    rootFragment.view!!
                        .startAnimation(
                            AnimationUtils.loadAnimation(
                                activity,
                                R.anim.view_out_litte_left
                            )
                        )
                }
            } else {
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            }
            transaction.add(containerViewId, fragment, tag).commitAllowingStateLoss()
        }

        fun addFragmentWithTAG(
            containerFragment: Fragment,
            containerViewId: Int,
            fragment: Fragment,
            tag: String?,
            animation: Boolean,
            rootFragment: Fragment?
        ) {
            if (fragment.isAdded) {
                return
            }
            val transaction = containerFragment.childFragmentManager.beginTransaction()
            if (animation) {
                transaction.setCustomAnimations(
                    R.anim.fragment_from_right,
                    R.anim.fragment_out_left,
                    R.anim.fragment_from_right,
                    R.anim.fragment_out_left
                )
                if (rootFragment != null) {
                    rootFragment.view!!.startAnimation(
                        AnimationUtils.loadAnimation(
                            containerFragment.context,
                            R.anim.view_out_litte_left
                        )
                    )
                }
            } else {
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            }
            transaction.add(containerViewId, fragment, tag).commitAllowingStateLoss()
        }

        fun replaceFragmentWithTAG(
            activity: FragmentActivity,
            containerViewId: Int,
            fragment: Fragment?,
            tag: String?
        ) {
            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(containerViewId, fragment!!, tag).commitAllowingStateLoss()
        }

        fun removeFragment(
            activity: FragmentActivity,
            fragment: Fragment?,
            animation: Boolean,
            prevFragment: Fragment?
        ) {
            if (fragment == null || !fragment.isAdded) {
                return
            }
            val transaction = activity.supportFragmentManager.beginTransaction()
            if (animation) {
                transaction.setCustomAnimations(
                    R.anim.fragment_from_right,
                    R.anim.fragment_out_left,
                    R.anim.fragment_from_right,
                    R.anim.fragment_out_left
                )
                if (prevFragment != null) {
                    prevFragment.view!!
                        .startAnimation(
                            AnimationUtils.loadAnimation(
                                activity,
                                R.anim.view_from_little_right
                            )
                        )
                }
            } else {
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            }
            transaction.remove(fragment).commitAllowingStateLoss()
        }

        fun removeFragment(
            containerFragment: Fragment,
            fragment: Fragment?,
            animation: Boolean,
            prevFragment: Fragment?
        ) {
            if (fragment == null || !fragment.isAdded) {
                return
            }
            val transaction = containerFragment.childFragmentManager.beginTransaction()
            if (animation) {
                transaction.setCustomAnimations(
                    R.anim.fragment_from_right,
                    R.anim.fragment_out_left,
                    R.anim.fragment_from_right,
                    R.anim.fragment_out_left
                )
                if (prevFragment != null) {
                    prevFragment.view!!.startAnimation(
                        AnimationUtils.loadAnimation(
                            containerFragment.context,
                            R.anim.view_from_little_right
                        )
                    )
                }
            } else {
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            }
            transaction.remove(fragment).commitAllowingStateLoss()
        }

        fun hideFragment(activity: FragmentActivity, fragment: Fragment) {
            val transaction: FragmentTransaction =
                activity.supportFragmentManager.beginTransaction()
            transaction.hide(fragment).commit()
        }

        fun hideFragment(fragmentManager: FragmentManager, fragment: Fragment) {
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.hide(fragment).commit()
        }

        fun showFragment(activity: FragmentActivity, fragment: Fragment) {
            val transaction: FragmentTransaction =
                activity.supportFragmentManager.beginTransaction()
            transaction.show(fragment).commit()
        }

        fun showFragment(fragmentManager: FragmentManager, fragment: Fragment) {
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.show(fragment).commit()
        }
    }

}