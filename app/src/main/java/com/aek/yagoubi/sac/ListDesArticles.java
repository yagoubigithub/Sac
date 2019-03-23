package com.aek.yagoubi.sac;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aek.yagoubi.sac.Databases_P.AjouterClient;
import com.aek.yagoubi.sac.com.google.zxing.integration.android.IntentIntegrator;
import com.aek.yagoubi.sac.com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class ListDesArticles extends AppCompatActivity {

    AjouterClient database;
    Cursor res;
    ArrayList<SacClient> sacs;
    SacClientAdapter adapter;
    ListView listView;
    FloatingActionButton showAjouterArticleBtn;
    Button codeBareBtn;
    String codeBare="";
    String codeBareFormat="";
    String clientName="";
    String articleName="";
    EditText serch_input_list_des_articles;
    EditText serch_input_by_client_name_list_des_articles;
    CheckBox see_all_checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getSupportActionBar().hide();
        setContentView(R.layout.activity_list_des_articles);
        see_all_checkbox = (CheckBox) findViewById(R.id.see_all_checkbox);
        database = new AjouterClient(this);

        sacs = new ArrayList<>();
        res = database.getAllArticlesAndClients();

        while (res.moveToNext()) {
            sacs.add(new SacClient(new Sac(res.getInt(0), res.getInt(1), res.getString(2),
                    res.getString(3), res.getString(4), res.getInt(5),
                    res.getFloat(6), res.getInt(7)),
                    new Client(res.getInt(1),
                            res.getString(8), res.getString(9)))
            );

        }
        adapter = new SacClientAdapter(this, sacs);


        listView = (ListView) findViewById(R.id.list_view_articles_in_ListDesArticlesActivity);


        listView.setAdapter(adapter);


         serch_input_list_des_articles = (EditText) findViewById(R.id.serch_input_list_des_articles);

         serch_input_list_des_articles.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {


                 sacs = new ArrayList<>();
                 articleName = serch_input_list_des_articles.getText().toString();
                 clientName = serch_input_by_client_name_list_des_articles.getText().toString();
                 res = database.searchArticlesAndClients(articleName,clientName,codeBare,codeBareFormat);

                 while (res.moveToNext()) {
                     sacs.add(new SacClient(new Sac(res.getInt(0), res.getInt(1), res.getString(2),
                             res.getString(3), res.getString(4), res.getInt(5),
                             res.getFloat(6), res.getInt(7)),
                             new Client(res.getInt(1),
                                     res.getString(8), res.getString(9)))
                     );
                 }
                 see_all_checkbox.setChecked(false);
                 adapter = new SacClientAdapter(ListDesArticles.this, sacs);
                 adapter.notifyDataSetChanged();
                 listView.setAdapter(adapter);
             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });
        serch_input_by_client_name_list_des_articles = (EditText) findViewById(R.id.serch_input_by_client_name_list_des_articles);


        showAjouterArticleBtn = (FloatingActionButton) findViewById(R.id.showAjouterArticleBtn);
        serch_input_by_client_name_list_des_articles.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                sacs = new ArrayList<>();
                articleName = serch_input_list_des_articles.getText().toString();
                clientName = serch_input_by_client_name_list_des_articles.getText().toString();
                res = database.searchArticlesAndClients(articleName,clientName,codeBare,codeBareFormat);

                while (res.moveToNext()) {
                    sacs.add(new SacClient(new Sac(res.getInt(0), res.getInt(1), res.getString(2),
                            res.getString(3), res.getString(4), res.getInt(5),
                            res.getFloat(6), res.getInt(7)),
                            new Client(res.getInt(1),
                                    res.getString(8), res.getString(9)))
                    );
                }
                see_all_checkbox.setChecked(false);
                adapter = new SacClientAdapter(ListDesArticles.this, sacs);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        showAjouterArticleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListDesArticles.this, AjouterArticles.class);
                startActivity(intent);
            }
        });

        codeBareBtn = (Button) findViewById(R.id.codeBareBtn);
        codeBareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(ListDesArticles.this);
                scanIntegrator.initiateScan();
            }
        });
        see_all_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    seeAll();
                }
            }
        });


    }

    public void seeAll(){
        sacs = new ArrayList<>();

        res = database.getAllArticlesAndClients();


        while (res.moveToNext()) {
            sacs.add(new SacClient(new Sac(res.getInt(0), res.getInt(1), res.getString(2),
                    res.getString(3), res.getString(4), res.getInt(5),
                    res.getFloat(6), res.getInt(7)),
                    new Client(res.getInt(1),
                            res.getString(8), res.getString(9)))
            );


        }
        adapter = new SacClientAdapter(this, sacs);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
       seeAll();

        super.onResume();
    }



    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            TextView codeBareTextView = (TextView) findViewById(R.id.codeBareTextView);
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            if(scanContent != null && scanFormat != null){
                codeBare = scanContent;
                codeBareFormat = scanFormat;

                codeBareTextView.setText(codeBare);
                sacs.clear();
                sacs = new ArrayList<>();
                articleName = serch_input_list_des_articles.getText().toString();
                clientName = serch_input_by_client_name_list_des_articles.getText().toString();
                AjouterClient database = new AjouterClient(this);

                res = database.searchArticlesAndClients(articleName,clientName,codeBare,codeBareFormat);

                while (res.moveToNext()) {
                    sacs.add(new SacClient(new Sac(res.getInt(0), res.getInt(1), res.getString(2),
                            res.getString(3), res.getString(4), res.getInt(5),
                            res.getFloat(6), res.getInt(7)),
                            new Client(res.getInt(1),
                                    res.getString(8), res.getString(9)))
                    );

                }
                Toast.makeText(this, sacs.size() + " ..." , Toast.LENGTH_SHORT).show();

                adapter = new SacClientAdapter(this, sacs);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
