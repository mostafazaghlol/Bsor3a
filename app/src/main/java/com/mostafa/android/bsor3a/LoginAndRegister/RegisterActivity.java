package com.mostafa.android.bsor3a.LoginAndRegister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mostafa.android.bsor3a.R;
import com.mostafa.android.bsor3a.setBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.Register)
    Button ButtonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setBar.setStatusBarColored(this);
        ButterKnife.bind(this);
        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openConfirmationPage = new Intent(RegisterActivity.this, ConfirmationActivity.class);
                startActivity(openConfirmationPage);
            }
        });
    }

    public void back(View view) {
        onBackPressed();
    }
}
