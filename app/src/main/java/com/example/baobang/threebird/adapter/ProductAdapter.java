package com.example.baobang.threebird.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.annimator.RecyclerViewAnimator;
import com.example.baobang.threebird.annimator.VegaLayoutManager;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.utils.MySupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baobang on 12/19/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> implements Filterable{

    private List<Product> products;
    private List<Product> temproducts;
    private RecyclerViewAnimator mAnimator;
    private RecyclerView recyclerView;
    public ProductAdapter(List<Product> products, RecyclerView recyclerView) {
        this.products = products;
        this.temproducts = new ArrayList<>(this.products);
        this.recyclerView = recyclerView;
        mAnimator = new RecyclerViewAnimator(recyclerView);
    }

    @Override
    public ProductAdapter.ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_product, parent, false);
        ProductHolder productHolder = new ProductHolder(view);
        mAnimator.onCreateViewHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ProductHolder holder, int position) {
        Product product = this.products.get(position);
        if(product.getImages().size() > 0){
            holder.imgProduct.setImageBitmap(MySupport.getRoundedRectBitmap(MySupport.StringToBitMap(product.getImages().first())));
        }
        holder.txtProductName.setText(product.getName());
        holder.txtPrice.setText(String.valueOf(product.getPrice()));
        mAnimator.onBindViewHolder(holder.layoutItem, position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new ProductFilter(this);
    }

    private class ProductFilter extends Filter{
        private ProductAdapter productAdapter;
        public ProductFilter( ProductAdapter productAdapter) {
            this.productAdapter = productAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String filterSeq = charSequence.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (filterSeq != null && filterSeq.length() > 0) {
                ArrayList<Product> filter = new ArrayList<>();
                for (Product product : temproducts) {
                    // the filtering itself:
                    if (product.toString().toLowerCase().contains(filterSeq))
                        filter.add(product);
                }
                result.count = filter.size();
                result.values = filter;
                products = filter;
            } else {
                // add all objects
                result.values = temproducts;
                result.count = temproducts.size();
                products = temproducts;
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            productAdapter = new ProductAdapter(products, recyclerView);
            VegaLayoutManager vegaLayoutManager = (VegaLayoutManager) recyclerView.getLayoutManager();
            vegaLayoutManager.setDeafaut();
            notifyDataSetChanged();
        }
    }

    public class ProductHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct;
        TextView txtProductName;
        TextView txtPrice;
        LinearLayout layoutItem;
        public ProductHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            layoutItem = itemView.findViewById(R.id.layoutItem);
        }
    }
}
