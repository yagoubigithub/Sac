package com.aek.yagoubi.sac;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FullImageGalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_gallery);
        ViewPager viewPager = (ViewPager) findViewById(R.id.gallery_viewPager);

        FullImageGalleryAdapter adapter = new FullImageGalleryAdapter(this);
        viewPager.setAdapter(adapter);
    }
}
