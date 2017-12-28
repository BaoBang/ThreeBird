package com.example.baobang.threebird.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.activity.CreateOrderActivity;
import com.example.baobang.threebird.activity.LoginActivity;
import com.example.baobang.threebird.activity.MainActivity;
import com.example.baobang.threebird.adapter.OrderAdapter;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.bussinesslogic.OrderBL;
import com.example.baobang.threebird.utils.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    private ListView lvOrders;
    private List<Order> orders;
    private OrderAdapter orderAdapter;

    private LinearLayout layoutOrder, layoutNewOrder, layoutCancelOrder, layoutCompletedOrder;
    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolBarOrder);
        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            toolbar.setTitle(null);
        }
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment

        lvOrders = view.findViewById(R.id.lvOrders);
        orders = getOrder(-1);
        orderAdapter = new OrderAdapter(getActivity(), R.layout.item_order, orders);
        lvOrders.setAdapter(orderAdapter);
        lvOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToCreateOrderActivity(orders.get(i).getId());
            }
        });

        layoutOrder = view.findViewById(R.id.layoutOrder);
        layoutNewOrder = view.findViewById(R.id.layoutNewOrder);
        layoutCancelOrder = view.findViewById(R.id.layoutCancelOrder);
        layoutCompletedOrder = view.findViewById(R.id.layoutCompletedOrder);

        layoutCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orders = getOrder(Constants.CANCEL);
                orderAdapter = new OrderAdapter(getActivity(), R.layout.item_order, orders);
                lvOrders.setAdapter(orderAdapter);
            }
        });

        layoutCompletedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orders = getOrder(Constants.COMPLETED);
                orderAdapter = new OrderAdapter(getActivity(), R.layout.item_order, orders);
                lvOrders.setAdapter(orderAdapter);
            }
        });

        layoutOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orders = getOrder(-1);
                orderAdapter = new OrderAdapter(getActivity(), R.layout.item_order, orders);
                lvOrders.setAdapter(orderAdapter);
            }
        });

        layoutNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orders = getOrder(Constants.DELIVERY);
                orderAdapter = new OrderAdapter(getActivity(), R.layout.item_order, orders);
                lvOrders.setAdapter(orderAdapter);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.ORDER_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            int orderId = bundle.getInt(Constants.ORDER);
            Order order = OrderBL.getOrder(orderId);
            int indexChange = checkOrders(orderId);
            if( indexChange == -1){
                orders.add(order);
            }else{
                orders.set(indexChange, order);
            }
            orderAdapter.notifyDataSetChanged();
        }
    }

    private int checkOrders(int orderId) {
        for(int i = 0; i < orders.size(); i++){
            if(orders.get(i).getId() == orderId){
                return i;
            }
        }
        return -1;
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
                goToCreateOrderActivity(-1);
                break;
            case R.id.actionBar_search:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToCreateOrderActivity(int id) {
        Intent addProductActivity = new Intent(getActivity(), CreateOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ORDER, id);
        addProductActivity.putExtras(bundle);
        startActivityForResult(addProductActivity, Constants.ORDER_REQUEST_CODE);
    }

    private ArrayList<Order> getOrder(int status){
        ArrayList<Order> list = OrderBL.getAllOrder();
        switch (status){
            case 0:
                list = OrderBL.getOrderByStatus(Constants.COMPLETED);
                break;
            case 1:
                list = OrderBL.getOrderByStatus(Constants.CANCEL);
                break;
            case 2:
                list = OrderBL.getOrderByStatus(Constants.DELIVERY);
                break;
        }
        return list;
    }
}
