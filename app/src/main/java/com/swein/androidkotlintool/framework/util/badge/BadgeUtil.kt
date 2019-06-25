package com.swein.androidkotlintool.framework.util.badge

import android.content.Context
import me.leolin.shortcutbadger.ShortcutBadger

class BadgeUtil {

    companion object {
        fun setBadgeNumber(context: Context, number: Int) {
            ShortcutBadger.applyCount(context, number)
        }

        fun clearBadgeNumber(context: Context) {
            ShortcutBadger.removeCount(context)
        }

        fun clearBadgeNumber(context: Context, number: Int) {
            ShortcutBadger.applyCount(context, number)
        }
    }
}