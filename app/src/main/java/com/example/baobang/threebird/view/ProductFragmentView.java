package com.example.baobang.threebird.view;

import android.view.View;

import com.example.baobang.threebird.listener.OnItemRecyclerViewClickListener;
import com.example.baobang.threebird.model.Brand;
import com.example.baobang.threebird.model.Category;
import com.example.baobang.threebird.model.Product;

import java.util.ArrayList;

/**
 * Created by baobang on 1/10/18.
 */

public interface ProductFragmentView {
    void addControls(View view);
    void addToolBar(View view);
    void showMessage(String message);
    void showRecyclerViewProduct(ArrayList<Product> products);

    void addSpinnerCategory(View view, ArrayList<Category> categories);
    void addSpinnerBrand(View view, ArrayList<Brand> brands);
    void addSpinnerSortBy(View view);

    void updateRecyclerView(ArrayList<Product> products);
}
