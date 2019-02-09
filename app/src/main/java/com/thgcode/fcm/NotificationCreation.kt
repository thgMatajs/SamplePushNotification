package com.thgcode.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationCreation {
    private var notificationManager: NotificationManager? = null

    const val NOTIFY_ID = 1000
    private val VIBRATION = longArrayOf(300, 400, 500, 400, 300)

    private const val CHANNEL_ID = "MovileNext"
    private const val CHANNEL_NAME = "MovileNext - Push"
    private const val CHANNEL_DESCRIPTION = "MovileNext - Push Channel"

    fun create(context: Context, title: String, body: String) {
        if (notificationManager == null) {
            notificationManager = context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
        }

        notificationManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                var channel = it.getNotificationChannel(CHANNEL_ID)

                if (channel == null) {
                    val importance = NotificationManager.IMPORTANCE_HIGH

                    channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
                    channel.description = CHANNEL_DESCRIPTION
                    channel.enableVibration(true)
                    channel.enableLights(true)
                    channel.vibrationPattern = VIBRATION

                    it.createNotificationChannel(channel)
                }
            }

            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP

            val pendingIntent = PendingIntent.getActivity(context, 0,
                intent, 0)

            val builder =
                NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(title)
                    .setSmallIcon(android.R.drawable.ic_dialog_alert)
                    .setContentText(body)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(title)
                    .setVibrate(VIBRATION)
                    .setOnlyAlertOnce(true)
                    .setStyle(NotificationCompat
                        .BigTextStyle()
                        .bigText(body))

            val notification = builder.build()

            it.notify(NOTIFY_ID, notification)

        }

    }
}