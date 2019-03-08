package com.aek.yagoubi.sac;

import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.aek.yagoubi.sac.Databases_P.AjouterClient;

import java.util.ArrayList;

public class ListDesClients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_clients);
        final ArrayList<Client> clients =  new ArrayList<>();
       final AjouterClient ajouterClient =new AjouterClient(this);

        Cursor res = ajouterClient.getAllClients();
        if (res.getCount() == 0) {
            //Clients list empty
           Toast.makeText(this,"La liste et vide",Toast.LENGTH_SHORT).show();

            return;
        }



        while (res.moveToNext()) {
            clients.add(new Client(res.getInt(0),res.getString(1),res.getString(2)));


        }


        ClientAdapter adapter = new ClientAdapter(this,clients);
       final ListView listView = (ListView) findViewById(R.id.list_view_clients);


        listView.setAdapter(adapter);



        //Serch
       final TextInputEditText serchInput = (TextInputEditText)findViewById(R.id.edit_text_serch_client_by_name);

        serchInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
               String serchText =  serchInput.getText().toString();
                Cursor res =null;
                ArrayList<Client> clients =  new ArrayList<>();
               if(serchText.equals("")){
                res = ajouterClient.getAllClients();
               }else{
                res =  ajouterClient.getClientsByName(serchText);
               }

                if (res.getCount() == 0) {
                    //Clients list empty
                    Toast.makeText(ListDesClients.this,"La liste et vide",Toast.LENGTH_SHORT).show();

                }

                while (res.moveToNext()) {
                    clients.add(new Client(res.getInt(0),res.getString(1),res.getString(2)));

                }

                ClientAdapter adapter = new ClientAdapter(ListDesClients.this,clients);

                listView.setAdapter(adapter);

                return false;
            }
        });

    }
}
