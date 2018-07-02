package com.mostafa.android.bsor3a.LoginAndRegister;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.mostafa.android.bsor3a.LoginAndRegister.Requests.LoginRequest;
import com.mostafa.android.bsor3a.MainActivity;
import com.mostafa.android.bsor3a.MainNavigationActivity;
import com.mostafa.android.bsor3a.R;
import com.mostafa.android.bsor3a.setBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.Login)
    Button loginButton;
    @BindView(R.id.Eduser)
    EditText Eduser;
    @BindView(R.id.Edpassword)
    EditText Edpassword;
    String user, pass, lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
            lang = String.valueOf(MainActivity.lang);
            setBar.setStatusBarColored(this);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!Eduser.getText().toString().isEmpty()) {
                        user = Eduser.getText().toString();
                    } else {
                        Eduser.setError("Enter user !");
                    }
                    if (!Edpassword.getText().toString().isEmpty()) {
                        pass = Edpassword.getText().toString();
                    } else {
                        Edpassword.setError("Enter password !");
                    }
                    login(user, pass, lang);
                }
            });

        }catch (Exception e){
            Log.e("LoginActivity",e.getMessage().toString());
        }
    }

    //login process {xuer --> username,xpass -->password ,xlan -->language}
    public void login(String xuser, String xpass, String xlan) {
        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject respons = jsonArray.getJSONObject(i);
                        String message = respons.getString("message");
                        int messsageid = respons.getInt("messageID");
                        if (messsageid == 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage(message)
                                    .setNegativeButton(getString(R.string.okay), null)
                                    .create()
                                    .show();
                        } else if (messsageid == 1) {
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginPatiRequest = new LoginRequest(xuser, xpass, xlan, responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginPatiRequest);

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
