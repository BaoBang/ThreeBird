package com.example.baobang.threebird.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.annimator.RecyclerViewAnimator;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.utils.MySupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientHolder> implements Filterable{


    private List<Client> clients;
    private List<Client> tempClients;
    private RecyclerViewAnimator mAnimator;

    public ClientAdapter(List<Client> clients, RecyclerView recyclerView) {
        this.clients = clients;
        this.tempClients = new ArrayList<>(clients);
        mAnimator = new RecyclerViewAnimator(recyclerView);
    }


    @Override
    public ClientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_client, parent, false);
        ClientHolder clientHolder = new ClientHolder(view);
        mAnimator.onCreateViewHolder(view);
        return clientHolder;
    }

    @Override
    public void onBindViewHolder(ClientHolder holder, int position) {

        Client client = this.clients.get(position);
        holder.txtAddress.setText(client.getAddress().toString());
        holder.txtName.setText(client.getName());
        if(client.getAvatar() != null && client.getAvatar().length() > 0){
            Bitmap avatar = MySupport.StringToBitMap(client.getAvatar());
            holder.imgAvatar.setImageBitmap(MySupport.getRoundedRectBitmap(avatar));
        }else{
            holder.imgAvatar.setImageResource(R.drawable.noimage);
        }
        mAnimator.onBindViewHolder(holder.layoutItem, position);
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }


    @Override
    public Filter getFilter() {
        return new ClientFilter();
    }


    private class ClientFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String filterSeq = charSequence.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (filterSeq.length() > 0) {
                ArrayList<Client> filter = new ArrayList<>();
                for (Client client : tempClients) {
                    // the filtering itself:
                    if (client.toString().toLowerCase().contains(filterSeq))
                        filter.add(client);
                }
                result.values = filter;
                result.count = filter.size();
//                animateTo(filter);

            } else {
                // add all objects
                result.values = tempClients;
                result.count = tempClients.size();
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clients = (List<Client>) filterResults.values;
            notifyDataSetChanged();
        }
    }

    public class ClientHolder extends RecyclerView.ViewHolder{
        TextView txtName;
        TextView txtAddress;
        ImageView imgAvatar;
        LinearLayout layoutItem;
        public ClientHolder(View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            txtName = itemView.findViewById(R.id.txtName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
        }
    }
}
