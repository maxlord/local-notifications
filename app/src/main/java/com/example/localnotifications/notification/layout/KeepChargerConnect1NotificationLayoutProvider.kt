package com.example.localnotifications.notification.layout

import android.app.PendingIntent
import android.content.Context
import android.security.identity.PersonalizationData
import android.widget.RemoteViews
import com.example.localnotifications.R

class KeepChargerConnect1NotificationLayoutProvider(
    private val context: Context,
    private val percentValue: Double,
    private val temperatureValue: Double,
    private val voltageValue: Double
) : NotificationLayoutProvider {

    override fun buildContentView(pendingIntent: PendingIntent): RemoteViews {
        return RemoteViews(context.packageName, R.layout.view_keep_charger_connect1_small).apply {
            setTextViewText(R.id.textChargePercent, percentValue.toString())
            setOnClickPendingIntent(R.id.btnAction, pendingIntent)
        }
    }

    override fun buildBigContentView(pendingIntent: PendingIntent): RemoteViews {
        return RemoteViews(context.packageName, R.layout.view_keep_charger_connect1_big).apply {
            setTextViewText(R.id.textChargePercent, percentValue.toString())
            setTextViewText(R.id.textTemperature, temperatureValue.toString())
            setTextViewText(R.id.textVoltage, voltageValue.toString())
            setOnClickPendingIntent(R.id.btnAction, pendingIntent)
        }
    }
}