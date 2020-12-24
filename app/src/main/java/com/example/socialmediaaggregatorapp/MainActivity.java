package com.example.socialmediaaggregatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.socialmediaaggregatorapp.Adapters.HashtagArrayAdapter;
import com.example.socialmediaaggregatorapp.Models.Hashtag;
import com.example.socialmediaaggregatorapp.Tasks.GetTrendingHashtagsTask;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView hashtagListView = (ListView) findViewById(R.id.hashtagListView);

        HashtagArrayAdapter adapter = new HashtagArrayAdapter(this, R.layout.hashtag_item, new ArrayList<Hashtag>(), hashtagListView);
        GetTrendingHashtagsTask getTwitterHashtagTask = new GetTrendingHashtagsTask(adapter);
        getTwitterHashtagTask.execute();
    }
}