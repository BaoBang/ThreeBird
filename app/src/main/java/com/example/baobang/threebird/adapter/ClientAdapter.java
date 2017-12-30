package com.example.baobang.threebird.adapter;

/**
 * Created by baobang on 12/14/17.
 */
import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.utils.MySupport;
import java.util.ArrayList;
import java.util.List;


public class ClientAdapter extends ArrayAdapter<Client>{
    private Activity context;
    private int resource;
    private List<Client> objects, tempObjects;
    private int itemSelected = -1;
    public ClientAdapter(@NonNull Activity context, int resource, @NonNull List<Client> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        tempObjects = new ArrayList<>(objects);
    }

    public void setItemSelected(int itemSelected) {
        this.itemSelected = itemSelected;
    }

    public void setTempObjects(List<Client> tempObjects) {
        this.tempObjects = tempObjects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        convertView = layoutInflater.inflate(this.resource, null);
        LinearLayout layout = convertView.findViewById(R.id.layoutItem);
        if(position == itemSelected){
            layout.setBackgroundColor(this.context.getResources().getColor(R.color.colorPrimary));
        }else{
            layout.setBackgroundColor(this.context.getResources().getColor(R.color.color_white));
        }
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtAddress = convertView.findViewById(R.id.txtAddress);
        ImageView imgAvatar = convertView.findViewById(R.id.imgAvatar);

        Client client = this.objects.get(position);
        txtAddress.setText(client.getAddress().toString());
        txtName.setText(client.getName());
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

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String filterSeq = charSequence.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (filterSeq.length() > 0) {
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
