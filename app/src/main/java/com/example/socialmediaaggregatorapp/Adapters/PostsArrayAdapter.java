package com.example.socialmediaaggregatorapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.socialmediaaggregatorapp.Models.Post;
import com.example.socialmediaaggregatorapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostsArrayAdapter extends ArrayAdapter<Post> {
    private List<Post> postsList;
    private final LayoutInflater inflater;
    private final int layoutResource;
    private ListView postsListView;

    public static final String FACEBOOK_ICON = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Facebook_logo_%28square%29.png/240px-Facebook_logo_%28square%29.png";
    public static final String TWITTER_ICON = "https://el.wikipedia.org/wiki/Twitter#/media/%CE%91%CF%81%CF%87%CE%B5%CE%AF%CE%BF:Twitter_bird_logo_2012.png";
    public static final String INSTAGRAM_ICON = "https://en.wikipedia.org/wiki/File:Instagram_logo_2016.svg";
    public static final String UNKNOWN_USER_ICON = "https://icon-library.com/images/unknown-person-icon/unknown-person-icon-4.jpg";

    public PostsArrayAdapter(@NonNull Context context, int resource, @NonNull List<Post> objects, ListView listView) {
        super(context, resource, objects);
        postsListView = listView;
        postsList = objects;
        inflater = LayoutInflater.from(context);
        layoutResource = resource;
    }

    @Override
    public int getCount() {
        return postsList.size();
    }

    @Nullable
    @Override
    public Post getItem(int position) {
        return (postsList != null && postsList.size() >= position) ? postsList.get(position) : null;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PostsArrayAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(layoutResource, parent, false);
            viewHolder = new PostsArrayAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
            Log.w("VIEW_HOLDER", "View Holder Created");
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Post post = postsList.get(position);

        // Load profile image of user if there is one
        if (post.getProfileImage().equals(null)){
            Picasso.get().load(UNKNOWN_USER_ICON).into(viewHolder.profileImage);
        } else {
            Picasso.get().load(post.getProfileImage()).into(viewHolder.profileImage);
        }

        // Update username
        if (post.getUsername().equals(null)){
            viewHolder.usernameText.setText("Unknown user");
        } else {
            viewHolder.usernameText.setText(post.getUsername());
        }

        // Update type icon
        switch (post.getType()) {
            case twitter:
                Picasso.get().load(TWITTER_ICON).into(viewHolder.typeImage);
            case facebook:
                Picasso.get().load(FACEBOOK_ICON).into(viewHolder.typeImage);
            case instagram:
                Picasso.get().load(INSTAGRAM_ICON).into(viewHolder.typeImage);
        }

        // update post image
        if (post.getPostImage().equals(null)){
            viewHolder.postImage.setVisibility(View.GONE);
        } else {
            Picasso.get().load(post.getPostImage()).into(viewHolder.postImage);
        }

        // update post description
        if (post.getPostDescription().equals(null)){
            viewHolder.postText.setVisibility(View.GONE);
        } else {
            viewHolder.postText.setText(post.getPostDescription());
        }

        return convertView;
    }

    public void setPosts(List<Post> posts) {
        if (this.postsList != null) {
            this.postsList = null;
        }
        this.postsList = posts;
        postsListView.setAdapter(this);
    }

    private class ViewHolder {
        final ImageView profileImage;
        final TextView usernameText;
        final ImageView typeImage;
        final ImageView postImage;
        final TextView postText;

        ViewHolder(View view) {
            profileImage = view.findViewById(R.id.profileImage);
            usernameText = view.findViewById(R.id.userName);
            typeImage = view.findViewById(R.id.socialMediaImage);
            postImage = view.findViewById(R.id.postImage);
            postText = view.findViewById(R.id.postText);
        }

    }
}
