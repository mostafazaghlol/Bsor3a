package com.mostafa.android.bsor3a.LoginAndRegister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mostafa.android.bsor3a.R;
import com.mostafa.android.bsor3a.setBar;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setBar.setStatusBarColored(this);
    }

    public void back(View view) {
    }
}
