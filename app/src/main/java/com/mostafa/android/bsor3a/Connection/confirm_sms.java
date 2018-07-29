package com.mostafa.android.bsor3a.Connection;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.mostafa.android.bsor3a.MainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mostafa salah zaghloul
 * you can reach me on 01148295140 on 7/29/18.
 */

public class confirm_sms extends StringRequest {
    private static final String Url = "http://bsor3a.com/Clients/confirm_sms";
    private Map<String, String> params;

    public confirm_sms(String Key, String id_order, Response.Listener<String> listener) {
        super(Method.POST, Url, listener, null);
        params = new HashMap<>();
        params.put("key", Key);
        params.put("lang", String.valueOf(MainActivity.lang));
        params.put("id_order", id_order);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
