package com.aek.yagoubi.sac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SacAdapter extends ArrayAdapter<Sac> {

    Context myContext;
    Client client;
    public SacAdapter(Context context, ArrayList<Sac> sacs,Client client) {
        super(context, 0,sacs);
        this.myContext = context;
        this.client = client;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_sac_item, parent, false);
        }

        final Sac sac = getItem(position);

        TextView sac_nom = (TextView) listItemView.findViewById(R.id.list_item_article_nom);
        TextView sac_prix = (TextView) listItemView.findViewById(R.id.list_item_article_prix);
        TextView sac_prix_total = (TextView) listItemView.findViewById(R.id.list_item_article_prix_total);
        TextView list_item_article_qte = (TextView) listItemView.findViewById(R.id.list_item_article_qte);

        sac_nom.setText(sac.getNom());

        int qte = sac.getQte();
        Double prix = sac.getPrix();

        list_item_article_qte.setText("Quantité : " + qte);


        sac_prix.setText("Prix :"  + sac.getPrix() + " $");
        sac_prix_total.setText("Prix Totale : " + (qte * prix) +  "$");
        CheckBox checkBox = (CheckBox) listItemView.findViewById(R.id.list_item_paye_Checkbox);
        checkBox.setChecked(sac.isPayee());

        LinearLayout mySacItemLinearLyout = (LinearLayout)listItemView.findViewById(R.id.mySacItemLinearLyout);


        mySacItemLinearLyout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext,SacActivity.class);
                intent.putExtra("client",client);
                intent.putExtra("sac",sac);
               myContext.startActivity(intent);


            }
        });
        FloatingActionButton DeleteBtn = (FloatingActionButton)listItemView.findViewById(R.id.list_item_delete_article_btn);
        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete Sac

                Toast.makeText(myContext, "Delete",Toast.LENGTH_LONG).show();


            }
        });




       /* TextView nomClient = (TextView)listItemView.findViewById(R.id.list_item_nom_client);
        final TextView numClient = (TextView)listItemView.findViewById(R.id.list_item_num_client);
        LinearLayout myClientItemLinearLayout = (LinearLayout)listItemView.findViewById(R.id.myClientItemLinearLayout);


        myClientItemLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext,ClientActivity.class);
                myContext.startActivity(intent);
            }
        });

        FloatingActionButton callBtn = (FloatingActionButton)listItemView.findViewById(R.id.list_item_btn_call);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the intent.

                String phoneNumber = String.format("tel: %s",
                        client.getNumeroTele());
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                // Set the data for the intent as the phone number.
                dialIntent.setData(Uri.parse(phoneNumber));
                // If package resolves to an app, send intent.
                if (dialIntent.resolveActivity(myContext.getPackageManager()) != null) {
                    myContext.startActivity(dialIntent);
                } else {
                    Log.e("Tag", "Can't resolve app for ACTION_DIAL Intent.");
                }


            }
        });*/

/*
        nomClient.setText(client.getNom());
        numClient.setText(client.getNumeroTele());*/
        return listItemView;
    }


}
