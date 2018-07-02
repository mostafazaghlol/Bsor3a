package com.mostafa.android.bsor3a;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplachActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);
        setBar.setStatusBarColored(this);
        if (isNetworkConnected()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intentHome = new Intent(SplachActivity.this, MainActivity.class);
                    startActivity(intentHome);
                    finish();
                }
            }, 2000);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //Uncomment the below code to Set the message and title from the strings.xml file
            //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

            //Setting message manually and performing action on button click
            builder.setMessage("No Internet connection !")
                    .setCancelable(false)
                    .setPositiveButton("again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            recreate();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("No Internet connection !");
            alert.show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
