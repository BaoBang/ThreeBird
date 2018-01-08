package com.example.baobang.threebird.presenter;

import com.example.baobang.threebird.model.Product;

/**
 * Created by baobang on 1/8/18.
 */

public interface ProductPresenter {

    void loadSpinnerData();
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
}
