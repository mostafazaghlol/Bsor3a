package com.mostafa.android.bsor3a.LoginAndRegister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mostafa.android.bsor3a.MainNavigationActivity;
import com.mostafa.android.bsor3a.R;
import com.mostafa.android.bsor3a.setBar;

public class ModifyTheDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_the_data);
        setBar.setStatusBarColored(this);
    }

    public void back(View view) {
        startActivity(new Intent(ModifyTheDataActivity.this, MainNavigationActivity.class));
        finish();
    }

    public void Signup(View view) {
        Intent openSignUp = new Intent(ModifyTheDataActivity.this, RegisterActivity.class);
        startActivity(openSignUp);
    }
}
