package com.swein.androidkotlintool.framework.util.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.swein.androidkotlintool.R

class ViewUtil {

    companion object {

        fun inflateView(context: Context?, resource: Int, viewGroup: ViewGroup?): View? {
            return LayoutInflater.from(context).inflate(resource, viewGroup)
        }

        fun viewFromBottom(context: Context?, view: View) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.view_from_bottom)
            view.startAnimation(animation)
        }

        fun viewOutBottom(context: Context?, view: View) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.view_out_bottom)
            view.startAnimation(animation)
        }

        fun viewLayoutInflater(context: Context, resourceId: Int, parent: ViewGroup?, attachToRoot: Boolean): View {
            return LayoutInflater.from(context).inflate(resourceId, parent, attachToRoot)
        }
    }
}