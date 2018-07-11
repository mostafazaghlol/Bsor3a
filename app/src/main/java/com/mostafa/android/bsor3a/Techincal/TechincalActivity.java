package com.mostafa.android.bsor3a.Techincal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.mostafa.android.bsor3a.*;
import com.mostafa.android.bsor3a.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TechincalActivity extends AppCompatActivity {
    @BindView(com.mostafa.android.bsor3a.R.id.firstNumber)
    TextView TxFirstNumber;
    @BindView(R.id.secondNumber)
    TextView TxSecound;
    @BindView(R.id.Technical_email)
    TextView TxEmail;
    @BindView(R.id.WebSite)
    TextView TxWebSite;

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
                        String phone = ob.getString("phone");
                        String email = ob.getString("email");
                        String hot_line = ob.getString("hot_line");
                        String fax = ob.getString("fax");
                        String face_account = ob.getString("face_account");
                        String googleplus = ob.getString("googleplus");
                        String twitter = ob.getString("twitter");
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
}
