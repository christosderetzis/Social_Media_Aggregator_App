package com.example.socialmediaaggregatorapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.socialmediaaggregatorapp.Adapters.HashtagArrayAdapter;
import com.example.socialmediaaggregatorapp.Models.Hashtag;
import com.example.socialmediaaggregatorapp.R;
import com.example.socialmediaaggregatorapp.Tasks.GetTrendingHashtagsTask;
import com.example.socialmediaaggregatorapp.Tasks.SearchHashtagsTask;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText searchQueryView;
    private Button searchButton;
    private ListView hashtagListView;
    private TextView resultsTitle;

    private HashtagArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize graphical components of main activity
        searchQueryView = (EditText) findViewById(R.id.hashtagSearchTxt);
        searchButton = (Button) findViewById(R.id.searchButton);
        resultsTitle = (TextView) findViewById(R.id.resultsTitle);
        hashtagListView = (ListView) findViewById(R.id.hashtagListView);

        // Initialize listView adapter
        adapter = new HashtagArrayAdapter(this, R.layout.hashtag_item, new ArrayList<Hashtag>(), hashtagListView);

        // Trending Hashtags Functionality
        GetTrendingHashtagsTask getTwitterHashtagTask = new GetTrendingHashtagsTask(this, adapter);
        getTwitterHashtagTask.execute();

        // Search hashtags functionality
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get text from input and update the title of the list
                String searchQuery = searchQueryView.getText().toString();
                resultsTitle.setText("Results for query: #" + searchQuery);

                // Get results from backend
                SearchHashtagsTask searchHashtagsTask = new SearchHashtagsTask(MainActivity.this, searchQuery, adapter);
                searchHashtagsTask.execute();
            }
        });

        hashtagListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Hashtag hashtag = (Hashtag) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, PostsActivity.class);
                intent.putExtra("hashtag", hashtag);

                startActivity(intent);
            }
        });
    }
}