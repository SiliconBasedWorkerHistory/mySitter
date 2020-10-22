package com.wh.mysitter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.wh.mysitter.widget.MyDialog;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecvShareTextActivity extends AppCompatActivity {
    private String TAG = "WH_" + getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recv_share_text);

        Intent intent = getIntent();
        String ACTION = intent.getAction() + "";
        String TEXT = "There must be something wrong while getting text!";
        switch (ACTION) {
            case Intent.ACTION_PROCESS_TEXT: {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    TEXT = intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT) + "";
                }
                break;
            }
            case Intent.ACTION_SEND: {
                TEXT = intent.getStringExtra(Intent.EXTRA_TEXT) + "";
                break;
            }
        }
        final String finalTEXT = TEXT;
        MyDialog myDialog = new MyDialog(
                new AlertDialog.Builder(this)
                        .setTitle("Send To myDeskClock")
                        .setMessage(TEXT)
                        .setPositiveButton("send as task", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String DEVICE = BaseApp.spf_default.getString("settings_device_name", "default device");
                                String TITLE = "FromMySitter";
                                String URL = BaseApp.spf_default.getString("settings_server_address", "http://127.0.0.1");

                                OkHttpClient client = new OkHttpClient();

                                Request request = new Request.Builder()
                                        .url(Objects.requireNonNull(URL) + "/task/add?device=" + DEVICE + "&title=" + TITLE + "&task=" + finalTEXT)
                                        .get()
                                        .build();
                                Call call = client.newCall(request);
                                call.enqueue(new Callback() {
                                    @Override
                                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                        Log.e(TAG, "onFailure: ");
                                    }

                                    @Override
                                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                        Log.d(TAG, "onResponse: " + response.code());
                                    }
                                });
                            }
                        })
                        .setNegativeButton("close", null)
        );
        myDialog.show(getSupportFragmentManager(), "recv_text_to_share");

    }
}