package art.coded.wireframe.ui.work;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

final class WorkUtilities {

    static void loadNotificationChannel(
            Context context,
            String channelId,
            String channelName,
            String channelDescription,
            int importance) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel =
                    new NotificationChannel(
                            channelId, channelName, importance);
            notificationChannel.setDescription(channelDescription);

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
    }
}
