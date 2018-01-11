package com.example.baobang.threebird.presenter;

import android.app.Activity;
import android.view.View;

import com.example.baobang.threebird.listener.OnItemRecyclerViewClickListener;
import com.example.baobang.threebird.model.Brand;
import com.example.baobang.threebird.model.Category;
import com.example.baobang.threebird.model.Product;

import java.util.ArrayList;

/**
 * Created by baobang on 1/10/18.
 */

public interface ProductFragmentPresenter {
    void init(View view);
    int checkClients(ArrayList<Product> products, Product product);
    void deleteProduct(Activity activity, ArrayList<Product> products, int productId,
                       OnItemRecyclerViewClickListener onItemRecyclerViewClickListener);
    void doSort(Brand brand, Category category);
    void addProduct(ArrayList<Product> products, int productId, OnItemRecyclerViewClickListener onItemRecyclerViewClickListener);
}
