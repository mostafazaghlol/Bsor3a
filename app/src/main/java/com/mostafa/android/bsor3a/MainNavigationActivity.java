package com.mostafa.android.bsor3a;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mostafa.android.bsor3a.LoginAndRegister.LoginActivity;
import com.mostafa.android.bsor3a.LoginAndRegister.ModifyTheDataActivity;
import com.mostafa.android.bsor3a.Techincal.TechincalActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mostafa.android.bsor3a.MainActivity.MY_PREFS_NAME;

public class MainNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.RequestClient)
    Button ButtonRequestClient;
    NavigationView navigationView;
    DrawerLayout drawer;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String imageUrl;
    Typeface face;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            imageUrl = prefs.getString("customer_img", "");


            // custom_font = Typeface.createFromAsset(getAssets(), "font.otf");
            face = Typeface.createFromAsset(getAssets(), "font.otf");

            setContentView(R.layout.activity_main_navigation);

            //            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            ButterKnife.bind(this);
            //setBar.setStatusBarColored(this);
//        navigationView.getBackground().setAlpha(122);
            //setSupportActionBar(toolbar);
            drawer = findViewById(R.id.drawer_layout);
            //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            // this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            //drawer.addDrawerListener(toggle);
            //toggle.syncState();

            navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.getBackground().setAlpha(30);
            View view = navigationView.getHeaderView(0);
            Menu m = navigationView.getMenu();
            for (int i = 0; i < m.size(); i++) {
                MenuItem mi = m.getItem(i);

                //for aapplying a font to subMenu ...
                SubMenu subMenu = mi.getSubMenu();
                if (subMenu != null && subMenu.size() > 0) {
                    for (int j = 0; j < subMenu.size(); j++) {
                        MenuItem subMenuItem = subMenu.getItem(j);
                        applyFontToMenuItem(subMenuItem);
                    }
                }

                //the method we have create in activity
                applyFontToMenuItem(mi);
            }
            ImageView imageView = view.findViewById(R.id.profile_image);
            if (imageUrl.isEmpty()) {
                imageView.setImageResource(R.drawable.person);
            } else {
                Picasso.get().load(imageUrl).into(imageView);
            }
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
        Intent MapActivity = new Intent(MainNavigationActivity.this, FullShippmentActivity.class);
        startActivity(MapActivity);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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


        TextView textView = (TextView) menu.getItem(0).getActionView();
        textView.setText("hhhhhhhh");
        return true;
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "font.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
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
        } else if (id == R.id.Newly_shipments) {
            startActivity(new Intent(MainNavigationActivity.this, NewShipmentsActivity.class));
        } else if (id == R.id.sign_out) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainNavigationActivity.this);
            builder.setTitle(getResources().getString(R.string.goout));
            builder.setMessage(getResources().getString(R.string.dogoout));
            builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editor.clear();
                    editor.remove("nickname");
                    editor.remove("customer_email");
                    editor.remove("customer_phone");
                    editor.remove("customer_id");
                    editor.remove("customer_img");
                    editor.commit();
                    startActivity(new Intent(MainNavigationActivity.this, LoginActivity.class));
                    finish();
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.no), null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
