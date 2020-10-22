package com.wh.mysitter.myXXX;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.wh.mysitter.VolleySingleton;

import java.util.Objects;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class XXXTaskApiRequest {

    public static class TaskListGetter {
        private String BaseUrl = XXXApiConfig.BaseUrl;
        private String UrlPath = "/task/get/all";
        private String AccessToken = XXXApiConfig.AccessToken;
        private Response.Listener<String> listener;
        private Response.ErrorListener errorListener;

        public TaskListGetter BaseUrl(String URL){
            this.BaseUrl = URL;
            return this;
        }

        public TaskListGetter AccessToken(String AccessToken){
            this.AccessToken = AccessToken;
            return this;
        }

        public TaskListGetter ResultListener(Response.Listener<String> listener){
            this.listener = listener;
            return this;
        }

        public TaskListGetter ErrorListener(Response.ErrorListener errorListener){
            this.errorListener = errorListener;
            return this;
        }

        public TaskListGetter getAll(){
            this.UrlPath = "/task/get/all";
            return this;
        }

        public TaskListGetter getUndone(){
            this.UrlPath = "/task/get/undone";
            return this;
        }

        public void run(Context applicationContext) {
            StringRequestWithHeaders stringRequestWithHeaders = new StringRequestWithHeaders(BaseUrl+UrlPath, listener,errorListener);
            stringRequestWithHeaders.setHeader("access_token", AccessToken);
            VolleySingleton.getInstance(applicationContext).addToRequestQueue(stringRequestWithHeaders, applicationContext);
        }
    }

    public static class TaskDoneUndoneSetter {
        private String BaseUrl = XXXApiConfig.BaseUrl;
        private String UrlPath = "/task/done"; // done or undone
        private String AccessToken = XXXApiConfig.AccessToken;
        private Response.Listener<String> listener;
        private Response.ErrorListener errorListener;

        public TaskDoneUndoneSetter BaseUrl(String BaseUrl){
            this.BaseUrl = BaseUrl;
            return this;
        }

        public TaskDoneUndoneSetter AccessToken(String AccessToken){
            this.AccessToken = AccessToken;
            return this;
        }

        public TaskDoneUndoneSetter ResultListener(Response.Listener<String> listener){
            this.listener = listener;
            return this;
        }

        public TaskDoneUndoneSetter ErrorListener(Response.ErrorListener errorListener){
            this.errorListener = errorListener;
            return this;
        }

        public TaskDoneUndoneSetter done(int id){
            this.UrlPath = "/task/done/"+id;
            return this;
        }

        public TaskDoneUndoneSetter undone(int id){
            this.UrlPath = "/task/undone/"+id;
            return this;
        }

        public void run(Context applicationContext){
            StringRequestWithHeaders stringRequestWithHeaders = new StringRequestWithHeaders(BaseUrl+UrlPath, listener,errorListener);
            stringRequestWithHeaders.setHeader("access_token", AccessToken);
            VolleySingleton.getInstance(applicationContext).addToRequestQueue(stringRequestWithHeaders, applicationContext);
        }
    }

    public static class TaskAdder{
        private String BaseUrl = XXXApiConfig.BaseUrl;
        private String UrlPath = "/task/add";
        private String FinalUrl;
        private String AccessToken = XXXApiConfig.AccessToken;
        private Response.Listener<String> listener;
        private Response.ErrorListener errorListener;

        public TaskAdder BaseUrl(String BaseUrl){
            this.BaseUrl = BaseUrl;
            return this;
        }

        public TaskAdder AccessToken(String AccessToken){
            this.AccessToken = AccessToken;
            return this;
        }

        public TaskAdder ResultListener(Response.Listener<String> listener){
            this.listener = listener;
            return this;
        }

        public TaskAdder ErrorListener(Response.ErrorListener errorListener){
            this.errorListener = errorListener;
            return this;
        }

        public TaskAdder set(String Title, String Task, String Device){
            Request.Builder reqBuild = new Request.Builder();
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(BaseUrl + UrlPath)).newBuilder();
            urlBuilder.addQueryParameter("title",Title);
            urlBuilder.addQueryParameter("task",Task);
            urlBuilder.addQueryParameter("device",Device);
            String url = urlBuilder.build().toString();
            Log.d("WH_", "set: "+url);
            FinalUrl = url;
            return this;
        }

        public TaskAdder set(String Title, String Task){
            Request.Builder reqBuild = new Request.Builder();
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(BaseUrl + UrlPath)).newBuilder();
            urlBuilder.addQueryParameter("title",Title);
            urlBuilder.addQueryParameter("task",Task);
            urlBuilder.addQueryParameter("device", XXXApiConfig.DeviceName);
            String url = urlBuilder.build().toString();
            Log.d("WH_", "set: "+url);
            FinalUrl = url;
            return this;
        }

        public void run(Context applicationContext){
            StringRequestWithHeaders stringRequestWithHeaders = new StringRequestWithHeaders(FinalUrl,listener,errorListener);
            stringRequestWithHeaders.setHeader("access_token", AccessToken);
            VolleySingleton.getInstance(applicationContext).addToRequestQueue(stringRequestWithHeaders, applicationContext);
        }
    }
}
