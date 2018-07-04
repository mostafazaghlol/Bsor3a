package com.mostafa.android.bsor3a.Shipping;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.mostafa.android.bsor3a.MainActivity;
import com.mostafa.android.bsor3a.R;
import com.mostafa.android.bsor3a.Recycler.Shipping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mostafa salah zaghloul
 * you can reach me on 01148295140 on 7/4/18.
 */

public class ShippingRequest extends StringRequest {
    private final static String url = "https://bsor3a.com/clients/new_order";
    private Map<String, String> params;

    //    public  ShippingRequest(ArrayList<String> Sender, ArrayList<String> Receiver, Response.Listener<String> listener){
    public ShippingRequest(Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        try {
            params.put("lang", String.valueOf(MainActivity.lang));
            params.put("name", "hi");
            params.put("id_client", "21");
            params.put("map_address_client", "hi");
            params.put("client_lat", "hi");
            params.put("client_lag", "hi");
            params.put("map_address_reciver", "hi");
            params.put("reciver_lat", "hi");
            params.put("reciver_lag", "hi");
            params.put("delivery_time", "hi");
            params.put("space", "hi");
            params.put("name_reciver", "hi");
            params.put("phone_reciver", "0000000000");
            params.put("address_reciver", "hi");
            params.put("street_number_reciver", "hi");
            params.put("flower_number_reciver", "hi");
            params.put("building_number_reciver", "hi");
            params.put("promo_code", "");
//            params.put("lang", String.valueOf(MainActivity.lang));
//            params.put("name", Sender.get(0));
//            params.put("id_client", Sender.get(10));
//            params.put("map_address_client", Sender.get(7));
//            params.put("client_lat", Sender.get(8));
//            params.put("client_lag", Sender.get(9));
//            params.put("map_address_reciver", Receiver.get(7));
//            params.put("reciver_lat", Receiver.get(8));
//            params.put("reciver_lag", Receiver.get(9));
//            params.put("delivery_time", Sender.get(10));
//            params.put("space", Receiver.get(11));
//            params.put("name_reciver", Receiver.get(0));
//            params.put("phone_reciver", "0000000000");
//            params.put("address_reciver", Receiver.get(7));
//            params.put("street_number_reciver", Receiver.get(3));
//            params.put("flower_number_reciver", Receiver.get(6));
//            params.put("building_number_reciver", Receiver.get(4));
//            params.put("promo_code", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
