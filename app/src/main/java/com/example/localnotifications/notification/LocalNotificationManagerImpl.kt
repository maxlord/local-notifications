package com.example.localnotifications.notification

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.SystemClock
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.localnotifications.MainActivity
import com.example.localnotifications.R
import com.example.localnotifications.alarm.AlarmReceiver
import com.example.localnotifications.notification.layout.KeepCleanNotificationLayoutProvider
import com.example.localnotifications.notification.layout.NotificationLayoutProvider
import com.example.localnotifications.notification.layout.SmartCleanerNotificationLayoutProvider

class LocalNotificationManagerImpl(private val context: Context) : LocalNotificationManager {

    private var alarmManager: AlarmManager? = null

    init {
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager?
    }

    override fun sendKeepCleanNotification() {
        createNotification(
            context,
            KeepCleanNotificationLayoutProvider(
                context,
                "Storm Cleaner & File Manager",
                "Sensitive permission may have been obtained!",
                "Storm Cleaner & File Manager successfully installed",
                "VIEW",
                "SCAN FOR SENSITIVE PERMISSIONS"
            )
        )
    }

    override fun sendStormCleanerNotification() {
        createNotification(
            context,
            SmartCleanerNotificationLayoutProvider(
                context,
                "Keep your device clean",
                "Delete"
            )
        )
    }

    override fun scheduleKeepCleanNotification(delay: Long) {
        val intent = Intent(context, AlarmReceiver::class.java)
            .apply {
                putExtra(AlarmReceiver.ALARM_TYPE, AlarmReceiver.ALARM_TYPE_KEEP_CLEAN)
            }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ALARM_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager?.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + delay,
            pendingIntent
        )
    }

    override fun scheduleStormCleanerNotification(delay: Long) {
        val intent = Intent(context, AlarmReceiver::class.java)
            .apply {
                putExtra(AlarmReceiver.ALARM_TYPE, AlarmReceiver.ALARM_TYPE_SMART_CLEANER)
            }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ALARM_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager?.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + delay,
            pendingIntent
        )
    }

    private fun createNotification(
        context: Context,
        layoutProvider: NotificationLayoutProvider
    ) {
        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        createNotificationChannel(context)

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(layoutProvider.buildContentView())
            .setCustomBigContentView(layoutProvider.buildBigContentView())
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setAutoCancel(true)
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            val notificationId = System.currentTimeMillis().toInt()
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = android.app.NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                importance
            ).apply {
                description = NOTIFICATION_CHANNEL_DESCRIPTION
            }
            // Register the channel with the system
            val notificationManager: android.app.NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val ALARM_REQUEST_CODE = 1
        private const val NOTIFICATION_CHANNEL_ID = "channel_local_notifications"
        private const val NOTIFICATION_CHANNEL_NAME = "Локальные уведомления"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION =
            "Канал для отправки локальных уведомлений"
    }
}