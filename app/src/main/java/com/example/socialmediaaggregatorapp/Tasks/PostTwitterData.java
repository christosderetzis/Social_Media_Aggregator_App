package com.example.socialmediaaggregatorapp.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.socialmediaaggregatorapp.Services.TwitterService;

import java.io.IOException;

public class PostTwitterData extends AsyncTask<String, Void, String> {
    private TwitterService twitterService;

    public PostTwitterData() {
        twitterService = new TwitterService();
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];

        String response = null;
        try {
            response = twitterService.handleTwitterData(url, "POST");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("SMA_APP", response);

        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        String result = s;
    }
}
