package com.aek.yagoubi.sac;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SacActivity extends AppCompatActivity {

   Client client;
   Sac sac;
   EditText edit_text_change_article_name,edit_text_change_article_prix,edit_text_change_article_qte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sac);
        Intent intent = getIntent();
        client = (Client) intent.getSerializableExtra("client");
        sac = (Sac) intent.getSerializableExtra("sac");

        edit_text_change_article_name =(EditText) findViewById(R.id.edit_text_change_article_name);
        edit_text_change_article_prix =(EditText) findViewById(R.id.edit_text_change_article_prix);
        edit_text_change_article_qte =(EditText) findViewById(R.id.edit_text_change_article_qte);

        edit_text_change_article_name.setText(sac.getNom());

        edit_text_change_article_prix.setText(sac.getPrix() + "");

        edit_text_change_article_qte.setText(sac.getQte() + "");


    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"finish", Toast.LENGTH_LONG).show();
        finish();
    }
}


