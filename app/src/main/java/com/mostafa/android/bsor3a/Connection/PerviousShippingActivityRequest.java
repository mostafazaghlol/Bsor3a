package com.mostafa.android.bsor3a.Connection;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.mostafa.android.bsor3a.MainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mostafa salah zaghloul
 * you can reach me on 01148295140 on 7/5/18.
 */

public class PerviousShippingActivityRequest extends StringRequest {
    private static final String url = "https://bsor3a.com/clients/orders";
    private Map<String, String> params;

    public PerviousShippingActivityRequest(String ClientID, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("id_client", ClientID);
        params.put("lang", String.valueOf(MainActivity.lang));
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
