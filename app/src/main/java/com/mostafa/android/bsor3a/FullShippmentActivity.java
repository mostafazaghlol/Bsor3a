package com.mostafa.android.bsor3a;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.mostafa.android.bsor3a.Connection.confirm_sms;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;

import static com.mostafa.android.bsor3a.MainActivity.MY_PREFS_NAME;
import static com.mostafa.android.bsor3a.MainNavigationActivity.xFlatNmuber;
import static com.mostafa.android.bsor3a.MainNavigationActivity.xFlowerNumber;
import static com.mostafa.android.bsor3a.MainNavigationActivity.xGovernote;
import static com.mostafa.android.bsor3a.MainNavigationActivity.xbuilding_number;
import static com.mostafa.android.bsor3a.MainNavigationActivity.xcity_name;
import static com.mostafa.android.bsor3a.MainNavigationActivity.xstreet_name;


public class FullShippmentActivity extends AppCompatActivity {
    @BindView(R.id.EditTextShippingName)
    EditText EdShippingName;
    @BindView(R.id.EditTextGovernorate)
    EditText EdGovernorate;
    @BindView(R.id.EditTextDistrict)
    EditText EdDiscrete;
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

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    boolean isTouched = false;
    Calendar currentTime = Calendar.getInstance();
    @BindView(R.id.typefaceText)
    TextView textViewTypedFace;
    @BindView(R.id.typefaceText11)
    TextView textViewTypedFace11;
    Typeface face;
    @BindView(R.id.EditTextToShippingName)
    EditText EdToShippingName;
    @BindView(R.id.EditTextToGovernorate)
    EditText EdToGovernorate;
    @BindView(R.id.EditTextToPhone)
    EditText EdToPhone;
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

    @BindView(R.id.relative2)
    LinearLayout linearLayout;
    String Id = "0";
    @BindView(R.id.typefaceText1)
    TextView textViewTypedFace2;
    @BindView(R.id.ButtonFinishShipping)
    Button finishButton;
    String ShippingName2 = "", Governorate2 = "", Discrete2 = "", Street2 = "", HouseNumber2 = "", Storey2 = "", ApartmentNumber2 = "", promo = " ";
    String urluser = "https://bsor3a.com/Clients/new_order", result;
    String client_id = "", ShippingName = "", Governorate = "", Discrete = "", Street = "", HouseNumber = "", Storey = "", ApartmentNumber = "";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String message, order_id, price, name, id_client, map_address_client, clientlat, clientlag, map_address_reciver, reciver_lat, reciver_lag, delivery_time, space, name_receiver, phone_receiver2, address_reciver, street_number_reciver, flower_number_reciver, building_number_reciver, Promo;
    String street_name, building_number, flower_number, flat_number;
    boolean isplacepickerFinished = false;
    Intent intent;
    String placeHolder, lat1 = "", lng1 = "", lat2 = "", lng2 = "";
    ProgressDialog progDailog, progDailog2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_shippment);
        // setBar.setStatusBarColored(this);
        ButterKnife.bind(this);
        try {
            sharedPreferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            client_id = sharedPreferences.getString("customer_id", "");
            setData();
            face = Typeface.createFromAsset(getAssets(), "font.otf");
            textViewTypedFace.setTypeface(face);
            textViewTypedFace2.setTypeface(face);
            textViewTypedFace11.setTypeface(face);

            new MaterialIntroView.Builder(FullShippmentActivity.this).setTargetPadding(10)
                    .enableDotAnimation(true)
                    .enableIcon(false)
                    .setFocusGravity(FocusGravity.CENTER)
                    .setFocusType(Focus.NORMAL)
                    .setDelayMillis(500)
                    .enableFadeAnimation(true)
                    .performClick(false)
                    .setInfoText(getString(R.string.canRecir4))
                    .setShape(ShapeType.CIRCLE)
                    .setTarget(EdShippingName)
                    .setUsageId("intro_card9600") //THIS SHOULD BE UNIQUE ID
                    .show();
            finishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intialize();
                    if (validate1() && validate()) {
                        name = ShippingName;
                        street_name = Street;
                        building_number = HouseNumber;
                        flower_number = Storey;
                        flat_number = ApartmentNumber;
                        map_address_client = Governorate;
                        delivery_time = getTime();
                        name_receiver = ShippingName2;
                        street_number_reciver = Discrete2;
                        flower_number_reciver = Storey2;
                        building_number_reciver = HouseNumber2;
                        id_client = id_client;

                        startActivityForResult(new Intent(FullShippmentActivity.this, MapsActivity.class), 0);
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Shipping", e.getMessage().toString());
        }
    }


    private void setData() {
        Log.e("Governota", xGovernote);
        Log.e("xcity_name", xcity_name);
        Log.e("xstreet_name", xstreet_name);
        Log.e("xbuilding_number", xbuilding_number);
        Log.e("xFlowerNumber", xFlowerNumber);
        Log.e("xFlatNmuber", xFlatNmuber);
        EdGovernorate.setText(xGovernote);
        EdDiscrete.setText(xcity_name);
        EdStreet.setText(xstreet_name);
        EdHouseNumber.setText(xbuilding_number);
        EdStorey.setText(xFlowerNumber);
        EdApartmentNumber.setText(xFlatNmuber);
    }

    private boolean validate() {
        return !(ShippingName.isEmpty() || Street.isEmpty() || HouseNumber.isEmpty() || Storey.isEmpty() || ApartmentNumber.isEmpty() ||
                ShippingName2.isEmpty() || Governorate2.isEmpty() || Discrete2.isEmpty()
                || Street2.isEmpty() || HouseNumber2.isEmpty() || Storey2.isEmpty()
                || ApartmentNumber.isEmpty() || phone_receiver2.isEmpty() || phone_receiver2.length() != 11);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                try {

                    Place place = PlacePicker.getPlace(this, data);
                    placeHolder = place.getAddress().toString();
                    lat1 = String.valueOf(place.getLatLng().latitude);
                    lng1 = String.valueOf(place.getLatLng().longitude);


                    clientlat = lat1;
                    clientlag = lng1;
                    if (promo.length() > 0) {
                        promo = promo;
                    } else {
                        promo = "";
                    }
                    //Toast.makeText(this, "" + getString(R.string.getLocationofreceiverplz), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 3) {
            Place place = PlacePicker.getPlace(this, data);
            placeHolder = place.getAddress().toString();
            lat2 = String.valueOf(place.getLatLng().latitude);
            lng2 = String.valueOf(place.getLatLng().longitude);
            map_address_reciver = Governorate2;
            reciver_lat = lat2;
            reciver_lag = lng2;
            address_reciver = map_address_reciver;
            space = getDistance(new LatLng(Double.parseDouble(lat1), Double.parseDouble(lng1)), new LatLng(Double.parseDouble(clientlat), Double.parseDouble(clientlag)));
        } else if (requestCode == 0) {
            if (resultCode == 100) {
                try {
                    clientlat = data.getStringExtra("latClient");
                    clientlag = data.getStringExtra("lagClient");
                    reciver_lat = data.getStringExtra("latReciver");
                    reciver_lag = data.getStringExtra("lagReciver");
                    space = getDistance(new LatLng(Double.parseDouble(clientlat), Double.parseDouble(clientlag)),
                            new LatLng(Double.parseDouble(reciver_lat), Double.parseDouble(reciver_lag)));
                    map_address_client = Street;
                    map_address_reciver = Street2;
                    address_reciver = map_address_reciver;
                    if (promo.length() > 0) {
                        promo = promo;
                    } else {
                        promo = "";
                    }
                    //sendData();
                    onRegisterSuccess();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onRegisterSuccess() {
        processing();
        Log.e("SendDataName", name);
        Log.e("SendDataId", client_id);
        Log.e("SendDataAddress", map_address_client);
        Log.e("SendDataLat", clientlat);
        Log.e("SendDataLng", clientlag);
        Log.e("SendDataAddress", map_address_reciver);
        Log.e("SendDataLat", reciver_lat);
        Log.e("SendDataLag", reciver_lag);
        Log.e("SendDataTime", delivery_time);
        Log.e("SendDataspaace", space);
        Log.e("SendDataname_receiver", name_receiver);
        Log.e("SendDataphone_receiver2", phone_receiver2);
        Log.e("SendDataaddress_reciver", address_reciver);
        Log.e("street_number_reciver", street_number_reciver);
        Log.e("flower_number_reciver", flower_number_reciver);
        Log.e("building_number_reciver", building_number_reciver);
        Log.e("SendDatastreet_name", street_name);
        Log.e("SendDatabuilding_number", building_number);
        Log.e("SendDataId", Id);
        Log.e("SendDataflower_number", flower_number);
        Log.e("SendDataflat_number", flat_number);
        Log.e("street_number_reciver", street_number_reciver);
        Log.e("flower_number_reciver", flower_number_reciver);
        Log.e("building_number_reciver", building_number_reciver);
        if (promo == null) {
            promo = " ";
        } else {
            promo = promo;
        }
        new uploadOrder().execute(name, client_id,
                map_address_client,
                clientlat,
                clientlag,
                map_address_reciver,
                reciver_lat,
                reciver_lag,
                delivery_time,
                space,
                Id,
                street_name,
                building_number,
                flower_number,
                flat_number, name_receiver,
                phone_receiver2,
                address_reciver,
                street_number_reciver,
                flower_number_reciver,
                building_number_reciver
                , promo);
    }

    private String street(LatLng x) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(x.latitude, x.longitude, 1);

            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("");
                }
                return strReturnedAddress.toString();
            } else {
                return "No Address returned!";
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "No Address returned!";
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

            Log.e("SendDataName", name);
            Log.e("SendDataId", client_id);
            Log.e("SendDataAddress", map_address_client);
            Log.e("SendDataLat", clientlat);
            Log.e("SendDataLng", clientlag);
            Log.e("SendDataAddress", map_address_reciver);
            Log.e("SendDataLat", reciver_lat);
            Log.e("SendDataLag", reciver_lag);
            Log.e("SendDataTime", delivery_time);
            Log.e("SendDataspaace", space);
            Log.e("SendDataname_receiver", name_receiver);
            Log.e("SendDataphone_receiver2", phone_receiver2);
            Log.e("SendDataaddress_reciver", address_reciver);
            Log.e("street_number_reciver", street_number_reciver);
            Log.e("flower_number_reciver", flower_number_reciver);
            Log.e("building_number_reciver", building_number_reciver);
            Log.e("SendDatastreet_name", street_name);
            Log.e("SendDatabuilding_number", building_number);
            Log.e("SendDataId", Id);
            Log.e("SendDataflower_number", flower_number);
            Log.e("SendDataflat_number", flat_number);
            Log.e("street_number_reciver", street_number_reciver);
            Log.e("flower_number_reciver", flower_number_reciver);
            Log.e("building_number_reciver", building_number_reciver);
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
                                                finishIt(order_id);
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

    private void finishIt(String order_id) {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject j = new JSONObject(response);
                } catch (JSONException j1) {
                    j1.printStackTrace();
                }
            }
        };

        confirm_sms obj = new confirm_sms("1", order_id, listener);
        RequestQueue requestQueue = Volley.newRequestQueue(FullShippmentActivity.this);
        requestQueue.add(obj);

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle(getString(R.string.wewill)).setMessage(getString(R.string.messageAnt5a));
        dialog.setPositiveButton(getString(R.string.Confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                startActivity(new Intent(FullShippmentActivity.this, MainNavigationActivity.class));
                finish();
            }
        });
        dialog.setIcon(R.drawable.correct);
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
        progDailog.setIcon(R.drawable.logo);
        progDailog.setProgress(0);
        progDailog.setMax(70);
        progDailog.setCanceledOnTouchOutside(false);
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

    public void processing2() {
        progDailog2 = new ProgressDialog(FullShippmentActivity.this);
        progDailog2.setTitle(getString(R.string.UploadData));
        progDailog2.setMessage(getString(R.string.pleasewait));
        progDailog2.setProgress(0);
        progDailog2.setMax(70);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (progress <= 50) {
                    try {
                        progDailog2.setProgress(progress);
                        progress++;
                        Thread.sleep(500);
                    } catch (Exception e) {

                    }
                }
                progDailog2.dismiss();
            }
        });
        thread.start();
        progDailog2.show();
    }

    private boolean validate1() {

        return true;
    }

    private void intialize() {
        ShippingName = getTextFromEditText(EdShippingName, R.string.enterShipping);
        Street = getTextFromEditText(EdStreet, R.string.enterstreetname);
        HouseNumber = getTextFromEditText(EdHouseNumber, R.string.enterHouseNumber);
        Storey = getTextFromEditText(EdStorey, R.string.enterStorey);
        ApartmentNumber = getTextFromEditText(EdApartmentNumber, R.string.enterApartmentNumber);
        Governorate = getTextFromEditText(EdGovernorate, R.string.enterGovernorate);
        Discrete = getTextFromEditText(EdDiscrete, R.string.enterDiscrete);
        if (EdPromo.getText().length() > 0) {
            promo = getTextFromEditText(EdPromo, R.string.enter_Promo_code);
        }
        ShippingName2 = getTextFromEditText(EdToShippingName, R.string.enterShipping);
        Street2 = getTextFromEditText(EdToStreet, R.string.enterstreetname);
        HouseNumber2 = getTextFromEditText(EdToHouseNumber, R.string.enterHouseNumber);
        Storey2 = getTextFromEditText(EdToStorey, R.string.enterStorey);
        ApartmentNumber2 = getTextFromEditText(EdToApartmentNumber, R.string.enterApartmentNumber);
        phone_receiver2 = getTextFromEditText(EdToPhone, R.string.enterPhone, 11);
        Discrete2 = getTextFromEditText(EdToDiscrete, R.string.enterDiscrete);
        Governorate2 = getTextFromEditText(EdToGovernorate, R.string.enterGovernorate);
    }

    public String getTextFromEditText(EditText editText, int id) {

        if (editText.getText().toString().isEmpty()) {
            editText.setError(getString(id));
        } else {
            return editText.getText().toString();
        }
        return "";
    }

    public String getTextFromEditText(EditText editText, int id, int numberOfCharacter) {

        if (editText.getText().toString().isEmpty()) {
            editText.setError(getString(id));
        } else if (editText.getText().length() != numberOfCharacter) {
            editText.setError(getString(R.string.enterPhoneGood));
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


    public class uploadOrder extends AsyncTask<String, Boolean, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            String name = strings[0];
            String id_client = strings[1];
            String map_address_client = strings[2];
            String client_lat = strings[3];
            String client_lag = strings[4];
            String map_address_reciver = strings[5];
            String reciver_lat = strings[6];
            String reciver_lag = strings[7];
            String delivery_time = strings[8];
            String space = strings[9];
            String id_city = strings[10];
            String street_name = strings[11];
            String building_number = strings[12];
            String flower_number = strings[13];
            String flat_number = strings[14];
            String name_reciver = strings[15];
            String phone_reciver = strings[16];
            String address_reciver = strings[17];
            String street_number_reciver = strings[18];
            String flower_number_reciver = strings[19];
            String building_number_reciver = strings[20];
            String promo_code = strings[21];

            // if  you have  to send  data  to the databse
            ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("lang", String.valueOf(MainActivity.lang)));
            pairs.add(new BasicNameValuePair("name", name));
            pairs.add(new BasicNameValuePair("id_client", id_client));
            pairs.add(new BasicNameValuePair("map_address_client", map_address_client));
            pairs.add(new BasicNameValuePair("client_lat", client_lat));
            pairs.add(new BasicNameValuePair("client_lag", client_lag));
            pairs.add(new BasicNameValuePair("map_address_reciver", map_address_reciver));
            pairs.add(new BasicNameValuePair("reciver_lat", reciver_lat));
            pairs.add(new BasicNameValuePair("reciver_lag", reciver_lag));
            pairs.add(new BasicNameValuePair("delivery_time", delivery_time));
            pairs.add(new BasicNameValuePair("space", space));
            pairs.add(new BasicNameValuePair("id_city", id_city));
            pairs.add(new BasicNameValuePair("street_name", street_name));
            pairs.add(new BasicNameValuePair("building_number", building_number));
            pairs.add(new BasicNameValuePair("flower_number", flower_number));
            pairs.add(new BasicNameValuePair("flat_number", flat_number));
            pairs.add(new BasicNameValuePair("name_reciver", name_reciver));
            pairs.add(new BasicNameValuePair("phone_reciver", phone_reciver));
            pairs.add(new BasicNameValuePair("address_reciver", address_reciver));
            pairs.add(new BasicNameValuePair("street_number_reciver", street_number_reciver));
            pairs.add(new BasicNameValuePair("flower_number_reciver", flower_number_reciver));
            pairs.add(new BasicNameValuePair("building_number_reciver", building_number_reciver));
            pairs.add(new BasicNameValuePair("promo_code", promo_code));

            JsonReader3 j = new JsonReader3("https://bsor3a.com/Clients/new_order", pairs);
            result = j.sendRequest();
            JSONObject jsonobject;
            try {
                jsonobject = new JSONObject(result);
                JSONArray jsonArray = jsonobject.getJSONArray("message");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject respons = jsonArray.getJSONObject(i);
                    int messageID = respons.getInt("messageID");
                    if (messageID == 1) {
                        message = respons.getString("message");
                        price = "";
                        if (respons.has("price")) {
                            price = respons.getString("price");
                        }
                        order_id = respons.getString("order_id");
                    } else {
                        progDailog.cancel();
                        Toast.makeText(FullShippmentActivity.this, "" + "error", Toast.LENGTH_SHORT).show();

                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progDailog.cancel();
            AlertDialog.Builder builder = new AlertDialog.Builder(FullShippmentActivity.this);
            builder.setMessage(message + "\n" + getString(R.string.orderprice) + price)
                    .setPositiveButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            finishIt(order_id);
                        }
                    }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DeleteRequest(order_id);
                }
            })
                    .create()
                    .show();

        }

    }
}
