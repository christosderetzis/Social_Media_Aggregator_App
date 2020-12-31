package com.example.socialmediaaggregatorapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialmediaaggregatorapp.Models.Hashtag;
import com.example.socialmediaaggregatorapp.R;

public class PostsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        TextView postsTitle = findViewById(R.id.postsTitle);

        Intent intent = getIntent();
        Hashtag hashtag = (Hashtag) intent.getSerializableExtra("hashtag");

        postsTitle.setText("Famous posts containing hashtag: " + hashtag.getName());
    }
}