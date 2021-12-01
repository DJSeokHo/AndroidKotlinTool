package com.swein.androidkotlintool.framework.utility.shortcut

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.swein.androidkotlintool.framework.utility.toast.ToastUtility

class ShortcutReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let {
            ToastUtility.showShortToastNormal(it, "바로가기 추가되었습니다.")
        }

    }

}