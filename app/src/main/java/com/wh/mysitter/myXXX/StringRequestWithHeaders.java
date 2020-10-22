package com.wh.mysitter.myXXX;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.LinkedHashMap;
import java.util.Map;

public class StringRequestWithHeaders extends StringRequest {
    Map<String, String> header;

    public StringRequestWithHeaders(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        header = new LinkedHashMap<>();
    }

    public StringRequestWithHeaders(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        header = new LinkedHashMap<>();
    }

    public void setHeader(String key,String value) {
        this.header.put(key,value);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return header;
    }
}
