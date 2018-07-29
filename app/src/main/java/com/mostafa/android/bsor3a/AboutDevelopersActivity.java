package com.mostafa.android.bsor3a;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AboutDevelopersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_developers);
    }

    public void call(View view) {
        if (view.getId() == R.id.textView3) {
            dialContactPhone("01010344525");
        } else if (view.getId() == R.id.textView4) {
            dialContactPhone("01148295140");

        } else if (view.getId() == R.id.textView5) {
            dialContactPhone("01201187477");

        }
    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    public void backicon(View view) {
        onBackPressed();
    }

    public void back(View view) {
        onBackPressed();
    }
}
