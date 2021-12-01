package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.swein.androidkotlintool.framework.utility.debug.ILog
import kotlin.Exception

object MultipleBackStackManager {

    private const val TAG = "BackStackManager"

    private var multipleBackStack = BackStack()

    fun createChildFragmentOnCurrentRootFragment(backStackAbleFragment: BackStackAbleFragment) {

        // find parent fragment first
        multipleBackStack.peek()?.let { rootFragment ->

            if (rootFragment.getFragmentInfo().containerInFragment == -1) {
                throw Exception("check container id of your root fragment, run setAsRootFragment(containerIdInRootFragment: Int) in onCreate method of your root fragment")
            }

            ILog.debug("???", "${rootFragment.getFragmentInfo().actionTag} ${rootFragment.getFragmentInfo().fragmentTag} ${rootFragment.getFragmentInfo().stack.count()}")

            if (rootFragment.getFragmentInfo().stack.isEmpty()) {
                // empty stack in root, just replace an new child fragment in container id in root fragment

                // add new fragment to parent
                val rootTransaction = rootFragment.childFragmentManager.beginTransaction()
                rootTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

                rootTransaction.replace(rootFragment.getFragmentInfo().containerInFragment, backStackAbleFragment, backStackAbleFragment.getFragmentInfo().fragmentTag)
                rootTransaction.commitAllowingStateLoss()
                rootFragment.getFragmentInfo().stack.push(backStackAbleFragment)

                Log.d(TAG, multipleBackStack.toString())

                return
            }

            rootFragment.getFragmentInfo().stack.peek()?.let { childTopItem ->
                // detach prev child fragment and replace new child fragment

                val rootTransaction = rootFragment.childFragmentManager.beginTransaction()
                rootTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                rootTransaction.detach(childTopItem)

                rootTransaction.replace(rootFragment.getFragmentInfo().containerInFragment, backStackAbleFragment, backStackAbleFragment.getFragmentInfo().fragmentTag)
                rootTransaction.commitAllowingStateLoss()
                rootFragment.getFragmentInfo().stack.push(backStackAbleFragment)

                Log.d(TAG, multipleBackStack.toString())

            }

        } ?: run {
            Log.d(TAG, "root fragment is null")
            return
        }
    }

    fun createRootFragment(activity: FragmentActivity, backStackAbleFragment: BackStackAbleFragment, containerId: Int) {

        // find by fragment tag and action tag
        val existFragment = multipleBackStack.findByFragmentTagAndActionTag(
            backStackAbleFragment.getFragmentInfo().fragmentTag,
            backStackAbleFragment.getFragmentInfo().actionTag
        )
        if (existFragment != null) {

            // check existFragment is at top
            multipleBackStack.peek()?.let { topItem ->
                if (topItem.getFragmentInfo().fragmentTag == existFragment.getFragmentInfo().fragmentTag
                    && topItem.getFragmentInfo().actionTag == existFragment.getFragmentInfo().actionTag
                ) {
                    Log.d(TAG, "existFragment is at top, do nothing")
                    return
                }

                // existFragment is not at top, and is single, move it to top and attach it
                val transaction = activity.supportFragmentManager.beginTransaction()
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

                transaction.detach(topItem)

                multipleBackStack.toTop(existFragment)
                transaction.attach(existFragment)
                transaction.commitAllowingStateLoss()

                Log.d(TAG, multipleBackStack.toString())
                return
            }

            // pass check, can create new one
        }

        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

        // detach prev fragment
        multipleBackStack.peek()?.let { topItem ->
            transaction.detach(topItem)
        }

        // add new fragment
        transaction.replace(containerId, backStackAbleFragment, backStackAbleFragment.getFragmentInfo().fragmentTag)
        transaction.commitAllowingStateLoss()
        multipleBackStack.push(backStackAbleFragment)

        Log.d(TAG, multipleBackStack.toString())
    }

    fun pressBack(fragmentActivity: FragmentActivity, uiUpdateByCurrentActionTag: (actionTag: String, isRoot: Boolean) -> Unit) {

        multipleBackStack.peek()?.let { rootFragment ->

            // if rootFragment has child, pop it and return
            rootFragment.getFragmentInfo().stack.pop()?.let { childFragment ->

                val transaction = rootFragment.childFragmentManager.beginTransaction()
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                transaction.remove(childFragment)

                rootFragment.getFragmentInfo().stack.peek()?.let { topItem ->
                    transaction.attach(topItem)
                    uiUpdateByCurrentActionTag(topItem.getFragmentInfo().actionTag, topItem.getFragmentInfo().containerInFragment != -1)
                }
                transaction.commitAllowingStateLoss()

                return
            }
            // if rootFragment has no child, go on
        }

        multipleBackStack.pop()?.let { parentFragment ->

            val transaction = fragmentActivity.supportFragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

            transaction.remove(parentFragment)

            multipleBackStack.peek()?.let { topItem ->
                transaction.attach(topItem)
                uiUpdateByCurrentActionTag(topItem.getFragmentInfo().actionTag, topItem.getFragmentInfo().containerInFragment != -1)
            }
            transaction.commitAllowingStateLoss()
        }

        Log.d(TAG, multipleBackStack.toString())
        if (multipleBackStack.isEmpty()) {
            fragmentActivity.finish()
        }
    }

    /**
     * If you want, maybe put this in your onDestroy Method of Activity
     * or put this in your onCreate Method of Activity if your app is Single Activity with Fragments
     */
    fun clear() {
        multipleBackStack.clear()
    }
}