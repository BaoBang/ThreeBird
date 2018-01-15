package com.example.baobang.threebird.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
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
import com.example.baobang.threebird.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        this.mAnimator = new RecyclerViewAnimator(recyclerView);
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
    public void onBindViewHolder(final ClientHolder holder, int position) {
        holder.setPostion(position);
        Client client = this.clients.get(position);
        holder.txtAddress.setText(client.getAddress().toString());
        holder.txtName.setText(client.getName());
        if(client.getAvatar() != null && client.getAvatar().length() > 0){
            Bitmap avatar = Utils.StringToBitMap(client.getAvatar());
            holder.imgAvatar.setImageBitmap(Utils.getRoundedRectBitmap(avatar));
        }else{
            holder.imgAvatar.setImageResource(R.drawable.noimage);
        }

        holder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(clients.get(holder.getPostion())));

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

        private ClientFilter(ClientAdapter clientAdapter) {
            this.clientAdapter = clientAdapter;
        }

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
                clients = filter;

            } else {
                // add all objects
                result.values = tempClients;
                result.count = tempClients.size();
                clients = tempClients;
            }
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

    class ClientHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txtName)
        TextView txtName;

        @BindView(R.id.txtAddress)
        TextView txtAddress;

        @BindView(R.id.imgAvatar)
        ImageView imgAvatar;

        @BindView(R.id.layoutItem)
        LinearLayout layoutItem;
        int postion = -1;

        private int getPostion() {
            return postion;
        }

        private void setPostion(int postion) {
            this.postion = postion;
        }

        private ClientHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
