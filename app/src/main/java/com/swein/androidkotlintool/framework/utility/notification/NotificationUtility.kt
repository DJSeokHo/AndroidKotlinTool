package com.swein.androidkotlintool.framework.utility.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/**
    public static final int PRIORITY_DEFAULT = 0;
    /**
     * Lower notification priority for {@link NotificationCompat.Builder#setPriority(int)},
     * for items that are less important. The UI may choose to show
     * these items smaller, or at a different position in the list,
     * compared with your app's {@link #PRIORITY_DEFAULT} items.
     */
    public static final int PRIORITY_LOW = -1;

    /**
     * Lowest notification priority for {@link NotificationCompat.Builder#setPriority(int)};
     * these items might not be shown to the user except under
     * special circumstances, such as detailed notification logs.
     */
    public static final int PRIORITY_MIN = -2;

    /**
     * Higher notification priority for {@link NotificationCompat.Builder#setPriority(int)},
     * for more important notifications or alerts. The UI may choose
     * to show these items larger, or at a different position in
     * notification lists, compared with your app's {@link #PRIORITY_DEFAULT} items.
     */
    public static final int PRIORITY_HIGH = 1;

    /**
     * Highest notification priority for {@link NotificationCompat.Builder#setPriority(int)},
     * for your application's most important items that require the user's
     * prompt attention or input.
     */
    public static final int PRIORITY_MAX = 2;
*/
object NotificationUtility {

    enum class NotificationImportanceType {
        DEFAULT, LOW, MIN, HIGH, MAX
    }

    enum class NotificationBigType(var any: Any?, var hideLargeIconWhenBigType: Boolean = true) {
        TEXT(null), IMAGE(null, true)
    }

    fun createSimpleNotification(
        context: Context,
        title: String,
        message: String,
        smallIcon: Int,
        channelId: String,
        channelNameForAndroidO: String, // can not be a "" (a empty string)
        channelDescriptionForAndroidO: String = "",
        pendingIntent: PendingIntent? = null,
        largeIcon: Bitmap? = null,
        bigType: NotificationBigType? = null,
        importanceType: NotificationImportanceType = NotificationImportanceType.DEFAULT,
        autoCancel: Boolean = true,
        autoCancelAfterSecond: Int = 0
    ): Notification {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, channelNameForAndroidO, when (importanceType) {
                NotificationImportanceType.DEFAULT -> {
                    NotificationManager.IMPORTANCE_DEFAULT
                }

                NotificationImportanceType.LOW -> {
                    NotificationManager.IMPORTANCE_LOW
                }

                NotificationImportanceType.MIN -> {
                    NotificationManager.IMPORTANCE_MIN
                }

                NotificationImportanceType.HIGH -> {
                    NotificationManager.IMPORTANCE_HIGH
                }

                NotificationImportanceType.MAX -> {
                    NotificationManager.IMPORTANCE_MAX
                }
            }).apply {
                description = channelDescriptionForAndroidO
            }
            // Register the channel with the system
            NotificationManagerCompat.from(context).createNotificationChannel(notificationChannel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
        builder.setSmallIcon(smallIcon).setContentTitle(title).setContentText(message)
        builder.priority = when (importanceType) {
            NotificationImportanceType.DEFAULT -> {
                NotificationCompat.PRIORITY_DEFAULT
            }

            NotificationImportanceType.LOW -> {
                NotificationCompat.PRIORITY_LOW
            }

            NotificationImportanceType.MIN -> {
                NotificationCompat.PRIORITY_MIN
            }

            NotificationImportanceType.HIGH -> {
                NotificationCompat.PRIORITY_HIGH
            }

            NotificationImportanceType.MAX -> {
                NotificationCompat.PRIORITY_MAX
            }
        }

        builder.setAutoCancel(autoCancel)

        pendingIntent?.let {
            builder.setContentIntent(it)
        }

        bigType?.let {

            when (it) {
                NotificationBigType.TEXT -> {

                    it.any?.let { any ->
                        if (any is String && any != "") {
                            builder.setStyle(NotificationCompat.BigTextStyle().bigText(any))
                        }
                    }

                }

                NotificationBigType.IMAGE -> {

                    it.any?.let { any ->
                        if (any is Bitmap) {
                            builder.setStyle(
                                NotificationCompat
                                    .BigPictureStyle()
                                    .bigPicture(any)
                                    .bigLargeIcon(if (it.hideLargeIconWhenBigType) {
                                        null
                                    }
                                    else {
                                        largeIcon
                                    })
                            )
                        }
                    }

                }
            }

        }

        largeIcon?.let {
            builder.setLargeIcon(it)
        }

        if (autoCancelAfterSecond != 0) {
            builder.setTimeoutAfter(autoCancelAfterSecond * 1000L)
        }

        return builder.build()
    }

    /**
     * need to save the id
     * id is for cancel the notification
     */
    fun showNotification(context: Context, notificationId: Int, notification: Notification): Int {
        NotificationManagerCompat.from(context).notify(notificationId, notification)
        return notificationId
    }
}