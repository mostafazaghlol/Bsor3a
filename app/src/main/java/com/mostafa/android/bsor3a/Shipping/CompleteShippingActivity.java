package com.mostafa.android.bsor3a.Shipping;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.mostafa.android.bsor3a.Connection.AddDieRequest;
import com.mostafa.android.bsor3a.LoginAndRegister.RegisterActivity;
import com.mostafa.android.bsor3a.MainActivity;
import com.mostafa.android.bsor3a.MainNavigationActivity;
import com.mostafa.android.bsor3a.MapsActivity;
import com.mostafa.android.bsor3a.PerviousShipmentsActivity;
import com.mostafa.android.bsor3a.R;
import com.mostafa.android.bsor3a.SpinnerSenderData.CityAdapter;
import com.mostafa.android.bsor3a.SpinnerSenderData.CityItem;
import com.mostafa.android.bsor3a.SpinnerSenderData.GovernatItem;
import com.mostafa.android.bsor3a.SpinnerSenderData.GovernteAdapter;
import com.mostafa.android.bsor3a.setBar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mostafa.android.bsor3a.MainActivity.MY_PREFS_NAME;

public class CompleteShippingActivity extends Activity {
    @BindView(R.id.EditTextToShippingName)
    EditText EdToShippingName;
    @BindView(R.id.EditTextToGovernorate)
    Spinner EdToGovernorate;
    @BindView(R.id.EditTextToPhone)
    EditText EdToPhone;
    @BindView(R.id.EditTextToDistrict)
    Spinner EdToDiscrete;
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
    @BindView(R.id.scrollView1)
    ScrollView scrollView;
    @BindView(R.id.prog)
    ProgressBar progressBar;
    String Id;
    @BindView(R.id.typefaceText1)
    TextView textViewTypedFace;
    Typeface face;

    String urluser = "https://bsor3a.com/clients/new_order", result;
    String client_id, ShippingName, Governorate = "", Discrete = "", Street, HouseNumber, Storey, ApartmentNumber;
    ArrayList<String> dataArray = new ArrayList<>();
    ArrayList<String> Sender = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private ArrayList<GovernatItem> mCountryList;
    private GovernteAdapter mAdapter;
    private ArrayList<CityItem> mCityList;
    private CityAdapter mCityAdapter;
    boolean isTouched = false;
    String street_name, building_number, flower_number, flat_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_shipping);
        // setBar.setStatusBarColored(this);
        ButterKnife.bind(this);
        try {
            inialize();
            getcity();
            sharedPreferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            client_id = sharedPreferences.getString("customer_id", "");
            Sender = getIntent().getStringArrayListExtra("DataArray");

            // custom_font = Typeface.createFromAsset(getAssets(), "font.otf");
            face = Typeface.createFromAsset(getAssets(), "font.otf");
            textViewTypedFace.setTypeface(face);
            name = Sender.get(0);
            street_name = Sender.get(3);
            building_number = Sender.get(4);
            flower_number = Sender.get(5);
            flat_number = Sender.get(6);
            map_address_client = Sender.get(2);
            clientlat = Sender.get(8);
            clientlag = Sender.get(9);
            delivery_time = Sender.get(10);
            Promo = Sender.get(11);
            //Adapters of Spinners
            mAdapter = new GovernteAdapter(this, mCountryList);
            mCityAdapter = new CityAdapter(this, mCityList);
            EdToGovernorate.setAdapter(mAdapter);
            EdToDiscrete.setAdapter(mCityAdapter);

            EdToGovernorate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {

                    } else {
                        GovernatItem clickedItem = (GovernatItem) parent.getItemAtPosition(position);
                        String clickedCountryName = clickedItem.getmGovernatName();
                        Governorate = clickedCountryName;
                        Log.d("hhhhh", Governorate);
                        Id = clickedItem.getId();
                        isTouched = true;
                        getcity(Id);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            EdToDiscrete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {

                    } else {
                        CityItem clickedItem = (CityItem) adapterView.getItemAtPosition(i);
                        String clickedCountryName = clickedItem.getmGovernatName();
                        Discrete = clickedCountryName;
                        Log.d("hhhhh", Discrete);
                        String Id = clickedItem.getId();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


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
                        street_number_reciver = dataArray.get(3);
                        flower_number_reciver = dataArray.get(6);
                        building_number_reciver = dataArray.get(4);
                        id_client = dataArray.get(7);
                        Toast.makeText(CompleteShippingActivity.this, getString(R.string.Location2), Toast.LENGTH_LONG).show();
                        openPlacePicker();

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openPlacePicker2() {
        Intent intent = new Intent(CompleteShippingActivity.this, MapsActivity.class);
        startActivityForResult(intent, 100);
    }

    private void getcity(String id) {
        mCityList.clear();
        mCityList.add(new CityItem(getString(R.string.District), ""));
        scrollView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int messageId = object.getInt("messageID");
                    if (messageId == 1) {
                        JSONArray array = object.getJSONArray("main_data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object1 = array.getJSONObject(i);
                            String state_name = object1.getString("city name");
                            String id = object1.getString("id_city");
                            mCityList.add(new CityItem(state_name, id));
                            progressBar.setVisibility(View.INVISIBLE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    } else if (messageId == 0) {
                        Toast.makeText(CompleteShippingActivity.this, "Sorry,there is no any city", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        scrollView.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ;
                }
            }
        };

        getcity getstates = new getcity(id, listener);
        RequestQueue queue = Volley.newRequestQueue(CompleteShippingActivity.this);
        queue.add(getstates);
    }

    private void getcity() {
        mCityList = new ArrayList<>();
        mCityList.add(new CityItem(getString(R.string.District), ""));
    }

    private void inialize() {
        mCountryList = new ArrayList<>();
        mCountryList.add(new GovernatItem(getString(R.string.Governorate), 0));
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int messageId = object.getInt("messageID");
                    if (messageId == 1) {
                        JSONArray array = object.getJSONArray("main_data");
                        // mCountryList.clear();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object1 = array.getJSONObject(i);
                            String state_name = object1.getString("state name");
                            String id = object1.getString("id_state");
                            mCountryList.add(new GovernatItem(state_name, id));
                            progressBar.setVisibility(View.INVISIBLE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    } else if (messageId == 0) {
                        Toast.makeText(CompleteShippingActivity.this, "Sorry,there is no any Country", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        scrollView.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ;
                }
            }
        };

        getstates getstates = new getstates(listener);
        RequestQueue queue = Volley.newRequestQueue(CompleteShippingActivity.this);
        queue.add(getstates);

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
                map_address_reciver = dataArray.get(2);
                reciver_lat = dataArray.get(9);
                reciver_lag = dataArray.get(10);
                address_reciver = map_address_reciver;


                space = dataArray.get(11);

                sendData();
            }
        } else if (requestCode == 100) {
            try {
                reciver_lat = String.valueOf(data.getDoubleExtra("lat", 0.0));
                reciver_lag = String.valueOf(data.getDoubleExtra("lag", 0.0));
                dataArray.add("555");
                dataArray.add(reciver_lat);
                dataArray.add(reciver_lag);
                dataArray.add(getDistance(new LatLng(Double.parseDouble(reciver_lat), Double.parseDouble(reciver_lag)), new LatLng(Double.parseDouble(Sender.get(8)), Double.parseDouble(Sender.get(9)))));
                address_reciver = "iklk";
                Log.e("Complete", reciver_lat);
                Log.e("Complete", reciver_lag);

            } catch (Exception e) {
                Log.e("request 100", e.getMessage().toString());
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
        distanceInKm = Math.floor(distanceInKm) + 0.1;
        return String.valueOf(distanceInKm);
    }

    String message, name, id_client, map_address_client, clientlat, clientlag, map_address_reciver, reciver_lat, reciver_lag, delivery_time, space, name_receiver, phone_receiver, address_reciver, street_number_reciver, flower_number_reciver, building_number_reciver, Promo;

    /*
    setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
     */
    private void sendData() {
        try {

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
            Log.e("SendData", street_name);
            Log.e("SendData", building_number);
            Log.e("SendData", Id);
            Log.e("SendData", flower_number);
            Log.e("SendData", flat_number);
            Log.e("SendData", street_number_reciver);
            Log.e("SendData", flower_number_reciver);
            Log.e("SendData", building_number_reciver);
            processing();
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        progDailog.cancel();
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("message");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject respons = jsonArray.getJSONObject(i);
                            int messageID = respons.getInt("messageID");
                            if (messageID == 1) {
                                String message = respons.getString("message");
                                String price = "";
                                if (respons.has("price")) {
                                    price = respons.getString("price");
                                }
                                final String order_id = respons.getString("order_id");


                                AlertDialog.Builder builder = new AlertDialog.Builder(CompleteShippingActivity.this);
                                builder.setMessage(message + "\n" + getString(R.string.orderprice) + price)
                                        .setPositiveButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finishIt();
                                            }
                                        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DeleteRequest(order_id);
                                    }
                                })
                                        .create()
                                        .show();
                            } else {

                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CompleteShippingActivity.this, "Error with the server", Toast.LENGTH_LONG).show();
                    }
                }
            };

            AddDieRequest registerRequest = new AddDieRequest(String.valueOf(MainActivity.lang), name,
                    id_client,
                    map_address_client,
                    clientlat,
                    clientlag,
                    map_address_reciver,
                    reciver_lat,
                    reciver_lag,
                    delivery_time,
                    space,
                    name_receiver,
                    phone_receiver,
                    address_reciver,
                    street_number_reciver,
                    flower_number_reciver,
                    building_number_reciver,
                    Promo,
                    ApartmentNumber,
                    Id,
                    street_name,
                    building_number,
                    flower_number, responseListener);
            RequestQueue queue = Volley.newRequestQueue(CompleteShippingActivity.this);
            queue.add(registerRequest);
        } catch (Exception e) {
            Log.e("CompleteShipping", e.getMessage().toString());
        }
    }


    private void finishIt() {


        final AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle(getString(R.string.wewill)).setMessage(getString(R.string.messageAnt5a));
        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                startActivity(new Intent(CompleteShippingActivity.this, MainNavigationActivity.class));
                finish();
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();

// Hide after some seconds
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        };

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 10000);


    }

    private void exitLauncher() {
        finish();
    }


    private void DeleteRequest(final String order_id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CompleteShippingActivity.this);
        builder.setTitle(getString(R.string.sureWord)).setMessage(getString(R.string.sure)).setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressBar.setVisibility(View.VISIBLE);
                delete(order_id);
            }
        }).setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).create().show();

    }


    private void delete(String order_id) {
        try {
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray message = jsonObject.getJSONArray("message");
                        String message1 = message.getJSONObject(0).getString("message");
                        AlertDialog.Builder builder = new AlertDialog.Builder(CompleteShippingActivity.this);
                        builder.setTitle(message1).setMessage(getString(R.string.Would)).setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(CompleteShippingActivity.this, ShippingDetails.class));
                                finish();
                            }
                        }).setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }).create().show();
                        progressBar.setVisibility(View.INVISIBLE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            deleteRequest mdDeleteRequest = new deleteRequest(order_id, listener);
            RequestQueue requestQueue = Volley.newRequestQueue(CompleteShippingActivity.this);
            requestQueue.add(mdDeleteRequest);
        } catch (Exception e) {
            Log.e("Error", e.getMessage().toString());
        }
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
        if (Governorate.isEmpty()) {
            Toast.makeText(this, getString(R.string.enterGovernorate), Toast.LENGTH_SHORT).show();

            return false;
        }
        if (Discrete.isEmpty()) {
            Toast.makeText(this, getString(R.string.enterDiscrete), Toast.LENGTH_SHORT).show();

            return false;
        }
        if (ShippingName.isEmpty() || Governorate.isEmpty() || Discrete.isEmpty() || Street.isEmpty() || HouseNumber.isEmpty() || Storey.isEmpty() || ApartmentNumber.isEmpty() || phone_receiver.isEmpty()) {
            return false;
        }
        return true;
    }

    private void intialize() {
        ShippingName = getTextFromEditText(EdToShippingName, R.string.enterShipping);
        Street = getTextFromEditText(EdToStreet, R.string.enterstreetname);
        HouseNumber = getTextFromEditText(EdToHouseNumber, R.string.enterHouseNumber);
        Storey = getTextFromEditText(EdToStorey, R.string.enterStorey);
        ApartmentNumber = getTextFromEditText(EdToApartmentNumber, R.string.enterApartmentNumber);
        phone_receiver = getTextFromEditText(EdToPhone, R.string.enterPhone);
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


    public class getstates extends StringRequest {
        private final static String url = "https://bsor3a.com/clients/get_state";
        public Map<String, String> params;

        public getstates(Response.Listener<String> listener) {
            super(Method.POST, url, listener, null);
            params = new HashMap<>();
            params.put("lang", String.valueOf(MainActivity.lang));

        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }

    public class getcity extends StringRequest {
        private final static String url = "https://bsor3a.com/clients/get_city";
        public Map<String, String> params;

        public getcity(String id_state, Response.Listener<String> listener) {
            super(Method.POST, url, listener, null);
            params = new HashMap<>();
            params.put("lang", String.valueOf(MainActivity.lang));
            params.put("id_state", id_state);

        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }

    public class deleteRequest extends StringRequest {
        private static final String url = "https://bsor3a.com/Clients/cancel_order";
        public Map<String, String> params;

        public deleteRequest(String order, Response.Listener listener) {
            super(Method.POST, url, listener, null);
            params = new HashMap<>();
            params.put("id_order", order);
            params.put("lang", String.valueOf(MainActivity.lang));
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }
}

