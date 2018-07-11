package com.mostafa.android.bsor3a.Shipping;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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
import com.mostafa.android.bsor3a.MainActivity;
import com.mostafa.android.bsor3a.R;
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

public class ShippingDetails extends Activity {
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

    //Adapters of The Spinners
    private ArrayList<GovernatItem> mCountryList;
    private GovernteAdapter mAdapter;
    private ArrayList<CityItem> mCityList;
    private CityAdapter mCityAdapter;

    String ShippingName, Governorate = "", Discrete = "", Street, HouseNumber, Storey, ApartmentNumber, promo = " ";
    ArrayList<String> dataArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_details);
        // setBar.setStatusBarColored(this);
        ButterKnife.bind(this);
        try {
            inialize();
            getcity();

            // custom_font = Typeface.createFromAsset(getAssets(), "font.otf");
            face = Typeface.createFromAsset(getAssets(), "font.otf");
            textViewTypedFace.setTypeface(face);
            //Adapters of the Spinners
            mAdapter = new GovernteAdapter(this, mCountryList);
            mCityAdapter = new CityAdapter(this, mCityList);
            EdGovernorate.setAdapter(mAdapter);
            EdDiscrete.setAdapter(mCityAdapter);

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
                        if (validate()) {
                            dataArray.add(ShippingName);
                            dataArray.add(Governorate);
                            dataArray.add(Discrete);
                            dataArray.add(Street);
                            dataArray.add(HouseNumber);
                            dataArray.add(Storey);
                            dataArray.add(ApartmentNumber);
                            Log.e("Governorate", Governorate);
                            Log.e("Discrete", Discrete);
                            Toast.makeText(ShippingDetails.this, getString(R.string.Location), Toast.LENGTH_LONG).show();
                            openPlacePicker();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Shipping", e.getMessage().toString());
        }
    }

    //gettting the cities
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
                        Toast.makeText(ShippingDetails.this, "Sorry,there is no any city", Toast.LENGTH_SHORT).show();
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
        RequestQueue queue = Volley.newRequestQueue(ShippingDetails.this);
        queue.add(getstates);
    }

    //inalize the cities
    private void getcity() {
        mCityList = new ArrayList<>();
        mCityList.add(new CityItem(getString(R.string.District), ""));
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
                        Toast.makeText(ShippingDetails.this, "Sorry,there is no any Country", Toast.LENGTH_SHORT).show();
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
        RequestQueue queue = Volley.newRequestQueue(ShippingDetails.this);
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

    boolean isplacepickerFinished = false;
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
                try {

                    Place place = PlacePicker.getPlace(this, data);
                    placeHolder = place.getAddress().toString();
                    lat = String.valueOf(place.getLatLng().latitude);
                    lng = String.valueOf(place.getLatLng().longitude);
                    dataArray.add(placeHolder);
                    dataArray.add(lat);
                    dataArray.add(lng);
                    dataArray.add(getTime());
                    if (promo.length() > 0) {
                        dataArray.add(promo);
                    } else {
                        dataArray.add("");
                    }
                    Intent intent = new Intent(ShippingDetails.this, CompleteShippingActivity.class);
                    intent.putStringArrayListExtra("DataArray", dataArray);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
}
