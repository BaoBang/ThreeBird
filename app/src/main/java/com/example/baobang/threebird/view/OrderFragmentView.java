package com.example.baobang.threebird.view;

import android.view.View;

import com.example.baobang.threebird.model.Order;

import java.util.ArrayList;

/**
 * Created by baobang on 1/10/18.
 */

public interface OrderFragmentView {
    void addControls(View view);
    void addToolBar(View view);
    void showMessage(String message);
    void showRecyclerViewOrder(ArrayList<Order> orders);
    void setupOrderInDay(int allOrderAmount, int newOrderAmount,
                         int cancelOrderAmount, int completedOrderAmount);

    void addEvents();
    void updateListView(ArrayList<Order> orders);
}
