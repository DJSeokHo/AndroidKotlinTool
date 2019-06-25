package com.swein.androidkotlintool.framework.util.views

import android.widget.EditText

class EditTextUtil {

    companion object {

        fun setEditTextSelectionToEnd(editText: EditText) {
            editText.setSelection(editText.text.toString().trim { it <= ' ' }.length)
        }
    }
}