package com.swein.androidkotlintool.framework.util.softkeyboard

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

class SoftKeyBoardUtil {

    companion object {

        fun dismissKeyboard(activity: Activity?) {
            val inputMethodManager: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            var view: View? = activity.currentFocus

            if(view == null) {
                view = View(activity)
            }
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun dismissKeyboardForView(focusView: View?) {
            if (focusView == null) {
                return
            }

            dismissKeyboard(focusView.context as Activity)
        }

        fun clearFocusOnView(view: View) {
            val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            view.isFocusable = false
            view.isFocusableInTouchMode = true
        }

        fun showKeyboardForView(view: View?) {
            if (view == null) return

            view.requestFocus()
            val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED)
        }

        fun isShowKeyboard(context: Context, view: View): Boolean {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            if (inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)) {
                inputMethodManager.showSoftInput(view, 0)

                //soft keyboard shown
                return true
            }
            else {

                //soft keyboard dismissed
                return false
            }
        }

        /**
         * use this in onCreate before setContentView()
         * @param activity activity
         */
        fun autoScrollLayerUpNotHideEditor(activity: Activity) {
            activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }
    }

}