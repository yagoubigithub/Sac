package com.aek.yagoubi.sac;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.aek.yagoubi.sac.Databases_P.AjouterClient;

import java.util.ArrayList;

public class ListDesArticles extends AppCompatActivity {

    AjouterClient database;
    Cursor res;
    ArrayList<SacClient> sacs;

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
                    res.getDouble(6), res.getInt(7)),
                    new Client(res.getInt(1),
                            res.getString(8), res.getString(9)))
            );


        }






    }
}
