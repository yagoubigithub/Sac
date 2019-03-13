package com.aek.yagoubi.sac;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.aek.yagoubi.sac.Databases_P.AjouterClient;

import java.util.ArrayList;

public class AjouterArticles extends AppCompatActivity {
    AutoCompleteTextView edit_text_client_name;
    AjouterClient database;
    Cursor res;
    EditText e;
    ImageButton ajouter_picture_btn;
    ArrayList<String> fileNames;
    ArrayList<Client> clients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_articles);
        edit_text_client_name = (AutoCompleteTextView) findViewById(R.id.edit_text_client_name);


        database = new AjouterClient(this);
        res = database.getAllClients();

        clients = new ArrayList<>();


        while (res.moveToNext()) {

            clients.add(new Client(res.getInt(0), res.getString(1), res.getString(2)));


        }
        edit_text_client_name.setThreshold(1);

       final  ClientDropDownAdapert adapert = new ClientDropDownAdapert(this,clients);
        edit_text_client_name.setAdapter(adapert);


        edit_text_client_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i("Columns", adapert.getItem(position).toString() + " = "+ clients.get(position).getNom());

            }
        });

        ajouter_picture_btn = (ImageButton) findViewById(R.id.ajouter_picture_btn);
        ajouter_picture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AjouterArticles.this, getPictureActivity.class);


                startActivityForResult(intent1,1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == AjouterArticles.RESULT_OK) {
                // result camera
                String[] fileNamesArray =  data.getStringExtra("fileNames").split(",");
                for (int i = 0;i <fileNamesArray.length;i++){

                    fileNames.add(fileNamesArray[i]);
                }

            }

        }
    }
}
