package com.example.fredbrume.meterialdesign.Data.Remote.Api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fredbrume on 11/14/17.
 */

public class VolleyStringRequest {

    private String jsonURL;
    private RequestQueue requestQueue;
    private String jsonresponse;

    private Map<String, String> header;
    private Map<String, String> params;

    public VolleyStringRequest(Context context, String jsonURL) {

        this.jsonURL = jsonURL;
        requestQueue = Volley.newRequestQueue(context);
        header = new HashMap<>();
        params = new HashMap<>();

    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    public void addParams(String key, String value) {
        params.put(key, value);
    }

    public void executeRequest(int method, final VolleyCallback callback) {

        final StringRequest stringRequest = new StringRequest(method, jsonURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                jsonresponse = response;
                callback.getResponse(jsonresponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(this.getClass().getSimpleName(), error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    public interface VolleyCallback {
        void getResponse(String response);
    }
}
