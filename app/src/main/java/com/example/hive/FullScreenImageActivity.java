package com.example.hive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.example.hive.LoadPackage.ImageAdapter;
import com.example.hive.PostPackage.PostActivity;

public class FullScreenImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        NetworkImageView imageFullScreen = (NetworkImageView) findViewById(R.id.full_screen_image);

        Intent intentImage = getIntent();
        String url = intentImage.getExtras().getString("UrlImage");

        ImageLoader imageLoader = ImageAdapter.getInstance(this).getImageLoader();

        imageLoader.get(url,
                ImageLoader.getImageListener(
                        imageFullScreen,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                ));


        imageFullScreen.setImageUrl(url, imageLoader);
    }
}