package com.example.baobang.threebird.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.Commune;
import com.example.baobang.threebird.model.District;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.Payment;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.ProductOrder;
import com.example.baobang.threebird.model.Province;
import com.example.baobang.threebird.model.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public interface OrderPresenter {

    void init();

    void loadSpinnerData();

    void setData(Activity activity,Order order);

    int setClientData(Activity activity, Client client);

    void setDistrictSelected(ArrayList<District> districts, int district);

    void setProvinceSelected(ArrayList<Province> provinces, int province);

    void setCommuneSelected(ArrayList<Commune> communes, int commune);

    Order getOrderFromBundle(Bundle bundle);

    int getOptionFromBundle(Bundle bundle);

    List<ProductOrder> getProductListFromOrder(Order order);

    Bitmap getImageFromProduct(Activity activity, Product product);

    int getAmountProduct(List<ProductOrder> productList, Product product);

    int getAmountAllProduct(List<ProductOrder> productList);

    int getStatusSelectedPosition(ArrayList<Status> statuses, int statusId);

    int getPaymentSelectedPosition(ArrayList<Payment> payments, int paymentId);

    List<ProductOrder> getProductFromList(List<ProductOrder> productList, Product product, int amount);

    int checkProduct(List<ProductOrder> productList,Product product);

    void orderCompetition(List<ProductOrder> productList);

    void addProductToLayout(List<ProductOrder> productList);

    int addOrder(int clientId, String name, Date date,
                 Status status, String phone, Province province,
                 District district, Commune commune, String address,
                 List<ProductOrder> products, Date deliveryDate, Payment payment);
    int updateOrder(Order order, int clientId, String name,
                    Date date, Status status, String phone,
                    Province province, District district, Commune commune,
                    String address, List<ProductOrder> products,
                    Date deliveryDate, Payment payment);

    void clickAddOptionMenu(Order order, int option, int clientId, String name,
                            Date date, Status status, String phone,
                            Province province, District district, Commune commune,
                            String address, List<ProductOrder> products,
                            Date deliveryDate, Payment payment);


    void showDialogClient();

    void showDialogProduct();

    boolean removeProductOrder(List<ProductOrder> productOrders, Product product);
}
