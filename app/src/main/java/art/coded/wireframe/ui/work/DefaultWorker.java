package art.coded.wireframe.ui.work;

import android.app.Notification;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DefaultWorker extends Worker {

    public DefaultWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull @Override public Result doWork() {

        Context applicationContext = getApplicationContext();

        try {

            // do task requiring guaranteed execution
            // (e.g. local/remote data sync, file i/o, image processing)
            try { // placeholder
                Thread.sleep(WorkConstants.DEFAULT_WORK_TIME_MILLIS);
            } catch (InterruptedException e) { return Result.failure(); }

            WorkUtilities.loadNotificationChannel(
                    applicationContext,
                    WorkConstants.CHANNEL_ID,
                    WorkConstants.CHANNEL_NAME,
                    WorkConstants.CHANNEL_DESCRIPTION,
                    WorkConstants.CHANNEL_IMPORTANCE);

            Notification notification =
                    new NotificationCompat.Builder(applicationContext, WorkConstants.CHANNEL_ID)
                            .setContentTitle(WorkConstants.NOTIFICATION_TITLE)
                            .setContentText(WorkConstants.NOTIFICATION_MESSAGE)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setVibrate(new long[0])
                            .build();

            NotificationManagerCompat.from(applicationContext)
                    .notify(WorkConstants.NOTIFICATION_ID, notification);

            return Result.success();

        } catch (Throwable t) { return Result.failure(); }
    }
}
