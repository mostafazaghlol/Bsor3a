package com.mostafa.android.bsor3a.LoginAndRegister;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.mostafa.android.bsor3a.MainNavigationActivity;
import com.mostafa.android.bsor3a.R;
import com.mostafa.android.bsor3a.setBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.Login)
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
            setBar.setStatusBarColored(this);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openNavigationOffTheApp();
                    finish();
                }
            });

        }catch (Exception e){
            Log.e("LoginActivity",e.getMessage().toString());
        }
    }

    private void openNavigationOffTheApp() {
        Intent openNavigationIntent = new Intent(LoginActivity.this, MainNavigationActivity.class);
        startActivity(openNavigationIntent);
    }


    public void back(View view) {
        onBackPressed();
    }

    public void Signup(View view) {
        Intent openSignUp = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(openSignUp);
    }

    public void ResetPassword(View view) {
        Intent openSignUp = new Intent(LoginActivity.this, ReSetPasswordActivity.class);
        startActivity(openSignUp);
    }
}
