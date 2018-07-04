package com.mostafa.android.bsor3a.Shipping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.mostafa.android.bsor3a.R;
import com.mostafa.android.bsor3a.setBar;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShippingDetails extends AppCompatActivity {
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
    @BindView(R.id.ButtonRShipping)
    Button BtShipping;
    Calendar currentTime = Calendar.getInstance();

    String ShippingName, Governorate, Discrete, Street, HouseNumber, Storey, ApartmentNumber;
    ArrayList<String> dataArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_details);
        setBar.setStatusBarColored(this);
        ButterKnife.bind(this);


        BtShipping.setOnClickListener(new View.OnClickListener() {
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
        int dayOfweek = currentTime.get(Calendar.DAY_OF_WEEK);
        String day = getTheDay(dayOfweek);
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int min = currentTime.get(Calendar.MINUTE);
        int sec = currentTime.get(Calendar.SECOND);
        String fullTime = day + String.valueOf(hour) + ":" + String.valueOf(min) + ":" + String.valueOf(sec);
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
                Place place = PlacePicker.getPlace(this, data);
                placeHolder = place.getAddress().toString();
                lat = String.valueOf(place.getLatLng().latitude);
                lng = String.valueOf(place.getLatLng().longitude);
                dataArray.add(placeHolder);
                dataArray.add(lat);
                dataArray.add(lng);
                dataArray.add(getTime());
                Intent intent = new Intent(ShippingDetails.this, CompleteShippingActivity.class);
                intent.putStringArrayListExtra("DataArray", dataArray);
                startActivity(intent);
                finish();
            }
        }
    }

    private boolean validate() {
        if (ShippingName.isEmpty() || Governorate.isEmpty() || Discrete.isEmpty() || Street.isEmpty() || HouseNumber.isEmpty() || Storey.isEmpty() || ApartmentNumber.isEmpty()) {
            return false;
        }
        return true;
    }

    private void intialize() {
        ShippingName = getTextFromEditText(EdShippingName, R.string.enterShipping);
        Governorate = getTextFromEditText(EdGovernorate, R.string.enterGovernorate);
        Discrete = getTextFromEditText(EdDiscrete, R.string.enterDiscrete);
        Street = getTextFromEditText(EdStreet, R.string.enterstreetname);
        HouseNumber = getTextFromEditText(EdHouseNumber, R.string.enterHouseNumber);
        Storey = getTextFromEditText(EdStorey, R.string.enterStorey);
        ApartmentNumber = getTextFromEditText(EdApartmentNumber, R.string.enterApartmentNumber);
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
