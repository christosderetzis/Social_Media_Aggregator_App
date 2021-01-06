package com.example.socialmediaaggregatorapp.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.socialmediaaggregatorapp.Adapters.PostsArrayAdapter;
import com.example.socialmediaaggregatorapp.Models.Post;
import com.example.socialmediaaggregatorapp.Services.TwitterService;

import org.json.JSONObject;

import java.io.IOException;

public class PostTwitterData extends AsyncTask<String, Void, Post> {
    public static final String TAG = "SMA_APP";

    public static final String TWITTER_POSTS = "statuses";
    public static final String TWITTER_ID = "id";
    public static final String TWITTER_TIMESTAMP = "created_at";
    public static final String TWITTER_TEXT = "text";
    public static final String TWITTER_ENTITIES = "entities";
    public static final String TWITTER_MEDIA = "media";
    public static final String TWITTER_MEDIA_TYPE = "type";
    public static final String TWITTER_IMAGE_URL = "media_url_https";
    public static final String TWITTER_USER = "user";
    public static final String TWITTER_USERNAME = "screen_name";
    public static final String TWITTER_USER_IMAGE = "profile_image_url_https";
    public static final String TWITTER_LIKES = "favorite_count";
    public static final String TWITTER_RETWEETS = "retweet_count";
    public static final String TWITTER_FAVORITED = "favorited";
    public static final String TWITTER_RETWEETED = "retweeted";

    private TwitterService twitterService;
    private PostsArrayAdapter postsArrayAdapter;

    public PostTwitterData(PostsArrayAdapter adapter) {
        twitterService = new TwitterService();
        postsArrayAdapter = adapter;
    }

    private Post updatedPost(String jsonData) {
        Post post = null;
        try {
            JSONObject tweet_json = new JSONObject(jsonData);

            // Get id, date of creation and text from tweet
            long id = tweet_json.getLong(TWITTER_ID);
            String dateTime = tweet_json.getString(TWITTER_TIMESTAMP);
            String tweetText = tweet_json.getString(TWITTER_TEXT);


            // Check if twitter media exists inside twitter Entities object,
            // in order to retrieve the image of the tweet, if it exists.
            JSONObject twitterEntites = tweet_json.getJSONObject(TWITTER_ENTITIES);
            String tweetImage = null;
            if (twitterEntites.has(TWITTER_MEDIA)){
                JSONObject twitterMedia = twitterEntites.getJSONArray(TWITTER_MEDIA).getJSONObject(0);
                String mediaType = twitterMedia.getString(TWITTER_MEDIA_TYPE);

                if (mediaType.equals("photo")){
                    tweetImage = twitterMedia.getString(TWITTER_IMAGE_URL);
                }
            }

            // Get username and profile image of user
            JSONObject twitterUser = tweet_json.getJSONObject(TWITTER_USER);
            String username = twitterUser.getString(TWITTER_USERNAME);
            String profileImage = twitterUser.getString(TWITTER_USER_IMAGE);

            // Get followers and retweets of tweet
            int numberOfLikes = tweet_json.getInt(TWITTER_LIKES);
            int numberOfRetweets = tweet_json.getInt(TWITTER_RETWEETS);
            boolean favorited = tweet_json.getBoolean(TWITTER_FAVORITED);
            boolean retweeted = tweet_json.getBoolean(TWITTER_RETWEETED);

            // Build post object and add it to the list
            post = new Post();

            post.setUsername(username);
            post.setProfileImage(profileImage);
            post.setType(Post.socialMediaType.twitter);
            post.setId(id);
            post.setDate(dateTime);
            post.setPostDescription(tweetText);
            post.setPostImage(tweetImage);
            post.setNumberOfLikes(numberOfLikes);
            post.setNumberOfRetweets(numberOfRetweets);
            post.setNumberOfComments(null);
            post.setFavorited(favorited);
            post.setRetweeted(retweeted);
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "Error while parsing json data for Twitter Post");
        }
        return post;
    }

    @Override
    protected Post doInBackground(String... strings) {
        String url = strings[0];

        String response = null;
        try {
            response = twitterService.handleTwitterData(url, "POST");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("SMA_APP", response);

        Post updatedPost = updatedPost(response);
        return updatedPost;
    }

    @Override
    protected void onPostExecute(Post post) {
        Post updatedPost = post;
        postsArrayAdapter.updatePosts(updatedPost);
    }
}
