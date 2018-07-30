package com.dicoding.hendropurwoko.mysubmission05.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dicoding.hendropurwoko.mysubmission05.activity.MainActivity;
import com.dicoding.hendropurwoko.mysubmission05.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReleaseTodayService extends IntentService {
    public static final String TAG = "ReleaseTodayService";
    String URL;

    /*public ReleaseTodayService(String name) {
        super(name);
    }*/

    public ReleaseTodayService(String name) {
        super("ReleaseTodayService");
    }

    public ReleaseTodayService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        URL = "https://api.themoviedb.org/3/movie/now_playing?api_key="+ MainActivity.API + "&language=en-US";
        new LoadTodayMovies().execute();
    }

    private class LoadTodayMovies extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            SyncHttpClient client = new SyncHttpClient();

            final int notifId = 212;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            final String date_today = simpleDateFormat.format(today);

            client.get(URL, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                    String result = new String(responseBody);
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        JSONArray jsonArray = jsonObj.getJSONArray("results");

                        for (int i = 0; i < jsonArray.length() ; i++) {
                            JSONObject data = jsonArray.getJSONObject(i);

                            if (date_today.equalsIgnoreCase(data.getString("release_date").toString().trim())) {

                                NotificationManager notificationManagerCompat = (NotificationManager)
                                        ReleaseTodayService.this.getSystemService(Context.NOTIFICATION_SERVICE);
                                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                                NotificationCompat.Builder builder = new NotificationCompat.Builder(ReleaseTodayService.this)
                                        .setSmallIcon(R.drawable.ic_notifications_white)
                                        .setContentTitle(data.getString("title").toString().trim())
                                        .setContentText("Hari ini " + data.getString("title").toString().trim() + " Release")
                                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                        .setSound(alarmSound);
                                notificationManagerCompat.notify(notifId, builder.build());
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("ERROR: ", statusCode +": "+ error.toString());
                }
            });
            return null;
        }
    }
}