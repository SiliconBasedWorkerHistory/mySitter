package com.wh.mysitter;

import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainService extends Service {
    private String TAG = "WH_"+getClass().getSimpleName();
    private SmsReceiver smsReceiver;
    public static String sms_number = "null";
    public static String sms_body = "null";

    private NotificationManager notificationManager;

    public MainService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");

        smsReceiver = new SmsReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.addAction("mySitter_service_exit");
        registerReceiver(smsReceiver, intentFilter);
        notificationManager = getNotificationManager();
        // 创建通知
        NotificationCompat.Builder frontActivityNotificationBuilder = genForegroundNotification();
        // 启动前台通知
        startForeground(1, frontActivityNotificationBuilder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver);
        }
    }

    NotificationCompat.Builder genForegroundNotification() {
        Intent intent_exit = new Intent();
        intent_exit.setAction("mySitter_service_exit");
        PendingIntent pendingIntent_exit = PendingIntent.getBroadcast(MainService.this, 0, intent_exit, 0);

        return new NotificationCompat.Builder(this, "mySitterService_Foreground")
                .setContentTitle("mySitter")
                .setSmallIcon(R.drawable.ic_baseline_camera_enhance_24)
                .addAction(R.drawable.ic_baseline_camera_enhance_24, "exit", pendingIntent_exit)
                .setWhen(System.currentTimeMillis());
    }

    NotificationManager getNotificationManager() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 创建通知渠道
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel_server_service_foreground = new NotificationChannel(
                    "mySitterService_Foreground",
                    "服务器前台活动",
                    NotificationManager.IMPORTANCE_MIN);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel_server_service_foreground);
            }
        }
        return notificationManager;
    }

    public class SmsReceiver extends BroadcastReceiver {
        private String TAG = "WH_" + getClass().getSimpleName();
        private Service service;

        public SmsReceiver(Service service) {
            this.service = service;
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction() + "";

            switch (action) {
                case "android.provider.Telephony.SMS_RECEIVED": {
                    //pdus短信单位pdu
                    //解析短信内容
                    Object[] pdus = (Object[]) Objects.requireNonNull(intent.getExtras()).get("pdus");
                    assert pdus != null;
                    for (Object pdu : pdus) {
                        //封装短信参数的对象
                        SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);
                        final String number = sms.getOriginatingAddress();
                        final String body = sms.getMessageBody();
                        MainService.sms_number=number;
                        MainService.sms_body = body;
                        Log.d(TAG, "onReceive: " + number);
                        Log.d(TAG, "onReceive: " + body);
                        //TODO run work
                        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest
                                .Builder(SmsNotifyWorker.class)
                                .addTag("sms_notify")
                                .build();
                        WorkManager.getInstance(MainService.this).enqueueUniqueWork("sms_notify_", ExistingWorkPolicy.APPEND,oneTimeWorkRequest);
                        break;
                    }
                }
                case "mySitter_service_exit": {
                    this.service.stopSelf();
                    break;
                }
            }
        }
    }
}
