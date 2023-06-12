package com.example.localnotifications.notification.layout

import android.content.Context
import android.widget.RemoteViews
import com.example.localnotifications.R

class StormChargerDisconnectNotificationLayoutProvider(
    private val context: Context
) : NotificationLayoutProvider {

    override fun buildContentView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.view_storm_charger_disconnect_small)
    }

    override fun buildBigContentView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.view_storm_charger_disconnect_big)
    }
}