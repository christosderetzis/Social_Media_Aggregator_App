package com.example.socialmediaaggregatorapp.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.socialmediaaggregatorapp.Models.Hashtag;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SearchHashtagsTask extends AsyncTask<String, Void, List<Hashtag>> {
    public static final String TAG = "SMA_App";

    public List<Hashtag> parseSearchedHashtags(String searchedJsonData) {
        List<Hashtag> hashtagList = new ArrayList<>();

        try {
            JSONArray statuses = new JSONObject(searchedJsonData).getJSONArray("statuses");

            for (int i=0; i<statuses.length(); i++) {
                JSONObject status = statuses.getJSONObject(i);
                JSONArray hashtags = status.getJSONObject("entities").getJSONArray("hashtags");

                for (int j=0;j<hashtags.length();j++) {
                    String hashtagText = hashtags.getJSONObject(j).getString("text");

                    // Check if hashtag already exists in the hashtagList
                    boolean hashtagExists = false;
                    for (Hashtag hashtag : hashtagList) {
                        if (hashtag.getName().equals(hashtagText))
                            hashtagExists = true;
                    }
                    if (!hashtagExists) {
                        Hashtag hashtag = new Hashtag();
                        hashtag.setName(hashtagText);
                        hashtag.setQuery(URLEncoder.encode(hashtagText, StandardCharsets.UTF_8.toString()));
                        hashtag.setTweet_volume(0);

                        hashtagList.add(hashtag);
                    }
                }
            }
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while parsing hashtag json data");
        }

        return null;
    }

    @Override
    protected List<Hashtag> doInBackground(String... strings) {
        return null;
    }
}
