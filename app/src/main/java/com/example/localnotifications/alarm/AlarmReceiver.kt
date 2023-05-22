package com.example.localnotifications.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.localnotifications.notification.LocalNotificationManagerImpl

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            handleAlarmData(context, it)
        }
    }

    private fun handleAlarmData(context: Context?, intent: Intent) {
        context?.let {
            val alarmType = intent.getIntExtra(ALARM_TYPE, 0)
            val notificationManager = LocalNotificationManagerImpl(it)
            when (alarmType) {
                ALARM_TYPE_KEEP_CLEAN -> {
                    notificationManager.sendKeepCleanNotification()
                }
                ALARM_TYPE_STORM_CLEANER -> {
                    notificationManager.sendStormCleanerNotification()
                }
            }
        }
    }

    companion object {
        const val ALARM_TYPE = "alarmType"
        const val ALARM_TYPE_KEEP_CLEAN = 1
        const val ALARM_TYPE_STORM_CLEANER = 2
    }
}