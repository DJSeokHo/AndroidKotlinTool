package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.rootfragment

import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference

class RootFragmentBuilder private constructor(
    val fragmentTag: String,
    val actionTag: String,
    val onContext: WeakReference<(() -> FragmentActivity)>?,
    val onContainerId: WeakReference<(() -> Int)>?,
    val onFragment: WeakReference<((fragmentTag: String, actionTag: String) -> RootBackStackAbleFragment)>?,
) {

    class Builder {
        var fragmentTag: String = ""
            private set
        var actionTag: String = ""
            private set
        var onContext: WeakReference<(() -> FragmentActivity)>? = null
            private set
        var onContainerId: WeakReference<(() -> Int)>? = null
            private set
        var onFragment: WeakReference<((fragmentTag: String, actionTag: String) -> RootBackStackAbleFragment)>? = null
            private set

        fun setFragmentTag(fragmentTag: String) = apply {
            this.fragmentTag = fragmentTag
        }

        fun setActionTag(actionTag: String) = apply {
            this.actionTag = actionTag
        }

        fun setContextCallback(onContext: (() -> FragmentActivity)) = apply {
            this.onContext = WeakReference(onContext)
        }

        fun setContainerIdCallback(onContainerId:(() -> Int)) = apply {
            this.onContainerId = WeakReference(onContainerId)
        }

        fun setFragmentCallback(onFragment: ((fragmentTag: String, actionTag: String) -> RootBackStackAbleFragment)) = apply {
            this.onFragment = WeakReference(onFragment)
        }

        fun build() = RootFragmentBuilder(fragmentTag, actionTag, onContext, onContainerId, onFragment)
    }
}