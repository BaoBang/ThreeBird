package com.example.baobang.threebird.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.ProductOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by baobang on 1/8/18.
 */

public interface OrderPresenter {

    void init();
    void loadSpinnerData();
    void setData(Activity activity,Order order);

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

    void setPaymentSelected(int position);
    void setDistrictSelected(ArrayList<String> districts, String district);
    void setProvinceSelected(ArrayList<String> provinces, String province);
    void setCommuneSelected(ArrayList<String> communes, String commune);

    void addProductToLayout(List<ProductOrder> productList);

    int addOrder(int clientId, String name, Date date, int status , String phone,
            String province, String district, String commune,
                 String address, List<ProductOrder> products,
            Date deliveryDate,int payment);
    int updateOrder(Order order, int clientId, String name, Date date, int status , String phone,
                    String province, String district, String commune,
                    String address, List<ProductOrder> products,
                    Date deliveryDate,int payment);

    int setClientData(Activity activity, Client client);

    void showDialogClient();

    void showDialogProduct();
}
