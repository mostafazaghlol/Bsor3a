package com.mostafa.android.bsor3a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PolicyActivity extends AppCompatActivity {
    @BindView(R.id.policyText)
    TextView textView;
    @BindView(R.id.pro)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        ButterKnife.bind(this);
        getpolicy();
    }

    private void getpolicy() {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String policy = jsonObject.getString("policy");
                    progressBar.setVisibility(View.INVISIBLE);
                    textView.setText(policy);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        getPolicy getPolicy = new getPolicy(listener, null);
        RequestQueue requestQueue = Volley.newRequestQueue(PolicyActivity.this);
        requestQueue.add(getPolicy);
    }

    public void back(View view) {
        onBackPressed();
    }

    public class getPolicy extends StringRequest {
        private final static String url = "https://bsor3a.com/clients/policy_api";
        private Map<String, String> params;

        public getPolicy(Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(Method.POST, url, listener, errorListener);
            params = new HashMap<>();

        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }

}
