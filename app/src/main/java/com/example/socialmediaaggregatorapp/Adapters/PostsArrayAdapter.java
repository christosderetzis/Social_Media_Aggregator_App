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

    public static final String FACEBOOK_ICON = "https://www.vectorico.com/download/social_media/Facebook-Logo-Square.jpg";
    public static final String TWITTER_ICON = "https://www.vectorico.com/download/social_media/Twitter-Logo-Blue.jpg";
    public static final String INSTAGRAM_ICON = "https://www.tailorbrands.com/wp-content/uploads/2020/03/The_Instagram_Logo.jpg";
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
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            Log.w("VIEW_HOLDER", "View Holder Created");
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Post post = postsList.get(position);

        // Load profile image of user if there is one
        if (post.getProfileImage() != null){
            Picasso.get().load(post.getProfileImage()).into(viewHolder.profileImage);
        } else {
            viewHolder.profileImage.setImageResource(R.drawable.unknown_user);
        }

        // Update username
        if (post.getUsername() != null){
            viewHolder.usernameText.setText(post.getUsername());
        } else {
            viewHolder.usernameText.setText("Unknown user");
        }

        // Update type icon
        if (post.getType().equals(Post.socialMediaType.twitter)){
            viewHolder.typeImage.setImageResource(R.drawable.twitter);
        } else if (post.getType().equals(Post.socialMediaType.instagram)) {
            viewHolder.typeImage.setImageResource(R.drawable.instagram);
        } else {
            viewHolder.typeImage.setImageResource(R.drawable.facebook);
        }

        // update post image
        if (post.getPostImage() != null){
            viewHolder.postImage.setVisibility(View.VISIBLE);
            Picasso.get().load(post.getPostImage()).resize(200,200).centerInside().into(viewHolder.postImage);
        } else {
            viewHolder.postImage.setVisibility(View.GONE);

        }

        // update post description
        if (post.getPostDescription() != null){
            viewHolder.postText.setVisibility(View.VISIBLE);
            viewHolder.postText.setText(post.getPostDescription());
        } else {
            viewHolder.postText.setVisibility(View.GONE);
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
            profileImage = (ImageView) view.findViewById(R.id.profileImage);
            usernameText = (TextView) view.findViewById(R.id.userName);
            typeImage = (ImageView) view.findViewById(R.id.socialMediaImage);
            postImage = (ImageView) view.findViewById(R.id.postImage);
            postText = (TextView) view.findViewById(R.id.postText);
        }

    }
}
