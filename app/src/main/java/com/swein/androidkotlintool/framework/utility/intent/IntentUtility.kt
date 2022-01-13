package com.swein.androidkotlintool.framework.utility.intent

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

object IntentUtility {

    enum class PendingIntentEnum {
        ACTIVITY, SERVICE, FOREGROUND_SERVICE, BROADCAST,
    }

    fun intentStartActionBackToHome(activity: Activity) {

        Intent().apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_HOME)
            activity.startActivity(this)
        }
    }

    fun createPendingIntent(
        type: PendingIntentEnum,
        context: Context,
        requestCode: Int,
        intent: Intent,
        flag: Int = PendingIntent.FLAG_UPDATE_CURRENT,
    ): PendingIntent {
        /*
        public static final int FLAG_CANCEL_CURRENT = 268435456
        public static final int FLAG_IMMUTABLE = 67108864
        public static final int FLAG_MUTABLE = 33554432
        public static final int FLAG_NO_CREATE = 536870912
        public static final int FLAG_ONE_SHOT = 1073741824
        public static final int FLAG_UPDATE_CURRENT = 134217728
         */
        return when (type) {
            PendingIntentEnum.ACTIVITY -> {
                PendingIntent.getActivity(context, requestCode, intent, flag)
            }

            PendingIntentEnum.SERVICE -> {
                PendingIntent.getService(context, requestCode, intent, flag)
            }

            PendingIntentEnum.FOREGROUND_SERVICE -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    PendingIntent.getForegroundService(context, requestCode, intent, flag)
                }
                else {
                    PendingIntent.getService(context, requestCode, intent, flag)
                }
            }

            PendingIntentEnum.BROADCAST -> {
                PendingIntent.getBroadcast(context, requestCode, intent, flag)
            }
        }
    }
}