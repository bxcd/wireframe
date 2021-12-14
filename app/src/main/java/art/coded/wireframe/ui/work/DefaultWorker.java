package art.coded.wireframe.ui.work;

import android.app.Notification;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DefaultWorker extends Worker {

    static final String LOG_TAG = DefaultWorker.class.getSimpleName();

    public DefaultWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull @Override public Result doWork() {

        Context applicationContext = getApplicationContext();

        try {

            WorkUtilities.loadNotificationChannel(
                    applicationContext,
                    WorkConstants.CHANNEL_ID,
                    WorkConstants.CHANNEL_NAME,
                    WorkConstants.CHANNEL_DESCRIPTION,
                    WorkConstants.CHANNEL_IMPORTANCE);

            Notification notificationStarted =
                    new NotificationCompat.Builder(applicationContext, WorkConstants.CHANNEL_ID)
                            .setSmallIcon(android.R.drawable.ic_menu_manage)
                            .setContentTitle(WorkConstants.NOTIFICATION_TITLE_STARTED)
                            .setContentText(WorkConstants.NOTIFICATION_MESSAGE_STARTED)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setVibrate(new long[0])
                            .build();

            NotificationManagerCompat.from(applicationContext)
                    .notify(WorkConstants.NOTIFICATION_ID_STARTED, notificationStarted);

            // do task requiring guaranteed execution
            // (e.g. local/remote data sync, file i/o, image processing)
            try { // placeholder
                Thread.sleep(WorkConstants.DEFAULT_WORK_TIME_MILLIS);
            } catch (InterruptedException e) { Log.d(LOG_TAG, "failure" + e.getMessage()); return Result.failure(); }

            Notification notificationFinished =
                    new NotificationCompat.Builder(applicationContext, WorkConstants.CHANNEL_ID)
                            .setSmallIcon(android.R.drawable.ic_menu_manage)
                            .setContentTitle(WorkConstants.NOTIFICATION_TITLE_FINISHED)
                            .setContentText(WorkConstants.NOTIFICATION_MESSAGE_FINISHED)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setVibrate(new long[0])
                            .build();

            NotificationManagerCompat.from(applicationContext)
                    .notify(WorkConstants.NOTIFICATION_ID_FINISHED, notificationFinished);

            Log.d(LOG_TAG, "success");

            return Result.success();

        } catch (Throwable t) { Log.d(LOG_TAG, "failure" + t.getMessage()); return Result.failure(); }
    }
}
