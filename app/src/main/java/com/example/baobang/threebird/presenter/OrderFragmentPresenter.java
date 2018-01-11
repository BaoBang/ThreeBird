package com.example.baobang.threebird.presenter;

import android.app.Activity;
import android.view.View;

import com.example.baobang.threebird.model.Order;

import java.util.ArrayList;

/**
 * Created by baobang on 1/10/18.
 */

public interface OrderFragmentPresenter {
    void init(View view);
    int checkOrders(ArrayList<Order> orders, int orderId);
    void deleteOrder(Activity activity, ArrayList<Order> orders, int orderId);
    ArrayList<Order> getOrderListByStatus(int status);

    void addOrder(ArrayList<Order> orders, int orderId);
}
