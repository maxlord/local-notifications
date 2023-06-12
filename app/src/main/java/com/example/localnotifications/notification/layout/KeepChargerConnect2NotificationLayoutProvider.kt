package com.example.localnotifications.notification.layout

import android.content.Context
import android.widget.RemoteViews
import com.example.localnotifications.R

class KeepChargerConnect2NotificationLayoutProvider(
    private val context: Context
) : NotificationLayoutProvider {

    override fun buildContentView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.view_keep_charger_connect2_small)
    }

    override fun buildBigContentView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.view_keep_charger_connect2_big)
    }
}