package com.example.socialmediaaggregatorapp.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.socialmediaaggregatorapp.Adapters.HashtagArrayAdapter;
import com.example.socialmediaaggregatorapp.Models.Hashtag;
import com.example.socialmediaaggregatorapp.Services.TwitterService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetTrendingHashtagsTask extends AsyncTask<String, Void, List<Hashtag>> {
    public static final String TAG = "SMA_App";
    public static final String TRENDS_URL = "https://api.twitter.com/1.1/trends/place.json?id=23424833";

    public static final String TWEET_NAME = "name";
    public static final String TWEET_QUERY = "query";
    public static final String TWEET_VOLUME = "tweet_volume";

    public List<Hashtag> hashtagList;
    private HashtagArrayAdapter adapter;
    private TwitterService twitterService;

    public GetTrendingHashtagsTask(Context context, HashtagArrayAdapter adapter) {
        this.adapter = adapter;
        twitterService = new TwitterService(context);
    }

    private List<Hashtag> parseHashtagData(String hashtagJsonData) {
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

                if (name.contains("#")){
                    Hashtag hashtag = new Hashtag();
                    hashtag.setName(name);
                    hashtag.setQuery(query);
                    hashtag.setTweet_volume(tweet_volume);

                    hashtagList.add(hashtag);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while parsing hashtag json data");
        }
        return hashtagList;
    }


    @Override
    protected List<Hashtag> doInBackground(String... strings) {
        String url = TRENDS_URL;
        Log.d(TAG, "Doing task in background for url: "  + url);

        String hashtagJson = null;
        try {
            hashtagJson = twitterService.handleTwitterData(url, "GET");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Hashtag> results = parseHashtagData(hashtagJson);
        return results;
    }

    @Override
    protected void onPostExecute(List<Hashtag> hashtags) {
        hashtagList = hashtags;
        adapter.setHashtags(hashtagList);
    }
}
