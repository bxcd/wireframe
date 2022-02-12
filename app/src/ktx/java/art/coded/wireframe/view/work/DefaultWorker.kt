package art.coded.wireframe.view.work

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.content.Context
import android.util.Log
import androidx.work.*

class DefaultWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val applicationContext = applicationContext
        return try {
            WorkUtilities.loadNotificationChannel(
                applicationContext,
                WorkConstants.CHANNEL_ID,
                WorkConstants.CHANNEL_NAME,
                WorkConstants.CHANNEL_DESCRIPTION,
                WorkConstants.CHANNEL_IMPORTANCE
            )
            val notificationStarted =
                NotificationCompat.Builder(applicationContext, WorkConstants.CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.ic_menu_manage)
                    .setContentTitle(WorkConstants.NOTIFICATION_TITLE_STARTED)
                    .setContentText(WorkConstants.NOTIFICATION_MESSAGE_STARTED)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setVibrate(LongArray(0))
                    .build()
            NotificationManagerCompat.from(applicationContext)
                .notify(WorkConstants.NOTIFICATION_ID_STARTED, notificationStarted)

            // do task requiring guaranteed execution
            // (e.g. local/remote data sync, file i/o, image processing)
            try { // placeholder
                Thread.sleep(WorkConstants.DEFAULT_WORK_TIME_MILLIS)
            } catch (e: InterruptedException) {
                Log.d(LOG_TAG, "failure" + e.message)
                return Result.failure()
            }
            val notificationFinished =
                NotificationCompat.Builder(applicationContext, WorkConstants.CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.ic_menu_manage)
                    .setContentTitle(WorkConstants.NOTIFICATION_TITLE_FINISHED)
                    .setContentText(WorkConstants.NOTIFICATION_MESSAGE_FINISHED)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setVibrate(LongArray(0))
                    .build()
            NotificationManagerCompat.from(applicationContext)
                .notify(WorkConstants.NOTIFICATION_ID_FINISHED, notificationFinished)
            Log.d(LOG_TAG, "success")
            Result.success()
        } catch (t: Throwable) {
            Log.d(LOG_TAG, "failure" + t.message)
            Result.failure()
        }
    }

    companion object {
        val LOG_TAG = DefaultWorker::class.java.simpleName
    }
}