package com.example.baobang.threebird.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.annimator.RecyclerViewAnimator;
import com.example.baobang.threebird.annimator.VegaLayoutManager;
import com.example.baobang.threebird.listener.OnItemRecyclerViewClickListener;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.utils.MySupport;

import java.util.ArrayList;
import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientHolder> implements Filterable{


    private List<Client> clients;
    private List<Client> tempClients;
    private RecyclerViewAnimator mAnimator;
    private RecyclerView recyclerView;

    private OnItemRecyclerViewClickListener onItemClickListener;

    public ClientAdapter(List<Client> clients, RecyclerView recyclerView, OnItemRecyclerViewClickListener onItemClickListener) {
        this.clients = clients;
        this.tempClients = new ArrayList<>(this.clients);
        this.recyclerView = recyclerView;
        mAnimator = new RecyclerViewAnimator(recyclerView);
        this.onItemClickListener = onItemClickListener;
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
    public void onBindViewHolder(ClientHolder holder, final int position) {

        Client client = this.clients.get(position);
        holder.txtAddress.setText(client.getAddress().toString());
        holder.txtName.setText(client.getName());
        if(client.getAvatar() != null && client.getAvatar().length() > 0){
            Bitmap avatar = MySupport.StringToBitMap(client.getAvatar());
            holder.imgAvatar.setImageBitmap(MySupport.getRoundedRectBitmap(avatar));
        }else{
            holder.imgAvatar.setImageResource(R.drawable.noimage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(clients.get(position));
            }
        });

        mAnimator.onBindViewHolder(holder.layoutItem, position);
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }


    @Override
    public Filter getFilter() {
        return new ClientFilter(this);
    }


    private class ClientFilter extends Filter{

        private ClientAdapter clientAdapter;

        public ClientFilter(ClientAdapter clientAdapter) {
            this.clientAdapter = clientAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            Log.e("charSequence", charSequence.toString());
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
                clients = filter;

            } else {
                // add all objects
                result.values = tempClients;
                result.count = tempClients.size();
                clients = tempClients;
            }
            Log.e("result", result.count + "");
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clientAdapter = new ClientAdapter(clients, recyclerView, onItemClickListener);
            VegaLayoutManager vegaLayoutManager = (VegaLayoutManager) recyclerView.getLayoutManager();
            vegaLayoutManager.setDeafaut();
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
