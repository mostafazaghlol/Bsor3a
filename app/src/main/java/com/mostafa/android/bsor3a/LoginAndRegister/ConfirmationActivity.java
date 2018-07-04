package com.mostafa.android.bsor3a.LoginAndRegister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.mostafa.android.bsor3a.LoginAndRegister.Requests.ConfirmationRequest;
import com.mostafa.android.bsor3a.LoginAndRegister.Requests.LoginRequest;
import com.mostafa.android.bsor3a.LoginAndRegister.Requests.ModifyRequest;
import com.mostafa.android.bsor3a.MainActivity;
import com.mostafa.android.bsor3a.MainNavigationActivity;
import com.mostafa.android.bsor3a.R;
import com.mostafa.android.bsor3a.setBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mostafa.android.bsor3a.MainActivity.MY_PREFS_NAME;

public class ConfirmationActivity extends AppCompatActivity {
    @BindView(R.id.EditTextConfirmation)
    EditText EdConfirmation;
    @BindView(R.id.ConfirmRegisterNumber)
    Button BtConfirmation;
    String Confirmation, message;
    int messageId;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    String RecentCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        setBar.setStatusBarColored(this);
        ButterKnife.bind(this);
        RecentCode = getIntent().getStringExtra("code");
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        BtConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Confirmation = getTextFromEditText(EdConfirmation, R.string.enterconfirmation);
                if (Confirmation.equals(RecentCode)) {
                    EndActivation();
                } else {
                    Toast.makeText(ConfirmationActivity.this, "" + getString(R.string.errorcode), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void EndActivation() {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("message");
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject ob = jsonArray.getJSONObject(j);
                        message = ob.getString("message");
                        messageId = ob.getInt("messageID");
                    }
                    if (messageId == 1) {
                        JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                        String clientId = jsonObject1.getString("id_client");
                        String phone = jsonObject1.getString("phone");
                        editor.putString("customer_phone", phone);
                        editor.putString("customer_id", clientId);
                        editor.apply();
                        openMainNavigationActivity();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        ConfirmationRequest confirmationRequest = new ConfirmationRequest(Confirmation, String.valueOf(MainActivity.lang), listener);
        RequestQueue queue = Volley.newRequestQueue(ConfirmationActivity.this);
        queue.add(confirmationRequest);
    }

    private void openMainNavigationActivity() {
        startActivity(new Intent(ConfirmationActivity.this, MainNavigationActivity.class));
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    public String getTextFromEditText(EditText editText, int id) {

        if (editText.getText().toString().isEmpty()) {
            editText.setError(getString(id));
        } else {
            return editText.getText().toString();
        }
        return "";
    }

    public void back(View view) {
        onBackPressed();
    }
}
