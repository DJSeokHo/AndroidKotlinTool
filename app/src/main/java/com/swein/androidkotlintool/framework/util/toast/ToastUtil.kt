package com.swein.androidkotlintool.framework.util.toast

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.swein.androidkotlintool.R

class ToastUtil {
    companion object {

        fun showShortToastNormal(context: Context, string: String) {

            Toast.makeText(context, string, Toast.LENGTH_SHORT).show()

        }

        fun showShortToastNormal(context: Context, string: CharSequence) {

            Toast.makeText(context, string, Toast.LENGTH_SHORT).show()

        }


        fun showLongToastNormal(context: Context, string: String) {

            Toast.makeText(context, string, Toast.LENGTH_LONG).show()

        }

        fun showCustomShortToastNormal(context: Context, string: String) {

            val toast = Toast(context)

            val linearLayout = LinearLayout(context)
            linearLayout.setBackgroundResource(R.drawable.toast_custom_background)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.gravity = Gravity.CENTER
            linearLayout.setPadding(5, 5, 5, 5)

            val textView = TextView(context)
            textView.text = string
            textView.setPadding(5, 5, 5, 5)
            textView.gravity = Gravity.CENTER
            textView.textSize = 16f
            textView.setTextColor(Color.WHITE)
            linearLayout.addView(textView)

            toast.view = linearLayout
            toast.duration = Toast.LENGTH_SHORT
            toast.show()

        }

        fun showCustomLongToastNormal(context: Context, string: String) {

            val toast = Toast(context)

            val linearLayout = LinearLayout(context)
            linearLayout.setBackgroundResource(R.drawable.toast_custom_background)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.gravity = Gravity.CENTER
            linearLayout.setPadding(5, 5, 5, 5)

            val textView = TextView(context)
            textView.text = string
            textView.setPadding(5, 5, 5, 5)
            textView.gravity = Gravity.CENTER
            textView.textSize = 16f
            textView.setTextColor(Color.WHITE)
            linearLayout.addView(textView)

            toast.view = linearLayout
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
        }

        fun showCustomShortToastWithImageResourceId(context: Context, string: String, resourceId: Int) {

            val toast = Toast(context)

            val imageView = ImageView(context)
            imageView.setImageResource(resourceId)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(5, 5, 5, 5)

            val linearLayout = LinearLayout(context)
            linearLayout.setBackgroundResource(R.drawable.toast_custom_background)
            linearLayout.addView(imageView)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.gravity = Gravity.CENTER
            linearLayout.setPadding(5, 5, 5, 5)

            val textView = TextView(context)
            textView.text = string
            textView.setPadding(5, 5, 5, 5)
            textView.gravity = Gravity.CENTER
            textView.textSize = 16f
            textView.setTextColor(Color.WHITE)
            linearLayout.addView(textView)

            toast.view = linearLayout
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
        }

        fun showCustomLongToastWithImageResourceId(context: Context, string: String, resourceId: Int) {

            val toast = Toast(context)

            val imageView = ImageView(context)
            imageView.setImageResource(resourceId)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(5, 5, 5, 5)

            val linearLayout = LinearLayout(context)
            linearLayout.setBackgroundResource(R.drawable.toast_custom_background)
            linearLayout.addView(imageView)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.gravity = Gravity.CENTER
            linearLayout.setPadding(5, 5, 5, 5)

            val textView = TextView(context)
            textView.text = string
            textView.setPadding(5, 5, 5, 5)
            textView.gravity = Gravity.CENTER
            textView.textSize = 16f
            textView.setTextColor(Color.WHITE)
            linearLayout.addView(textView)

            toast.view = linearLayout
            toast.duration = Toast.LENGTH_LONG
            toast.show()
        }

    }
}