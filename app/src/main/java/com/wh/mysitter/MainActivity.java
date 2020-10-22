package com.wh.mysitter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private String TAG = "WH_"+getClass().getSimpleName();
    private TextView tv_qr_result;
    private Pattern pattern;


    private static final int RequestCode_get_scan_result = 436;
    private static final int RequestCode_get_permission_camera = 144;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String isAddress = "^(https?://)?(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()!@:%_+.~#?&//=]*)";
        pattern = Pattern.compile(isAddress);

        FloatingActionButton fab_scan = findViewById(R.id.fab_scan);
        fab_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScan();
            }
        });
        fab_scan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("请输入本文")
                        .setMessage("测试")
                        .setPositiveButton("gen", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(MainActivity.this,QRCodeStageActivity.class);
                                intent.putExtra("qrcodestring","测试");
                                startActivity(intent);
                            }
                        }).create();
                alertDialog.show();
                return true;
            }
        });
        tv_qr_result = findViewById(R.id.tv_qr_result);
        tv_qr_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String result = String.valueOf(tv_qr_result.getText());
                Matcher matcher = pattern.matcher(result);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("操作")
                        .setMessage(result)
                        .setPositiveButton("复制", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData mClipData = ClipData.newPlainText("scanResult", result);
                                clipboardManager.setPrimaryClip(mClipData);
                            }
                        })
                        .setNegativeButton("关闭",null);
                if(matcher.matches()){
                    builder.setNeutralButton("使用浏览器打开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
                            startActivity(intent);
                        }
                    });
                }

                builder.create().show();
            }
        });

        if (BaseApp.spf_default.getBoolean("settings_start_scan_on_start", false)) {
            startScan();
        }else {
            tv_qr_result.setText(BaseApp.sharedPreferences.getString("last_scan_history","have no history"));
        }


        startService(new Intent(this,MainService.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return true;
        }
    }

    private void startScan() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, RequestCode_get_permission_camera);
            }
        } else {
            Intent intent = new Intent(this, QRCodeScanActivity.class);
            intent.putExtra("forResult", "1");
            startActivityForResult(intent, RequestCode_get_scan_result);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RequestCode_get_permission_camera: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startScan();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RequestCode_get_scan_result: {
                if (data != null) {
                    String d = data.getStringExtra("result");
                    tv_qr_result.setText(d);

                    SharedPreferences.Editor editor = BaseApp.sharedPreferences.edit();
                    editor.putString("last_scan_history", d);
                    editor.apply();
                    tv_qr_result.callOnClick();
                }
            }
        }
    }
}