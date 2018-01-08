package com.example.baobang.threebird.presenter;

import android.os.Bundle;

import com.example.baobang.threebird.model.Brand;
import com.example.baobang.threebird.model.Category;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.helper.BrandHelper;
import com.example.baobang.threebird.model.helper.CategoryHelper;
import com.example.baobang.threebird.model.helper.ProductHelper;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.view.ProductView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baobang on 1/8/18.
 */

public class ProductPresenterImp implements ProductPresenter{

    private ProductView productView;

    public ProductPresenterImp(ProductView productView) {
        this.productView = productView;
    }
    public void init(){
        productView.addControls();
        productView.addEvents();
        loadSpinnerData();
    }

    @Override
    public void loadSpinnerData() {
        productView.showSpinnerBrand((ArrayList<Brand>) getBrands());
        productView.showSpinnerCategory((ArrayList<Category>) getCategories());
    }

    @Override
    public int addProduct(Product product) {
        return ProductHelper.createProudct(product);
    }

    @Override
    public int updateProduct(Product product) {
        return ProductHelper.updateProduct(product);
    }

    @Override
    public Product getProductFromBundle(Bundle bundle) {
        int productId = bundle != null ? bundle.getInt(Constants.PRODUCT) : -1;
        if(productId == -1)
            return null;
        return ProductHelper.getProduct(productId);
    }

    @Override
    public int getOptionFromBundle(Bundle bundle) {
        return bundle == null ? Constants.ADD_OPTION : bundle.getInt(Constants.OPTION);
    }

    private List<Brand> getBrands(){
        return BrandHelper.getAllBrand();
    }

    private List<Category> getCategories(){
        return CategoryHelper.getAllCategory();
    }
}
