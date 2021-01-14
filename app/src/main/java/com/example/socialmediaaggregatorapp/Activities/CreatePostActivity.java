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
import com.example.socialmediaaggregatorapp.Tasks.CreateTwitterPost;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class CreatePostActivity extends AppCompatActivity {

    private Button imagePickerBtn;
    private ImageView imagePost;
    private EditText textPost;
    private Button uploadPostBtn;
    private CheckBox facebookCheckBox;
    private CheckBox twitterCheckBox;
    private CheckBox instagramCheckBox;

    private Uri imageURI;
    private InputStream imageStream;
    private Bitmap selectedImageBitmap;
    private ByteArrayInputStream encodedImageBAOS;

    private String text;

    private ShareDialog shareDialog;

    public static final String TAG = "SMA_APP";
    public static final int REQUEST_CODE_READ_IMAGE = 1;
    public static boolean READ_IMAGE_GRANTED = false;

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

        shareDialog = new ShareDialog(this);

        imagePickerBtn.setOnClickListener((listener) -> {
            checkPermissions();

            pickImageFromResources();
        });

        uploadPostBtn.setOnClickListener((listener) -> {
            text = textPost.getText().toString();
            if (facebookCheckBox.isChecked()){
                postToFacebook();
            }

            if (twitterCheckBox.isChecked()){
                postToTwitter();
            }

            if (instagramCheckBox.isChecked()){
                postToInstagram();
            }
        });

    }

    private void postToFacebook() {
        if (selectedImageBitmap != null){
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(selectedImageBitmap)
                    .build();
            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();

            shareDialog.show(content);

        }
    }


    private void postToTwitter() {
        if (selectedImageBitmap != null || text != null){
            CreateTwitterPost createTwitterPost = new CreateTwitterPost(this, encodedImageBAOS, text);
            createTwitterPost.execute();
        } else {
            Toast.makeText(this, "Please, provide a text or an image for the twitter post", Toast.LENGTH_LONG).show();
        }
    }

    private void postToInstagram() {
        if (imageURI != null){
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            share.putExtra(Intent.EXTRA_STREAM, imageURI);
            share.setPackage("com.instagram.android");
            startActivity(Intent.createChooser(share, "ShareTo"));
        } else {
            Toast.makeText(this, "Please provide an image for the instagram post", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == REQUEST_CODE_READ_IMAGE && resultCode == Activity.RESULT_OK){
                imagePost.setVisibility(View.VISIBLE);

                imageURI = data.getData();
                imageStream = getContentResolver().openInputStream(imageURI);
                selectedImageBitmap = BitmapFactory.decodeStream(imageStream);
                encodedImageBAOS = encodeImage(selectedImageBitmap);

                imagePost.setImageBitmap(selectedImageBitmap);
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

    private ByteArrayInputStream encodeImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b);

        return byteArrayInputStream;
    }
}