package com.example.baobang.threebird.presenter;

import android.os.Bundle;

import com.example.baobang.threebird.model.Product;

/**
 * Created by baobang on 1/8/18.
 */

public interface ProductPresenter {

    void loadSpinnerData();
    int addProduct(Product product);
    int updateProduct(Product product);
    Product getProductFromBundle(Bundle bundle);
    int getOptionFromBundle(Bundle bundle);
}
