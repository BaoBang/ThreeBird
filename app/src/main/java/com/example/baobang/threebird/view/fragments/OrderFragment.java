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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.adapter.OrderAdapter;
import com.example.baobang.threebird.annimator.VegaLayoutManager;
import com.example.baobang.threebird.listener.OnItemRecyclerViewClickListener;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.helper.OrderHelper;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.activity.OrderActivity;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    private TextView txtAmountAllOrder, txtAmountNewOrder, txtAmountCancelOrder, txtAmountCompletedOrder;

    private RecyclerView rcOrders;
    private List<Order> orders;
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
        Toolbar toolbar = view.findViewById(R.id.toolBarOrder);
        if (toolbar != null) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            if(appCompatActivity != null){
                appCompatActivity.setSupportActionBar(toolbar);
            }
            toolbar.setTitle(null);
        }
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        txtAmountAllOrder = view.findViewById(R.id.txtAmountAllOrder);
        txtAmountNewOrder = view.findViewById(R.id.txtAmountNewOrder);
        txtAmountCancelOrder = view.findViewById(R.id.txtAmountCancelOrder);
        txtAmountCompletedOrder = view.findViewById(R.id.txtAmountCompletedOrder);

        setupOrderInDay();

        rcOrders = view.findViewById(R.id.rcOrders);
        orders = OrderHelper.getAllOrderByStatusInDay();
        onItemRecyclerViewClickListener = item -> {
            Order order = (Order) item;
            openOptionDialog(order.getId());
        };
        orderAdapter = new OrderAdapter(getActivity(), orders, rcOrders, onItemRecyclerViewClickListener);
        rcOrders.setLayoutManager(new VegaLayoutManager());
        rcOrders.setAdapter(orderAdapter);

        layoutOrder = view.findViewById(R.id.layoutOrder);
        layoutNewOrder = view.findViewById(R.id.layoutNewOrder);
        layoutCancelOrder = view.findViewById(R.id.layoutCancelOrder);
        layoutCompletedOrder = view.findViewById(R.id.layoutCompletedOrder);

        layoutCancelOrder.setOnClickListener(view1 -> {
            setBackGround(layoutCancelOrder, 2);
            orders = getOrderListByStatus(Constants.CANCEL);
            updateListView();
        });

        layoutCompletedOrder.setOnClickListener(view12 -> {
            setBackGround(layoutCompletedOrder, 3);
            orders = getOrderListByStatus(Constants.COMPLETED);
            updateListView();
        });

        layoutOrder.setOnClickListener(view13 -> {
            setBackGround(layoutOrder, 0);
            orders = getOrderListByStatus(-1);
            updateListView();
        });

        layoutNewOrder.setOnClickListener(view14 -> {
            setBackGround(layoutNewOrder, 1);
            orders = getOrderListByStatus(Constants.DELIVERY);
            updateListView();
        });
        return view;
    }

    private void setupOrderInDay() {

        txtAmountAllOrder.setText(String.valueOf(getOrderListByStatus(-1).size()));
        txtAmountNewOrder.setText(String.valueOf(getOrderListByStatus(Constants.DELIVERY).size()));
        txtAmountCancelOrder.setText(String.valueOf(getOrderListByStatus(Constants.CANCEL).size()));
        txtAmountCompletedOrder.setText(String.valueOf(getOrderListByStatus(Constants.COMPLETED).size()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.ORDER_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            int orderId = -1;
            if(bundle != null){
                orderId = bundle.getInt(Constants.ORDER);
            }
            Order order = OrderHelper.getOrder(orderId);
            int indexChange = checkOrders(orderId);
            if( indexChange == -1){
                orders.add(order);
            }else{
                orders.set(indexChange, order);
            }
            orderAdapter.notifyDataSetChanged();
            setupOrderInDay();
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


    private void setBackGround(LinearLayout  layout, int layoutSelected){

        switch (this.layoutSelected){
            case 0:
                layoutOrder.setBackgroundResource(R.drawable.background_border_white);
//                setTextColorForViewChild(layoutOrder, R.color.bold_word_80);
                break;
            case 1:
                layoutNewOrder.setBackgroundResource(R.drawable.background_border_white);
//                setTextColorForViewChild(layoutNewOrder, R.color.bold_word_80);
                break;
            case 2:
                layoutCancelOrder.setBackgroundResource(R.drawable.background_border_white);
//                setTextColorForViewChild(layoutCancelOrder, R.color.bold_word_80);
                break;
            case 3:
                layoutCompletedOrder.setBackgroundResource(R.drawable.background_border_white);
//                setTextColorForViewChild(layoutCompletedOrder, R.color.bold_word_80);
                break;
        }
        layout.setBackgroundResource(R.drawable.background_color);
//        setTextColorForViewChild(layout, R.color.red);
        this.layoutSelected = layoutSelected;
    }

    private void openOptionDialog(final int orderId){
        final CharSequence[] items = { "Thêm", "Sửa", "Xem chi tiết", "Xóa"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Lựa chọn");
        builder.setItems(items, (dialog, item) -> {

            if (items[item].equals("Thêm")) {
                goToCreateOrderActivity(-1, Constants.ADD_OPTION);
            } else if (items[item].equals("Sửa")) {
                goToCreateOrderActivity(orderId,Constants.EDIT_OPTION);
            }else if(items[item].equals("Xem chi tiết")){
                goToCreateOrderActivity(orderId, Constants.DETAIL_OPTION);
            }else{
                deleteOrder(orderId);
            }
        });
        builder.show();
    }

    private void deleteOrder(int orderId) {
        Order order = OrderHelper.getOrder(orderId);
        if(order != null){
            boolean res = OrderHelper.deleteOrder(order);
            if(res){
                remove(order);
                updateListView();
                Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
            }else{
                Utils.openDialog(getActivity(), "Có lỗi xảy ra, vui lòng thử lại");
            }
        }else{
            Utils.openDialog(getActivity(), "Có lỗi xảy ra, vui lòng thử lại");
        }
    }

    private boolean remove(Order order){
        for(Order o : orders){
            if(o.getId() == order.getId()){
                orders.remove(o);
                return true;
            }
        }
        return false;
    }

    private void updateListView() {
        orderAdapter = new OrderAdapter(getActivity(), orders, rcOrders, onItemRecyclerViewClickListener);
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

    private ArrayList<Order> getOrderListByStatus(int status){
        ArrayList<Order> list;
        switch (status){
            case 0:
                list = OrderHelper.getOrderByStatusInDay(Constants.COMPLETED);
                break;
            case 1:
                list = OrderHelper.getOrderByStatusInDay(Constants.CANCEL);
                break;
            case 2:
                list = OrderHelper.getOrderByStatusInDay(Constants.DELIVERY);
                break;
            default:
                list = OrderHelper.getAllOrderByStatusInDay();
        }
        return list;
    }
}
