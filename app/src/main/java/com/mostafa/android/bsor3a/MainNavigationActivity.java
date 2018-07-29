package com.mostafa.android.bsor3a;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.mostafa.android.bsor3a.LoginAndRegister.LoginActivity;
import com.mostafa.android.bsor3a.LoginAndRegister.ModifyTheDataActivity;
import com.mostafa.android.bsor3a.LoginAndRegister.Requests.GetDataRequest;
import com.mostafa.android.bsor3a.Techincal.TechincalActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.mobiwise.materialintro.animation.MaterialIntroListener;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;

import static com.mostafa.android.bsor3a.MainActivity.MY_PREFS_NAME;

public class MainNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String xFlatNmuber, xFlowerNumber, xbuilding_number, xstreet_name, xnickname, xcity_name,
            xGovernote, xphone, xemail, xname;
    @BindView(R.id.RequestClient)
    Button ButtonRequestClient;
    @BindView(R.id.backLogin)
    ImageView ButtonBackLogin;
    NavigationView navigationView;
    DrawerLayout drawer;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String imageUrl;
    Typeface face;
    String client_id, nickname;
    ProgressDialog progDailog2;
    private boolean x = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);
        ButterKnife.bind(this);
        try {
            prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            imageUrl = prefs.getString("customer_img", "");

            client_id = prefs.getString("customer_id", "");
            nickname = prefs.getString("nickname", "");
            Log.e("client_id", client_id);
            getData();
            face = Typeface.createFromAsset(getAssets(), "font.otf");
            drawer = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);

            new MaterialIntroView.Builder(this).setTargetPadding(10)
                    .enableDotAnimation(true)
                    .enableIcon(false)
                    .setFocusGravity(FocusGravity.CENTER)
                    .setFocusType(Focus.NORMAL)
                    .setDelayMillis(0)
                    .enableFadeAnimation(true)
                    .performClick(false)
                    .setInfoText(getString(R.string.canRecir))
                    .setShape(ShapeType.RECTANGLE)
                    .setTarget(ButtonRequestClient)
                    .setUsageId("intro_card").setListener(new MaterialIntroListener() {
                @Override
                public void onUserClicked(String s) {
                    new MaterialIntroView.Builder(MainNavigationActivity.this).setTargetPadding(10)
                            .enableDotAnimation(true)
                            .enableIcon(false)
                            .setFocusGravity(FocusGravity.CENTER)
                            .setFocusType(Focus.NORMAL)
                            .setDelayMillis(0)
                            .enableFadeAnimation(true)
                            .performClick(false)
                            .setInfoText(getString(R.string.canRecir1))
                            .setShape(ShapeType.CIRCLE)
                            .setTarget(ButtonBackLogin)
                            .setUsageId("intro_card2") //THIS SHOULD BE UNIQUE ID
                            .show();
                }
            }) //THIS SHOULD BE UNIQUE ID
                    .show();

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

            TextView nicknam1e = view.findViewById(R.id.nickname);
            nicknam1e.setText(nickname);
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

    private void getData() {
        processing2();
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject j = new JSONObject(response);
                    JSONArray array = j.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(0);
                        xemail = ob.getString("email");
                        xphone = ob.getString("phone");
                        xGovernote = ob.getString("state_name");
                        xcity_name = ob.getString("city_name");
                        xnickname = ob.getString("nickname");
                        xbuilding_number = ob.getString("building_number");
                        xstreet_name = ob.getString("street_name");
                        xFlowerNumber = ob.getString("flower_number");
                        xFlatNmuber = ob.getString("flat_number");
                        editor.putString("xemail", xemail);
                        editor.putString("xphone", xphone);
                        editor.putString("xGovernote", xGovernote);
                        editor.putString("xcity_name", xcity_name);
                        editor.putString("xnickname", xnickname);
                        editor.putString("xbuilding_number", xbuilding_number);
                        editor.putString("xstreet_name", xstreet_name);
                        editor.putString("xFlowerNumber", xFlowerNumber);
                        editor.putString("xFlatNmuber", xFlatNmuber);
                        editor.commit();
                        progDailog2.cancel();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GetDataRequest getDataRequest = new GetDataRequest(client_id, listener);
        RequestQueue requestQueue = Volley.newRequestQueue(MainNavigationActivity.this);
        requestQueue.add(getDataRequest);


    }

    public void processing2() {
        progDailog2 = new ProgressDialog(MainNavigationActivity.this);
        progDailog2.setTitle(getString(R.string.UploadData));
        progDailog2.setMessage(getString(R.string.pleasewait));
        progDailog2.setIcon(R.drawable.logo);
        progDailog2.setProgress(0);
        progDailog2.setMax(70);
        progDailog2.setCanceledOnTouchOutside(true);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (progress <= 30) {
                    try {
                        progDailog2.setProgress(progress);
                        progress++;
                        Thread.sleep(1000);
                    } catch (Exception e) {

                    }
                }
                progDailog2.dismiss();
            }
        });
        thread.start();
        progDailog2.show();
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

        } else if (id == R.id.AboutDeve) {
            startActivity(new Intent(MainNavigationActivity.this, AboutDevelopersActivity.class));

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
