package com.mostafa.android.bsor3a.Connection;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class AddDieRequest extends StringRequest {
    // private static final String REGISTER_REQUEST_URL = "http://wellsksa.com/webservices/Register.php";
    private static final String REGISTER_REQUEST_URL = "https://bsor3a.com/clients/new_order";
    private Map<String, String> params;

    public AddDieRequest(String lang,
                         String name,
                         String id_client,
                         String map_address_client,
                         String client_lat,
                         String client_lag,
                         String map_address_reciver,
                         String reciver_lat,
                         String reciver_lag,
                         String delivery_time,
                         String space,
                         String name_reciver,
                         String phone_reciver,
                         String address_reciver,
                         String street_number_reciver,
                         String flower_number_reciver,
                         String building_number_reciver,
                         String promo,
                         String apartmentNumber,
                         String id_city,
                         String street_name,
                         String building_number,
                         String flower_number,
                         Response.Listener<String> listener) {

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("lang", lang);
        params.put("name", name);
        params.put("id_client", id_client);
        params.put("map_address_client", map_address_client);
        params.put("client_lat", client_lat);
        params.put("client_lag", client_lag);
        params.put("map_address_reciver", map_address_reciver);
        params.put("reciver_lat", reciver_lat);
        params.put("reciver_lag", reciver_lag);
        params.put("delivery_time", delivery_time);
        params.put("space", space);
        params.put("id_city", id_city);
        params.put("street_name", street_name);
        params.put("building_number", building_number);
        params.put("flower_number", flower_number);
        params.put("flat_number", apartmentNumber);
        params.put("name_reciver", name_reciver);
        params.put("phone_reciver", phone_reciver);
        params.put("address_reciver", address_reciver);
        params.put("street_number_reciver", street_number_reciver);
        params.put("flower_number_reciver", flower_number_reciver);
        params.put("building_number_reciver", building_number_reciver);
        if (promo == null) {
            params.put("promo_code", " ");

        } else {
            params.put("promo_code", promo);
        }
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
