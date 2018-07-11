package com.mostafa.android.bsor3a;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.mostafa.android.bsor3a.Connection.NewShippingActivityRequest;
import com.mostafa.android.bsor3a.Connection.PerviousShippingActivityRequest;
import com.mostafa.android.bsor3a.Recycler.Shipping;
import com.mostafa.android.bsor3a.Recycler.ShippingAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mostafa.android.bsor3a.MainActivity.MY_PREFS_NAME;

public class NewShipmentsActivity extends Activity {
    @BindView(R.id.recycler_view1)
    RecyclerView mHistoryRecyclerView;
    @BindView(R.id.Progress1)
    ProgressBar progressBar;
    @BindView(R.id.TextEmpty1)
    TextView TxEmpty;
    private RecyclerView.Adapter mHistoryAdapter;
    private RecyclerView.LayoutManager mHistoryLayoutManager;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String clientID;
    int messageId;
    String message;
    JSONArray data;
    static List<Shipping> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shipments);
        setTitle(R.string.Newly_shipments);
        MainActivity.NEWOROLD = 1;
        //setBar.setStatusBarColored(this);
        ButterKnife.bind(this);
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        clientID = prefs.getString("customer_id", "");


        getData(clientID);

        mHistoryRecyclerView.setNestedScrollingEnabled(true);
        mHistoryRecyclerView.setHasFixedSize(true);
        mHistoryLayoutManager = new LinearLayoutManager(NewShipmentsActivity.this);
        mHistoryRecyclerView.setLayoutManager(mHistoryLayoutManager);
        mHistoryAdapter = new ShippingAdapter(dataList, NewShipmentsActivity.this);
        mHistoryRecyclerView.setAdapter(mHistoryAdapter);
        mHistoryAdapter.notifyDataSetChanged();

    }

    private void getData(final String clientID) {

        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("data")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        int size = data.length();
                        JSONObject check = data.getJSONObject(size - 1);
                        message = check.getString("message");
                        messageId = check.getInt("messageID");
                        if (messageId == 1) {
                            Toast.makeText(NewShipmentsActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            for (int i = 0; i < data.length() - 1; i++) {
                                JSONObject Shippingdata = data.getJSONObject(i);
                                String id = Shippingdata.getString("id");
                                String name = Shippingdata.getString("name");
                                String code_order = Shippingdata.getString("code_order");
                                String id_delivery = Shippingdata.getString("id_delivery");
                                String creation_date = Shippingdata.getString("creation_date");
                                String status = Shippingdata.getString("status");
                                String id_reciver = Shippingdata.getString("id_reciver");
                                String map_address_client = Shippingdata.getString("map_address_client");
                                String client_lat = Shippingdata.getString("client_lat");
                                String client_lag = Shippingdata.getString("client_lag");
                                String map_address_reciver = Shippingdata.getString("map_address_reciver");
                                String reciver_lat = Shippingdata.getString("reciver_lat");
                                String reciver_lag = Shippingdata.getString("reciver_lag");
                                String total_price = Shippingdata.getString("total_price");
                                String promo_id = Shippingdata.getString("promo_id");
                                String delivery_time = Shippingdata.getString("delivery_time");
                                String space = Shippingdata.getString("space");
                                String rate_client = Shippingdata.getString("rate_client");
                                String rate_delivery = Shippingdata.getString("rate_delivery");
                                String date_delivery = Shippingdata.getString("date_delivery");
                                dataList.add(new Shipping(name
                                        , map_address_client, map_address_reciver
                                        , id, code_order, clientID, creation_date, status, id_reciver, map_address_client, client_lat
                                        , client_lag, map_address_reciver, reciver_lag, reciver_lat, total_price, promo_id, delivery_time, space
                                        , rate_client, rate_delivery, date_delivery));

                            }
                            mHistoryAdapter.notifyDataSetChanged();

                        } else if (messageId == 0) {
                            progressBar.setVisibility(View.INVISIBLE);
                            AlertDialog.Builder builder = new AlertDialog.Builder(NewShipmentsActivity.this);
                            builder.setMessage(message)
                                    .setNegativeButton("" + getString(R.string.Okay), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    } else if (jsonObject.has("message")) {
                        JSONArray data = jsonObject.getJSONArray("message");
                        int size = data.length();
                        JSONObject check = data.getJSONObject(size - 1);
                        message = check.getString("message");
                        messageId = check.getInt("messageID");
                        if (messageId == 1) {
                            Toast.makeText(NewShipmentsActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            for (int i = 0; i < data.length() - 1; i++) {
                                JSONObject Shippingdata = data.getJSONObject(i);
                                String id = Shippingdata.getString("id");
                                String name = Shippingdata.getString("name");
                                String code_order = Shippingdata.getString("code_order");
                                String id_delivery = Shippingdata.getString("id_delivery");
                                String creation_date = Shippingdata.getString("creation_date");
                                String status = Shippingdata.getString("status");
                                String id_reciver = Shippingdata.getString("id_reciver");
                                String map_address_client = Shippingdata.getString("map_address_client");
                                String client_lat = Shippingdata.getString("client_lat");
                                String client_lag = Shippingdata.getString("client_lag");
                                String map_address_reciver = Shippingdata.getString("map_address_reciver");
                                String reciver_lat = Shippingdata.getString("reciver_lat");
                                String reciver_lag = Shippingdata.getString("reciver_lag");
                                String total_price = Shippingdata.getString("total_price");
                                String promo_id = Shippingdata.getString("promo_id");
                                String delivery_time = Shippingdata.getString("delivery_time");
                                String space = Shippingdata.getString("space");
                                String rate_client = Shippingdata.getString("rate_client");
                                String rate_delivery = Shippingdata.getString("rate_delivery");
                                String date_delivery = Shippingdata.getString("date_delivery");
                                dataList.add(new Shipping(name
                                        , map_address_client, map_address_reciver
                                        , id, code_order, clientID, creation_date, status, id_reciver, map_address_client, client_lat
                                        , client_lag, map_address_reciver, reciver_lag, reciver_lat, total_price, promo_id, delivery_time, space
                                        , rate_client, rate_delivery, date_delivery));

                            }
                            mHistoryAdapter.notifyDataSetChanged();

                        } else if (messageId == 0) {
                            progressBar.setVisibility(View.INVISIBLE);
                            AlertDialog.Builder builder = new AlertDialog.Builder(NewShipmentsActivity.this);
                            builder.setMessage(message)
                                    .setNegativeButton("" + getString(R.string.Okay), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        NewShippingActivityRequest perviousShippingActivityRequest = new NewShippingActivityRequest(clientID, listener);
        RequestQueue requestQueue = Volley.newRequestQueue(NewShipmentsActivity.this);
        requestQueue.add(perviousShippingActivityRequest);

    }


    @Override
    protected void onStop() {
        dataList.clear();
        getData(clientID);
        super.onStop();
    }

    @Override
    protected void onResume() {
        getData(clientID);
        super.onResume();
    }

    @Override
    protected void onStart() {
        getData(clientID);
        super.onStart();
    }

    public void back(View view) {
        onBackPressed();
    }
}
