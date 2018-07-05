package com.mostafa.android.bsor3a.Shipping;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.mostafa.android.bsor3a.MainActivity;

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

    public ShippingRequest(ArrayList<String> Sender, ArrayList<String> Receiver, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        try {
//            params.put("lang", String.valueOf(MainActivity.lang));
//            params.put("name", "hi");
//            params.put("id_client", "21");
//            params.put("map_address_client", "hi");
//            params.put("client_lat", "hi");
//            params.put("client_lag", "hi");
//            params.put("map_address_reciver", "hi");
//            params.put("reciver_lat", "hi");
//            params.put("reciver_lag", "hi");
//            params.put("delivery_time", "hi");
//            params.put("space", "hi");
//            params.put("name_reciver", "hi");
//            params.put("phone_reciver", "0000000000");
//            params.put("address_reciver", "hi");
//            params.put("street_number_reciver", "hi");
//            params.put("flower_number_reciver", "hi");
//            params.put("building_number_reciver", "hi");
//

            params.put("lang", String.valueOf(MainActivity.lang));
            params.put("name", Sender.get(0));
            params.put("id_client", Sender.get(10));
            params.put("map_address_client", Sender.get(7));
            params.put("client_lat", Sender.get(8));
            params.put("client_lag", Sender.get(9));
            params.put("map_address_reciver", Receiver.get(7));
            params.put("reciver_lat", Receiver.get(8));
            params.put("reciver_lag", Receiver.get(9));
            params.put("delivery_time", Sender.get(10));
            params.put("space", Receiver.get(11));
            params.put("name_reciver", Receiver.get(0));
            params.put("phone_reciver", "0000000000");
            params.put("address_reciver", Receiver.get(7));
            params.put("street_number_reciver", Receiver.get(3));
            params.put("flower_number_reciver", Receiver.get(6));
            params.put("building_number_reciver", Receiver.get(4));
            params.put("promo_code", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ShippingRequest(String name, String id_client, String map_address_client, String clientlat, String clientlag, String map_address_reciver, String reciver_lat, String reciver_lag, String delivery_time, String space, String name_receiver, String phone_receiver, String address_reciver, String street_number_reciver, String flower_number_reciver, String building_number_reciver, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("lang", String.valueOf(MainActivity.lang));
        params.put("name", name);
        params.put("id_client", id_client);
        params.put("map_address_client", map_address_client);
        params.put("client_lat", clientlat);
        params.put("client_lag", clientlag);
        params.put("map_address_reciver", map_address_reciver);
        params.put("reciver_lat", reciver_lat);
        params.put("reciver_lag", reciver_lag);
        params.put("delivery_time", delivery_time);
        params.put("space", space);
        params.put("name_reciver", name_receiver);
        params.put("phone_reciver", phone_receiver);
        params.put("address_reciver", address_reciver);
        params.put("street_number_reciver", street_number_reciver);
        params.put("flower_number_reciver", flower_number_reciver);
        params.put("building_number_reciver", building_number_reciver);
    }
    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
