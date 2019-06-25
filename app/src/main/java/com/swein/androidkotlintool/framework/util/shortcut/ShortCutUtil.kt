package com.swein.androidkotlintool.framework.util.shortcut

import android.annotation.TargetApi
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle

/**
 * <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
 * <uses-permission android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" />
 */
class ShortCutUtil {

    companion object {

        private const val ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT"
        private const val DUPLICATE_ENABLE = "duplicate"

        fun addShortcut(
            context: Context,
            targetClass: Class<*>,
            bundle: Bundle?,
            shortcutName: String,
            iconResource: Int
        ) {

            val shortcutIntent = Intent(context, targetClass)
            shortcutIntent.action = Intent.ACTION_MAIN

            if (bundle != null) {
                shortcutIntent.putExtras(bundle)
            }

            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            val addIntent = Intent()
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent)
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName)
            addIntent.putExtra(
                Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(context, iconResource)
            )

            addIntent.action = ACTION_ADD_SHORTCUT
            addIntent.putExtra(DUPLICATE_ENABLE, false)
            context.sendBroadcast(addIntent)
        }

        fun removeShortcut(
            context: Context,
            targetClass: Class<*>,
            bundle: Bundle?,
            shortcutName: String,
            iconResource: Int
        ) {

            val shortcutIntent = Intent(context, targetClass)
            shortcutIntent.action = Intent.ACTION_MAIN

            if (bundle != null) {
                shortcutIntent.putExtras(bundle)
            }

            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            val addIntent = Intent()
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent)
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName)
            addIntent.putExtra(
                Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(context, iconResource)
            )

            addIntent.action = ACTION_ADD_SHORTCUT
            context.sendBroadcast(addIntent)

        }

        /**
         *
         * @param context context
         * @param targetClass targetClass
         * @param bundle bundle
         * @param shortcutId every single shortcut must be have a only id
         * @param shortcutName shortcutName
         * @param iconResource iconResource
         */
        @TargetApi(Build.VERSION_CODES.O)
        fun addShortcut(
            context: Context,
            targetClass: Class<*>,
            bundle: Bundle?,
            shortcutId: String,
            shortcutName: String,
            iconResource: Int
        ) {

            val shortcutManager = context.getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager ?: return

            if (shortcutManager.isRequestPinShortcutSupported) {

                val launcherIntent = Intent(context, targetClass)

                launcherIntent.action = Intent.ACTION_VIEW

                if (bundle != null) {
                    launcherIntent.putExtras(bundle)
                }

                launcherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                launcherIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                val shortcutInfo = ShortcutInfo.Builder(context, shortcutId)
                    .setIcon(Icon.createWithResource(context, iconResource))
                    .setShortLabel(shortcutName)
                    .setIntent(launcherIntent)
                    .build()

                /**
                add to AndroidManifest.xml

                <receiver
                    android:name="com.swein.framework.tools.util.shortcut.ShortcutReceiver"
                    android:exported="false">
                </receiver>
                */
                val shortcutCallbackIntent = PendingIntent.getBroadcast(
                    context,
                    0,
                    Intent(context, ShortcutReceiver::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                shortcutManager.requestPinShortcut(shortcutInfo, shortcutCallbackIntent.intentSender)
            }
        }
    }
}