package com.aek.yagoubi.sac;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SacClientAdapter   extends ArrayAdapter<SacClient>  {
    Context myContext;




    public SacClientAdapter(Context context, ArrayList<SacClient> sacClients) {
        super(context, 0,sacClients);
        this.myContext = context;



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



        return listItemView;
    }





}
