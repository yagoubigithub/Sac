package com.aek.yagoubi.sac;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

import com.aek.yagoubi.sac.Databases_P.AjouterClient;

import java.util.Currency;

public class MainActivity extends AppCompatActivity {

    LinearLayout btn_list_des_client,btn_ajouter_client,btn_list_des_articles,ajouter_articles_btn;
    CardView btn_list_des_articles_card,btn_list_des_clients_card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_list_des_client = (LinearLayout) findViewById(R.id.btn_list_des_clients);
        btn_ajouter_client = (LinearLayout) findViewById(R.id.btn_ajouter_client);
        btn_list_des_articles = (LinearLayout) findViewById(R.id.btn_list_des_articles);
        ajouter_articles_btn = (LinearLayout) findViewById(R.id.ajouter_articles_btn);



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
        // Ajouter Article

        ajouter_articles_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this,AjouterArticles.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        btn_list_des_articles_card = (CardView) findViewById(R.id.btn_list_des_articles_card);
        btn_list_des_clients_card = (CardView) findViewById(R.id.btn_list_des_clients_card);


        AjouterClient database = new AjouterClient(this);

        Cursor res = database.getAllClients();


        if (res.getCount() > 0){
            btn_list_des_clients_card.setCardBackgroundColor(Color.parseColor("#001f3f"));
            btn_list_des_client.setClickable(true);
        }else{
            btn_list_des_clients_card.setCardBackgroundColor(Color.rgb(200,200,200));
            btn_list_des_client.setClickable(false);
        }

         res = database.getAllArticlesAndClients();


        if (res.getCount() > 0){
            btn_list_des_articles_card.setCardBackgroundColor(Color.parseColor("#FF4136"));
            btn_list_des_articles.setClickable(true);
        }else{
            btn_list_des_articles_card.setCardBackgroundColor(Color.rgb(200,200,200));
            btn_list_des_articles.setClickable(false);
        }

        super.onResume();
    }
}
