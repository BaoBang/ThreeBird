package com.example.baobang.threebird.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.utils.MySupport;

import java.util.List;

/**
 * Created by baobang on 12/19/17.
 */

public class ProductAdapter extends ArrayAdapter<Product> {

    private Activity context;
    private int resource;
    private List<Product> objects;

    public ProductAdapter(@NonNull Activity context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        convertView = layoutInflater.inflate(this.resource, null);

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
}
