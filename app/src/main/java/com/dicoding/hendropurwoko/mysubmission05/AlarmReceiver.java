package com.dicoding.hendropurwoko.mysubmission05;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    private int NOTIF_ID_DAILY_MOVIE_REMINDER = 100;
    private int NOTIF_ID_RELEASE_TODAY_MOVIE_REMINDER = 101;

    private String EXTRA_TYPE = "type";
    private String DAILY_REMINDER_NOTIFICATION = "DailyReminderNotification";

    @Override
    public void onReceive(Context context, Intent intent) {
        //createNotification(context, "Times Up", "Sudah 5 Menit", "Alert");
        String type = intent.getStringExtra(EXTRA_TYPE);

        String title = "";
        String message = "";
        int notifId = 0;

        if (type.equalsIgnoreCase(DAILY_REMINDER_NOTIFICATION)) {
            // Daily Reminder
            title = "Movie App";
            message = "Movie App missing you...";
            notifId = NOTIF_ID_DAILY_MOVIE_REMINDER;
            showNotification(context, title, message);
        }
    }

    private void showNotification(Context context, String msg, String msgText) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

        android.support.v4.app.NotificationCompat.Builder notification  = new android.support.v4.app.NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setContentTitle(msg)
                .setContentText(msgText)
                .setLargeIcon(BitmapFactory
                        .decodeResource(context.getResources()
                                , R.drawable.ic_notifications_white))
                .setSmallIcon(R.drawable.ic_notifications_white)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(SettingActivity.NOTIF_ID, notification.build());
    }

    public void setDailyReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, DAILY_REMINDER_NOTIFICATION);

        // Notification set at 07:00
        String time = "07:00";
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID_DAILY_MOVIE_REMINDER, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context, "Set Daily Reminder At "+ time, Toast.LENGTH_SHORT).show();
    }

    public void unsetDailyReminder(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID_DAILY_MOVIE_REMINDER, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, "Unset Daily Reminder", Toast.LENGTH_SHORT).show();
    }

    public void setReleaseTodayReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, NOTIF_ID_RELEASE_TODAY_MOVIE_REMINDER,
                intent, 0);

        // Notification set at 08:00
        String time = "08:00";
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context, "Set Release Today Reminder At "+ time, Toast.LENGTH_SHORT).show();
    }

    public void unsetReleaseTodayReminder(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, NOTIF_ID_RELEASE_TODAY_MOVIE_REMINDER,
                intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, "Unset Release Today Reminder", Toast.LENGTH_SHORT).show();
    }
}