package com.example.socialmediaaggregatorapp.Services;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InstagramService {
    public static final String access_token = "EAALANmZACq14BAMrrdsBAmEIlW7kKCRYHIWDsymlALC9UEWvxyZACXGZAN9vXDPIhCutZB4FgGEjwBG0ZAs46bp0eSokr5WMDIhpShKO9PwYdMgmaF9k4dFqvxx0wxGaFzPZAaByzZCJs9NDS6MFjZBuOth2wexiDLo3mnXoeSQdvDDbJZAkzp1q9E8J5Vy5YHMYUexQZBcdb7DgZDZD";

    private Long instagramIdAccount;

    public InstagramService(Long id){
        instagramIdAccount = id;
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
