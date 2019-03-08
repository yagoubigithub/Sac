package com.aek.yagoubi.sac;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.aek.yagoubi.sac.Databases_P.AjouterClient;


import java.util.ArrayList;

public class ClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        ArrayList<Sac> sacs = new ArrayList<>();
        Intent intent = getIntent();
        final Client client = (Client) intent.getSerializableExtra("client");


        EditText editText_change_name = (EditText)findViewById(R.id.edit_text_modifier_le_nom_client);
        EditText editText_change_tele = (EditText)findViewById(R.id.edit_text_modifier_le_tele_client);

        editText_change_name.setText(client.getNom());
        editText_change_tele.setText(client.getNumeroTele());


        AjouterClient database = new AjouterClient(this);

        Cursor res = database.getAllArticles();



        while (res.moveToNext()){
            sacs.add(new Sac(res.getInt(0),res.getInt(1), res.getString(2),
                    res.getString(3),res.getString(4), res.getInt(5),
                    res.getDouble(6)));
        }



        SacAdapter adapter = new SacAdapter(this,sacs);
        ListView listView = (ListView) findViewById(R.id.list_view_articles_in_clientActivity);


        listView.setAdapter(adapter);


        //Delete client
        FloatingActionButton delete_client_btn = (FloatingActionButton)findViewById(R.id.delete_client_btn);

        delete_client_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });


        //call client

        FloatingActionButton call_client_btn = (FloatingActionButton)findViewById(R.id.call_client_btn);

        call_client_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = String.format("tel: %s",
                        client.getNumeroTele());
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                // Set the data for the intent as the phone number.
                dialIntent.setData(Uri.parse(phoneNumber));
                // If package resolves to an app, send intent.
                if (dialIntent.resolveActivity(getPackageManager()) != null) {
                   startActivity(dialIntent);
                } else {
                    Log.e("Tag", "Can't resolve app for ACTION_DIAL Intent.");
                }
            }
        });

        //ajouter_article_btn
        FloatingActionButton ajouter_article_btn = (FloatingActionButton)findViewById(R.id.ajouter_article_btn);

        ajouter_article_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(ClientActivity.this,AjouterSac.class);
                intent1.putExtra("client", client);


                startActivity(intent1);
            }
        });



    }
}
