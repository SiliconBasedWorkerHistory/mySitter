package com.wh.mysitter;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.wh.mysitter.myXXX.XXXApiConfig;

public class BaseApp extends Application {

    public static SharedPreferences spf_default;
    public static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        spf_default = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = getSharedPreferences("qr_scan_history",MODE_PRIVATE);
        XXXApiConfig.AccessToken = "asdfghjkl";
        XXXApiConfig.BaseUrl = "http://39.103.143.157";
        XXXApiConfig.DeviceName = "cancro";
    }
}
