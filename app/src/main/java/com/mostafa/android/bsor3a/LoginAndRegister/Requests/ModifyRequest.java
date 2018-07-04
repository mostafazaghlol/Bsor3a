package com.mostafa.android.bsor3a.LoginAndRegister.Requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mostafa on 7/3/18.
 */

public class ModifyRequest extends StringRequest {
    private static final String url = "https://bsor3a.com/clients/edit_profile";
    private Map<String, String> params;

    public ModifyRequest(String lang, String building_number, String street_name, String state_id, String password, String email, String id_client, String phone, String name, String flower_number, String nickname, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("lang", lang);
        params.put("id_client", id_client);
        params.put("name", name);
        params.put("phone", phone);
        params.put("email", email);
        params.put("password", password);
        params.put("state_id", state_id);
        params.put("street_name", street_name);
        params.put("building_number", building_number);
        params.put("flower_number", flower_number);
        params.put("nickname", nickname);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
