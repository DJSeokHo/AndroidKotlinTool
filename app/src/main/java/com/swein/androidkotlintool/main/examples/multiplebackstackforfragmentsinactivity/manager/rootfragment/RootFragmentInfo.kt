package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.rootfragment

import androidx.fragment.app.FragmentActivity
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.childfragment.ChildBackStack

data class RootFragmentInfo(
    val fragmentTag: String,
    val actionTag: String,
    val stack: ChildBackStack = ChildBackStack()
)