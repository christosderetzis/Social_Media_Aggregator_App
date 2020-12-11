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
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = (JSONArray) jsonObject.get("trends");

            for (int i=0; i<jsonArray.length();i++) {
                JSONObject hashtagJsonObject = jsonArray.getJSONObject(i);
                String name = hashtagJsonObject.getString(TWEET_NAME);
                String query = hashtagJsonObject.getString(TWEET_QUERY);
                int tweet_volume = hashtagJsonObject.getInt(TWEET_VOLUME);

                Hashtag hashtag = new Hashtag();
                hashtag.setName(name);
                hashtag.setQuery(query);
                hashtag.setTweet_volume(tweet_volume);
                hashtagList.add(hashtag);
            }
        } catch (JSONException e) {
            Log.d(TAG, "Error while parsing hashtag json data");
        }
         return hashtagList;
    }
}
