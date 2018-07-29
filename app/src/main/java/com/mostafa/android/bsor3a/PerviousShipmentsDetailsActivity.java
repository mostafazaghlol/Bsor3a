package com.mostafa.android.bsor3a;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import co.mobiwise.materialintro.animation.MaterialIntroListener;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;

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
        ButterKnife.bind(this);
        int id = getIntent().getIntExtra("id", 0);
        int idTag = getIntent().getIntExtra("idTag", 0);
        Shipping shippingObject = new Shipping();
        if (idTag == 1) {
            shippingObject = NewShipmentsActivity.dataList.get(id);

        } else if (idTag == 2) {
            shippingObject = PerviousShipmentsActivity.dataList.get(id);
            delete.setVisibility(View.GONE);
        }

        new MaterialIntroView.Builder(PerviousShipmentsDetailsActivity.this).setTargetPadding(10)
                .enableDotAnimation(true)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.NORMAL)
                .setDelayMillis(500)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText(getString(R.string.canRecir6))
                .setShape(ShapeType.CIRCLE)
                .setTarget(relativeLayout)
                .setUsageId("intro_card200010").setListener(new MaterialIntroListener() {
            @Override
            public void onUserClicked(String s) {
                if (delete.getVisibility() == View.VISIBLE) {
                    new MaterialIntroView.Builder(PerviousShipmentsDetailsActivity.this).setTargetPadding(10)
                            .enableDotAnimation(true)
                            .enableIcon(false)
                            .setFocusGravity(FocusGravity.CENTER)
                            .setFocusType(Focus.NORMAL)
                            .setDelayMillis(500)
                            .enableFadeAnimation(true)
                            .performClick(false)
                            .setInfoText(getString(R.string.canRecir5))
                            .setShape(ShapeType.CIRCLE)
                            .setTarget(delete).setUsageId("intro20022").show();
                }
            }
        }) //THIS SHOULD BE UNIQUE ID
                .show();

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
        startActivity(new Intent(PerviousShipmentsDetailsActivity.this, NewShipmentsActivity.class));
        finish();
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
                        //Toast.makeText(PerviousShipmentsDetailsActivity.this, "" + message1, Toast.LENGTH_SHORT).show();
                        view.setVisibility(View.INVISIBLE);
                        pro.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(PerviousShipmentsDetailsActivity.this, NewShipmentsActivity.class));
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
