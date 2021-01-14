package com.example.socialmediaaggregatorapp.Tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.socialmediaaggregatorapp.Activities.CreatePostActivity;
import com.example.socialmediaaggregatorapp.R;
import com.example.socialmediaaggregatorapp.Services.TwitterService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class CreateTwitterPost extends AsyncTask<String, Void, String> {
    private ConfigurationBuilder configurationBuilder;
    private Configuration configuration;
    private Twitter twitter;
    private StatusUpdate status;

    private Context context;
    private ByteArrayInputStream image;
    private String text;

    public CreateTwitterPost(Context context, ByteArrayInputStream image, String text) {
        // Setup twitter4j connection
        configurationBuilder = new ConfigurationBuilder()
                .setDebugEnabled(true)
                .setOAuthConsumerKey(context.getResources().getString(R.string.twitter_API_key))
                .setOAuthConsumerSecret(context.getResources().getString(R.string.twitter_API_key_secret))
                .setOAuthAccessToken(context.getResources().getString(R.string.twitter_Access_token))
                .setOAuthAccessTokenSecret(context.getResources().getString(R.string.twitter_Access_token_secret));
        configuration = configurationBuilder.build();
        twitter = new TwitterFactory(configuration).getInstance();

        this.context = context;
        this.image = image;
        this.text = text;
    }
    @Override
    protected String doInBackground(String... strings) {
        status = new StatusUpdate(text);
        if (image != null){
            status.setMedia("image", image);
        }
        try {
            twitter.updateStatus(status);
        } catch (twitter4j.TwitterException e) {
            e.printStackTrace();
        }

        return "Complete";
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context, "Tweet was uploaded successfully", Toast.LENGTH_LONG).show();
    }
}
