package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager

data class FragmentInfo(
    val fragmentTag: String,
    val actionTag: String,
    val stack:BackStack = BackStack()
) {
    var containerInFragment: Int = -1
}