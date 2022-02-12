package art.coded.wireframe.view.work

import android.os.Build
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

internal object WorkUtilities {
    fun loadNotificationChannel(
        context: Context,
        channelId: String?,
        channelName: String?,
        channelDescription: String?,
        importance: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId, channelName, importance
            )
            notificationChannel.description = channelDescription
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(notificationChannel)
        }
    }
}