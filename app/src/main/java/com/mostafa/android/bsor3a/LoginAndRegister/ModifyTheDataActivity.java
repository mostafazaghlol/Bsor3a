package com.mostafa.android.bsor3a.LoginAndRegister;

import android.app.AlertDialog;
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

public class ModifyTheDataActivity extends AppCompatActivity {
    @BindView(R.id.EditFullName)
    EditText EdFullName;
    @BindView(R.id.EditNumber)
    EditText EdNumber;
    @BindView(R.id.EditEmail)
    EditText EdEmail;
    @BindView(R.id.EditShortName)
    EditText EdShortName;
    @BindView(R.id.EditPassword)
    EditText EdPassword;
    @BindView(R.id.EditStateid)
    EditText EdStateid;
    @BindView(R.id.EditStreetName)
    EditText EdStreetName;
    @BindView(R.id.Editbuilding_number)
    EditText EdBuildingNumber;
    @BindView(R.id.Editflower_number)
    EditText EdFlower_number;
    @BindView(R.id.modifyConfirm)
    Button BtConfirm;
    @BindView(R.id.modifyCancel)
    Button BtCancel;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_the_data);
        ButterKnife.bind(this);
        setBar.setStatusBarColored(this);
        BtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        BtConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
    }

    private void sendData() {
        initalize();
        if (FullName.isEmpty() && Number.isEmpty() && Email.isEmpty() && ShortName.isEmpty() && Password.isEmpty() && State_id.isEmpty() && StreetName.isEmpty() && BuildingNumber.isEmpty() && FlowerNumber.isEmpty()) {

        } else {
            prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            editor = prefs.edit();
            String clientID = prefs.getString("customer_id", null);
            modifyData(FullName, Number, Email, ShortName, Password, State_id, StreetName, BuildingNumber, FlowerNumber, clientID);
        }
    }

    private void modifyData(String fullName, String number, String email, String shortName, String password, String state_id, String streetName, String buildingNumber, String flowerNumber, String customer_id) {

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject respons = jsonArray.getJSONObject(i);
                        String message = respons.getString("message");
                        int messageID = respons.getInt("messageID");
                        if (messageID == 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ModifyTheDataActivity.this);
                            builder.setMessage(message)
                                    .setNegativeButton(getString(R.string.okay), null)
                                    .create()
                                    .show();
                        } else if (messageID == 1) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int k = 0; k < data.length(); k++) {
                                JSONObject object1 = data.getJSONObject(k);
                                Toast.makeText(ModifyTheDataActivity.this, "hi", Toast.LENGTH_SHORT).show();
                                String fullName = object1.getString("name");
                                String number = object1.getString("phone");
                                String email = object1.getString("email");
                                String shortName = object1.getString("nickname");
                                String password = object1.getString("password");
                                String state_id = object1.getString("state_id");
                                String streetName = object1.getString("street_name");
                                String buildingNumber = object1.getString("building_number");
                                String flowerNumber = object1.getString("flower_number");
                                String image = object1.getString("img");
                                editor.putString("nickname", shortName);
                                editor.putString("customer_email", email);
                                editor.putString("customer_phone", number);
                                editor.putString("customer_img", image);
                                editor.apply();
                            }
                            finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ModifyRequest modifyRequest = new ModifyRequest(String.valueOf(MainActivity.lang), BuildingNumber, StreetName, State_id, Password
                , Email, customer_id, Number, FullName, FlowerNumber, ShortName, listener);
        RequestQueue queue = Volley.newRequestQueue(ModifyTheDataActivity.this);
        queue.add(modifyRequest);
    }

    private String FullName, Number, Email, ShortName, Password, State_id, StreetName, BuildingNumber, FlowerNumber;

    private void initalize() {
        FullName = getTextFromEditText(EdFullName, R.string.enterName);
        Number = getTextFromEditText(EdNumber, R.string.enterPhone);
        Email = getTextFromEditText(EdEmail, R.string.enterEmail);
        ShortName = getTextFromEditText(EdShortName, R.string.enterShortName);
        Password = getTextFromEditText(EdPassword, R.string.enterPassword);
        State_id = getTextFromEditText(EdStateid, R.string.enterstateid);
        StreetName = getTextFromEditText(EdStreetName, R.string.enterstreetname);
        BuildingNumber = getTextFromEditText(EdBuildingNumber, R.string.enterbuildingnumber);
        FlowerNumber = getTextFromEditText(EdFlower_number, R.string.enterFlowerNumber);
    }

    public String getTextFromEditText(EditText editText, int id) {

        if (editText.getText().toString().isEmpty()) {
            editText.setError(getString(id));
            return "";
        } else {
            return editText.getText().toString();
        }
    }
    public void back(View view) {
        startActivity(new Intent(ModifyTheDataActivity.this, MainNavigationActivity.class));
        finish();
    }
    public void Signup(View view) {
        Intent openSignUp = new Intent(ModifyTheDataActivity.this, RegisterActivity.class);
        startActivity(openSignUp);
    }
}
