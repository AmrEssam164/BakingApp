package com.amressam.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_view_widget);
        Intent listServiceIntent = new Intent(context, ListWidgetService.class);
        views.setRemoteAdapter(R.id.recieps_list_view, listServiceIntent);

        Intent ingredientsStepsIntent = new Intent(context,IngredientsStepsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,ingredientsStepsIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.recieps_list_view,pendingIntent);

        views.setEmptyView(R.id.recieps_list_view, R.id.widget_empty_view);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
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

