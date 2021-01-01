package com.example.socialmediaaggregatorapp.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.socialmediaaggregatorapp.Models.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetPostsTask extends AsyncTask<String, Void, List<Post>> {
    public static final String TAG = "SMA_APP";

    public static final String INSTA_DATA = "data";
    public static final String INSTA_ID = "id";
    public static final String INSTA_MEDIA_TYPE = "media_type";
    public static final String INSTA_COMMENTS = "comments_count";
    public static final String INSTA_LIKES = "likes_count";
    public static final String INSTA_IMAGE_URL = "media_url";
    public static final String INSTA_TIME = "timestamp";

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

    private int getHashtagID(String jsonData) {
        int id = Integer.parseInt(null);
        try {
            JSONArray data = new JSONObject(jsonData).getJSONArray(INSTA_DATA);
            JSONObject IdObject = data.getJSONObject(0);
            String ID_Str = IdObject.getString(INSTA_ID);
            id = Integer.parseInt(ID_Str);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while parsing json data for hashtag ID");
        }

        return id;
    }

    private List<Post> getInstagramPosts(String jsonData) {
        List<Post> instagram_posts = new ArrayList<>();
        try {
            JSONArray data = new JSONObject(jsonData).getJSONArray(INSTA_DATA);

            for (int i=0;i<data.length();i++){
                JSONObject instagramPost = data.getJSONObject(i);
                int id = Integer.parseInt(instagramPost.getString(INSTA_ID));
                String type_post = instagramPost.getString(INSTA_MEDIA_TYPE);
                int numberOfLikes = instagramPost.getInt(INSTA_LIKES);
                int numberOfComments = instagramPost.getInt(INSTA_COMMENTS);
                String photo_url = instagramPost.getString(INSTA_IMAGE_URL);
                String time = instagramPost.getString(INSTA_TIME);

                // check if the type of the post is an image
                if (type_post.equals("IMAGE")){
                    Post post = new Post();

                    post.setId(id);
                    post.setType(Post.socialMediaType.instagram);
                    post.setPostImage(photo_url);
                    post.setDate(time);
                    post.setNumberOfLikes(numberOfLikes);
                    post.setNumberOfComments(numberOfComments);
                    post.setNumberOfRetweets(null);
                    post.setPostDescription(null);
                    post.setProfileImage(null);
                    post.setUsername(null);

                    instagram_posts.add(post);
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
            Log.d(TAG, "Error while parsing json data for instagram Posts");
        }

        return instagram_posts;
    }



    private List<Post> getTwitterPosts(String jsonData) {
        List<Post> twitter_posts = new ArrayList<>();

        try {
            JSONArray tweets = new JSONObject(jsonData).getJSONArray(TWITTER_POSTS);

            for (int i=0;i<tweets.length();i++){
                JSONObject tweet_json = tweets.getJSONObject(i);

                // Get id, date of creation and text from tweet
                int id = tweet_json.getInt(TWITTER_ID);
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

                // Build post object and add it to the list
                Post post = new Post();

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

                twitter_posts.add(post);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while parsing json data for instagram Posts");
        }

        return twitter_posts;
    }


    @Override
    protected List<Post> doInBackground(String... strings) {
        return null;
    }
}
