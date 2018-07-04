package com.mostafa.android.bsor3a.LoginAndRegister.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.mostafa.android.bsor3a.LoginAndRegister.ConfirmationActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mostafa salah zaghloul
 * you can reach me on 01148295140 on 7/3/18.
 */

public class ConfirmationRequest extends StringRequest {
    private static final String url = "https://bsor3a.com/clients/confirm_code";
    private Map<String, String> params;

    public ConfirmationRequest(String code, String lang, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("code", code);
        params.put("lang", lang);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
