package com.example.localnotifications.notification.layout

import android.widget.RemoteViews

interface NotificationLayoutProvider {

    fun buildContentView(): RemoteViews
    fun buildBigContentView(): RemoteViews
}