package com.mostafa.android.bsor3a.Shipping;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.mostafa.android.bsor3a.Connection.AddDieRequest;
import com.mostafa.android.bsor3a.MainActivity;
import com.mostafa.android.bsor3a.R;
import com.mostafa.android.bsor3a.setBar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    String urluser = "https://bsor3a.com/clients/new_order", result;
    String client_id, ShippingName, Governorate, Discrete, Street, HouseNumber, Storey, ApartmentNumber;
    ArrayList<String> dataArray = new ArrayList<>();
    ArrayList<String> Sender = new ArrayList<>();
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
        Sender = getIntent().getStringArrayListExtra("DataArray");
        name = Sender.get(0);
        map_address_client = Sender.get(7);
        clientlat = Sender.get(8);
        clientlag = Sender.get(9);
        delivery_time = Sender.get(10);

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
                    dataArray.add(client_id);
                    name_receiver = dataArray.get(0);
                    phone_receiver = "000000000";
                    street_number_reciver = dataArray.get(3);
                    flower_number_reciver = dataArray.get(6);
                    building_number_reciver = dataArray.get(4);
                    id_client = dataArray.get(7);
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
                dataArray.add(placeHolder);
                dataArray.add(lat);
                dataArray.add(lng);
                dataArray.add(getDistance(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)), new LatLng(Double.parseDouble(Sender.get(8)), Double.parseDouble(Sender.get(9)))));
                map_address_reciver = dataArray.get(8);
                reciver_lat = dataArray.get(9);
                reciver_lag = dataArray.get(10);
                address_reciver = map_address_reciver;


                space = dataArray.get(11);

                sendData();
            }
        }
    }

    private String getDistance(LatLng latLng, LatLng latLng1) {
        Location startPoint = new Location("locationA");
        startPoint.setLatitude(latLng.latitude);
        startPoint.setLongitude(latLng.longitude);

        Location endPoint = new Location("locationB");
        endPoint.setLatitude(latLng1.latitude);
        endPoint.setLongitude(latLng1.longitude);

        double distance = startPoint.distanceTo(endPoint);
        double distanceInKm = distance / 1000;
        if (distanceInKm < 1) {
            return "1";
        }
        return String.valueOf(distanceInKm);
    }

    String message, name, id_client, map_address_client, clientlat, clientlag, map_address_reciver, reciver_lat, reciver_lag, delivery_time, space, name_receiver, phone_receiver, address_reciver, street_number_reciver, flower_number_reciver, building_number_reciver;

    private void sendData() {
        Log.e("SendData", name);
        Log.e("SendData", id_client);
        Log.e("SendData", map_address_client);
        Log.e("SendData", clientlat);
        Log.e("SendData", clientlag);
        Log.e("SendData", map_address_reciver);
        Log.e("SendData", reciver_lat);
        Log.e("SendData", reciver_lag);
        Log.e("SendData", delivery_time);
        Log.e("SendData", space);
        Log.e("SendData", name_receiver);
        Log.e("SendData", phone_receiver);
        Log.e("SendData", address_reciver);
        Log.e("SendData", street_number_reciver);
        Log.e("SendData", flower_number_reciver);
        Log.e("SendData", building_number_reciver);
//        processing();
//        new GetDataRequest().execute(name, id_client, map_address_client, clientlat, clientlag, map_address_reciver, reciver_lat, reciver_lag, delivery_time,
//         space, name_receiver, phone_receiver, address_reciver, street_number_reciver, flower_number_reciver, building_number_reciver);
////
//    new GetDataRequest().execute(" "," "," "," "," "," ",
//                " "," "," "," "," "," " ," "," "," "," ");
//

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject respons = jsonArray.getJSONObject(i);
                        int messageID = respons.getInt("messageID");
                        String message = respons.getString("message");
                        String price = respons.getString("price");

                        AlertDialog.Builder builder = new AlertDialog.Builder(CompleteShippingActivity.this);
                        builder.setMessage(message + "\n" + "سعر الطلب: " + price)
                                .setNegativeButton("موافق", null)
                                .create()
                                .show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        AddDieRequest registerRequest = new AddDieRequest(String.valueOf(MainActivity.lang), name, id_client, map_address_client, clientlat, clientlag, map_address_reciver,
                reciver_lat, reciver_lag, delivery_time, space, name_receiver, phone_receiver, address_reciver,
                street_number_reciver, flower_number_reciver, building_number_reciver, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CompleteShippingActivity.this);
        queue.add(registerRequest);
    }

    ProgressDialog progDailog;

    public void processing() {
        progDailog = new ProgressDialog(CompleteShippingActivity.this);
        progDailog.setTitle(getString(R.string.UploadData));
        progDailog.setMessage(getString(R.string.pleasewait));
        progDailog.setProgress(0);
        progDailog.setMax(70);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (progress <= 70) {
                    try {
                        progDailog.setProgress(progress);
                        progress++;
                        Thread.sleep(1000);
                    } catch (Exception e) {

                    }
                }
                progDailog.dismiss();
            }
        });
        thread.start();
        progDailog.show();
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

    class GetDataRequest extends AsyncTask<String, Boolean, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            String name = strings[0];
            String id_client = strings[1];
            String map_address_client = strings[2];
            String clientlat = strings[3];
            String clientlag = strings[4];
            String map_address_reciver = strings[5];
            String reciver_lat = strings[6];
            String reciver_lag = strings[7];
            String delivery_time = strings[8];
            String space = strings[9];
            String name_receiver = strings[10];
            String phone_receiver = strings[11];
            String address_reciver = strings[12];
            String street_number_reciver = strings[13];
            String flower_number_reciver = strings[14];
            String building_number_reciver = strings[15];
            Log.d("Values", name);
            Log.d("Values", id_client);
            Log.d("Values", map_address_client);
            Log.d("Values", clientlat);
            Log.d("Values", clientlag);
            Log.d("Values", map_address_reciver);
            Log.d("Values", reciver_lat);
            Log.d("Values", reciver_lag);
            Log.d("Values", delivery_time);
            Log.d("Values", space);
            Log.d("Values", name_receiver);
            Log.d("Values", phone_receiver);
            Log.d("Values", address_reciver);
            Log.d("Values", street_number_reciver);
            Log.d("Values", flower_number_reciver);
            Log.d("Values", building_number_reciver);
            // if  you have  to send  data  to the databse
            ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
//pairs.add(new BasicNameValuePair("lang", String.valueOf(MainActivity.lang)));
//pairs.add(new BasicNameValuePair("name", name));
//pairs.add(new BasicNameValuePair("id_client", id_client));
//pairs.add(new BasicNameValuePair("map_address_client", map_address_client));
//            pairs.add(new BasicNameValuePair("client_lat", clientlat));
//           pairs.add(new BasicNameValuePair("client_lag", clientlag));
//            pairs.add(new BasicNameValuePair("map_address_reciver", map_address_reciver));
//            pairs.add(new BasicNameValuePair("reciver_lat", reciver_lat));
//            pairs.add(new BasicNameValuePair("reciver_lag", reciver_lag));
//            pairs.add(new BasicNameValuePair("delivery_time", delivery_time));
//            pairs.add(new BasicNameValuePair("space", space));
//            pairs.add(new BasicNameValuePair("name_reciver", name_receiver));
//            pairs.add(new BasicNameValuePair("phone_reciver", phone_receiver));
//            pairs.add(new BasicNameValuePair("address_reciver", address_reciver));
//            pairs.add(new BasicNameValuePair("street_number_reciver", street_number_reciver));
//            pairs.add(new BasicNameValuePair("flower_number_reciver", flower_number_reciver));
//            pairs.add(new BasicNameValuePair("building_number_reciver", "jjj"));

            pairs.add(new BasicNameValuePair("lang", String.valueOf(MainActivity.lang)));
            pairs.add(new BasicNameValuePair("name", name));
            pairs.add(new BasicNameValuePair("id_client", id_client));
            pairs.add(new BasicNameValuePair("map_address_client", map_address_client));
            pairs.add(new BasicNameValuePair("client_lat", clientlat));
            pairs.add(new BasicNameValuePair("client_lag", clientlag));
            pairs.add(new BasicNameValuePair("map_address_reciver", map_address_reciver));
            pairs.add(new BasicNameValuePair("reciver_lat", reciver_lat));
            pairs.add(new BasicNameValuePair("reciver_lag", reciver_lag));
            pairs.add(new BasicNameValuePair("delivery_time", "55"));
            pairs.add(new BasicNameValuePair("space", space));
            pairs.add(new BasicNameValuePair("name_reciver", "5"));
            pairs.add(new BasicNameValuePair("phone_reciver", "20"));
            pairs.add(new BasicNameValuePair("address_reciver", "5"));
            pairs.add(new BasicNameValuePair("street_number_reciver", "6"));
            pairs.add(new BasicNameValuePair("flower_number_reciver", "5"));
            pairs.add(new BasicNameValuePair("building_number_reciver", "5"));

            // pairs.add(new BasicNameValuePair("promo_code", "20"));

            //  pairs.add(new BasicNameValuePair(address_reciver));


            com.mostafa.android.bsor3a.JsonReader2 j = new com.mostafa.android.bsor3a.JsonReader2(urluser, pairs);
            result = j.sendRequest();

            try {
                JSONObject jsonObject = new JSONObject(result);
                int messageId;
                if (jsonObject.has("message")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        message = jsonobject.getString("message");
                        messageId = jsonobject.getInt("messageID");
                    }
                } else if (jsonObject.has("data")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        message = jsonobject.getString("message");
                        messageId = jsonobject.getInt("messageID");
                    }
                }
                Toast.makeText(CompleteShippingActivity.this, "" + message, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }


            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            progDailog.cancel();
            AlertDialog.Builder builder = new AlertDialog.Builder(CompleteShippingActivity.this);
            builder.setMessage(message)
                    .setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(CompleteShippingActivity.this, "onPostExecute", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create()
                    .show();

        }

    }

}
