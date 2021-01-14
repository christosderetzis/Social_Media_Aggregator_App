package com.example.socialmediaaggregatorapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.socialmediaaggregatorapp.R;

import java.io.InputStream;

public class CreateStoryActivity extends AppCompatActivity {

    private Button imagePickerBtn;
    private ImageView imageStory;
    private Button uploadStoryBtn;
    private CheckBox facebookCheckBox;
    private CheckBox twitterCheckBox;
    private CheckBox instagramCheckBox;

    private Uri imageURI;
    private InputStream imageStream;
    private Bitmap selectedImageBitmap;

    public static final int REQUEST_CODE_READ_IMAGE = 1;
    public static final String TAG = "SMA_APP";
    public static boolean READ_IMAGE_GRANTED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_story);

        // Initialize UI Components
        imagePickerBtn = findViewById(R.id.pickPhotoBtn);
        imageStory = findViewById(R.id.imageStory);
        uploadStoryBtn = findViewById(R.id.uploadStoryBtn);
        facebookCheckBox = (CheckBox) findViewById(R.id.facebookCheckBox);
        twitterCheckBox = (CheckBox) findViewById(R.id.twitterCheckBox);
        instagramCheckBox = (CheckBox) findViewById(R.id.instagramCheckBox);

        imageStory.setVisibility(View.GONE);
        imagePickerBtn.setVisibility(View.VISIBLE);

        imagePickerBtn.setOnClickListener((listener) -> {
            checkPermissions();

            pickImageFromResources();
        });

        uploadStoryBtn.setOnClickListener((listener) -> {
            if (facebookCheckBox.isChecked()){
                storyToFacebook();
            } else if (instagramCheckBox.isChecked()){
                storyToInstagram();
            } else if (twitterCheckBox.isChecked()){
                storyToTwitter();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == REQUEST_CODE_READ_IMAGE && resultCode == Activity.RESULT_OK){
                imageStory.setVisibility(View.VISIBLE);

                imageURI = data.getData();
                imageStream = getContentResolver().openInputStream(imageURI);
                selectedImageBitmap = BitmapFactory.decodeStream(imageStream);

                imageStory.setImageBitmap(selectedImageBitmap);
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){
            Log.d(TAG, "Exception in file image: " + e.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_READ_IMAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    Log.d(TAG, "Permission Granted!");
                    READ_IMAGE_GRANTED = true;
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d(TAG, "Permission refused");

                }
                return;
        }
    }

    private void checkPermissions() {
        int hasReadImagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);


        if(hasReadImagePermission == PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "Permission Granted");
            READ_IMAGE_GRANTED = true;
        }
        else{
            Log.d(TAG, "Requesting permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_IMAGE);
        }
    }

    private void pickImageFromResources() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_READ_IMAGE);
    }

    private void storyToFacebook(){
        if (imageURI != null){
            String appId = getString(R.string.facebook_app_id);

            // Instantiate implicit intent with ADD_TO_STORY action
            Intent intent = new Intent("com.facebook.stories.ADD_TO_STORY");
            intent.setDataAndType(imageURI, "image/*");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra("com.facebook.platform.extra.APPLICATION_ID", appId);

            // Instantiate activity and verify it will resolve implicit intent
            Activity activity = this;
            if (activity.getPackageManager().resolveActivity(intent, 0) != null) {
                activity.startActivityForResult(intent, 0);
            }
        } else {
            Toast.makeText(this, "Please provide an image for the facebook story", Toast.LENGTH_LONG).show();
        }
    }

    private void storyToInstagram() {
        if (imageURI != null) {
            // Instantiate implicit intent with ADD_TO_STORY action and background asset
            Intent intent = new Intent("com.instagram.share.ADD_TO_STORY");
            intent.setDataAndType(imageURI, "image/*");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Instantiate activity and verify it will resolve implicit intent
            Activity activity = this;
            if (activity.getPackageManager().resolveActivity(intent, 0) != null) {
                activity.startActivityForResult(intent, 0);
            }
        } else {
            Toast.makeText(this, "Please provide an image for the instagram story", Toast.LENGTH_LONG).show();
        }
    }

    private void storyToTwitter() {
        if (imageURI != null){
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            share.putExtra(Intent.EXTRA_STREAM, imageURI);
            share.setPackage("com.twitter.android");
            startActivity(Intent.createChooser(share, "ShareTo"));
        } else {
            Toast.makeText(this, "Please provide an image for the twitter story", Toast.LENGTH_LONG).show();
        }
    }
}