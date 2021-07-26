package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager

data class FragmentInfo(
    val fragmentTag: String,
    val actionTag: String,
    val containerInFragment: Int = -1,
    val stack:BackStack = BackStack()
)