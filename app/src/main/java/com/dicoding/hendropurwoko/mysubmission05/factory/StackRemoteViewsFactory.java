package com.dicoding.hendropurwoko.mysubmission05.factory;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.dicoding.hendropurwoko.mysubmission05.widget.MovieAppWidget;
import com.dicoding.hendropurwoko.mysubmission05.R;
import com.dicoding.hendropurwoko.mysubmission05.database.MovieContract;
import com.dicoding.hendropurwoko.mysubmission05.database.MovieModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Bitmap> mWidgetItems = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;
    private Cursor favMovieCursor = null;

    public StackRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        // take fav movie from database
        final long identityToken = Binder.clearCallingIdentity();
        // using content resolver
        favMovieCursor = mContext.getContentResolver().query(
                MovieContract.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (favMovieCursor == null){
            return 0;
        } else {
            return favMovieCursor.getCount();
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        final MovieModel movie = getMovieCursorItem(position);

        Bitmap bmp = null;
        try {
            bmp = Glide.with(mContext)
                    .asBitmap() //Put asBitmap right after load
                    .load(movie.getPoster())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();

            Log.e("poster ", movie.getPoster());
        } catch (InterruptedException | ExecutionException e) {
            Log.e("WidgetLoadError", "error: " + e.toString());
        }

        remoteViews.setImageViewBitmap(R.id.imageViewWidget, bmp);

        Bundle extras = new Bundle();
        extras.putInt(MovieAppWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.imageViewWidget, fillInIntent);

        return remoteViews;
    }

    private MovieModel getMovieCursorItem(int position) {
        if (!favMovieCursor.moveToPosition(position)){
            throw new IllegalStateException("Invalid Position");
        }
        return new MovieModel(favMovieCursor);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
