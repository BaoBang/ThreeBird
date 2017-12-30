package com.example.baobang.threebird.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.utils.Constants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by baobang on 12/27/17.
 */

public class OrderAdapter extends ArrayAdapter<Order> {
    private Activity context;
    private int resource;
    private List<Order> objects;
    private List<Order> tempObjects;

    public OrderAdapter(@NonNull Activity context, int resource, @NonNull List<Order> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.resource = resource;
        tempObjects = new ArrayList<>(objects);
    }

    public void setTempObjects(List<Order> tempObjects) {
        this.tempObjects = tempObjects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        convertView = layoutInflater.inflate(this.resource, null);

        TextView txtId = convertView.findViewById(R.id.txtOrderId);
        TextView txtClientName = convertView.findViewById(R.id.txtClientName);
        TextView txtCreatedAt = convertView.findViewById(R.id.txtCreatedAt);
        TextView txtPayment = convertView.findViewById(R.id.txtPayment);
        TextView txtStatus =  convertView.findViewById(R.id.txtStatus);

        Order order = this.objects.get(position);
        txtId.setText(String.valueOf(order.getId()));
        txtClientName.setText(order.getClientName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyyy", new Locale("vi", "VN"));
        txtCreatedAt.setText(simpleDateFormat.format(order.getCreatedAt()));
        txtPayment.setText(String.valueOf(order.getToal()));
        if(order.getStatus() == Constants.COMPLETED){
                txtStatus.setText(R.string.complete);
                txtStatus.setTextColor(this.context.getResources().getColor(R.color.green));
        }else if(order.getStatus() == Constants.CANCEL){
            txtStatus.setText(R.string.destroy);
            txtStatus.setTextColor(this.context.getResources().getColor(R.color.red));
        }else{
            txtStatus.setText(R.string.delivery);
            txtStatus.setTextColor(this.context.getResources().getColor(R.color.blue));
        }


        return convertView;
    }
    @NonNull
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
                ArrayList<Order> filter = new ArrayList<>();
                for (Order order : tempObjects) {
                    // the filtering itself:
                    if (order.toString().toLowerCase().contains(filterSeq))
                        filter.add(order);
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
            ArrayList<Order> filtered = (ArrayList<Order>) filterResults.values;
            notifyDataSetChanged();
            clear();
            for(Order order : filtered){
                add(order);
            }
            notifyDataSetInvalidated();

        }
    }
}
