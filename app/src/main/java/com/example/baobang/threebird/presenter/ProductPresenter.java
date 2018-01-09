package com.example.baobang.threebird.presenter;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.baobang.threebird.model.Brand;
import com.example.baobang.threebird.model.Category;
import com.example.baobang.threebird.model.Product;

import java.util.HashMap;
import java.util.List;

/**
 * Created by baobang on 1/8/18.
 */

public interface ProductPresenter {

    void loadSpinnerData();
    int addProduct(String name, int categoryId, int brandId,
                int inventory, int priceInventory,int price,String detail
            , HashMap<Integer,Bitmap> bitmaps);
    int updateProduct(Product product,String name, int categoryId, int brandId,
                      int inventory, int priceInventory,int price,String detail
            , HashMap<Integer,Bitmap> bitmaps);
    Product getProductFromBundle(Bundle bundle);
    int getOptionFromBundle(Bundle bundle);
    void setData(Product product);
    void setBrandSelected(List<Brand> brands, int brandId);
    void setCategorySelected(List<Category> categories, int categoryId);
}
