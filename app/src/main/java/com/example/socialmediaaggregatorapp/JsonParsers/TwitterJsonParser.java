package com.example.socialmediaaggregatorapp.JsonParsers;

import android.util.Log;

import com.example.socialmediaaggregatorapp.Models.Hashtag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TwitterJsonParser {
    public static final String TAG = "Twitter Json Parser";

    public static final String TWEET_NAME = "name";
    public static final String TWEET_QUERY = "query";
    public static final String TWEET_VOLUME = "tweet_volume";

    public List<Hashtag> parseHashtagData(String hashtagJsonData) {
        List<Hashtag> hashtagList = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(hashtagJsonData);
            JSONObject body = array.getJSONObject(0);
            JSONArray hashtags = body.getJSONArray("trends");

            Log.d(TAG, "Length of json array: " + hashtags.length());
            for (int i=0; i<hashtags.length();i++) {
                JSONObject hashtagJsonObject = hashtags.getJSONObject(i);
                String name = hashtagJsonObject.getString(TWEET_NAME);
                String query = hashtagJsonObject.getString(TWEET_QUERY);
                String tweet_volume_string = hashtagJsonObject.getString(TWEET_VOLUME);

                Integer tweet_volume;
                if (tweet_volume_string != "null") {
                    tweet_volume = Integer.valueOf(tweet_volume_string);
                } else {
                    tweet_volume = 0;
                }
                Hashtag hashtag = new Hashtag();
                hashtag.setName(name);
                hashtag.setQuery(query);
                hashtag.setTweet_volume(tweet_volume);

                hashtagList.add(hashtag);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while parsing hashtag json data");
        }
         return hashtagList;
    }
}
