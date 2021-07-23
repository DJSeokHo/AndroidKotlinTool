package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.childfragment.ChildFragmentBuilder
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.rootfragment.RootBackStack
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.rootfragment.RootFragmentBuilder

object MultipleBackStackManager {

    private const val TAG = "BackStackManager"

    private var multipleBackStack = RootBackStack()

    fun createChildFragment(childFragmentBuilder: ChildFragmentBuilder, parentFragmentTag: String, parentActionTag: String) {

        // find parent fragment first
        multipleBackStack.findByFragmentTagAndActionTag(parentFragmentTag, parentActionTag)?.let { parentFragment ->

            // process prev fragment
            multipleBackStack.peek()?.let { topItem ->
                // detach prev fragment
                parentFragment.activity?.supportFragmentManager?.beginTransaction()?.let {
                    it.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    it.detach(topItem)
                }
            }

            // add new fragment to parent
            childFragmentBuilder.onFragment?.get()?.let {

                childFragmentBuilder.onContainerId?.get()?.let { containerId ->

                    val fragment = it(childFragmentBuilder.fragmentTag, childFragmentBuilder.actionTag)

                    val childTransaction = parentFragment.childFragmentManager.beginTransaction()
                    childTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    childTransaction.replace(containerId(), fragment, fragment.getFragmentInfo().fragmentTag)
                    childTransaction.commitAllowingStateLoss()
                    parentFragment.getFragmentInfo().stack.push(fragment)

                    Log.d(TAG, multipleBackStack.toString())

                }

            }

        } ?: run {
            Log.d(TAG, "parent fragment is null")
            return
        }
    }

    fun createRootFragment(rootFragmentBuilder: RootFragmentBuilder) {

        // find by fragment tag and action tag
        val existFragment = multipleBackStack.findByFragmentTagAndActionTag(
            rootFragmentBuilder.fragmentTag,
            rootFragmentBuilder.actionTag
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
                rootFragmentBuilder.onContext?.get()?.let { context ->

                    val transaction = context().supportFragmentManager.beginTransaction()
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

                    transaction.detach(topItem)

                    multipleBackStack.toTop(existFragment)
                    transaction.attach(existFragment)
                    transaction.commitAllowingStateLoss()

                    Log.d(TAG, multipleBackStack.toString())
                    return
                }
            }

            // pass check, can create new one
        }

        rootFragmentBuilder.onContext?.get()?.let { context ->

            val transaction = context().supportFragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

            // detach prev fragment
            multipleBackStack.peek()?.let { topItem ->
                transaction.detach(topItem)
            }

            // add new fragment
            rootFragmentBuilder.onFragment?.get()?.let {

                val fragment = it(rootFragmentBuilder.fragmentTag, rootFragmentBuilder.actionTag)

                rootFragmentBuilder.onContainerId?.get()?.let { containerId ->

                    transaction.replace(containerId(), fragment, fragment.getFragmentInfo().fragmentTag)
                    transaction.commitAllowingStateLoss()
                    multipleBackStack.push(fragment)

                    Log.d(TAG, multipleBackStack.toString())
                }
            }
        }
    }

    fun pressBack(fragmentActivity: FragmentActivity) {

        multipleBackStack.peek()?.let { rootFragment ->

            // if rootFragment has child, pop it and return
            rootFragment.getFragmentInfo().stack.pop()?.let { childFragment ->

                val transaction = rootFragment.childFragmentManager.beginTransaction()
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                transaction.remove(childFragment)

                rootFragment.getFragmentInfo().stack.peek()?.let { topItem ->
                    transaction.attach(topItem)
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