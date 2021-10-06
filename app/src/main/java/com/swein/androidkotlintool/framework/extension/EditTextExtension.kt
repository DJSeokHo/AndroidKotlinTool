package com.swein.androidkotlintool.framework.extension

import android.widget.EditText

fun EditText.setCursorToEnd() {
    setSelection(text.toString().trim { it <= ' ' }.length)
}