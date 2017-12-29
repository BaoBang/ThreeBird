package com.example.baobang.threebird.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.utils.MySupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baobang on 12/19/17.
 */

public class ProductAdapter extends ArrayAdapter<Product> {

    private Activity context;
    private int resource;
    private List<Product> objects;
    private List<Product> tempObjects;
    private int itemSelected = -1;
    public ProductAdapter(@NonNull Activity context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        tempObjects = new ArrayList<>(objects);
    }

    public void setItemSelected(int itemSelected) {
        this.itemSelected = itemSelected;
    }

    public void setTempObjects(List<Product> tempObjects) {
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
        ImageView imgProduct = convertView.findViewById(R.id.imgProduct);
        TextView txtProductName = convertView.findViewById(R.id.txtProductName);
        TextView txtPrice = convertView.findViewById(R.id.txtPrice);

        Product product = this.objects.get(position);
        if(product.getImages().size() > 0){
            imgProduct.setImageBitmap(MySupport.getRoundedRectBitmap(MySupport.StringToBitMap(product.getImages().first())));
        }
        txtProductName.setText(product.getName());
        txtPrice.setText(product.getPrice() + "");

        return convertView;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return new ProductFilter();
    }

    private class ProductFilter extends Filter{

        public ProductFilter() {
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String filterSeq = charSequence.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (filterSeq != null && filterSeq.length() > 0) {
                ArrayList<Product> filter = new ArrayList<>();
                for (Product product : tempObjects) {
                    // the filtering itself:
                    if (product.toString().toLowerCase().contains(filterSeq))
                        filter.add(product);
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
            ArrayList<Product> filtered = (ArrayList<Product>) filterResults.values;
            notifyDataSetChanged();
            clear();
            for(Product product : filtered){
                add(product);
            }
            notifyDataSetInvalidated();

        }
    }
}
