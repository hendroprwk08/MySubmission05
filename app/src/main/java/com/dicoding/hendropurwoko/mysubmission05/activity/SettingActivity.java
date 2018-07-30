package com.dicoding.hendropurwoko.mysubmission05.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.dicoding.hendropurwoko.mysubmission05.R;
import com.dicoding.hendropurwoko.mysubmission05.service.AlarmReceiver;

public class SettingActivity extends AppCompatActivity{

    Button btnNotif;
    Switch swDaily, swRelease;
    boolean daily, release_today;
    AlarmReceiver alarmReceiver = new AlarmReceiver();

    public static int NOTIF_ID = 33;

    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btnNotif = (Button) findViewById(R.id.bt_notif);
        swDaily = (Switch) findViewById(R.id.swDaily);
        swRelease = (Switch) findViewById(R.id.swRelease);

        //check switch
        //preference
        sharedPreferences = getSharedPreferences("MyMoviePreferences", Context.MODE_PRIVATE);
        daily = sharedPreferences.getBoolean("daily_reminder", false);
        release_today = sharedPreferences.getBoolean("release_date_reminder", false);

        if (daily) swDaily.setChecked(true);
        if (release_today) swRelease.setChecked(true);

        swDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (isChecked){
                    alarmReceiver.setDailyReminder(getApplicationContext());
                    editor.putBoolean("daily_reminder", true);
                }else{
                    alarmReceiver.unsetDailyReminder(getApplicationContext());
                    editor.putBoolean("daily_reminder", false);
                }

                editor.commit();
            }
        });


        swRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (isChecked){
                    alarmReceiver.setReleaseTodayReminder(getApplicationContext());
                    editor.putBoolean("release_date_reminder", true);
                }else{
                    alarmReceiver.unsetReleaseTodayReminder(getApplicationContext());
                    editor.putBoolean("release_date_reminder", false);
                }

                editor.commit();
            }
        });

        getSupportActionBar().setSubtitle("Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back arrow
    }

    public void showNotification(View view) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Init Intent
        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(this, 0, mainIntent, 0);

        NotificationCompat.Builder notification  = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(getResources().getString(R.string.notification_daily))
                .setLargeIcon(BitmapFactory
                        .decodeResource(getResources()
                                , R.drawable.ic_notifications_white))
                .setSmallIcon(R.drawable.ic_notifications_white)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIF_ID, notification.build());
    }
}
