package com.aek.yagoubi.sac;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.aek.yagoubi.sac.Databases_P.AjouterClient;


import java.util.ArrayList;
import java.util.List;

public class ClientActivity extends AppCompatActivity {
    Client client;
    AjouterClient database;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        ArrayList<Sac> sacs = new ArrayList<>();
        Intent intent = getIntent();
        client = (Client) intent.getSerializableExtra("client");


        final EditText editText_change_name = (EditText) findViewById(R.id.edit_text_modifier_le_nom_client);
        final EditText editText_change_tele = (EditText) findViewById(R.id.edit_text_modifier_le_tele_client);


        if (client != null) {

            editText_change_name.setText(client.getNom());
            editText_change_tele.setText(client.getNumeroTele());

            database = new AjouterClient(this);

            Cursor res = database.getAllArticlesById(client.getId());


            while (res.moveToNext()) {
                sacs.add(new Sac(res.getInt(0), res.getInt(1), res.getString(2),
                        res.getString(3), res.getString(4), res.getInt(5),
                        res.getDouble(6), res.getInt(7)));
            }


            SacAdapter adapter = new SacAdapter(this, sacs, client);
            ListView listView = (ListView) findViewById(R.id.list_view_articles_in_clientActivity);


            listView.setAdapter(adapter);


            //Delete client
            FloatingActionButton delete_client_btn = (FloatingActionButton) findViewById(R.id.delete_client_btn);

            delete_client_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ClientActivity.this);

                    builder.setMessage("Suprimer :" + client.getNom())
                            .setTitle("Suprimer");
                    builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean b = database.deleteClientByHisId(client.getId());
                            if (b)
                                finish();
                            else
                                Toast.makeText(ClientActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });


            //call client

            FloatingActionButton call_client_btn = (FloatingActionButton) findViewById(R.id.call_client_btn);

            if (client.getNumeroTele().equals("")) {
                call_client_btn.setVisibility(View.GONE);
            } else {
                call_client_btn.setVisibility(View.VISIBLE);
            }

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
            FloatingActionButton ajouter_article_btn = (FloatingActionButton) findViewById(R.id.ajouter_article_btn);

            ajouter_article_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(ClientActivity.this, AjouterSac.class);
                    intent1.putExtra("client", client);


                    startActivity(intent1);
                }
            });


            //update client
            FloatingActionButton update_client_information = (FloatingActionButton) findViewById(R.id.update_client_information);

            update_client_information.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = editText_change_name.getText().toString();
                    String Tele = editText_change_tele.getText().toString();
                    int id = client.getId();
                    boolean b = database.updateClientInformation(id, name, Tele);

                    if (b) {
                        //refresh
                        if (android.os.Build.VERSION.SDK_INT >= 11) {

                            recreate();

                        } else {
                            finish();
                        }



                    } else {
                        Toast.makeText(ClientActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }

                }
            });


        }
    }


    @Override
    protected void onResume() {
        AjouterClient database = new AjouterClient(this);

        ArrayList<Sac> sacs = new ArrayList<>();
        Cursor res = database.getAllArticlesById(client.getId());


        while (res.moveToNext()) {
            sacs.add(new Sac(res.getInt(0), res.getInt(1), res.getString(2),
                    res.getString(3), res.getString(4), res.getInt(5),
                    res.getDouble(6), res.getInt(7)));
        }


        SacAdapter adapter = new SacAdapter(this, sacs, client);
        ListView listView = (ListView) findViewById(R.id.list_view_articles_in_clientActivity);


        listView.setAdapter(adapter);

        super.onResume();
    }
}
