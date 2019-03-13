package com.aek.yagoubi.sac;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class ClientDropDownAdapert extends ArrayAdapter<Client>  implements Filterable {

    private ArrayList<Client> items;
    private ArrayList<Client> itemsAll;
    private ArrayList<Client> suggestions;
    public ClientDropDownAdapert(Context context, ArrayList<Client> clients) {
        super(context, 0,clients);
        this.items = clients;
        this.itemsAll = (ArrayList<Client>) clients.clone();
        this.suggestions = new ArrayList<Client>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.dropd_down_client_item, parent, false);
        }

        TextView name = (TextView) listItemView.findViewById(R.id.name_client_dropd_down);
        TextView tele = (TextView) listItemView.findViewById(R.id.tele_cllient_drop_down);

        final Client client = getItem(position);

        name.setText(client.getNom());
        tele.setText(client.getNumeroTele());




        return listItemView;

    }
    @Override
    public Filter getFilter() {
        return nameFilter;
    }
    Filter nameFilter = new Filter() {

        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Client) (resultValue)).getNom();
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (Client client : itemsAll) {
                    if(client.getNom().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(client);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            }else{
                return new FilterResults();
            }

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Client> filteredList = (ArrayList<Client>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (Client c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }


    };

}
