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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.socialmediaaggregatorapp.R;

import java.net.URI;

public class CreatePostActivity extends AppCompatActivity {

    private Button imagePickerBtn;
    private ImageView imagePost;
    private EditText textPost;
    private Button uploadPostBtn;
    private CheckBox facebookCheckBox;
    private CheckBox twitterCheckBox;
    private CheckBox instagramCheckBox;

    public static final String TAG = "SMA_APP";
    public static final int REQUEST_CODE_READ_IMAGE = 1;
    public static boolean READ_IMAGE_GRANTED = false;

    private Uri imageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // Initialize UI Components
        imagePickerBtn = (Button) findViewById(R.id.pickPhotoBtn);
        imagePost = (ImageView) findViewById(R.id.postImage);
        textPost = (EditText) findViewById(R.id.postText);
        uploadPostBtn = (Button) findViewById(R.id.uploadPostBtn);
        facebookCheckBox = (CheckBox) findViewById(R.id.facebookCheckBox);
        twitterCheckBox = (CheckBox) findViewById(R.id.twitterCheckBox);
        instagramCheckBox = (CheckBox) findViewById(R.id.instagramCheckBox);

        imagePost.setVisibility(View.GONE);
        imagePickerBtn.setVisibility(View.VISIBLE);

        imagePickerBtn.setOnClickListener((listener) -> {
            checkPermissions();

            pickImageFromResources();
        });

        String text = textPost.getText().toString();

        uploadPostBtn.setOnClickListener((listener) -> {
            if (facebookCheckBox.isChecked()){

            }

            if (twitterCheckBox.isChecked()){

            }

            if (instagramCheckBox.isChecked()){

            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_READ_IMAGE && resultCode == Activity.RESULT_OK){
            imagePost.setVisibility(View.VISIBLE);
            imagePickerBtn.setVisibility(View.GONE);

            imageURI = data.getData();
            imagePost.setImageURI(imageURI);

        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
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

    private class UploadPostTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}