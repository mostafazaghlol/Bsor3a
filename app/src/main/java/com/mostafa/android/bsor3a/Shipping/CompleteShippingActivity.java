package com.mostafa.android.bsor3a.Shipping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.mostafa.android.bsor3a.CustomRequest;
import com.mostafa.android.bsor3a.LoginAndRegister.ConfirmationActivity;
import com.mostafa.android.bsor3a.MainActivity;
import com.mostafa.android.bsor3a.R;
import com.mostafa.android.bsor3a.setBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mostafa.android.bsor3a.MainActivity.MY_PREFS_NAME;

public class CompleteShippingActivity extends AppCompatActivity {
    @BindView(R.id.EditTextToShippingName)
    EditText EdToShippingName;
    @BindView(R.id.EditTextToGovernorate)
    EditText EdToGovernorate;
    @BindView(R.id.EditTextToDistrict)
    EditText EdToDiscrete;
    @BindView(R.id.EditTextToStreet)
    EditText EdToStreet;
    @BindView(R.id.EditTextToHouseNumber)
    EditText EdToHouseNumber;
    @BindView(R.id.EditTextToStorey)
    EditText EdToStorey;
    @BindView(R.id.EditTextToApartmentNumber)
    EditText EdToApartmentNumber;
    @BindView(R.id.ButtonToShipping)
    Button BtToShipping;
    String client_id, ShippingName, Governorate, Discrete, Street, HouseNumber, Storey, ApartmentNumber;
    ArrayList<String> dataArray = new ArrayList<>();
    ArrayList<String> ReceivedArray = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_shipping);
        setBar.setStatusBarColored(this);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        client_id = sharedPreferences.getString("customer_id", "");
        // ReceivedArray = getIntent().getStringArrayListExtra("DataArray");
        BtToShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intialize();
                if (validate()) {

                    dataArray.add(ShippingName);
                    dataArray.add(Governorate);
                    dataArray.add(Discrete);
                    dataArray.add(Street);
                    dataArray.add(HouseNumber);
                    dataArray.add(Storey);
                    dataArray.add(ApartmentNumber);
                    openPlacePicker();

                }
            }
        });

    }

    Intent intent;

    private void openPlacePicker() {
        PlacePicker.IntentBuilder bulider = new PlacePicker.IntentBuilder();
        try {
            intent = bulider.build(this);
            startActivityForResult(intent, 2);
        } catch (GooglePlayServicesRepairableException e) {
            Log.e("error", e.getMessage().toString());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } finally {

        }
    }

    String placeHolder, lat, lng;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                placeHolder = place.getAddress().toString();
                lat = String.valueOf(place.getLatLng().latitude);
                lng = String.valueOf(place.getLatLng().longitude);
//                dataArray.add(placeHolder);
//                dataArray.add(lat);
//                dataArray.add(lng);
//                dataArray.add(client_id);
//                dataArray.add(getDistance(new LatLng(Double.parseDouble(lat),Double.parseDouble(lng)),new LatLng(Double.parseDouble(ReceivedArray.get(8)),Double.parseDouble(ReceivedArray.get(9)))));
                sendData();
            }
        }
    }

    private String getDistance(LatLng latLng, LatLng latLng1) {
        Location startPoint = new Location("locationA");
        startPoint.setLatitude(latLng.latitude);
        startPoint.setLongitude(latLng.longitude);

        Location endPoint = new Location("locationA");
        endPoint.setLatitude(latLng1.latitude);
        endPoint.setLongitude(latLng1.longitude);

        double distance = startPoint.distanceTo(endPoint);
        double distanceInKm = distance / 1000;
        return String.valueOf(distanceInKm);
    }

    String message;

    private void sendData() {
//        Response.Listener<String>  listener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.optJSONArray("data");
//                    for(int i = 0;i<jsonArray.length();i++){
//                        message = jsonArray.getJSONObject(i).getString("message");
//                    }
//                    Toast.makeText(CompleteShippingActivity.this, ""+message, Toast.LENGTH_SHORT).show();
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        };
//        ShippingRequest shippingRequest = new ShippingRequest(ReceivedArray,dataArray,listener);
//        ShippingRequest shippingRequest = new ShippingRequest(listener);
        RequestQueue requestQueue = Volley.newRequestQueue(CompleteShippingActivity.this);
        String URL = "https://bsor3a.com/clients/new_order";
        JSONObject jsonBody = new JSONObject();
        Map<String, String> params;
        params = new HashMap<>();
        params.put("lang", String.valueOf(MainActivity.lang));
        params.put("name", "hi");
        params.put("id_client", "21");
        params.put("map_address_client", "hi");
        params.put("client_lat", "hi");
//            params.put("client_lag", "hi");
//            params.put("map_address_reciver", "hi");
//            params.put("reciver_lat", "hi");
//            params.put("reciver_lag", "hi");
//            params.put("delivery_time", "hi");
//            params.put("space", "hi");
//            params.put("name_reciver", "hi");
//            params.put("phone_reciver", "0000000000");
//            params.put("address_reciver", "hi");
//            params.put("street_number_reciver", "hi");
//            params.put("flower_number_reciver", "hi");
//            params.put("building_number_reciver", "hi");
//            params.put("promo_code", "");
        try {
            jsonBody.put("lang", String.valueOf(MainActivity.lang));
            jsonBody.put("name", "hi");
            jsonBody.put("id_client", "21");
            jsonBody.put("map_address_client", "hi");
            jsonBody.put("client_lat", "hi");
            jsonBody.put("client_lag", "hi");
            jsonBody.put("map_address_reciver", "hi");
            jsonBody.put("reciver_lat", "hi");
            jsonBody.put("reciver_lag", "hi");
            jsonBody.put("delivery_time", "hi");
            jsonBody.put("space", "hi");
            jsonBody.put("name_reciver", "hi");
            jsonBody.put("phone_reciver", "0000000000");
            jsonBody.put("address_reciver", "hi");
            jsonBody.put("street_number_reciver", "hi");
            jsonBody.put("flower_number_reciver", "hi");
            jsonBody.put("building_number_reciver", "hi");
            jsonBody.put("promo_code", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URL, params, this.createRequestSuccessListener(), null);
        requestQueue.add(jsObjRequest);
//        requestQueue.add(shippingRequest);

    }

    private Response.Listener<JSONObject> createRequestSuccessListener() {
        final Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        message = jsonArray.getJSONObject(i).getString("message");
                    }
                    Toast.makeText(CompleteShippingActivity.this, "" + message, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        return listener;

    }

    private boolean validate() {
        if (ShippingName.isEmpty() || Governorate.isEmpty() || Discrete.isEmpty() || Street.isEmpty() || HouseNumber.isEmpty() || Storey.isEmpty() || ApartmentNumber.isEmpty()) {
            return false;
        }
        return true;
    }

    private void intialize() {
        ShippingName = getTextFromEditText(EdToShippingName, R.string.enterShipping);
        Governorate = getTextFromEditText(EdToGovernorate, R.string.enterGovernorate);
        Discrete = getTextFromEditText(EdToDiscrete, R.string.enterDiscrete);
        Street = getTextFromEditText(EdToStreet, R.string.enterstreetname);
        HouseNumber = getTextFromEditText(EdToHouseNumber, R.string.enterHouseNumber);
        Storey = getTextFromEditText(EdToStorey, R.string.enterStorey);
        ApartmentNumber = getTextFromEditText(EdToApartmentNumber, R.string.enterApartmentNumber);
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
