package kr.co.dotv365.android.framework.extension

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.core.view.ViewCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun BottomSheetDialogFragment.setScrimDimAmountAtOnViewCreated(dimAmount: Float = 0.0f) {

    dialog?.window?.let {
        val params: WindowManager.LayoutParams = it.attributes
        params.dimAmount = dimAmount
        it.attributes = params
    }

}

fun BottomSheetDialogFragment.keepStatusBarTheme(context: Context) {

    val currentNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

    if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            dialog?.window?.decorView?.let {
                ViewCompat.getWindowInsetsController(it)?.isAppearanceLightStatusBars = false
            }

        }
        else {
            dialog?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }

    }
    else {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            dialog?.window?.decorView?.let {
                ViewCompat.getWindowInsetsController(it)?.isAppearanceLightStatusBars = true
            }
        }
        else {
            dialog?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

    }

}