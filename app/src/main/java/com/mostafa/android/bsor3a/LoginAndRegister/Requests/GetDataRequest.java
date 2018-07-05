package com.mostafa.android.bsor3a.LoginAndRegister.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.mostafa.android.bsor3a.MainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mostafa salah zaghloul
 * you can reach me on 01148295140 on 7/4/18.
 */

public class GetDataRequest extends StringRequest {
    private final static String url = "https://bsor3a.com/clients/edit_profile";
    private Map<String, String> params;

    public GetDataRequest(String client_id, Response.Listener listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("id_client", client_id);
        params.put("lang", String.valueOf(MainActivity.lang));
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
