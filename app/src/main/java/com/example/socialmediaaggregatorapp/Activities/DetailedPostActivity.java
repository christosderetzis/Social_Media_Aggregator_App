package com.example.socialmediaaggregatorapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.socialmediaaggregatorapp.Models.Post;
import com.example.socialmediaaggregatorapp.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.time.format.DateTimeFormatter;

public class DetailedPostActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView username;
    private ImageView socialMediaIcon;
    private ImageView postImage;
    private TextView postDescription;
    private TextView postDateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_post);

        // Initialize GUIs for detailed post
        profileImage = (ImageView) findViewById(R.id.profileImage);
        username = (TextView) findViewById(R.id.userName);
        socialMediaIcon = (ImageView) findViewById(R.id.socialMediaImage);
        postImage = (ImageView) findViewById(R.id.postImage);
        postDescription = (TextView) findViewById(R.id.postText);
        postDateTime = (TextView) findViewById(R.id.dateCreatedText);

        // Get intent from PostsActivity
        Intent intent = getIntent();
        Post post = (Post) intent.getSerializableExtra("post");

        // Update data to post elements

        // Load profile image of user if there is one
        if (post.getProfileImage() != null){
            Picasso.get().load(post.getProfileImage()).into(profileImage);
        } else {
            profileImage.setImageResource(R.drawable.unknown_user);
        }

        // Update username
        if (post.getUsername() != null){
            username.setText(post.getUsername());
        } else {
            username.setText("Unknown user");
        }

        // Update type icon
        if (post.getType().equals(Post.socialMediaType.twitter)){
            socialMediaIcon.setImageResource(R.drawable.twitter);
        } else if (post.getType().equals(Post.socialMediaType.instagram)) {
            socialMediaIcon.setImageResource(R.drawable.instagram);
        } else {
            socialMediaIcon.setImageResource(R.drawable.facebook);
        }

        // update post image
        if (post.getPostImage() != null){
            postImage.setVisibility(View.VISIBLE);
            Picasso.get().load(post.getPostImage()).resize(400,400).centerInside().into(postImage);
        } else {
            postImage.setVisibility(View.GONE);
        }

        // update post description
        if (post.getPostDescription() != null){
            postDescription.setVisibility(View.VISIBLE);
            postDescription.setText(post.getPostDescription());
        } else {
            postDescription.setVisibility(View.GONE);
        }

        // Update post dateTime
        postDateTime.setText(post.getDate());
    }


}