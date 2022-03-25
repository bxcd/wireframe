package art.coded.wireframe.view

import android.app.ListActivity
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import art.coded.wireframe.R
import art.coded.wireframe.model.entity.Element
import art.coded.wireframe.model.local.ElementDao
import art.coded.wireframe.model.local.ElementRoomDatabase

private val LOG_TAG: String = AppWidget::class.java.simpleName

/**
 * Provides a home screen interface that displays user data and offers navigation shortcuts.
 */
class AppWidget : AppWidgetProvider() {

    /**
     * Updates all [AppWidget]s detected by [AppWidgetManager].
     */
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list)
        for (appWidgetId in appWidgetIds) updateAppWidget(context, appWidgetManager, appWidgetId)
    }

    override fun onEnabled(context: Context?) {
    }

    override fun onDisabled(context: Context?) {
    }

    /**
     * Generates a [RemoteViewsFactory] for populating remote collections.
     */
    class AppWidgetRemoteViewsService : RemoteViewsService() {
        override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
            return AppWidgetRemoteViewsFactory(getApplicationContext())
        }
    }

    /**
     * Builds [RemoteViews] for populating remote collections.
     */
    class AppWidgetRemoteViewsFactory internal constructor(private val context: Context) :
        RemoteViewsService.RemoteViewsFactory {
        private var mElementDao: ElementDao = ElementRoomDatabase.getInstance(context).elementDao()
        private var mElementList: List<Element>? = mElementDao.allUnwrapped

        /**
         * Triggered when remote collection adapter invokes notifyDataSetChanged; synchronous processing
         * does not disrupt application main thread.
         */
        override fun onDataSetChanged() {
            val token: Long = Binder.clearCallingIdentity()
            mElementList = mElementDao.allUnwrapped
            try {
                Thread.sleep(10000)
            } catch (e: InterruptedException) {
                throw RuntimeException("AppWidget is unable to refresh", e)
            }
            refresh(context)
            Binder.restoreCallingIdentity(token)
        }

        /**
         * Populates [RemoteViews] at each position of the remote collection.
         */
        override fun getViewAt(position: Int): RemoteViews {
            val name: String = mElementList!![position].name
            val remoteViews = RemoteViews(context.getPackageName(), R.layout.item_widget)
            remoteViews.setTextViewText(R.id.widget_item_name, name)
            return remoteViews
        }

        override fun onCreate() {}
        override fun onDestroy() {}
        override fun getCount() : Int { return if (mElementList != null) mElementList!!.size else 0 }
        override fun getLoadingView(): RemoteViews? { return null }
        override fun getViewTypeCount(): Int { return 1 }
        override fun getItemId(position: Int): Long { return position.toLong() }
        override fun hasStableIds(): Boolean { return false }
    }

    // TODO: Address refresh-notify call pattern
    companion object {

        /**
         * Retrieves and sets [PendingIntent] on [RemoteViews] of a single [AppWidget].
         */
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views = RemoteViews(context.getPackageName(), R.layout.app_widget)
            val populateIntent = Intent(context, AppWidgetRemoteViewsService::class.java)
            views.setRemoteAdapter(R.id.widget_list, populateIntent)
            val listIntent = Intent(context, ListActivity::class.java)
            val listPendingIntent: PendingIntent =
                PendingIntent.getActivity(context, 0, listIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setPendingIntentTemplate(R.id.widget_list, listPendingIntent)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun refresh(context: Context) {
            val awm: AppWidgetManager = AppWidgetManager.getInstance(context)
            val awc = ComponentName(context, AppWidget::class.java)
            val ids: IntArray = awm.getAppWidgetIds(awc)
            awm.notifyAppWidgetViewDataChanged(ids, R.id.widget_list)
            for (id in ids) updateAppWidget(context, awm, id)
        }
    }
}