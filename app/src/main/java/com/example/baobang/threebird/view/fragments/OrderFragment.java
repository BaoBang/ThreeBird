package com.example.baobang.threebird.view.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.adapter.OrderAdapter;
import com.example.baobang.threebird.annimator.VegaLayoutManager;
import com.example.baobang.threebird.listener.OnItemRecyclerViewClickListener;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.helper.OrderHelper;
import com.example.baobang.threebird.presenter.OrderFragmentPresenter;
import com.example.baobang.threebird.presenter.imp.OrderFragmentPresenterImp;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.OrderFragmentView;
import com.example.baobang.threebird.view.activity.OrderActivity;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment implements OrderFragmentView {

    private OrderFragmentPresenterImp orderFragmentPresenterImp;

    private TextView txtAmountAllOrder, txtAmountNewOrder,
            txtAmountCancelOrder, txtAmountCompletedOrder;
    private RecyclerView rcOrders;
    private ArrayList<Order> orders;
    private OrderAdapter orderAdapter;
    private LinearLayout layoutOrder, layoutNewOrder, layoutCancelOrder, layoutCompletedOrder;
    private int layoutSelected = 0;

    private OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;
    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        orderFragmentPresenterImp = new OrderFragmentPresenterImp(this);
        orderFragmentPresenterImp.init(view);
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        return view;
    }
    @Override
    public void setupOrderInDay(int allOrderAmount, int newOrderAmount,
                                 int cancelOrderAmount, int completedOrderAmount) {
        txtAmountAllOrder.setText(String.valueOf(allOrderAmount));
        txtAmountNewOrder.setText(String.valueOf(newOrderAmount));
        txtAmountCancelOrder.setText(String.valueOf(cancelOrderAmount));
        txtAmountCompletedOrder.setText(String.valueOf(completedOrderAmount));
    }

    @Override
    public void addEvents() {
        layoutCancelOrder.setOnClickListener(view1 -> {
            setBackGround(layoutCancelOrder, 2);
            orders = orderFragmentPresenterImp.getOrderListByStatus(Constants.CANCEL);
            updateListView(orders);
        });

        layoutCompletedOrder.setOnClickListener(view12 -> {
            setBackGround(layoutCompletedOrder, 3);
            orders = orderFragmentPresenterImp.getOrderListByStatus(Constants.COMPLETED);
            updateListView(orders);
        });

        layoutOrder.setOnClickListener(view13 -> {
            setBackGround(layoutOrder, 0);
            orders = orderFragmentPresenterImp.getOrderListByStatus(-1);
            updateListView(orders);
        });

        layoutNewOrder.setOnClickListener(view14 -> {
            setBackGround(layoutNewOrder, 1);
            orders = orderFragmentPresenterImp.getOrderListByStatus(Constants.DELIVERY);
            updateListView(orders);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.ORDER_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            int orderId = -1;
            if(bundle != null){
                orderId = bundle.getInt(Constants.ORDER);
            }
            orderFragmentPresenterImp.addOrder(orders, orderId);

        }
    }

    private void setBackGround(LinearLayout  layout, int layoutSelected){

        switch (this.layoutSelected){
            case 0:
                layoutOrder.setBackgroundResource(R.drawable.background_border_white);
                break;
            case 1:
                layoutNewOrder.setBackgroundResource(R.drawable.background_border_white);
                break;
            case 2:
                layoutCancelOrder.setBackgroundResource(R.drawable.background_border_white);
                break;
            case 3:
                layoutCompletedOrder.setBackgroundResource(R.drawable.background_border_white);
                break;
        }
        layout.setBackgroundResource(R.drawable.background_color);
        this.layoutSelected = layoutSelected;
    }

    private void openOptionDialog(int option, final int orderId){

        if(option == Constants.ADD_OPTION) {
             goToCreateOrderActivity(-1, Constants.ADD_OPTION);
        }else if(option == Constants.EDIT_OPTION) {
             goToCreateOrderActivity(orderId, Constants.EDIT_OPTION);
        } else if(option == Constants.DETAIL_OPTION){
             goToCreateOrderActivity(orderId, Constants.DETAIL_OPTION);
        }else{
              orderFragmentPresenterImp
                      .deleteOrder(
                              getActivity(),
                              orders,
                              orderId);
        }
    }

    @Override
    public void updateListView(ArrayList<Order> orders) {
        this.orders = orders;
        orderAdapter = new OrderAdapter(getActivity(), this.orders, rcOrders, onItemRecyclerViewClickListener);
        rcOrders.setAdapter(orderAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.actionBar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                orderAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.actionBar_add:
                goToCreateOrderActivity(-1, Constants.ADD_OPTION);
                break;
            case R.id.actionBar_search:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToCreateOrderActivity(int id, int option) {
        Intent addProductActivity = new Intent(getActivity(), OrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ORDER, id);
        bundle.putInt(Constants.OPTION, option);
        addProductActivity.putExtras(bundle);
        startActivityForResult(addProductActivity, Constants.ORDER_REQUEST_CODE);
    }

    @Override
    public void addControls(View view) {
        FrameLayout layoutRoot =  view.findViewById(R.id.layoutRoot);
        Utils.hideKeyboardOutside(layoutRoot, getActivity());
        txtAmountAllOrder = view.findViewById(R.id.txtAmountAllOrder);
        txtAmountNewOrder = view.findViewById(R.id.txtAmountNewOrder);
        txtAmountCancelOrder = view.findViewById(R.id.txtAmountCancelOrder);
        txtAmountCompletedOrder = view.findViewById(R.id.txtAmountCompletedOrder);
        rcOrders = view.findViewById(R.id.rcOrders);

        layoutOrder = view.findViewById(R.id.layoutOrder);
        layoutNewOrder = view.findViewById(R.id.layoutNewOrder);
        layoutCancelOrder = view.findViewById(R.id.layoutCancelOrder);
        layoutCompletedOrder = view.findViewById(R.id.layoutCompletedOrder);
    }

    @Override
    public void addToolBar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolBarOrder);
        if (toolbar != null) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            if(appCompatActivity != null){
                appCompatActivity.setSupportActionBar(toolbar);
            }
            toolbar.setTitle(null);
        }
    }

    @Override
    public void showMessage(String message) {
        Utils.openDialog(getActivity(), message);
    }

    @Override
    public void showRecyclerViewOrder(ArrayList<Order> orders) {
        this.orders = orders;
        onItemRecyclerViewClickListener = (option, item) -> {
            Order order = (Order) item;
            openOptionDialog(option, order.getId());
        };
        orderAdapter = new OrderAdapter(getActivity(), orders, rcOrders, onItemRecyclerViewClickListener);
        rcOrders.setLayoutManager(new VegaLayoutManager());
        rcOrders.setAdapter(orderAdapter);
    }
}
