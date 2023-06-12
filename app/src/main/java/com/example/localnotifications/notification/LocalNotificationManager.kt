package com.example.localnotifications.notification

interface LocalNotificationManager {

    fun sendKeepChargerConnect1()

    fun sendKeepChargerConnect2()

    fun sendKeepChargerDisconnect1()

    fun sendKeepChargerDisconnect2()

    fun sendStormChargerConnect()

    fun sendStormChargerDisconnect()

    fun sendStormChargerNewBehavior()

    fun scheduleAlarmNotification(alarmType: Int, delay: Long)

//    fun sendKeepCleanNotification()
//
//    fun sendStormCleanerNotification()
//
//    fun scheduleKeepCleanNotification(delay: Long)
//
//    fun scheduleStormCleanerNotification(delay: Long)
}