package com.aek.yagoubi.sac;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.aek.yagoubi.sac.Databases_P.AjouterClient;

import java.util.ArrayList;

public class ListDesArticles extends AppCompatActivity {

    AjouterClient database;
    Cursor res;
    ArrayList<SacClient> sacs;
    SacClientAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_des_articles);
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


        EditText serch_input_list_des_articles = (EditText) findViewById(R.id.serch_input_list_des_articles);

        serch_input_list_des_articles.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<SacClient> newArraySearch = new ArrayList<>();

                if (s == null || s.length() == 0) {
                    adapter = new SacClientAdapter(ListDesArticles.this, sacs);
                } else {
                    for (SacClient c : sacs) {
                        if (c.getSac().getNom().toUpperCase().contains(s.toString().toUpperCase())) {
                            Log.i("Filter", c.getSac().getNom());

                            newArraySearch.add(c);
                        }
                    }
                    adapter = new SacClientAdapter(ListDesArticles.this, newArraySearch);
                }
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    @Override
    protected void onResume() {
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

        super.onResume();
    }
}
