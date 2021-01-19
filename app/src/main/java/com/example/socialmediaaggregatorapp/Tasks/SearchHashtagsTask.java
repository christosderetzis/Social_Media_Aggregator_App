package com.example.socialmediaaggregatorapp.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.socialmediaaggregatorapp.Adapters.HashtagArrayAdapter;
import com.example.socialmediaaggregatorapp.Models.Hashtag;
import com.example.socialmediaaggregatorapp.Services.TwitterService;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SearchHashtagsTask extends AsyncTask<String, Void, List<Hashtag>> {
    public static final String TAG = "SMA_App";
    public static final String BASE_URL = "https://api.twitter.com/1.1/search/tweets.json?q=";
    public static final String RESPONSE_TWEETS = "statuses";
    public static final String TWEET_ENTITIES = "entities";
    public static final String TWEET_HASHTAGS = "hashtags";
    public static final String HASHTAG_TEXT = "text";

    private String url;
    private TwitterService twitterService;
    private HashtagArrayAdapter adapter;
    private List<Hashtag> hashtags;

    public SearchHashtagsTask(Context context, String searchQuery, HashtagArrayAdapter adapter) {
        try {
            url = BASE_URL + URLEncoder.encode("#"+searchQuery, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        twitterService = new TwitterService(context);
        this.adapter = adapter;
    }

    public List<Hashtag> parseSearchedHashtags(String searchedJsonData) {
        List<Hashtag> hashtagList = new ArrayList<>();

        try {
            JSONArray statuses = new JSONObject(searchedJsonData).getJSONArray(RESPONSE_TWEETS);

            for (int i=0; i<statuses.length(); i++) {
                JSONObject status = statuses.getJSONObject(i);
                JSONArray hashtags = status.getJSONObject(TWEET_ENTITIES).getJSONArray(TWEET_HASHTAGS);

                for (int j=0;j<hashtags.length();j++) {
                    String hashtagText = hashtags.getJSONObject(j).getString(HASHTAG_TEXT);
                    String hashtag_full = "#" + hashtagText;

                    // Check if hashtag already exists in the hashtagList
                    boolean hashtagExists = false;
                    for (Hashtag hashtag : hashtagList) {
                        if (hashtag.getName().equals(hashtag_full))
                            hashtagExists = true;
                    }

                    // if hashtag does not exist in the arraylist, add it to the list
                    if (!hashtagExists) {
                        Hashtag hashtag = new Hashtag();
                        hashtag.setName(hashtag_full);
                        hashtag.setQuery(URLEncoder.encode(hashtag_full, "UTF-8"));
                        hashtag.setTweet_volume(0);

                        hashtagList.add(hashtag);
                    }
                }
            }
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while parsing hashtag json data");
        }

        return hashtagList;
    }

    @Override
    protected List<Hashtag> doInBackground(String... strings) {
        String search_url = url;
        Log.d(TAG, "Doing task in background for url: "  + url);

        String searchDataJson = null;
        try {
            searchDataJson = twitterService.handleTwitterData(search_url, "GET");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, searchDataJson);
        List<Hashtag> results = parseSearchedHashtags(searchDataJson);
        return results;
    }

    @Override
    protected void onPostExecute(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
        adapter.setHashtags(this.hashtags);
    }
}
