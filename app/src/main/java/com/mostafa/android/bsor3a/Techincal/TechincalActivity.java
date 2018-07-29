package com.mostafa.android.bsor3a.Techincal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.mostafa.android.bsor3a.R;
import com.mostafa.android.bsor3a.setBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.mobiwise.materialintro.animation.MaterialIntroListener;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;

public class TechincalActivity extends AppCompatActivity {
    @BindView(com.mostafa.android.bsor3a.R.id.firstNumber)
    TextView TxFirstNumber;
    @BindView(R.id.secondNumber)
    TextView TxSecound;
    @BindView(R.id.Technical_email)
    TextView TxEmail;
    @BindView(R.id.WebSite)
    TextView TxWebSite;
    @BindView(R.id.socialMedia)
    LinearLayout linearLayout;
    String phone, email, hot_line, fax, face_account, googleplus, twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_techincal);
        setBar.setStatusBarColored(this);
        ButterKnife.bind(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TechincalActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
        setData();
        new MaterialIntroView.Builder(TechincalActivity.this).setTargetPadding(10)
                .enableDotAnimation(true)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.NORMAL)
                .setDelayMillis(0)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText(getString(R.string.canRecir7))
                .setShape(ShapeType.CIRCLE)
                .setTarget(TxFirstNumber)
                .setUsageId("intro_card3000").setListener(new MaterialIntroListener() {
            @Override
            public void onUserClicked(String s) {
                new MaterialIntroView.Builder(TechincalActivity.this).setTargetPadding(10)
                        .enableDotAnimation(true)
                        .enableIcon(false)
                        .setFocusGravity(FocusGravity.CENTER)
                        .setFocusType(Focus.NORMAL)
                        .setDelayMillis(0)
                        .enableFadeAnimation(true)
                        .performClick(false)
                        .setInfoText(getString(R.string.canRecir8))
                        .setShape(ShapeType.CIRCLE)
                        .setTarget(TxEmail)
                        .setUsageId("intro_card3001").setListener(new MaterialIntroListener() {
                    @Override
                    public void onUserClicked(String s) {
                        new MaterialIntroView.Builder(TechincalActivity.this).setTargetPadding(10)
                                .enableDotAnimation(true)
                                .enableIcon(false)
                                .setFocusGravity(FocusGravity.CENTER)
                                .setFocusType(Focus.NORMAL)
                                .setDelayMillis(0)
                                .enableFadeAnimation(true)
                                .performClick(false)
                                .setInfoText(getString(R.string.canRecir9))
                                .setShape(ShapeType.CIRCLE)
                                .setTarget(linearLayout)
                                .setUsageId("intro_card3002") //THIS SHOULD BE UNIQUE ID
                                .show();
                    }
                }) //THIS SHOULD BE UNIQUE ID
                        .show();
            }
        }) //THIS SHOULD BE UNIQUE ID
                .show();

    }

    private void setData() {
        Response.Listener<String> listener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        phone = ob.getString("phone");
                        email = ob.getString("email");
                        hot_line = ob.getString("hot_line");
                        fax = ob.getString("fax");
                        face_account = ob.getString("face_account");
                        googleplus = ob.getString("googleplus");
                        twitter = ob.getString("twitter");
                        TxFirstNumber.setText(phone);
                        TxSecound.setText(hot_line);
                        TxEmail.setText(email);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        TechincalRequest techincalRequest = new TechincalRequest(listener);
        RequestQueue requestQueue = Volley.newRequestQueue(TechincalActivity.this);
        requestQueue.add(techincalRequest);
    }

    public void back(View view) {
        onBackPressed();
    }

    @SuppressLint("MissingPermission")
    public void call(View view) {
        int id = view.getId();
        if (id == R.id.firstNumber) {
            TextView textView = (TextView) view;
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + textView.getText()));
            startActivity(intent);
        } else if (id == R.id.secondNumber) {
            TextView textView = (TextView) view;
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + textView.getText().toString().trim()));
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

    public void plus(View view) {
        openwebsite(googleplus);
    }

    public void facebook(View view) {
        openwebsite(face_account);
    }

    public void twitter(View view) {
        openwebsite(twitter);
    }

    private void openwebsite(String web) {
        if (web.isEmpty()) {
            String url = web;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } else {
            Toast.makeText(this, "No Web Site On The DataBase SOOOOOOOOOON Will be Add ", Toast.LENGTH_SHORT).show();
        }
    }

}
