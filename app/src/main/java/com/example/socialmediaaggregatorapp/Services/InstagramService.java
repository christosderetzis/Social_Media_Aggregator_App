package com.example.socialmediaaggregatorapp.Services;

import android.content.Context;
import android.util.Log;

import com.example.socialmediaaggregatorapp.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InstagramService {

    private Long instagramIdAccount;
    private String access_token;

    public InstagramService(Context context){
        instagramIdAccount = Long.valueOf(context.getResources().getString(R.string.instagram_app_id));
        access_token = context.getResources().getString(R.string.instagram_API_Key);
    }

    public String downloadInstagramData(String URL) throws IOException {

        // check if url has any parameters, in order to add access_token
        String url_parameters = URL.substring(URL.indexOf("?"));
        if (url_parameters.length() == 0){
            URL += "?access_token=" + access_token;
        } else {
            URL += "&access_token=" + access_token;
        }

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();

        // Send request data to the twitter server
        Request request = new Request.Builder()
                .method("GET", null)
                .url(URL)
                .build();

        // Get the response data
        Response response = client.newCall(request).execute();

        // Get the body from response and convert it to string
        String result = response.body().string();
        return result;
    }

    private Map<String, String> getUrlValues (String url) throws UnsupportedEncodingException {
        int i = url.indexOf("?");
        Map<String, String> paramsMap = new HashMap<>();
        if (i > -1) {
            String searchURL = url.substring(url.indexOf("?") + 1);
            String params[] = searchURL.split("&");

            for (String param : params) {
                String temp[] = param.split("=");
                paramsMap.put(temp[0], java.net.URLDecoder.decode(temp[1], "UTF-8"));
            }
        }
        return paramsMap;
    }

    public Long getInstagramIdAccount() {
        return instagramIdAccount;
    }
}
