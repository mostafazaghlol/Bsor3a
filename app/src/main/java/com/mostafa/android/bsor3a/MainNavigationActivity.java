package com.mostafa.android.bsor3a;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.mostafa.android.bsor3a.LoginAndRegister.ModifyTheDataActivity;
import com.mostafa.android.bsor3a.Shipping.ShippingDetails;
import com.mostafa.android.bsor3a.Techincal.TechincalActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.RequestClient)
    Button ButtonRequestClient;
    NavigationView navigationView;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main_navigation);
            //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            ButterKnife.bind(this);
            setBar.setStatusBarColored(this);
//        navigationView.getBackground().setAlpha(122);
            //setSupportActionBar(toolbar);
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            // this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            //drawer.addDrawerListener(toggle);
            //toggle.syncState();

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.getBackground().setAlpha(30);
            ButtonRequestClient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openMapActivity();
                }
            });
        } catch (Exception e) {
            Log.e("MainNavigationActivity", e.getMessage().toString());
        }


    }

    private void openMapActivity() {
        Intent MapActivity = new Intent(MainNavigationActivity.this, ShippingDetails.class);
        startActivity(MapActivity);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Modify_personal_data) {
            startActivity(new Intent(MainNavigationActivity.this, ModifyTheDataActivity.class));

        } else if (id == R.id.Technical_support) {
            startActivity(new Intent(MainNavigationActivity.this, TechincalActivity.class));
        } else if (id == R.id.Previous_shipments) {
            startActivity(new Intent(MainNavigationActivity.this, PerviousShipmentsActivity.class));
        } else if (id == R.id.sign_out) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainNavigationActivity.this);
            builder.setTitle(getResources().getString(R.string.goout));
            builder.setMessage(getResources().getString(R.string.dogoout));
            builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.no), null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openDrawer(View view) {
        try {
            if (MainActivity.lang == 1) {
                drawer.openDrawer(Gravity.LEFT);
            } else if (MainActivity.lang == 2) {
                drawer.openDrawer(Gravity.RIGHT);
            }

        } catch (Exception e) {
            Log.e("MainNavi", e.getMessage().toString());
        }
    }
}
