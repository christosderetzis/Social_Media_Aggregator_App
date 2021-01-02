package com.example.socialmediaaggregatorapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialmediaaggregatorapp.Adapters.PostsArrayAdapter;
import com.example.socialmediaaggregatorapp.Models.Hashtag;
import com.example.socialmediaaggregatorapp.Models.Post;
import com.example.socialmediaaggregatorapp.R;
import com.example.socialmediaaggregatorapp.Tasks.GetPostsTask;

import java.util.ArrayList;

public class PostsActivity extends AppCompatActivity {

    private TextView postsTitle;
    private ListView postsListView;

    private PostsArrayAdapter postsArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        postsTitle = (TextView) findViewById(R.id.postsTitle);
        postsListView = (ListView) findViewById(R.id.postListView);

        postsArrayAdapter = new PostsArrayAdapter(this, R.layout.post_item, new ArrayList<Post>(), postsListView);

        Intent intent = getIntent();
        Hashtag hashtag = (Hashtag) intent.getSerializableExtra("hashtag");
        postsTitle.setText("Famous posts containing hashtag: " + hashtag.getName());

        GetPostsTask postsTask = new GetPostsTask(hashtag, postsArrayAdapter);
        postsTask.execute();

        postsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Post post = (Post) adapterView.getItemAtPosition(i);

                Log.d("SMA_APP", "New hashtag");
                Log.d("SMA_APP", post.toString());
            }
        });

    }
}