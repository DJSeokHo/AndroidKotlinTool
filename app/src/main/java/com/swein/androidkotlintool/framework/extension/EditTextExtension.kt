package kr.co.dotv365.android.framework.extension

import android.widget.EditText

fun EditText.setCursorToEnd() {
    setSelection(text.toString().trim { it <= ' ' }.length)
}