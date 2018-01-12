package com.example.baobang.threebird.adapter;

import android.support.annotation.NonNull;
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
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> implements Filterable{

    private List<Product> products;
    private List<Product> tempproducts;
    private RecyclerViewAnimator mAnimator;
    private RecyclerView recyclerView;
    private OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;

    public ProductAdapter(List<Product> products, RecyclerView recyclerView,OnItemRecyclerViewClickListener onItemRecyclerViewClickListener) {
        this.products = products;
        this.tempproducts = new ArrayList<>(this.products);
        this.recyclerView = recyclerView;
        mAnimator = new RecyclerViewAnimator(recyclerView);
        this.onItemRecyclerViewClickListener = onItemRecyclerViewClickListener;
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
    public void onBindViewHolder(ProductAdapter.ProductHolder holder, final int position) {
        Product product = this.products.get(position);
        if(product.getImages().size() > 0){
            holder.imgProduct.setImageBitmap(Utils.getRoundedRectBitmap(Utils.StringToBitMap(product.getImages().first())));
        }
        holder.txtProductName.setText(product.getName());
        String text = String.valueOf(product.getPrice()) + " VNĐ";
        holder.txtPrice.setText(text);
        text = "Số lượng: " + String.valueOf(product.getInvetory());
        holder.txtAmount.setText(text);
        holder.itemView.setOnClickListener(view -> onItemRecyclerViewClickListener.onItemClick(products.get(position)));

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
        private ProductFilter( ProductAdapter productAdapter) {
            this.productAdapter = productAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String filterSeq = charSequence.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (filterSeq.length() > 0) {
                ArrayList<Product> filter = new ArrayList<>();
                for (Product product : tempproducts) {
                    // the filtering itself:
                    if (product.toString().toLowerCase().contains(filterSeq))
                        filter.add(product);
                }
                result.count = filter.size();
                result.values = filter;
                products = filter;
            } else {
                // add all objects
                result.values = tempproducts;
                result.count = tempproducts.size();
                products = tempproducts;
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            productAdapter = new ProductAdapter(products, recyclerView, onItemRecyclerViewClickListener);
            VegaLayoutManager vegaLayoutManager = (VegaLayoutManager) recyclerView.getLayoutManager();
            vegaLayoutManager.setDeafaut();
            notifyDataSetChanged();
        }
    }

    public class ProductHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imgProduct)
        ImageView imgProduct;

        @BindView(R.id.txtProductName)
        TextView txtProductName;

        @BindView(R.id.txtPrice)
        TextView txtPrice;

        @BindView(R.id.txtAmount)
        TextView txtAmount;

        @BindView(R.id.layoutItem)
        LinearLayout layoutItem;
        private ProductHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
