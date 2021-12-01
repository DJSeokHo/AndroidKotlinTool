package com.swein.androidkotlintool.framework.utility.popup

import android.app.Activity
import android.view.View

class PopupWindowUtil {

    companion object {

        /**
         * this method can not use at create() of any class because this method will called after activity shown
         */
        fun createPopupWindowWithView(
            activity: Activity,
            viewResource: Int,
            popupWindowWidth: Int,
            popupWindowHeight: Int,
            gravity: Int,
            popupWindowShowAtX: Int,
            popupWindowShowAtY: Int,
            parent: View
        ) {

            /*
              just example, add your custom view here
           */

//            val rootView : View = activity.layoutInflater.inflate(viewResource, null)
//            val popupWindow = PopupWindow(rootView, popupWindowWidth, popupWindowHeight)
//            val button = rootView.findViewById(R.id.popupWindowViewButton)
//            val editText = rootView.findViewById(R.id.popupWindowViewEditText)
//
//            button.setOnClickListener(View.OnClickListener {
//                ToastUtil.showCustomShortToastNormal(activity, editText.getText().toString().trim())
//                popupWindow.dismiss()
//            })

            // for edit text input enable
//            popupWindow.isFocusable = true
//            popupWindow.showAtLocation(parent, gravity, popupWindowShowAtX, popupWindowShowAtY)
        }
    }
}