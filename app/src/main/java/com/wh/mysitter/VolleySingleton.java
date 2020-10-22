package com.wh.mysitter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * usage
 * -- VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest,getApplicationContext());
 * or
 * -- RequestQueue queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue(getApplicationContext());
 * -- queue.add(stringRequest);
 */

public class VolleySingleton {
    private static VolleySingleton instance;
    private RequestQueue requestQueue;


    private VolleySingleton(Context context) {
        requestQueue = getRequestQueue(context);
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, Context context) {
        getRequestQueue(context).add(req);
    }
}