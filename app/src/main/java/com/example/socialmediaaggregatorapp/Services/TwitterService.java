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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TwitterService {

    private MediaType mediaType = null;

    private static String Token;
    private static String Token_secret;
    private static String Consumer_key;
    private static String Consumer_key_secret;

    private TwitterOauthHeaderGenerator generator;

    public TwitterService(Context context) {
        Token = context.getResources().getString(R.string.twitter_Access_token);
        Token_secret = context.getResources().getString(R.string.twitter_Access_token_secret);
        Consumer_key = context.getResources().getString(R.string.twitter_API_key);
        Consumer_key_secret = context.getResources().getString(R.string.twitter_API_key_secret);
        generator = new TwitterOauthHeaderGenerator(Consumer_key, Consumer_key_secret, Token, Token_secret);
    }

    public String handleTwitterData(String URL, String httpMethod) throws IOException {
        mediaType = MediaType.parse("text/plain");

        // get authorization header for request
        Map<String, String> requestParams = getUrlValues(URL);

        // divide main url from its parameters
        String url_without_parameters = URL;
        if (URL.contains("?")) {
            url_without_parameters = URL.substring(0, URL.indexOf("?"));
        }

        String authorization_header = generator.generateHeader(httpMethod, url_without_parameters, requestParams);

        Log.d("SMA_App", authorization_header);


        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();

        // Send request data to the twitter server
        Request request = null;
        if (httpMethod == "GET"){
            request = new Request.Builder()
                    .addHeader("Authorization", authorization_header)
                    .method("GET", null)
                    .url(URL)
                    .build();
        } else if (httpMethod == "POST") {
            RequestBody body = RequestBody.create(mediaType, "");
            request = new Request.Builder()
                    .addHeader("Authorization", authorization_header)
                    .method("POST", body)
                    .url(URL)
                    .build();
        }

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
