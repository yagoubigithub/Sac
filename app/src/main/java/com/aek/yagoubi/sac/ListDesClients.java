package com.aek.yagoubi.sac;

import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aek.yagoubi.sac.Databases_P.AjouterClient;

import java.util.ArrayList;

public class ListDesClients extends AppCompatActivity {
    ListView listView;
    ArrayList<Client> clients;
    AjouterClient ajouterClient;
    Cursor res;
    ClientAdapter adapter;
    EditText serchInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_clients);
        clients =  new ArrayList<>();
        ajouterClient =new AjouterClient(this);
       res = ajouterClient.getAllClients();
        if (res.getCount() == 0) {
            //Clients list empty
           Toast.makeText(this,"La liste et vide",Toast.LENGTH_SHORT).show();
            return;
        }
        while (res.moveToNext()) {
            clients.add(new Client(res.getInt(0), res.getString(1), res.getString(2)));
        }
         adapter = new ClientAdapter(this,clients);
        listView = (ListView) findViewById(R.id.list_view_clients);


        listView.setAdapter(adapter);



        //Serch
      serchInput = (EditText)findViewById(R.id.edit_text_serch_client_by_name);


serchInput.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        ArrayList<Client> newArraySearch = new ArrayList<>();

        if(s == null || s.length() == 0){
            adapter = new ClientAdapter(ListDesClients.this,clients);
        }else{
            for (Client c : clients){
                if (c.getNom().toUpperCase().contains( s.toString().toUpperCase() )) {
                    Log.i("Filter",c.getNom());

                    newArraySearch.add(c);
                }
            }
            adapter = new ClientAdapter(ListDesClients.this,newArraySearch);
        }
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
});


    }


}
