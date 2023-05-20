package com.example.localnotifications.notification

interface LocalNotificationManager {

    fun sendKeepCleanNotification()

    fun sendStormCleanerNotification()

    fun scheduleKeepCleanNotification(delay: Long)

    fun scheduleStormCleanerNotification(delay: Long)
}