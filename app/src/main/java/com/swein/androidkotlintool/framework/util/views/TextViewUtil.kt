package com.swein.androidkotlintool.framework.util.views

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.widget.TextView

class TextViewUtil {

    companion object {

        fun getSpannableString(context: Context, string: String, textView: TextView) {
            val spannableString = SpannableString(string)


            spannableString.setSpan(RelativeSizeSpan(1.0f), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(RelativeSizeSpan(1.0f), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


            textView.setText(spannableString, TextView.BufferType.SPANNABLE)
            textView.text = string
        }

        fun getSpannableString(context: Context, resourceId: Int, textView: TextView) {
            val spannableString = SpannableString(context.getString(resourceId))

            spannableString.setSpan(RelativeSizeSpan(1.0f), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(RelativeSizeSpan(1.0f), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            textView.setText(spannableString, TextView.BufferType.SPANNABLE)
            textView.text = context.getString(resourceId)
        }

    }
}