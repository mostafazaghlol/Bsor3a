package com.mostafa.android.bsor3a.LoginAndRegister.Requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.mostafa.android.bsor3a.MainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mostafa on 7/2/18.
 */
/*
1-Login Client
https://bsor3a.com/clients/login
post('username');
post('password');
post('lang');
return
{
Message
customer_id
customer_phone
customer_email
customer_img
nick name
}
 */
public class LoginRequest extends StringRequest {
    private static final String url = "https://bsor3a.com/clients/login";
    private Map<String, String> posts;

    public LoginRequest(String username, String password, String lang, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        posts = new HashMap<>();
        posts.put("username", username);
        posts.put("password", password);
        posts.put("lang", lang);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return posts;
    }
}
