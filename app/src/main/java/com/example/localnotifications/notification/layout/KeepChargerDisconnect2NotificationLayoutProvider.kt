package com.example.localnotifications.notification.layout

import android.content.Context
import android.widget.RemoteViews
import com.example.localnotifications.R

class KeepChargerDisconnect2NotificationLayoutProvider(
    private val context: Context,
    private val duration: Int,
    private val volume: Int
) : NotificationLayoutProvider {

    override fun buildContentView(): RemoteViews {
        return RemoteViews(
            context.packageName,
            R.layout.view_keep_charger_disconnect2_small
        ).apply {
            setTextViewText(R.id.textChargeDuration, "$duration мин")
            setTextViewText(R.id.textChargeVolume, "$volume %")
        }
    }

    override fun buildBigContentView(): RemoteViews {
        return RemoteViews(
            context.packageName,
            R.layout.view_keep_charger_disconnect2_big
        ).apply {
            setTextViewText(R.id.textChargePercent, volume.toString())
            setTextViewText(R.id.textChargeDuration, "$duration мин")
            setTextViewText(R.id.textChargeVolume, "$volume %")
        }
    }
}