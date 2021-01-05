package com.example.socialmediaaggregatorapp.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.socialmediaaggregatorapp.Adapters.PostsArrayAdapter;
import com.example.socialmediaaggregatorapp.Models.Hashtag;
import com.example.socialmediaaggregatorapp.Models.Post;
import com.example.socialmediaaggregatorapp.Services.InstagramService;
import com.example.socialmediaaggregatorapp.Services.TwitterService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetPostsTask extends AsyncTask<String, Void, List<Post>> {
    public static final String TAG = "SMA_APP";

    public static final String INSTA_DATA = "data";
    public static final String INSTA_ID = "id";
    public static final String INSTA_MEDIA_TYPE = "media_type";
    public static final String INSTA_COMMENTS = "comments_count";
    public static final String INSTA_LIKES = "like_count";
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
    public static final String TWITTER_FAVORITED = "favorited";
    public static final String TWITTER_RETWEETED = "retweeted";

    private Hashtag searchHashtag;
    private PostsArrayAdapter postsArrayAdapter;
    private List<Post> postsList;
    private TwitterService twitterService;
    private InstagramService instagramService;

    private String instagram_hashtag_id_url;
    private String instagram_top_posts_url;
    private String twitter_posts_url;

    public GetPostsTask(Hashtag searchHashtag, PostsArrayAdapter adapter) {
        this.searchHashtag = searchHashtag;
        postsArrayAdapter = adapter;
        postsList = new ArrayList<>();
        twitterService = new TwitterService();
        instagramService = new InstagramService(Long.valueOf("17841444557880145"));
    }

    private String getHashtagID(String jsonData) {
        String id = null;
        try {
            JSONArray data = new JSONObject(jsonData).getJSONArray(INSTA_DATA);
            JSONObject IdObject = data.getJSONObject(0);
            id = IdObject.getString(INSTA_ID);
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
                long id = Long.parseLong(instagramPost.getString(INSTA_ID));
                String type_post = instagramPost.getString(INSTA_MEDIA_TYPE);
                int numberOfLikes = instagramPost.getInt(INSTA_LIKES);
                int numberOfComments = instagramPost.getInt(INSTA_COMMENTS);

                String photo_url = null;
                if (instagramPost.has(INSTA_IMAGE_URL)){
                    photo_url = instagramPost.getString(INSTA_IMAGE_URL);
                }
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
                    post.setFavorited(false);
                    post.setRetweeted(false);

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
                boolean favorited = tweet_json.getBoolean(TWITTER_FAVORITED);
                boolean retweeted = tweet_json.getBoolean(TWITTER_RETWEETED);

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
                post.setFavorited(favorited);
                post.setRetweeted(retweeted);

                twitter_posts.add(post);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while parsing json data for Twitter Posts");
        }

        return twitter_posts;
    }


    @Override
    protected List<Post> doInBackground(String... strings) {
        List<Post> posts = new ArrayList<>();

        long instagram_user_id = instagramService.getInstagramIdAccount();
        // Get hashtag ID from instagram api
        instagram_hashtag_id_url = "https://graph.facebook.com/ig_hashtag_search?user_id=" + instagram_user_id + "&q=" + searchHashtag.getName().substring(1);
        String hashtag_id_json = null;
        try {
            hashtag_id_json = instagramService.downloadInstagramData(instagram_hashtag_id_url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String id = getHashtagID(hashtag_id_json);
        Long hashtag_id = Long.parseLong(id);

        // Get posts from instagram api with the hashtag id
        instagram_top_posts_url = "https://graph.facebook.com/" + hashtag_id + "/top_media?user_id=" + instagram_user_id + "&fields=id,media_type,comments_count,like_count,media_url,timestamp";
        String instagram_posts_json = null;
        try {
            instagram_posts_json = instagramService.downloadInstagramData(instagram_top_posts_url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Post> instagram_posts = getInstagramPosts(instagram_posts_json);

        // Get posts from twitter api with the hashtag
        twitter_posts_url = "https://api.twitter.com/1.1/search/tweets.json?q=" + searchHashtag.getQuery();
        String twitter_posts_json = null;
        try {
            twitter_posts_json = twitterService.downloadTwitterData(twitter_posts_url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Post> twitter_posts = getTwitterPosts(twitter_posts_json);

        // add data from 2 social media into one arraylist
        posts.addAll(twitter_posts);
        posts.addAll(instagram_posts);

        return posts;
    }

    @Override
    protected void onPostExecute(List<Post> posts) {
        for (Post post: posts){
            Log.d(TAG, post.toString());
        }
        postsList = posts;
        postsArrayAdapter.setPosts(postsList);
    }
}
