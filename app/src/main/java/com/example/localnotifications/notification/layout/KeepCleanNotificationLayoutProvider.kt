package com.example.localnotifications.notification.layout

import android.app.PendingIntent
import android.content.Context
import android.widget.RemoteViews
import com.example.localnotifications.R

@Deprecated("No use in current application")
class KeepCleanNotificationLayoutProvider(
    private val context: Context,
    private val applicationName: String,
    private val title: String,
    private val description: String,
    private val actionText: String,
    private val actionBigText: String
) : NotificationLayoutProvider {

    override fun buildContentView(pendingIntent: PendingIntent): RemoteViews {
        val notificationLayout =
            RemoteViews(context.packageName, R.layout.view_notification_keep_clean_short)
        notificationLayout.setTextViewText(R.id.tvTitle, title)
        notificationLayout.setTextViewText(R.id.tvDescription, description)
        notificationLayout.setTextViewText(R.id.btAction, actionText)
        return notificationLayout
    }

    override fun buildBigContentView(pendingIntent: PendingIntent): RemoteViews {
        val notificationLayout =
            RemoteViews(context.packageName, R.layout.view_notification_keep_clean_long)
        notificationLayout.setTextViewText(R.id.tvApplicationName, applicationName)
        notificationLayout.setTextViewText(R.id.tvDescription, title)
        notificationLayout.setTextViewText(R.id.btAction, actionBigText)
        return notificationLayout
    }
}