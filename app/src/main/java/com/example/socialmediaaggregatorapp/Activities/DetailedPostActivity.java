package com.example.socialmediaaggregatorapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.socialmediaaggregatorapp.Adapters.PostsArrayAdapter;
import com.example.socialmediaaggregatorapp.Models.Post;
import com.example.socialmediaaggregatorapp.R;
import com.example.socialmediaaggregatorapp.Tasks.PostTwitterData;
import com.squareup.picasso.Picasso;

public class DetailedPostActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView username;
    private ImageView socialMediaIcon;
    private ImageView postImage;
    private TextView postDescription;
    private TextView postDateTime;
    private ImageButton heartButton;
    private TextView numberOfLikes;
    private ImageButton retweetButton;
    private TextView numberOfRetweets;
    private ImageButton commentButton;
    private TextView numberOfComments;

    private String url_like;
    private String url_unlike;
    private String url_retweet;
    private String url_unretweet;
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
        heartButton = (ImageButton) findViewById(R.id.heartButton);
        numberOfLikes = (TextView) findViewById(R.id.likesNumberTxt);
        retweetButton = (ImageButton) findViewById(R.id.retweetButton);
        numberOfRetweets = (TextView) findViewById(R.id.retweetsNumberTxt);
        commentButton = (ImageButton) findViewById(R.id.commentButton);
        numberOfComments = (TextView) findViewById(R.id.commentsNumberTxt);

        // Get intent from PostsActivity
        Intent intent = getIntent();
        Post post = (Post) intent.getSerializableExtra("post");

        // initialize urls based on post
        long tweet_id = post.getId();
        url_like = "https://api.twitter.com/1.1/favorites/create.json?id=" + tweet_id;
        url_unlike = "https://api.twitter.com/1.1/favorites/destroy.json?id=" + tweet_id;
        url_retweet = "https://api.twitter.com/1.1/statuses/retweet/" + tweet_id + ".json";
        url_unretweet = "https://api.twitter.com/1.1/statuses/unretweet/" + tweet_id + ".json";

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
            Picasso.get().load(post.getPostImage()).resize(300,300).centerInside().into(postImage);
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

        // update icons based on the social media type
        if (post.getType().equals(Post.socialMediaType.twitter)) {
            if (post.isFavorited()) {
                heartButton.setImageResource(R.drawable.heart_filled);
            } else {
                heartButton.setImageResource(R.drawable.heart);
            }
            heartButton.setOnClickListener(new View.OnClickListener() {
                PostTwitterData postTwitterData = new PostTwitterData();
                String url;
                @Override
                public void onClick(View view) {
                    if (!post.isFavorited()){
                        heartButton.setImageResource(R.drawable.heart_filled);
                        url = url_like;
                        numberOfLikes.setText((post.getNumberOfLikes() + 1) + " likes");
                        post.setFavorited(true);
                        post.setNumberOfLikes(post.getNumberOfLikes() + 1);
                    } else {
                        heartButton.setImageResource(R.drawable.heart);
                        url = url_unlike;
                        numberOfLikes.setText((post.getNumberOfLikes() - 1) + " likes");
                        post.setFavorited(false);
                        post.setNumberOfLikes(post.getNumberOfLikes() - 1);
                    }
                    postTwitterData.execute(url);
                }
            });
            numberOfLikes.setText(post.getNumberOfLikes() + " likes");

            retweetButton.setVisibility(View.VISIBLE);
            if (post.isRetweeted()){
                retweetButton.setImageResource(R.drawable.retweet_filled);
            } else {
                retweetButton.setImageResource(R.drawable.retweet);
            }
            numberOfRetweets.setVisibility(View.VISIBLE);
            numberOfRetweets.setText(post.getNumberOfRetweets() + " retweets");

            commentButton.setVisibility(View.GONE);
            numberOfComments.setVisibility(View.GONE);
        } else if (post.getType().equals(Post.socialMediaType.instagram)) {
            heartButton.setImageResource(R.drawable.heart);
            numberOfLikes.setText(post.getNumberOfLikes() + " likes");

            retweetButton.setVisibility(View.GONE);
            numberOfRetweets.setVisibility(View.GONE);

            commentButton.setVisibility(View.VISIBLE);
            numberOfComments.setText(post.getNumberOfComments() + " comments");
        }
    }
}