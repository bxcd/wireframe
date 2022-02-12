package art.coded.wireframe.view.work;

public class WorkConstants {

    public static final String CHANNEL_ID = "WORK_STATUS";
    public static final String CHANNEL_NAME = "Work Status";
    public static final String CHANNEL_DESCRIPTION = "For indicating work status";
    public static final int CHANNEL_IMPORTANCE = 4;

    public static final int NOTIFICATION_ID_STARTED = 1;
    public static final String NOTIFICATION_TITLE_STARTED = "Work in progress...";
    public static final String NOTIFICATION_MESSAGE_STARTED = "Please wait, or press cancel to stop work";

    public static final int NOTIFICATION_ID_FINISHED = 2;
    public static final String NOTIFICATION_TITLE_FINISHED = "Work completed";
    public static final String NOTIFICATION_MESSAGE_FINISHED = "Press start to begin work";

    public static final String DEFAULT_WORK_NAME = "Default Work";
    public static final String DEFAULT_WORK_TAG = "DEFAULT_WORK";
    public static final long DEFAULT_WORK_TIME_MILLIS = 10000;
}
