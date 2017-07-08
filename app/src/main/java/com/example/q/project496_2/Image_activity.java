package com.example.q.project496_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by q on 2017-07-08.
 */

public class Image_activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.click_image);

        Intent intent = getIntent();
        String image = intent.getStringExtra("img");

        ImageView iv = (ImageView)findViewById(R.id.imageView2);
        Glide.with(this).load(image).into(iv);
    }
}
