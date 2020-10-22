package com.wh.mysitter;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class BaseApp extends Application {

    public static SharedPreferences spf_default;
    public static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        spf_default = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = getSharedPreferences("qr_scan_history",MODE_PRIVATE);
    }
}
