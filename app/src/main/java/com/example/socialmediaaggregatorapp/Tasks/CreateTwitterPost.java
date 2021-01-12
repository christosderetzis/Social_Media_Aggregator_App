package com.example.socialmediaaggregatorapp.Tasks;

import android.os.AsyncTask;

import com.example.socialmediaaggregatorapp.Services.TwitterService;

public class CreateTwitterPost {

    private String image;
    private String text;
    private TwitterService twitterService;

    public CreateTwitterPost(String imageToUpload, String textToUpload) {
        image = imageToUpload;
        text = textToUpload;
        twitterService = new TwitterService();
    }

}
