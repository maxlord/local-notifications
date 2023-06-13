package com.example.localnotifications.notification.layout

import android.app.PendingIntent
import android.widget.RemoteViews

interface NotificationLayoutProvider {

    fun buildContentView(pendingIntent: PendingIntent): RemoteViews
    fun buildBigContentView(pendingIntent: PendingIntent): RemoteViews
}