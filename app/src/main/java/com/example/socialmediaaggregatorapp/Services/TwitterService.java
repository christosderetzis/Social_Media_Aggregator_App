package com.example.socialmediaaggregatorapp.Services;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class TwitterService {
    private String apiKey;
    private String apiSecret;

    public TwitterService(String key, String secret) {
        apiKey = key;
        apiSecret = secret;
    }


}
