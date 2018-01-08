package com.example.baobang.threebird.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.ProductOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baobang on 1/8/18.
 */

public interface OrderPresenter {
    void init();
    Order getOrderFromBundle(Bundle bundle);
    int getOptionFromBundle(Bundle bundle);
    List<ProductOrder> getProductListFromOrder(Order order);
    Bitmap getImageFromProduct(Activity activity, Product product);
    int getAmountProduct(List<ProductOrder> productList, int id);
    int getAmountAllProduct(List<ProductOrder> productList);
    List<ProductOrder> getProductFromList(List<ProductOrder> productList, Product product);
    int checkProduct(List<ProductOrder> productList,Product product);
    Product checkInventory(List<ProductOrder> productList);
    void orderCompetition(List<ProductOrder> productList);
}
