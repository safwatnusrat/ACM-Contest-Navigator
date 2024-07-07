package com.example.lu_acm_navigator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

public class PermissinUtils {
    @RequiresApi(api = Build.VERSION_CODES.S)
    public static void requestScheduleExactAlarmPermission(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }
}
