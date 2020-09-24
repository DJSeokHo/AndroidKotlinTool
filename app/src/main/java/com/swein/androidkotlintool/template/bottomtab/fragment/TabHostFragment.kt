package com.swein.androidkotlintool.template.bottomtab.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.fragment.app.Fragment

import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.activity.ActivityUtil
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.template.bottomtab.subfragment.TabHostEventFragment
import com.swein.androidkotlintool.template.bottomtab.subfragment.TabHostFriendFragment
import com.swein.androidkotlintool.template.bottomtab.subfragment.TabHostHomeFragment
import com.swein.androidkotlintool.template.bottomtab.subfragment.TabHostProfileFragment

class TabHostFragment : Fragment() {

    companion object {
        private const val TAG = "TabHostFragment"
        private const val HOME_TAB = "HOME_TAB"
        private const val FRIEND_TAB = "FRIEND_TAB"
        private const val EVENT_TAB = "EVENT_TAB"
        private const val PROFILE_TAB = "PROFILE_TAB"
    }

    private var fragmentContentContainer: FrameLayout? = null

    private var imageButtonHome: ImageButton? = null
    private var imageButtonFriend: ImageButton? = null
    private var imageButtonEvent: ImageButton? = null
    private var imageButtonProfile: ImageButton? = null

    private var currentTapTag = HOME_TAB
    private var lastTapTag: String?= ""

    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        ILog.debug(TAG, "Inflate the layout for this fragment")
        rootView = inflater.inflate(R.layout.fragment_tab_host, container, false)
        findView()
        setListener()
        changeTab(currentTapTag)
        return rootView
    }

    private fun findView() {

        rootView?.let {
            fragmentContentContainer = it.findViewById(R.id.fragmentContentContainer)

            imageButtonHome = it.findViewById(R.id.imageButtonHome)
            imageButtonFriend = it.findViewById(R.id.imageButtonFriend)
            imageButtonEvent = it.findViewById(R.id.imageButtonEvent)
            imageButtonProfile = it.findViewById(R.id.imageButtonProfile)
        }

    }

    private fun setListener() {
        imageButtonHome?.setOnClickListener {
            changeTab(HOME_TAB)
        }

        imageButtonFriend?.setOnClickListener {
            changeTab(FRIEND_TAB)
        }

        imageButtonEvent?.setOnClickListener {
            changeTab(EVENT_TAB)
        }

        imageButtonProfile?.setOnClickListener {
            changeTab(PROFILE_TAB)
        }
    }

    private fun changeTab(tabId: String) {

        resetTabState()

        var currentFragment: Fragment?

        when {
            /* change to home */
            HOME_TAB.compareTo(tabId) == 0 -> {

                currentTapTag = tabId

                currentFragment = fragmentManager?.findFragmentByTag(currentTapTag)
                val lastFragment = fragmentManager?.findFragmentByTag(lastTapTag)

                if(lastFragment != null) {
                    ActivityUtil.hideFragment(childFragmentManager, lastFragment)
                }

                if(currentFragment == null) {
                    activity?.let {
                        currentFragment = TabHostHomeFragment()
                        ActivityUtil.addFragmentWithTAG(it, R.id.fragmentContentContainer, currentFragment!!, currentTapTag)
                    }
                }
                else {
                    ActivityUtil.showFragment(childFragmentManager, currentFragment!!)
                }

                lastTapTag = currentTapTag
                imageButtonHome?.isSelected = true
            }

            /* change to friend */
            FRIEND_TAB.compareTo(tabId) == 0 -> {

                currentTapTag = tabId

                currentFragment = fragmentManager?.findFragmentByTag(currentTapTag)
                val lastFragment = fragmentManager?.findFragmentByTag(lastTapTag)

                if(lastFragment != null) {
                    ActivityUtil.hideFragment(childFragmentManager, lastFragment)
                }

                if(currentFragment == null) {
                    activity?.let {
                        currentFragment = TabHostFriendFragment()
                        ActivityUtil.addFragmentWithTAG(it, R.id.fragmentContentContainer, currentFragment!!, currentTapTag)
                    }
                }
                else {
                    ActivityUtil.showFragment(childFragmentManager, currentFragment!!)
                }

                lastTapTag = currentTapTag
                imageButtonFriend?.isSelected = true
            }

            /* change to event */
            EVENT_TAB.compareTo(tabId) == 0 -> {

                currentTapTag = tabId

                currentFragment = fragmentManager?.findFragmentByTag(currentTapTag)
                val lastFragment = fragmentManager?.findFragmentByTag(lastTapTag)

                if(lastFragment != null) {
                    ActivityUtil.hideFragment(childFragmentManager, lastFragment)
                }

                if(currentFragment == null) {
                    activity?.let {
                        currentFragment = TabHostEventFragment()
                        ActivityUtil.addFragmentWithTAG(it, R.id.fragmentContentContainer, currentFragment!!, currentTapTag)
                    }
                }
                else {
                    ActivityUtil.showFragment(childFragmentManager, currentFragment!!)
                }

                lastTapTag = currentTapTag
                imageButtonEvent?.isSelected = true
            }

            /* change to profile */
            PROFILE_TAB.compareTo(tabId) == 0 -> {

                currentTapTag = tabId

                currentFragment = fragmentManager?.findFragmentByTag(currentTapTag)
                val lastFragment = fragmentManager?.findFragmentByTag(lastTapTag)

                if(lastFragment != null) {
                    ActivityUtil.hideFragment(childFragmentManager, lastFragment)
                }

                if(currentFragment == null) {
                    activity?.let {
                        currentFragment = TabHostProfileFragment()
                        ActivityUtil.addFragmentWithTAG(it, R.id.fragmentContentContainer, currentFragment!!, currentTapTag)
                    }
                }
                else {
                    ActivityUtil.showFragment(childFragmentManager, currentFragment!!)
                }

                lastTapTag = currentTapTag
                imageButtonProfile?.isSelected = true

            }
        }
    }

    private fun resetTabState() {
        imageButtonHome?.isSelected = false
        imageButtonFriend?.isSelected = false
        imageButtonEvent?.isSelected = false
        imageButtonProfile?.isSelected = false
    }


}
