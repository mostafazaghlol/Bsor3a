package com.mostafa.android.bsor3a.LoginAndRegister;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mostafa.android.bsor3a.R;
import com.mostafa.android.bsor3a.setBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mostafa.android.bsor3a.MainActivity.lang;

public class ReSetPasswordActivity extends Activity {
    @BindView(R.id.ReseteditText)
    EditText Edreset;
    @BindView(R.id.ResetPassword)
    Button BTReset;

    String emailorphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_set_password);
        ButterKnife.bind(this);
        BTReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetPasswordMethod();
            }
        });
    }

    private void ResetPasswordMethod() {
        inalize();
        if (validate()) {
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("message");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.optJSONObject(i);
                            final String message = object.getString("message");
                            final Integer messageID = object.getInt("messageID");
                            final AlertDialog.Builder builder = new AlertDialog.Builder(ReSetPasswordActivity.this);
                            builder.setMessage(message)
                                    .setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (messageID == 1) {
                                                finish();
                                            } else if (messageID == 0) {

                                            }

                                        }
                                    })
                                    .create()
                                    .show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            ResetPassword resetPassword = new ResetPassword(emailorphone, listener);
            RequestQueue requestQueue = Volley.newRequestQueue(ReSetPasswordActivity.this);
            requestQueue.add(resetPassword);
        }
    }

    private boolean validate() {
        if (emailorphone.isEmpty()) {
            return false;
        }
        return true;
    }

    public String getTextFromEditText(EditText editText, int id) {

        if (editText.getText().toString().isEmpty()) {
            editText.setError(getString(id));
        } else {
            return editText.getText().toString();
        }
        return "";
    }

    private void inalize() {
        emailorphone = getTextFromEditText(Edreset, R.string.enterEmailOrPhone);
    }

    public void back(View view) {
        onBackPressed();
    }

    public class ResetPassword extends StringRequest {
        private final static String url = "https://bsor3a.com/Delivery/foget_password_api";
        public Map<String, String> params;

        public ResetPassword(String memail, Response.Listener<String> listener) {
            super(Method.POST, url, listener, null);
            params = new HashMap<>();
            params.put("email", memail);
            params.put("lang", String.valueOf(lang));
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }
}
