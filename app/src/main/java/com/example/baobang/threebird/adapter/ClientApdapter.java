package com.example.baobang.threebird.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.bussinesslogic.ClientBL;
import com.example.baobang.threebird.utils.MySupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by baobang on 12/14/17.
 */

public class ClientApdapter extends ArrayAdapter<Client>{
    Activity context;
    int resource;
    List<Client> objects, tempObjects;
    public ClientApdapter(@NonNull Activity context, int resource, @NonNull List<Client> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        tempObjects = new ArrayList<>(objects);
    }

    public void setTempObjects(List<Client> tempObjects) {
        this.tempObjects = tempObjects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.item_client, null);
        //TextView txtLetterName = convertView.findViewById(R.id.txtLetterName);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtAddress = convertView.findViewById(R.id.txtAddress);
        ImageView imgAvatar = convertView.findViewById(R.id.imgAvatar);

        Client client = this.objects.get(position);
        txtAddress.setText(client.getAddress().toString());
        txtName.setText(client.getName());
       // txtLetterName.setText(client.getName().substring(0,1).toUpperCase());
        if(client.getAvatar() != null && client.getAvatar().length() > 0){
            Bitmap avatar = MySupport.StringToBitMap(client.getAvatar());
            imgAvatar.setImageBitmap(MySupport.getRoundedRectBitmap(avatar));
        }else{
            imgAvatar.setImageResource(R.drawable.noimage);
        }
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new ClientFilter();
    }

    private class ClientFilter extends Filter{

        public ClientFilter() {
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String filterSeq = charSequence.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (filterSeq != null && filterSeq.length() > 0) {
                ArrayList<Client> filter = new ArrayList<>();
                for (Client client : tempObjects) {
                    // the filtering itself:
                    if (client.toString().toLowerCase().contains(filterSeq))
                        filter.add(client);
                }
                result.count = filter.size();
                result.values = filter;
            } else {
                // add all objects
                    result.values = tempObjects;
                    result.count = tempObjects.size();
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//            // NOTE: this function is *always* called from the UI thread.
            ArrayList<Client> filtered = (ArrayList<Client>) filterResults.values;
            notifyDataSetChanged();
            clear();
            for(Client client : filtered){
                add(client);
            }
            notifyDataSetInvalidated();

        }
    }

}
