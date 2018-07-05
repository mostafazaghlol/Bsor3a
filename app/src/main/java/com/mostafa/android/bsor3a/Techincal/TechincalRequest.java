package com.mostafa.android.bsor3a.Techincal;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mostafa salah zaghloul
 * you can reach me on 01148295140 on 7/4/18.
 */

public class TechincalRequest extends StringRequest {
    private static final String url = "https://bsor3a.com/clients/contactus";
    private Map<String, String> params;

    public TechincalRequest(Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
