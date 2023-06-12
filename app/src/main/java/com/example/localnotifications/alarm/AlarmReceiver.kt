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
                ALARM_TYPE_KEEP_CHARGER_CONNECT1 -> {
                    notificationManager.sendKeepChargerConnect1()
                }

                ALARM_TYPE_KEEP_CHARGER_CONNECT2 -> {
                    notificationManager.sendKeepChargerConnect2()
                }

                ALARM_TYPE_KEEP_CHARGER_DISCONNECT1 -> {
                    notificationManager.sendKeepChargerDisconnect1()
                }

                ALARM_TYPE_KEEP_CHARGER_DISCONNECT2 -> {
                    notificationManager.sendKeepChargerDisconnect2()
                }

                ALARM_TYPE_STORM_CHARGER_CONNECT -> {
                    notificationManager.sendStormChargerConnect()
                }

                ALARM_TYPE_STORM_CHARGER_DISCONNECT -> {
                    notificationManager.sendStormChargerDisconnect()
                }

                ALARM_TYPE_STORM_CHARGER_CONNECT_NEW_BEHAVIOR -> {
                    notificationManager.sendStormChargerNewBehavior()
                }
            }
        }
    }

    companion object {
        const val ALARM_TYPE = "alarmType"
        const val ALARM_TYPE_KEEP_CHARGER_CONNECT1 = 1
        const val ALARM_TYPE_KEEP_CHARGER_CONNECT2 = 2
        const val ALARM_TYPE_KEEP_CHARGER_DISCONNECT1 = 3
        const val ALARM_TYPE_KEEP_CHARGER_DISCONNECT2 = 4
        const val ALARM_TYPE_STORM_CHARGER_CONNECT = 5
        const val ALARM_TYPE_STORM_CHARGER_DISCONNECT = 6
        const val ALARM_TYPE_STORM_CHARGER_CONNECT_NEW_BEHAVIOR = 7
    }
}