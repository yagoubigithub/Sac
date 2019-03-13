package com.aek.yagoubi.sac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    ArrayList<String> fileNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Intent intent = getIntent();
        fileNames = new ArrayList<>();
        String[] fileNamesArray =  intent.getStringExtra("fileNames").split(",");
        for (int i = 0;i <fileNamesArray.length;i++){

            if(fileNamesArray[i].length()> 0){
                fileNames.add(fileNamesArray[i]);
               
            }


        }

        GridView gridView = (GridView)findViewById(R.id.gridView);
        final ImageGalleryAdapter adapter = new ImageGalleryAdapter(this, fileNames);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GalleryActivity.this,FullImageGalleryActivity.class);
                intent.putExtra("fileName",adapter.getItem(position));
                startActivity(intent);
            }
        });
        gridView.setAdapter(adapter);
    }
}
