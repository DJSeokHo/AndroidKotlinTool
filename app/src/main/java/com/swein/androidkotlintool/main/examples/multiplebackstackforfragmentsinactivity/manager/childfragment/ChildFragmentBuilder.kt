package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.childfragment

import java.lang.ref.WeakReference

class ChildFragmentBuilder private constructor(
    val fragmentTag: String,
    val actionTag: String,
    val onContainerId: WeakReference<(() -> Int)>?,
    val onFragment: WeakReference<((fragmentTag: String, actionTag: String) -> ChildBackStackAbleFragment)>?,
) {

    class Builder {
        var fragmentTag: String = ""
            private set
        var actionTag: String = ""
            private set
        var onContainerId: WeakReference<(() -> Int)>? = null
            private set
        var onFragment: WeakReference<((fragmentTag: String, actionTag: String) -> ChildBackStackAbleFragment)>? = null
            private set

        fun setFragmentTag(fragmentTag: String) = apply {
            this.fragmentTag = fragmentTag
        }

        fun setActionTag(actionTag: String) = apply {
            this.actionTag = actionTag
        }

        fun setContainerIdInRootFragmentCallback(onContainerId:(() -> Int)) = apply {
            this.onContainerId = WeakReference(onContainerId)
        }

        fun setFragmentCallback(onFragment: ((fragmentTag: String, actionTag: String) -> ChildBackStackAbleFragment)) = apply {
            this.onFragment = WeakReference(onFragment)
        }

        fun build() = ChildFragmentBuilder(fragmentTag, actionTag, onContainerId, onFragment)
    }
}