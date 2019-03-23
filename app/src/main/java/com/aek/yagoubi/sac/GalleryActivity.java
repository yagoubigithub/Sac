package com.aek.yagoubi.sac;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.aek.yagoubi.sac.Databases_P.AjouterClient;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    ArrayList<String> fileNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        fileNames = new ArrayList<>();
        Intent intent = getIntent();
       int article_id = intent.getIntExtra("article_id",-1);



        AjouterClient database = new AjouterClient(this);

        Cursor res = database.getArticleImages(article_id);


        while (res.moveToNext()){
            if(res.getString(2).length() > 2){
                fileNames.add(res.getString(2));
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
