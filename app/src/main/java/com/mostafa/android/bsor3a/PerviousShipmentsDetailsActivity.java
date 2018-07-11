package com.mostafa.android.bsor3a;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mostafa.android.bsor3a.Recycler.Shipping;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PerviousShipmentsDetailsActivity extends Activity {
    @BindView(R.id.ShDate)
    TextView TxDate;
    @BindView(R.id.ShFrom)
    TextView TxFrom;
    @BindView(R.id.ShTo)
    TextView TxTo;
    @BindView(R.id.ShCost)
    TextView TxCost;
    String Id;
    String orderId;
    @BindView(R.id.xxx)
    RelativeLayout relativeLayout;
    @BindView(R.id.xxxxx)
    ProgressBar pro;
    @BindView(R.id.xxxx)
    Button delete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pervious_shipments_details);
        //setBar.setStatusBarColored(this);
        ButterKnife.bind(this);
        int id = getIntent().getIntExtra("id", 0);
        int idTag = getIntent().getIntExtra("idTag", 0);
//        OrderId=getIntent().getStringExtra("id");
        Toast.makeText(this, "" + String.valueOf(id), Toast.LENGTH_SHORT).show();
        Shipping shippingObject = new Shipping();
        if (idTag == 1) {
            shippingObject = NewShipmentsActivity.dataList.get(id);

        } else if (idTag == 2) {
            shippingObject = PerviousShipmentsActivity.dataList.get(id);
            delete.setVisibility(View.GONE);
        }
        orderId = shippingObject.getId();
        String Date = shippingObject.getCreation_date();
        String From = shippingObject.getFrom();
        String To = shippingObject.getTo();
        String price = shippingObject.getTotal_price();
        Id = shippingObject.getId();
        if (Date.equals("null")) {
            Date = getString(R.string.Empty_Field);
        } else {
            if (Date.length() > 10) {
                Date = Date.substring(0, 10);
            }
        }
        if (From == null) {
            From = "Empty Field";
        } else {
            if (From.length() > 10) {
                From = From.substring(0, 10);
            }
        }
        if (To == null) {
            To = "Empty Field";
        } else {
            if (To.length() > 10) {
                To = To.substring(0, 10);
            }
        }
        TxDate.setText(Date);
        TxFrom.setText(From);
        TxTo.setText(To);
        TxCost.setText(price);
    }

    public void back(View view) {
        onBackPressed();
    }

    public void deletex(final View view) {
        try {
            pro.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.INVISIBLE);
            view.setVisibility(View.INVISIBLE);
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray message = jsonObject.getJSONArray("message");
                        String message1 = message.getJSONObject(0).getString("message");
                        Toast.makeText(PerviousShipmentsDetailsActivity.this, "" + message1, Toast.LENGTH_SHORT).show();
                        view.setVisibility(View.INVISIBLE);
                        pro.setVisibility(View.INVISIBLE);
//                        startActivity(new Intent(PerviousShipmentsDetailsActivity.this,PerviousShipmentsActivity.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            deleteRequest mdDeleteRequest = new deleteRequest(orderId, listener);
            RequestQueue requestQueue = Volley.newRequestQueue(PerviousShipmentsDetailsActivity.this);
            requestQueue.add(mdDeleteRequest);
        } catch (Exception e) {
            Log.e("Error", e.getMessage().toString());
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
