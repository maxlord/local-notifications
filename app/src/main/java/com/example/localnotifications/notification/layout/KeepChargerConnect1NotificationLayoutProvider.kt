package com.example.localnotifications.notification.layout

import android.content.Context
import android.widget.RemoteViews
import com.example.localnotifications.R

class KeepChargerConnect1NotificationLayoutProvider(
    private val context: Context,
    private val percentValue: Double,
    private val temperatureValue: Double,
    private val voltageValue: Double
) : NotificationLayoutProvider {

    override fun buildContentView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.view_keep_charger_connect1_small).apply {
            setTextViewText(R.id.textChargePercent, percentValue.toString())
        }
    }

    override fun buildBigContentView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.view_keep_charger_connect1_big).apply {
            setTextViewText(R.id.textChargePercent, percentValue.toString())
            setTextViewText(R.id.textTemperature, temperatureValue.toString())
            setTextViewText(R.id.textVoltage, voltageValue.toString())
        }
    }
}