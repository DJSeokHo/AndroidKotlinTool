package com.swein.androidkotlintool.framework.util.snackbar

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackBarUtility {

    fun showSnackBar(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
    }

    fun showSnackBar(view: View, text: String, actionText: String, listener: View.OnClickListener) {
        val snackBar = Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText, listener).show()
    }

    fun showSnackBar(view: View, text: String, actionText: String, textColor: Int, backgroundColor: Int, listener: View.OnClickListener) {
        val snackBar = Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE)
        snackBar.view.setBackgroundColor(backgroundColor)
        snackBar.setActionTextColor(textColor)
        snackBar.setAction(actionText, listener).show()
    }
}