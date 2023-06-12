package com.example.localnotifications.notification

interface LocalNotificationManager {

    fun sendKeepChargerConnect1()

    fun scheduleAlarmNotification(alarmType: Int, delay: Long)


//    fun sendKeepCleanNotification()
//
//    fun sendStormCleanerNotification()
//
//    fun scheduleKeepCleanNotification(delay: Long)
//
//    fun scheduleStormCleanerNotification(delay: Long)
}