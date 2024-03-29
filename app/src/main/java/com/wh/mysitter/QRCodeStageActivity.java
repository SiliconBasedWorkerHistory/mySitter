package com.wh.mysitter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wh.mysitter.utils.QRCodeGenerator;


public class QRCodeStageActivity extends AppCompatActivity {
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_stage);

        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        final String qrstring = intent.getStringExtra("qrcodestring");

        if (qrstring != null) {
            textView.setText(qrstring);
//            imageView.setImageResource(R.drawable.ic_launcher_background);
            imageView.setImageBitmap(new QRCodeGenerator(qrstring, 300, 300).getQRCode());
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("qrcodestring", qrstring);
                clipboardManager.setPrimaryClip(mClipData);
                Toast.makeText(QRCodeStageActivity.this, "copied", Toast.LENGTH_LONG).show();
            }
        });
    }
}
