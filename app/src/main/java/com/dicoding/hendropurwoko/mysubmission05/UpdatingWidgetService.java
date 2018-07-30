package com.dicoding.hendropurwoko.mysubmission05;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class UpdatingWidgetService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        AppWidgetManager manager = AppWidgetManager.getInstance(this);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.movie_app_widget);
        ComponentName theWidget = new ComponentName(this, MovieAppWidget.class);
        jobFinished(params, false);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        return false;
    }
}
