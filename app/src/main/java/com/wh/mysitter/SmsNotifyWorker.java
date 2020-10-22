package com.wh.mysitter;

import android.content.Context;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SmsNotifyWorker extends Worker {
    private String TAG = "WH_" + getClass().getSimpleName();

    public SmsNotifyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String url = "http://39.103.143.157/notify";
                String url_ = "http://39.103.143.157/task/add";
                String url1 = "http://192.168.1.198:8081/notify";
                OkHttpClient client = new OkHttpClient();
                HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url_)).newBuilder();
                urlBuilder.addQueryParameter("device", "NutPro3");
                urlBuilder.addQueryParameter("title", "MSMFrom:" + MainService.sms_number);
                urlBuilder.addQueryParameter("task", "MSM:" + MainService.sms_body);
                HttpUrl u = urlBuilder.build();
                Log.d(TAG, "run: "+u.toString());

                Request request = new Request.Builder()
                        .url(u)
                        .header("access_token", "asdfghjkl")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    Log.d(TAG, "onReceive: " + Objects.requireNonNull(response.body()).string());
                    response.close();
                } catch (IOException e) {
                    Log.d(TAG, "run: " + e.getMessage());
                    Log.d(TAG, "run: " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }.start();
        return Result.success();
    }
}

