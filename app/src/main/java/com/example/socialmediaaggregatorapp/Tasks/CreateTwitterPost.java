package com.example.socialmediaaggregatorapp.Tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.socialmediaaggregatorapp.Services.TwitterService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CreateTwitterPost extends AsyncTask <String, Void, String>{

    public static final String TAG = "SMA_APP";
    public static final String MEDIA_ID = "media_id";

    private String image;
    private String text;
    private TwitterService twitterService;

    public CreateTwitterPost(String imageToUpload, String textToUpload) {
        image = imageToUpload;
        text = textToUpload;
        twitterService = new TwitterService();
    }

    private long getMediaId(String json_data) {
        long media_id = 0;
        try {
            JSONObject object = new JSONObject(json_data);
            media_id = object.getLong(MEDIA_ID);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return media_id;
    }

    @Override
    protected String  doInBackground(String... strings) {
        if (image != null){
            String initial_image_upload = "https://upload.twitter.com/1.1/media/upload.json?media=" + image;
            String dataJSON = null;
            try {
                dataJSON = twitterService.handleTwitterData(initial_image_upload, "POST", false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            long media_id = getMediaId(dataJSON);
            Log.d(TAG, "Data id is: " + dataJSON);

        } else {

        }

        return "yes";
    }

    @Override
    protected void onPostExecute(String s) {
        String result = s;
    }
}
