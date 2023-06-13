package com.example.localnotifications.notification.layout

import android.app.PendingIntent
import android.content.Context
import android.widget.RemoteViews
import com.example.localnotifications.R

class StormChargerDisconnectNotificationLayoutProvider(
    private val context: Context
) : NotificationLayoutProvider {

    override fun buildContentView(pendingIntent: PendingIntent): RemoteViews {
        return RemoteViews(context.packageName, R.layout.view_storm_charger_disconnect_small).apply {
            setOnClickPendingIntent(R.id.btnAction, pendingIntent)
        }
    }

    override fun buildBigContentView(pendingIntent: PendingIntent): RemoteViews {
        return RemoteViews(context.packageName, R.layout.view_storm_charger_disconnect_big).apply {
            setOnClickPendingIntent(R.id.btnAction, pendingIntent)
        }
    }
}