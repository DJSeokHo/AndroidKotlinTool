package com.swein.androidkotlintool.framework.util.popup

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu

class PopupMenuUtil {

    companion object {
        fun popupMenuByButton(context: Context, button: View, menuResource: Int) {
            val popupMenu = PopupMenu(context, button)
            popupMenu.menuInflater.inflate(menuResource, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {

                    //add popup menu item's it at here

                    else -> {
                    }
                }
                true
            }

            popupMenu.show()
        }
    }
}