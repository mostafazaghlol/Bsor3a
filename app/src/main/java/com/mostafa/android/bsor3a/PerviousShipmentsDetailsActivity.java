package com.mostafa.android.bsor3a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PerviousShipmentsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pervious_shipments_details);
        setBar.setStatusBarColored(this);
        if (MainActivity.lang == 1) {

        } else if (MainActivity.lang == 2) {

        }
    }

    public void back(View view) {
        onBackPressed();
    }
}
