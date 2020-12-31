package com.example.socialmediaaggregatorapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.socialmediaaggregatorapp.Models.Hashtag;
import com.example.socialmediaaggregatorapp.R;

import java.util.List;

public class HashtagArrayAdapter extends ArrayAdapter<Hashtag> {
    private List<Hashtag> hashtags;
    private final LayoutInflater inflater;
    private final int layoutResource;
    private ListView hashtagListView;


    public HashtagArrayAdapter(@NonNull Context context, int resource, @NonNull List<Hashtag> objects, ListView listView) {
        super(context, resource, objects);
        hashtagListView = listView;
        hashtags = objects;
        inflater = LayoutInflater.from(context);
        layoutResource = resource;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return hashtags.size();
    }

    @Nullable
    @Override
    public Hashtag getItem(int position) {
        return (hashtags != null && hashtags.size() >= position) ? hashtags.get(position) : null;
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

        Hashtag currentHashtag = hashtags.get(position);

        viewHolder.position.setText((position + 1) + ".");
        viewHolder.hashtagText.setText(currentHashtag.getName());

        if (currentHashtag.getTweet_volume() != 0) {
            viewHolder.tweetVolume.setText(currentHashtag.getTweet_volume() + " tweets");
        } else {
            viewHolder.tweetVolume.setText(" ");
        }

        return convertView;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        if (this.hashtags != null) {
            this.hashtags = null;
        }
        this.hashtags = hashtags;
        hashtagListView.setAdapter(this);
    }

    private class ViewHolder {
        final TextView position;
        final TextView hashtagText;
        final TextView tweetVolume;

        ViewHolder(View view) {
            position = view.findViewById(R.id.NumberID);
            hashtagText = view.findViewById(R.id.hashtagTxt);
            tweetVolume = view.findViewById(R.id.NumberOfTweetsTxt);
        }
    }
}
