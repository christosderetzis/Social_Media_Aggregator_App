package com.example.socialmediaaggregatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.socialmediaaggregatorapp.Adapters.HashtagArrayAdapter;
import com.example.socialmediaaggregatorapp.Models.Hashtag;
import com.example.socialmediaaggregatorapp.Services.TwitterOauthHeaderGenerator;
import com.example.socialmediaaggregatorapp.Tasks.GetTwitterHashtagTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "https://api.twitter.com/1.1/trends/place.json?id=23424833";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView hashtagListView = (ListView) findViewById(R.id.hashtagListView);

        HashtagArrayAdapter adapter = new HashtagArrayAdapter(this, R.layout.hashtag_item, new ArrayList<Hashtag>(), hashtagListView);
        GetTwitterHashtagTask getTwitterHashtagTask = new GetTwitterHashtagTask(adapter);
        getTwitterHashtagTask.execute(URL);
    }
}