package com.mostafa.android.bsor3a;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.mobiwise.materialintro.animation.MaterialIntroListener;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {
    @BindView(R.id.linear)
    LinearLayout mLinearLayout;
    Location location;
    @BindView(R.id.Confirm)
    Button ConfirmButton;
    @BindView(R.id.Reset)
    Button ResetButton;
    @BindView(R.id.detect_location)
    TextView seText;
    boolean isFirstLocationSet = false, isSecoundLocationSet = false;
    LatLng latLngSelected;
    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private LatLng latLngSelectedReciver, destinationLatLng;
    private String destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //setBar.setStatusBarColored(this);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Handler handler = new Handler();
        boolean isFinish = handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Snackbar snackbar = Snackbar
                        .make(mLinearLayout, getString(R.string.ChoosePlace), Snackbar.LENGTH_LONG);

                snackbar.show();
                View sbView = snackbar.getView();
                TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                TextView textView2 = sbView.findViewById(android.support.design.R.id.snackbar_action);
                textView.setTextColor(Color.BLACK);
                textView2.setTextColor(Color.BLACK);
                sbView.setBackgroundColor(getResources().getColor(R.color.Orange));
                snackbar.show();
            }
        }, 1000);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                destination = place.getName().toString();
                destinationLatLng = place.getLatLng();
                Log.e("Distination", destination);
                Log.e("DistinationLatLng", String.valueOf(destinationLatLng.latitude));
                Log.e("DistinationLatLng", String.valueOf(destinationLatLng.longitude));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destinationLatLng, 18), 1000, null);
                if (!isFirstLocationSet) {
                    latLngSelected = destinationLatLng;
                    isFirstLocationSet = true;
                    isSecoundLocationSet = true;
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(destinationLatLng.latitude, destinationLatLng.longitude))
                            .title(getString(R.string.sender))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker35)));

                    seText.setText(R.string.ChoosePlace2);
                    Handler handler = new Handler();
                    boolean isFinish = handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar snackbar = Snackbar
                                    .make(mLinearLayout, getString(R.string.ChoosePlace2), Snackbar.LENGTH_LONG);
                            snackbar.show();
                            View sbView = snackbar.getView();
                            TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                            TextView textView2 = sbView.findViewById(android.support.design.R.id.snackbar_action);
                            textView.setTextColor(Color.BLACK);
                            textView2.setTextColor(Color.BLACK);
                            sbView.setBackgroundColor(getResources().getColor(R.color.Orange));
                            snackbar.show();
                        }
                    }, 1000);
                } else if (isSecoundLocationSet) {
                    isSecoundLocationSet = false;
                    latLngSelectedReciver = destinationLatLng;
                    mMap.addMarker(new MarkerOptions().position(new LatLng(destinationLatLng.latitude, destinationLatLng.longitude)).
                            title("Your Selection !").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker35)));
                    seText.setText(R.string.Thanks);
                }
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
            }
        });


        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFirstLocationSet && !isSecoundLocationSet) {
                    Intent openActivtyShippingDetails = getIntent();
                    openActivtyShippingDetails.putExtra("latClient", String.valueOf(latLngSelected.latitude));
                    openActivtyShippingDetails.putExtra("lagClient", String.valueOf(latLngSelected.longitude));
                    openActivtyShippingDetails.putExtra("latReciver", String.valueOf(latLngSelectedReciver.latitude));
                    openActivtyShippingDetails.putExtra("lagReciver", String.valueOf(latLngSelectedReciver.longitude));
                    setResult(100, openActivtyShippingDetails);
                    finish();
                } else {
                    Toast.makeText(MapsActivity.this, getString(R.string.NotgetLocation), Toast.LENGTH_SHORT).show();
                }
            }
        });
        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                isFirstLocationSet = false;
                isSecoundLocationSet = false;

                Handler handler = new Handler();
                boolean isFinish = handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar snackbar = Snackbar
                                .make(mLinearLayout, getString(R.string.ChoosePlace), Snackbar.LENGTH_INDEFINITE);

                        snackbar.show();
                        View sbView = snackbar.getView();
                        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                        TextView textView2 = sbView.findViewById(android.support.design.R.id.snackbar_action);
                        textView.setTextColor(Color.BLACK);
                        textView2.setTextColor(Color.BLACK);
                        sbView.setBackgroundColor(getResources().getColor(R.color.Orange));
                        snackbar.show();
                    }
                }, 500);


            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        bulidGoogleApiClient();
        mMap.setMyLocationEnabled(true);
        new MaterialIntroView.Builder(MapsActivity.this).setTargetPadding(10)
                .enableDotAnimation(true)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.NORMAL)
                .setDelayMillis(0)
                .enableFadeAnimation(true)
                .performClick(true)
                .setInfoText(getString(R.string.canRecir10))
                .setShape(ShapeType.CIRCLE)
                .setTarget(mapFragment.getView())
                .setUsageId("intro_card4000").setListener(new MaterialIntroListener() {
            @Override
            public void onUserClicked(String s) {

                new MaterialIntroView.Builder(MapsActivity.this).setTargetPadding(10)
                        .enableDotAnimation(true)
                        .enableIcon(false)
                        .setFocusGravity(FocusGravity.CENTER)
                        .setFocusType(Focus.NORMAL)
                        .setDelayMillis(0)
                        .enableFadeAnimation(true)
                        .performClick(true)
                        .setInfoText(getString(R.string.canRecir11))
                        .setShape(ShapeType.CIRCLE)
                        .setTarget(mapFragment.getView())
                        .setUsageId("intro_card40001").setListener(new MaterialIntroListener() {
                    @Override
                    public void onUserClicked(String s) {

                        new MaterialIntroView.Builder(MapsActivity.this).setTargetPadding(10)
                                .enableDotAnimation(true)
                                .enableIcon(false)
                                .setFocusGravity(FocusGravity.CENTER)
                                .setFocusType(Focus.NORMAL)
                                .setDelayMillis(0)
                                .enableFadeAnimation(true)
                                .performClick(false)
                                .setInfoText(getString(R.string.canRecir12))
                                .setShape(ShapeType.CIRCLE)
                                .setTarget(ConfirmButton)
                                .setUsageId("intro_card40002").setListener(new MaterialIntroListener() {
                            @Override
                            public void onUserClicked(String s) {
                                new MaterialIntroView.Builder(MapsActivity.this).setTargetPadding(10)
                                        .enableDotAnimation(true)
                                        .enableIcon(false)
                                        .setFocusGravity(FocusGravity.CENTER)
                                        .setFocusType(Focus.NORMAL)
                                        .setDelayMillis(0)
                                        .enableFadeAnimation(true)
                                        .performClick(true)
                                        .setInfoText(getString(R.string.canRecir13))
                                        .setShape(ShapeType.CIRCLE)
                                        .setTarget(ResetButton)
                                        .setUsageId("intro_card40003") //THIS SHOULD BE UNIQUE ID
                                        .show();
                            }
                        }) //THIS SHOULD BE UNIQUE ID
                                .show();
                    }
                }) //THIS SHOULD BE UNIQUE ID
                        .show();
            }
        }) //THIS SHOULD BE UNIQUE ID
                .show();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (!isFirstLocationSet) {
                    latLngSelected = latLng;
                    isFirstLocationSet = true;
                    isSecoundLocationSet = true;
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latLng.latitude, latLng.longitude))
                            .title("Your Selection !")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker35)));
                    seText.setText(R.string.ChoosePlace2);
                    Handler handler = new Handler();
                    boolean isFinish = handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar snackbar = Snackbar
                                    .make(mLinearLayout, getString(R.string.ChoosePlace2), Snackbar.LENGTH_LONG);
                            snackbar.show();
                            View sbView = snackbar.getView();
                            TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                            TextView textView2 = sbView.findViewById(android.support.design.R.id.snackbar_action);
                            textView.setTextColor(Color.BLACK);
                            textView2.setTextColor(Color.BLACK);
                            sbView.setBackgroundColor(getResources().getColor(R.color.Orange));
                            snackbar.show();
                        }
                    }, 1000);
                } else if (isSecoundLocationSet) {
                    isSecoundLocationSet = false;
                    latLngSelectedReciver = latLng;
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).
                            title("Your Selection !").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker35)));
                    seText.setText(R.string.Thanks);
                }
            }
        });


    }

    protected synchronized void bulidGoogleApiClient() {
        GoogleApiClient mgooGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mgooGoogleApiClient.connect();
    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(100000);
        mLocationRequest.setFastestInterval(100000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                onLocationChanged(locationResult.getLastLocation());
            }
        }, Looper.myLooper());

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10), 1000, null);

    }
}
