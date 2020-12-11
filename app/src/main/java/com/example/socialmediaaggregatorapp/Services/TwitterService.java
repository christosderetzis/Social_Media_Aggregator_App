package com.example.socialmediaaggregatorapp.Services;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TwitterService {

    public static final String Access_Token = "";
    public static final String Access_Token_secret = "";
    public static final String API_Key = "";
    public static final String API_Key_secret = "";

    private TwitterOauthHeaderGenerator generator;

    public TwitterService() {
        generator = new TwitterOauthHeaderGenerator(API_Key, API_Key_secret, Access_Token, Access_Token_secret);
    }

    public String downloadHashtagData(String URL) throws IOException {

        // get authorization header for request
        Map<String, String> requestParams = getUrlValues(URL);
        Log.d("SMA_App", requestParams.toString());

        String url_without_parameters = URL.substring(0, URL.indexOf("?"));
        String authorization_header = generator.generateHeader("GET", url_without_parameters, requestParams);

        Log.d("SMA_App", authorization_header);

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();

        Request request = new Request.Builder()
                .addHeader("Authorization", authorization_header)
                .method("GET", null)
                .url(URL)
                .build();

        Response response = client.newCall(request).execute();

        Log.d("SMA_App", response.body().string());
        return response.body().string();
    }

    private Map<String, String> getUrlValues(String url) throws UnsupportedEncodingException {
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
}
