package com.mostafa.android.bsor3a;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.ArabicButton)
    Button ArabicButton;
    @BindView(R.id.EnglishButton)
    Button EnglishButton;
    private Locale myLocale;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("Language",null);
        if (restoredText != null) {
            if(restoredText.equals("Arabic")){
                Toast.makeText(this, getString(R.string.hi), Toast.LENGTH_SHORT).show();
            }else if(restoredText.equals("English")){
                Toast.makeText(this, getString(R.string.hi), Toast.LENGTH_SHORT).show();
            }
        }
        final SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        ArabicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("Language","Arabic");
                editor.apply();
                changeLang("ar");
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
        EnglishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("Language","English");
                editor.apply();
                changeLang("en");
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }
    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }
}
