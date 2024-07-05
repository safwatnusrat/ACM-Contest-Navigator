package com.example.lu_acm_navigator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class ReminderReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String contestName=intent.getStringExtra("name");

//            MediaPlayer mp = MediaPlayer.create(context, R.raw.android_alarm);
//            mp.start();
              Toast.makeText(context, "Reminder: " + contestName+" is starting soon!", Toast.LENGTH_LONG).show();
        }


}
