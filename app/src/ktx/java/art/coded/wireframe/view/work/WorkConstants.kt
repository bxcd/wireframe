package art.coded.wireframe.view.work

object WorkConstants {
    const val CHANNEL_ID = "WORK_STATUS"
    const val CHANNEL_NAME = "Work Status"
    const val CHANNEL_DESCRIPTION = "For indicating work status"
    const val CHANNEL_IMPORTANCE = 4
    const val NOTIFICATION_ID_STARTED = 1
    const val NOTIFICATION_TITLE_STARTED = "Work in progress..."
    const val NOTIFICATION_MESSAGE_STARTED = "Please wait, or press cancel to stop work"
    const val NOTIFICATION_ID_FINISHED = 2
    const val NOTIFICATION_TITLE_FINISHED = "Work completed"
    const val NOTIFICATION_MESSAGE_FINISHED = "Press start to begin work"
    const val DEFAULT_WORK_NAME = "Default Work"
    const val DEFAULT_WORK_TAG = "DEFAULT_WORK"
    const val DEFAULT_WORK_TIME_MILLIS: Long = 10000
}