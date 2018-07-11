package com.mostafa.android.bsor3a;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Location;
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
import com.mostafa.android.bsor3a.SpinnerSenderData.CityAdapter;
import com.mostafa.android.bsor3a.SpinnerSenderData.CityItem;
import com.mostafa.android.bsor3a.SpinnerSenderData.GovernatItem;
import com.mostafa.android.bsor3a.SpinnerSenderData.GovernteAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mostafa.android.bsor3a.MainActivity.MY_PREFS_NAME;


public class FullShippmentActivity extends AppCompatActivity {
    @BindView(R.id.EditTextShippingName)
    EditText EdShippingName;
    @BindView(R.id.EditTextGovernorate)
    Spinner EdGovernorate;
    @BindView(R.id.EditTextDistrict)
    Spinner EdDiscrete;
    @BindView(R.id.EditTextStreet)
    EditText EdStreet;
    @BindView(R.id.EditTextHouseNumber)
    EditText EdHouseNumber;
    @BindView(R.id.EditTextStorey)
    EditText EdStorey;
    @BindView(R.id.EditTextApartmentNumber)
    EditText EdApartmentNumber;
    @BindView(R.id.promo)
    EditText EdPromo;
    @BindView(R.id.ButtonRShipping)
    Button BtShipping;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    boolean isTouched = false;
    Calendar currentTime = Calendar.getInstance();
    @BindView(R.id.typefaceText)
    TextView textViewTypedFace;
    Typeface face;
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
    String Id;
    @BindView(R.id.typefaceText1)
    TextView textViewTypedFace2;
    @BindView(R.id.ButtonFinishShipping)
    Button finishButton;
    String ShippingName2, Governorate2 = "", Discrete2 = "", Street2, HouseNumber2, Storey2, ApartmentNumber2, promo = " ";
    String urluser = "https://bsor3a.com/clients/new_order", result;
    String client_id, ShippingName, Governorate = "", Discrete = "", Street, HouseNumber, Storey, ApartmentNumber;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String message, name, id_client, map_address_client, clientlat, clientlag, map_address_reciver, reciver_lat, reciver_lag, delivery_time, space, name_receiver, phone_receiver2, address_reciver, street_number_reciver, flower_number_reciver, building_number_reciver, Promo;
    String street_name, building_number, flower_number, flat_number;
    boolean isplacepickerFinished = false;
    Intent intent;
    String placeHolder, lat, lng;
    ProgressDialog progDailog;
    //Adapters of The Spinners
    private ArrayList<GovernatItem> mCountryList;
    private GovernteAdapter mAdapter, mAdapter2;
    private ArrayList<CityItem> mCityList1, mCityList2;
    private CityAdapter mCityAdapter, mCityAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_shippment);
        // setBar.setStatusBarColored(this);
        ButterKnife.bind(this);
        try {
            inialize();
            getcity();
            // custom_font = Typeface.createFromAsset(getAssets(), "font.otf");
            face = Typeface.createFromAsset(getAssets(), "font.otf");
            textViewTypedFace.setTypeface(face);
            textViewTypedFace2.setTypeface(face);
            //Adapters of the Spinners
            mAdapter = new GovernteAdapter(this, mCountryList);
            mAdapter2 = new GovernteAdapter(this, mCountryList);
            mCityAdapter = new CityAdapter(this, mCityList1);
            mCityAdapter2 = new CityAdapter(this, mCityList2);
            EdGovernorate.setAdapter(mAdapter);
            EdDiscrete.setAdapter(mCityAdapter);
            EdToGovernorate.setAdapter(mAdapter);
            EdToDiscrete.setAdapter(mCityAdapter2);
            finishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validate() && validate1())
                        sendData();
                    else
                        Toast.makeText(FullShippmentActivity.this, "Error Empty filed !", Toast.LENGTH_SHORT).show();
                }
            });
            EdGovernorate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {

                    } else {
                        GovernatItem clickedItem = (GovernatItem) parent.getItemAtPosition(position);
                        String clickedCountryName = clickedItem.getmGovernatName();
                        Governorate = clickedCountryName;
                        Log.d("hhhhh", Governorate);
                        String Id = clickedItem.getId();
                        isTouched = true;
                        getcity(Id);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            EdDiscrete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            BtShipping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        intialize();
                        if (validate1()) {
                            name = ShippingName;
                            street_name = Street;
                            building_number = HouseNumber;
                            flower_number = Storey;
                            flat_number = ApartmentNumber;
                            map_address_client = Governorate;
                            delivery_time = getTime();
                            Toast.makeText(FullShippmentActivity.this, getString(R.string.Location), Toast.LENGTH_LONG).show();
                            openPlacePicker();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            sharedPreferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            client_id = sharedPreferences.getString("customer_id", "");
            EdToGovernorate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {

                    } else {
                        GovernatItem clickedItem = (GovernatItem) parent.getItemAtPosition(position);
                        String clickedCountryName = clickedItem.getmGovernatName();
                        Governorate2 = clickedCountryName;
                        Log.d("hhhhh", Governorate);
                        Id = clickedItem.getId();
                        isTouched = true;
                        getcity2(Id);
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
                        Discrete2 = clickedCountryName;
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
                        name_receiver = ShippingName2;
                        street_number_reciver = Discrete2;
                        flower_number_reciver = Storey2;
                        building_number_reciver = HouseNumber2;
                        id_client = id_client;
                        Toast.makeText(FullShippmentActivity.this, getString(R.string.Location2), Toast.LENGTH_LONG).show();
                        openPlacePicker2();

                    }
                }
            });
        } catch (Exception e) {
            Log.e("Shipping", e.getMessage().toString());
        }
    }

    private boolean validate() {
        if (Governorate2.isEmpty()) {
            Toast.makeText(this, getString(R.string.enterGovernorate), Toast.LENGTH_SHORT).show();

            return false;
        }
        if (Discrete2.isEmpty()) {
            Toast.makeText(this, getString(R.string.enterDiscrete), Toast.LENGTH_SHORT).show();

            return false;
        }
        return !(ShippingName2.isEmpty() || Governorate2.isEmpty() || Discrete2.isEmpty()
                || Street2.isEmpty() || HouseNumber2.isEmpty() || Storey2.isEmpty() || ApartmentNumber.isEmpty() || phone_receiver2.isEmpty());
    }

    //gettting the cities
    private void getcity(String id) {
        mCityList1.clear();
        mCityList1.add(new CityItem(getString(R.string.District), ""));
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
                            mCityList1.add(new CityItem(state_name, id));
                            progressBar.setVisibility(View.INVISIBLE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    } else if (messageId == 0) {
                        Toast.makeText(FullShippmentActivity.this, "Sorry,there is no any city", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        scrollView.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        getcity getstates = new getcity(id, listener);
        RequestQueue queue = Volley.newRequestQueue(FullShippmentActivity.this);
        queue.add(getstates);
    }

    //inalize the cities
    private void getcity() {
        mCityList1 = new ArrayList<>();
        mCityList1.add(new CityItem(getString(R.string.District), ""));
        mCityList2 = new ArrayList<>();
        mCityList2.add(new CityItem(getString(R.string.District), ""));
    }

    //getting the countries
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
                        Toast.makeText(FullShippmentActivity.this, "Sorry,there is no any Country", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        scrollView.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        getstates getstates = new getstates(listener);
        RequestQueue queue = Volley.newRequestQueue(FullShippmentActivity.this);
        queue.add(getstates);

    }

    private String getTheDay(int dayOfweek) {
        String day;
        switch (dayOfweek) {
            case 1:
                day = "Sunday";
                break;
            case 2:
                day = "Monday";
                break;
            case 3:
                day = "TUESDAY";
                break;
            case 4:
                day = "WEDNESDAY";
                break;
            case 5:
                day = "THURSDAY";
                break;
            case 6:
                day = "FRIDAY";
                break;
            case 7:
                day = "SATURDAY";
                break;
            default:
                return "";
        }
        return day;
    }

    private String getTime() {
        Integer Year = currentTime.get(Calendar.YEAR);
        Integer month = currentTime.get(Calendar.MONTH);
        Integer dayOfweek = currentTime.get(Calendar.DAY_OF_WEEK);
        String day = getTheDay(dayOfweek);
        Integer hour = currentTime.get(Calendar.HOUR_OF_DAY);
        Integer min = currentTime.get(Calendar.MINUTE);
        Integer sec = currentTime.get(Calendar.SECOND);
        String fullTime = Year.toString() + " " + month.toString() + " " + day + " " + hour.toString() + ":" + min.toString() + ":" + sec.toString();
        return fullTime;
    }

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

    private void openPlacePicker2() {
        PlacePicker.IntentBuilder bulider = new PlacePicker.IntentBuilder();
        try {
            intent = bulider.build(this);
            startActivityForResult(intent, 3);
        } catch (GooglePlayServicesRepairableException e) {
            Log.e("error", e.getMessage().toString());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void getcity2(String id) {
        mCityList2.clear();
        mCityList2.add(new CityItem(getString(R.string.District), ""));
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
                            mCityList2.add(new CityItem(state_name, id));
                            progressBar.setVisibility(View.INVISIBLE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    } else if (messageId == 0) {
                        Toast.makeText(FullShippmentActivity.this, "Sorry,there is no any city", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        scrollView.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        getcity getstates = new getcity(id, listener);
        RequestQueue queue = Volley.newRequestQueue(FullShippmentActivity.this);
        queue.add(getstates);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                try {

                    Place place = PlacePicker.getPlace(this, data);
                    placeHolder = place.getAddress().toString();
                    lat = String.valueOf(place.getLatLng().latitude);
                    lng = String.valueOf(place.getLatLng().longitude);


                    clientlat = lat;
                    clientlag = lng;
                    if (promo.length() > 0) {
                        promo = promo;
                    } else {
                        promo = "";
                    }
                    Toast.makeText(this, "" + getString(R.string.getLocationofreceiverplz), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 3) {
            Place place = PlacePicker.getPlace(this, data);
            placeHolder = place.getAddress().toString();
            lat = String.valueOf(place.getLatLng().latitude);
            lng = String.valueOf(place.getLatLng().longitude);
            map_address_reciver = Governorate2;
            reciver_lat = lat;
            reciver_lag = lng;
            address_reciver = map_address_reciver;
            space = getDistance(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)), new LatLng(Double.parseDouble(clientlat), Double.parseDouble(clientlag)));
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

    private void sendData() {
        try {

            Log.e("SendData", name);
            Log.e("SendData", client_id);
            Log.e("SendData", map_address_client);
            Log.e("SendData", clientlat);
            Log.e("SendData", clientlag);
            Log.e("SendData", map_address_reciver);
            Log.e("SendData", reciver_lat);
            Log.e("SendData", reciver_lag);
            Log.e("SendData", delivery_time);
            Log.e("SendData", space);
            Log.e("SendData", name_receiver);
            Log.e("SendData", phone_receiver2);
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


                                AlertDialog.Builder builder = new AlertDialog.Builder(FullShippmentActivity.this);
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
                        Toast.makeText(FullShippmentActivity.this, "Error with the server", Toast.LENGTH_LONG).show();
                    }
                }
            };

            AddDieRequest registerRequest = new AddDieRequest(String.valueOf(MainActivity.lang), name,
                    client_id,
                    map_address_client,
                    clientlat,
                    clientlag,
                    map_address_reciver,
                    reciver_lat,
                    reciver_lag,
                    delivery_time,
                    space,
                    name_receiver,
                    phone_receiver2,
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
            RequestQueue queue = Volley.newRequestQueue(FullShippmentActivity.this);
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
                startActivity(new Intent(FullShippmentActivity.this, MainNavigationActivity.class));
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

        AlertDialog.Builder builder = new AlertDialog.Builder(FullShippmentActivity.this);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(FullShippmentActivity.this);
                        builder.setTitle(message1).setMessage(getString(R.string.Would)).setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(FullShippmentActivity.this, MainNavigationActivity.class));
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
            RequestQueue requestQueue = Volley.newRequestQueue(FullShippmentActivity.this);
            requestQueue.add(mdDeleteRequest);
        } catch (Exception e) {
            Log.e("Error", e.getMessage().toString());
        }
    }

    public void processing() {
        progDailog = new ProgressDialog(FullShippmentActivity.this);
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

    private boolean validate1() {
        if (Governorate.isEmpty()) {
            Toast.makeText(this, getString(R.string.enterGovernorate), Toast.LENGTH_SHORT).show();

            return false;
        }
        if (Discrete.isEmpty()) {
            Toast.makeText(this, getString(R.string.enterDiscrete), Toast.LENGTH_SHORT).show();

            return false;
        }
        if (ShippingName.isEmpty() || Street.isEmpty() || HouseNumber.isEmpty() || Storey.isEmpty() || ApartmentNumber.isEmpty()) {
            Log.d("hhhhh", "Error");
            return false;
        }
        return true;
    }

    private void intialize() {
        ShippingName = getTextFromEditText(EdShippingName, R.string.enterShipping);
        Street = getTextFromEditText(EdStreet, R.string.enterstreetname);
        HouseNumber = getTextFromEditText(EdHouseNumber, R.string.enterHouseNumber);
        Storey = getTextFromEditText(EdStorey, R.string.enterStorey);
        ApartmentNumber = getTextFromEditText(EdApartmentNumber, R.string.enterApartmentNumber);
        if (EdPromo.getText().length() > 0) {
            promo = getTextFromEditText(EdPromo, R.string.enter_Promo_code);
        }
        ShippingName2 = getTextFromEditText(EdToShippingName, R.string.enterShipping);
        Street2 = getTextFromEditText(EdToStreet, R.string.enterstreetname);
        HouseNumber2 = getTextFromEditText(EdToHouseNumber, R.string.enterHouseNumber);
        Storey2 = getTextFromEditText(EdToStorey, R.string.enterStorey);
        ApartmentNumber2 = getTextFromEditText(EdToApartmentNumber, R.string.enterApartmentNumber);
        phone_receiver2 = getTextFromEditText(EdToPhone, R.string.enterPhone);
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
}
