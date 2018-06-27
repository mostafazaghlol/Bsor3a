package com.mostafa.android.bsor3a;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TechincalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_techincal);
        setBar.setStatusBarColored(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TechincalActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
    }

    public void back(View view) {
        onBackPressed();
    }

    @SuppressLint("MissingPermission")
    public void call(View view) {
        int id = view.getId();
        if (id == R.id.firstNumber) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "000000000"));
            startActivity(intent);
        } else if (id == R.id.secondNumber) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "000000000"));
            startActivity(intent);
        }

    }

    public void email(View view) {
        TextView x = (TextView) view;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + x.getText().toString()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void website(View view) {
        String url = "http://www.bsor3a.com";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
