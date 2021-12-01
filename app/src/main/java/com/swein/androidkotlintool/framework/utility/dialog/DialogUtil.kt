package com.swein.androidkotlintool.framework.utility.dialog

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView

class DialogUtil {
    companion object {

        fun createNormalDialogWithOneButton(
            context: Context, title: String, message: String, cancelAble: Boolean, positiveButtonText: String,
            onClickListener: DialogInterface.OnClickListener
        ) {
            val alertDialogBuilder = AlertDialog.Builder(context)


            // set title
            alertDialogBuilder.setTitle(title)

            // set dialog message
            alertDialogBuilder
                .setMessage(message)
                .setCancelable(cancelAble)
                .setPositiveButton(positiveButtonText, onClickListener)

            // create alert dialog
            val alertDialog = alertDialogBuilder.create()

            // show it
            alertDialog.show()

        }

        fun createNormalDialogWithTwoButton(
            context: Context,
            title: String?,
            message: String?,
            cancelAble: Boolean,
            positiveButtonText: String,
            negativeButtonText: String,
            positiveButton: DialogInterface.OnClickListener,
            negativeButton: DialogInterface.OnClickListener
        ) {
            val alertDialogBuilder = AlertDialog.Builder(context)

            if (title == null || title.length == 0) {

            }

            if (message == null || message.length == 0) {

            }

            // set title
            alertDialogBuilder.setTitle(title)

            // set dialog message
            alertDialogBuilder
                .setMessage(message)
                .setCancelable(cancelAble)
                .setPositiveButton(positiveButtonText, positiveButton)
                .setNegativeButton(negativeButtonText, negativeButton)

            // create alert dialog
            val alertDialog = alertDialogBuilder.create()

            // show it
            alertDialog.show()
        }

        fun createNormalDialogWithThreeButton(
            context: Context,
            title: String?,
            message: String?,
            cancelAble: Boolean,
            positiveButtonText: String,
            negativeButtonText: String,
            otherButtonText: String,
            positiveButton: DialogInterface.OnClickListener,
            negativeButton: DialogInterface.OnClickListener,
            neutralButton: DialogInterface.OnClickListener
        ) {
            val alertDialogBuilder = AlertDialog.Builder(context)

            if (title == null || title.length == 0) {

            }

            if (message == null || message.length == 0) {

            }

            // set title
            alertDialogBuilder.setTitle(title)

            // set dialog message
            alertDialogBuilder
                .setMessage(message)
                .setCancelable(cancelAble)
                .setPositiveButton(positiveButtonText, positiveButton)
                .setNegativeButton(negativeButtonText, negativeButton)
                .setNeutralButton(otherButtonText, neutralButton)

            // create alert dialog
            val alertDialog = alertDialogBuilder.create()

            // show it
            alertDialog.show()
        }


        fun createListItemDialogWithTwoButton(
            context: Context, title: String, listAdapter: ListAdapter, adapter: DialogInterface.OnClickListener,
            cancelAble: Boolean, positiveButtonText: String, negativeButtonText: String,
            positiveButton: DialogInterface.OnClickListener, negativeButton: DialogInterface.OnClickListener
        ) {

            val alertDialogBuilder = AlertDialog.Builder(context)

            alertDialogBuilder.setTitle(title)

            alertDialogBuilder
                .setCancelable(cancelAble)
                .setAdapter(listAdapter, adapter)
                .setPositiveButton(positiveButtonText, positiveButton)
                .setNegativeButton(negativeButtonText, negativeButton)

            // create alert dialog
            val alertDialog = alertDialogBuilder.create()

            // show it
            alertDialog.show()
        }


        fun createProgressDialog(
            context: Context,
            title: String,
            msg: String,
            cancelAble: Boolean,
            canceledOnTouchOutside: Boolean
        ): ProgressDialog {
            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle(title)
            progressDialog.setMessage(msg)
            progressDialog.setCancelable(cancelAble)
            progressDialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            return progressDialog
        }

        fun createCustomListViewDialog(
            context: Context,
            title: String,
            positiveBtnTitle: String,
            negativeBtnTitle: String,
            listAdapter: ListAdapter,
            positiveButton: DialogInterface.OnClickListener,
            negativeButton: DialogInterface.OnClickListener,
            textColor: Int,
            padding: Int
        ) {
            var textColor = textColor

            if (textColor == -1) {
                textColor = Color.WHITE
            }

            val builderSingle = AlertDialog.Builder(context)
            builderSingle.setIcon(null)
            builderSingle.setCancelable(false)

            val titleTv = TextView(context)
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            titleTv.setPadding(padding, padding, padding, padding)
            titleTv.layoutParams = lp
            titleTv.text = title
            titleTv.setTextColor(textColor)
            titleTv.setTypeface(null, Typeface.BOLD)
            titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f)
            builderSingle.setCustomTitle(titleTv)

            builderSingle.setNegativeButton(negativeBtnTitle, negativeButton)

            builderSingle.setAdapter(listAdapter, null)
            builderSingle.setPositiveButton(positiveBtnTitle, positiveButton)

            builderSingle.show()
        }
    }
}