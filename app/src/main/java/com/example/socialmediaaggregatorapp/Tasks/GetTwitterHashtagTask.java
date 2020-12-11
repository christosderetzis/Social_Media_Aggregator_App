package com.example.socialmediaaggregatorapp.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.socialmediaaggregatorapp.Adapters.HashtagArrayAdapter;
import com.example.socialmediaaggregatorapp.JsonParsers.TwitterJsonParser;
import com.example.socialmediaaggregatorapp.Models.Hashtag;
import com.example.socialmediaaggregatorapp.Services.TwitterService;

import java.io.IOException;
import java.util.List;

public class GetTwitterHashtagTask  extends AsyncTask<String, Void, List<Hashtag>> {
    public static final String TAG = "SMA_App";

    public List<Hashtag> hashtagList;
    private HashtagArrayAdapter adapter;
    private TwitterService twitterService;

    public GetTwitterHashtagTask(HashtagArrayAdapter adapter) {
        this.adapter = adapter;
        twitterService = new TwitterService();
    }


    @Override
    protected List<Hashtag> doInBackground(String... strings) {
        String url = strings[0];
        Log.d(TAG, "Doing task in background for url: "  + url);
        String hashtagJson = null;
        try {
            hashtagJson = twitterService.downloadHashtagData(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TwitterJsonParser jsonParser = new TwitterJsonParser();
        return jsonParser.parseHashtagData(hashtagJson);
    }

    @Override
    protected void onPostExecute(List<Hashtag> hashtags) {
        hashtagList = hashtags;
        Log.d(TAG, "Just got results!");

        for (Hashtag hashtag: hashtagList) {
            Log.i(TAG, hashtag.toString());
        }

        adapter.setHashtags(hashtags);
    }
}
