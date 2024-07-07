package com.example.lu_acm_navigator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class ReminderReceiver extends BroadcastReceiver {
        @Override

        public void onReceive(Context context, Intent intent) {
            String contestName = intent.getStringExtra("Contest Name");

            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                vibrator.vibrate(1000);

            }

            Toast.makeText(context, "Reminder: " + contestName + " is starting soon!", Toast.LENGTH_LONG).show();
            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }

           Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
            if (ringtone != null) {
                ringtone.play();
                new Handler().postDelayed(() -> ringtone.stop(), 10000);
            }
        }


}
