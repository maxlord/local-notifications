package com.example.localnotifications.notification.layout

import android.app.PendingIntent
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.SystemClock
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
import com.example.localnotifications.R

@Deprecated("No use in current application")
class SmartCleanerNotificationLayoutProvider(
    private val context: Context,
    private val message: String,
    private val actionText: String
) : NotificationLayoutProvider {

    override fun buildContentView(pendingIntent: PendingIntent): RemoteViews {
        val notificationLayout =
            RemoteViews(context.packageName, R.layout.view_notification_smart_cleaner_short)
        notificationLayout.setTextViewText(R.id.tvMessage, message)
        notificationLayout.setTextViewText(R.id.btAction, actionText)
        return notificationLayout
    }

    override fun buildBigContentView(pendingIntent: PendingIntent): RemoteViews {
        val notificationLayout =
            RemoteViews(context.packageName, R.layout.view_notification_smart_cleaner_long)
        notificationLayout.setTextViewText(R.id.tvMessage, message)
        notificationLayout.setTextViewText(R.id.btAction, actionText)
        return notificationLayout
    }
}