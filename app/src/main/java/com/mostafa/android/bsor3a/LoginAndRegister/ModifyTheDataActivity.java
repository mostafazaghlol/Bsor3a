package com.mostafa.android.bsor3a.LoginAndRegister;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.mostafa.android.bsor3a.LoginAndRegister.Requests.GetDataRequest;
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

public class ModifyTheDataActivity extends Activity {
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
    @BindView(R.id.EditCityid)
    EditText Edcityname;
    @BindView(R.id.Editflatid)
    EditText Edflat;
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
        //setBar.setStatusBarColored(this);
        getTheData();
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

    private void getTheData() {
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = prefs.edit();
        String clientID = prefs.getString("customer_id", null);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject ob = new JSONObject(response);
                    JSONArray array = ob.getJSONArray("data");
                    JSONObject data = array.optJSONObject(0);
                    String name = data.getString("name");
                    String email = data.getString("email");
                    String state_id = data.getString("state_name");
                    String nickname = data.getString("nickname");
                    String streetname = data.getString("street_name");
                    String buildingnumber = data.getString("building_number");
                    String flowernumber = data.getString("flower_number");
                    String phone = data.getString("phone");
                    String cityname = data.getString("city_name");
                    String flat_number = data.getString("flat_number");
                    EdFullName.setText(name);
                    EdEmail.setText(email);
                    EdNumber.setText(phone);
                    EdShortName.setText(nickname);
                    EdStateid.setText(state_id);
                    EdStreetName.setText(streetname);
                    EdBuildingNumber.setText(buildingnumber);
                    EdFlower_number.setText(flowernumber);
                    EdBuildingNumber.setText(buildingnumber);
                    Edcityname.setText(cityname);
                    Edflat.setText(flat_number);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        GetDataRequest getDataRequest = new GetDataRequest(clientID, listener);
        RequestQueue requestQueue = Volley.newRequestQueue(ModifyTheDataActivity.this);
        requestQueue.add(getDataRequest);
    }

    private void sendData() {
        initalize();
        if (FullName.isEmpty() || Number.isEmpty() || Email.isEmpty() || ShortName.isEmpty() || Password.isEmpty() ||
                State_id.isEmpty() || StreetName.isEmpty() || BuildingNumber.isEmpty() || FlowerNumber.isEmpty()) {

        } else {
            prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            editor = prefs.edit();
            String clientID = prefs.getString("customer_id", null);
            modifyData(FullName, Number, Email, ShortName, Password, State_id, StreetName, BuildingNumber, FlowerNumber, clientID, City, flat_number);
        }
    }

    private void modifyData(String fullName, String number, String email, String shortName, String password, String state_id, String streetName, String buildingNumber, String flowerNumber, String customer_id, String city, String flat_number) {

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
                        if (messageID == 1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ModifyTheDataActivity.this);
                            builder.setMessage(message)
                                    .setNegativeButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                        }
                                    })
                                    .create()
                                    .show();
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int k = 0; k < data.length(); k++) {
                                JSONObject object1 = data.getJSONObject(k);
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
                , Email, customer_id, Number, FullName, FlowerNumber, ShortName, city, flat_number, listener);
        RequestQueue queue = Volley.newRequestQueue(ModifyTheDataActivity.this);
        queue.add(modifyRequest);
    }

    private String FullName, Number, Email, ShortName, Password, State_id, StreetName, BuildingNumber, FlowerNumber, City, flat_number;

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
        City = getTextFromEditText(Edcityname, R.string.enterGovernorate);
        flat_number = getTextFromEditText(Edflat, R.string.enterflatNumber);
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
