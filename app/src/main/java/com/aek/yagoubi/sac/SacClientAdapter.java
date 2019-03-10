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
                    R.layout.list_client_item, parent, false);
        }


        return listItemView;
    }





}
