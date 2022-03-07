package art.coded.wireframe.view;

import android.app.ListActivity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import art.coded.wireframe.R;
import art.coded.wireframe.model.entity.Element;
import art.coded.wireframe.model.local.ElementDao;
import art.coded.wireframe.model.local.ElementRoomDatabase;

/**
 * Provides a home screen interface that displays user data and offers navigation shortcuts.
 */
public class AppWidget extends AppWidgetProvider {

    private static final String LOG_TAG = AppWidget.class.getSimpleName();

    /**
     * Retrieves and sets {@link PendingIntent} on {@link RemoteViews} of a single {@link AppWidget}.
     */
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);

        Intent populateIntent = new Intent(context, AppWidgetRemoteViewsService.class);
        views.setRemoteAdapter(R.id.widget_list, populateIntent);

        Intent listIntent = new Intent(context, ListActivity.class);
        PendingIntent listPendingIntent = PendingIntent.getActivity(context, 0, listIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_list, listPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * Updates all {@link AppWidget}s detected by {@link AppWidgetManager}.
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
        for (int appWidgetId : appWidgetIds) updateAppWidget(context, appWidgetManager, appWidgetId);
    }

    @Override public void onEnabled(Context context) {}
    @Override public void onDisabled(Context context) {}

    /**
     * Generates a {@link RemoteViewsFactory} for populating remote collections.
     */
    public static class AppWidgetRemoteViewsService extends RemoteViewsService {
        @Override public RemoteViewsFactory onGetViewFactory(Intent intent) {
            return new AppWidgetRemoteViewsFactory(getApplication());
        }
    }

    /**
     * Builds {@link RemoteViews} for populating remote collections.
     */
    public static class AppWidgetRemoteViewsFactory
            implements RemoteViewsService.RemoteViewsFactory {

        Context mContext;
        List<Element> mElementList;
        ElementDao mElementDao;

        AppWidgetRemoteViewsFactory(Context context) {
            mContext = context;
            mElementDao = ElementRoomDatabase.getInstance(mContext).elementDao();
        }

        /**
         * Triggered when remote collection adapter invokes notifyDataSetChanged; synchronous processing
         * does not disrupt application main thread.
         */
        @Override public void onDataSetChanged() {
            long token = Binder.clearCallingIdentity();
            mElementList = mElementDao.getAllUnwrapped();
            refresh(mContext);
            Binder.restoreCallingIdentity(token);
        }

        /**
         * Populates {@link RemoteViews} at each position of the remote collection.
         */
        @Override public RemoteViews getViewAt(int position) {

            String name = mElementList.get(position).getName();

            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);
            remoteViews.setTextViewText(R.id.widget_item_name, name);

            return remoteViews;
        }

        @Override public void onCreate() {
        }
        @Override public void onDestroy() {}
        @Override public int getCount() {
            return mElementList != null ? mElementList.size() : 0;
        }
        @Override public RemoteViews getLoadingView() {
            return null;
        }
        @Override public int getViewTypeCount() {
            return 1;
        }
        @Override public long getItemId(int position) {
            return position;
        }
        @Override public boolean hasStableIds() {
            return false;
        }
    }

    public static void refresh(Context context) {
        AppWidgetManager awm = AppWidgetManager.getInstance(context);
        ComponentName awc = new ComponentName(context, AppWidget.class);

        int[] ids = awm.getAppWidgetIds(awc);

        awm.notifyAppWidgetViewDataChanged(ids, R.id.widget_list);
        for (int id : ids) AppWidget.updateAppWidget(context, awm, id);
    }
}