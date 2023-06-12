package com.example.localnotifications.notification

import android.Manifest
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.SystemClock
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmapOrNull
import com.example.localnotifications.MainActivity
import com.example.localnotifications.R
import com.example.localnotifications.alarm.AlarmReceiver
import com.example.localnotifications.notification.layout.KeepChargerConnect1NotificationLayoutProvider
import com.example.localnotifications.notification.layout.KeepChargerConnect2NotificationLayoutProvider
import com.example.localnotifications.notification.layout.KeepChargerDisconnect1NotificationLayoutProvider
import com.example.localnotifications.notification.layout.KeepChargerDisconnect2NotificationLayoutProvider
import com.example.localnotifications.notification.layout.KeepCleanNotificationLayoutProvider
import com.example.localnotifications.notification.layout.NotificationLayoutProvider
import com.example.localnotifications.notification.layout.SmartCleanerNotificationLayoutProvider
import com.example.localnotifications.notification.layout.StormChargerConnectNotificationLayoutProvider
import com.example.localnotifications.notification.layout.StormChargerDisconnectNotificationLayoutProvider
import com.google.android.material.bottomsheet.BottomSheetDialog

class LocalNotificationManagerImpl(private val context: Context) : LocalNotificationManager {

    private var alarmManager: AlarmManager? = null

    init {
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager?
    }

    override fun sendKeepChargerConnect1() {
        createNotification(
            context,
            KeepChargerConnect1NotificationLayoutProvider(
                context,
                77.0,
                35.0,
                3.9
            ),
            true
        )
    }

    override fun sendKeepChargerConnect2() {
        createNotification(
            context,
            KeepChargerConnect2NotificationLayoutProvider(context),
            true
        )
    }

    override fun sendKeepChargerDisconnect1() {
        createNotification(
            context,
            KeepChargerDisconnect1NotificationLayoutProvider(context),
            true
        )
    }

    override fun sendKeepChargerDisconnect2() {
        createNotification(
            context,
            KeepChargerDisconnect2NotificationLayoutProvider(context, 14, 77),
            true
        )
    }

    override fun sendStormChargerConnect() {
        createNotification(
            context,
            StormChargerConnectNotificationLayoutProvider(context),
            false
        )
    }

    override fun sendStormChargerDisconnect() {
        createNotification(
            context,
            StormChargerDisconnectNotificationLayoutProvider(context),
            false
        )
    }

    override fun sendStormChargerNewBehavior() {
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_storm)
        bottomSheetDialog.findViewById<Button>(R.id.btnLater)?.setOnClickListener { bottomSheetDialog.dismiss() }
        bottomSheetDialog.findViewById<Button>(R.id.btnAction)?.setOnClickListener {  }
        bottomSheetDialog.show()
    }

    override fun scheduleAlarmNotification(alarmType: Int, delay: Long) {
        val intent = Intent(context, AlarmReceiver::class.java)
            .apply {
                putExtra(AlarmReceiver.ALARM_TYPE, alarmType)
            }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ALARM_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager?.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + delay,
            pendingIntent
        )
    }

    private fun createNotification(
        context: Context,
        layoutProvider: NotificationLayoutProvider,
        isOngoing: Boolean
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
            .setOngoing(isOngoing)
            .setAutoCancel(true)
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .setCategory(Notification.CATEGORY_CALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        if (isOngoing) {
            builder.setFullScreenIntent(pendingIntent, true)
        } else {
            builder.setContentIntent(pendingIntent)
        }

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
            val notification = builder.build()
            notify(notificationId, notification)
        }
    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                importance
            ).apply {
                description = NOTIFICATION_CHANNEL_DESCRIPTION
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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