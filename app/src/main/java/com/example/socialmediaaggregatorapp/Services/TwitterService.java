package com.example.socialmediaaggregatorapp.Services;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.example.socialmediaaggregatorapp.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TwitterService {

    public static final String Token = "706907411873931264-WOVVPLgbxvU5IL6GtxVRT1WUiVMxy0W";
    public static final String Token_secret = "mNj338uAX1mMCkLJP51d5Gi9ZKIdZXPf3alxyj3B3oSLp";
    public static final String Consumer_key = "le9u9MVYJ3Zr8ULLmbu4rEDWb";
    public static final String Consumer_key_secret = "aja0OIUjLvBrU95IDCsl1ErcSwL8rIvOypUndS3owpUp4tsKUE";

    private TwitterOauthHeaderGenerator generator;

    public TwitterService() {
        generator = new TwitterOauthHeaderGenerator(Consumer_key, Consumer_key_secret, Token, Token_secret);
    }

    public String downloadTwitterData(String URL) throws IOException {

        // get authorization header for request
        Map<String, String> requestParams = getUrlValues(URL);

        // divide main url from its parameters
        String url_without_parameters = URL.substring(0, URL.indexOf("?"));
        String authorization_header = generator.generateHeader("GET", url_without_parameters, requestParams);

        Log.d("SMA_App", authorization_header);


        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();

        // Send request data to the twitter server
        Request request = new Request.Builder()
                .addHeader("Authorization", authorization_header)
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
    }
