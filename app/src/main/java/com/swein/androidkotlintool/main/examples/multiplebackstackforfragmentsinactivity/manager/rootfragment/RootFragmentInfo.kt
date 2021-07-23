package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.rootfragment

import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.childfragment.ChildBackStack

data class RootFragmentInfo(
    val rootFragmentTag: String,
    val rootActionTag: String,
    val stack: ChildBackStack = ChildBackStack()
)