package com.swein.androidkotlintool.framework.util.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ViewUtil {

    companion object {

        fun viewLayoutInflater(context: Context, resourceId: Int, parent: ViewGroup?, attachToRoot: Boolean): View {
            return LayoutInflater.from(context).inflate(resourceId, parent, attachToRoot)
        }
    }
}