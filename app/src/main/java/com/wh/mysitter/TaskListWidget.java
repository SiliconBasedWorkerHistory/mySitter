package com.wh.mysitter;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;


public class TaskListWidget extends AppWidgetProvider {
    private String TAG = "WH_"+getClass().getSimpleName();
    private static final String ACTION_REFRESH = "action_refresh";
    private static final String ACTION_SET_DONE = "action_set_done";
    private static final String ACTION_SHOW_DETAIL = "action_show_detail";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String ACTION = intent.getAction()+"";
        Log.d(TAG, "onReceive: "+ACTION);
        switch (ACTION){
            case ACTION_REFRESH:{
                // todo refresh task
                break;
            }
            case ACTION_SET_DONE:{
                // todo set done
                break;
            }
            case ACTION_SHOW_DETAIL:{
                // todo show detail dialog
                break;
            }
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.task_list_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            Intent intent = new Intent();
            intent.setAction(ACTION_REFRESH);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);

            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.task_list_widget);
            views.setOnClickPendingIntent(R.id.task_list_btn_refresh,pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId,views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

