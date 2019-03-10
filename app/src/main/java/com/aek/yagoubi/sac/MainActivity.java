package com.aek.yagoubi.sac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout btn_list_des_client,btn_ajouter_client,btn_list_des_articles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_list_des_client = (LinearLayout) findViewById(R.id.btn_list_des_clients);
        btn_ajouter_client = (LinearLayout) findViewById(R.id.btn_ajouter_client);
        btn_list_des_articles = (LinearLayout) findViewById(R.id.btn_list_des_articles);

        btn_list_des_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this,ListDesClients.class);
                startActivity(intent);

            }
        });

        // Ajouter Client
        btn_ajouter_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this,AjouterClientActivity.class);
                startActivity(intent);


            }
        });

        // List Articles
        btn_list_des_articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this,ListDesArticles.class);
                startActivity(intent);


            }
        });
    }
}
