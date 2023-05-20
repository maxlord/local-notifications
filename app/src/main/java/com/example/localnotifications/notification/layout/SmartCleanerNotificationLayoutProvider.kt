package com.example.localnotifications.notification.layout

import android.content.Context
import android.widget.RemoteViews
import com.example.localnotifications.R

class SmartCleanerNotificationLayoutProvider(
    private val context: Context,
    private val message: String,
    private val actionText: String
) : NotificationLayoutProvider {

    override fun buildContentView(): RemoteViews {
        val notificationLayout =
            RemoteViews(context.packageName, R.layout.view_notification_smart_cleaner_short)
        notificationLayout.setTextViewText(R.id.tvMessage, message)
        notificationLayout.setTextViewText(R.id.btAction, actionText)
        return notificationLayout
    }

    override fun buildBigContentView(): RemoteViews {
        val notificationLayout =
            RemoteViews(context.packageName, R.layout.view_notification_smart_cleaner_long)
        notificationLayout.setTextViewText(R.id.tvMessage, message)
        notificationLayout.setTextViewText(R.id.btAction, actionText)
        return notificationLayout
    }
}