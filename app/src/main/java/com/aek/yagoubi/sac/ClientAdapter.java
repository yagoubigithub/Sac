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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClientAdapter extends ArrayAdapter<Client> {

    Context myContext;
    public ClientAdapter(Context context, ArrayList<Client> clients) {
        super(context, 0,clients);
        this.myContext = context;

    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_client_item, parent, false);
        }

        final Client client = getItem(position);

        TextView nomClient = (TextView)listItemView.findViewById(R.id.list_item_nom_client);
        final TextView numClient = (TextView)listItemView.findViewById(R.id.list_item_num_client);
        LinearLayout myClientItemLinearLayout = (LinearLayout)listItemView.findViewById(R.id.myClientItemLinearLayout);



        myClientItemLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(myContext,ClientActivity.class);
               intent.putExtra("client",client);
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
        });

        FloatingActionButton deleteBtn = (FloatingActionButton)listItemView.findViewById(R.id.list_item_delete_client_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete Client.



                Toast.makeText(myContext, "Delete Client :" + client.getId(),Toast.LENGTH_LONG).show();



            }
        });


        nomClient.setText(client.getNom());
        numClient.setText(client.getNumeroTele());
        return listItemView;
    }

    public static class CameraPreview {
    }
}
