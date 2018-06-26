package com.mostafa.android.bsor3a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ShippingDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_details);
        setBar.setStatusBarColored(this);
    }

    public void back(View view) {
        onBackPressed();
    }
}
