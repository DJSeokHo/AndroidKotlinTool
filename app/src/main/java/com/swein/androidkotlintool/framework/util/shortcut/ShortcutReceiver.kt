package com.swein.androidkotlintool.framework.util.shortcut

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.swein.androidkotlintool.framework.util.toast.ToastUtil

class ShortcutReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let {
            ToastUtil.showShortToastNormal(it, "바로가기 추가되었습니다.")
        }

    }

}