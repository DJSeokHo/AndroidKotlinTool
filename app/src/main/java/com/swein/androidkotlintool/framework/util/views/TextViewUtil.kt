package com.swein.androidkotlintool.framework.util.views

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import com.swein.androidkotlintool.framework.util.dimension.DimensionUtil

class TextViewUtil {

    companion object {

        fun createWarpContentTextView(context: Context, paddingAll: Int, marginAll: Int, text: String, backgroundColor: Int, textSize: Float, textColor: Int): TextView {
            val textView = TextView(context)
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            params.setMargins(
                DimensionUtil.dimension(paddingAll, context),
                DimensionUtil.dimension(paddingAll, context),
                DimensionUtil.dimension(paddingAll, context),
                DimensionUtil.dimension(paddingAll, context)
            )

            textView.layoutParams = params

            textView.setPadding(
                DimensionUtil.dimension(marginAll, context),
                DimensionUtil.dimension(marginAll, context),
                DimensionUtil.dimension(marginAll, context),
                DimensionUtil.dimension(marginAll, context)
            )

            textView.setBackgroundColor(backgroundColor)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, textSize)
            textView.setTextColor(textColor)
            textView.text = text

            return textView
        }

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