package com.aek.yagoubi.sac;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aek.yagoubi.sac.Databases_P.AjouterClient;

import java.util.ArrayList;

public class SacClientAdapter   extends ArrayAdapter<SacClient>  {
    Context myContext;

    AjouterClient database;




    public SacClientAdapter(Context context, ArrayList<SacClient> sacClients) {
        super(context, 0,sacClients);
        this.myContext = context;
        database = new AjouterClient(context);



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_sacclient, parent, false);
        }

        final SacClient sacClient = getItem(position);

        TextView list_item_article_nom = (TextView) listItemView.findViewById(R.id.list_item_article_nom);
        TextView list_item_client_sacclient = (TextView) listItemView.findViewById(R.id.list_item_client_sacclient);
        TextView list_item_sacclient_prix = (TextView) listItemView.findViewById(R.id.list_item_sacclient_prix);
        TextView list_item_sacclient_prix_total = (TextView) listItemView.findViewById(R.id.list_item_sacclient_prix_total);
        TextView list_item_sacclient_qte = (TextView) listItemView.findViewById(R.id.list_item_sacclient_qte);


        list_item_article_nom.setText(sacClient.getSac().getNom());
        list_item_client_sacclient.setText("Nom de client : "  + sacClient.getClient().getNom());
        list_item_sacclient_prix.setText("Prix : " + sacClient.getSac().getPrix());
        list_item_sacclient_prix_total.setText("Prix Totale : " + (sacClient.getSac().getPrix() * sacClient.getSac().getQte()));
        list_item_sacclient_qte.setText("Quantité : " + sacClient.getSac().getQte());



        final CheckBox checkBox = (CheckBox) listItemView.findViewById(R.id.list_item_paye_Checkbox_sacclient);
        checkBox.setChecked(sacClient.getSac().isPayee());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //
                    boolean b =  database.updatePyee(sacClient.getSac().getId(),1);
                    if(!b){
                        checkBox.setChecked(false);
                        Toast.makeText(myContext, "Error",Toast.LENGTH_LONG).show();
                        ((Activity) myContext).finish();
                    }
                }else{
                    //
                    boolean b =   database.updatePyee(sacClient.getSac().getId(),0);
                    if(!b){
                        checkBox.setChecked(true);
                        Toast.makeText(myContext, "Error",Toast.LENGTH_LONG).show();
                        ((Activity) myContext).finish();
                    }
                }
            }
        });

        LinearLayout mySacClientItemLinearLyout = (LinearLayout)listItemView.findViewById(R.id.mySacClientItemLinearLyout);


        mySacClientItemLinearLyout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext,SacActivity.class);
                intent.putExtra("client",sacClient.getClient());
                intent.putExtra("sac",sacClient.getSac());
                myContext.startActivity(intent);


            }
        });
        return listItemView;
    }





}
