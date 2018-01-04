package com.example.baobang.threebird.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.activity.OrderActivity;
import com.example.baobang.threebird.adapter.OrderAdapter;
import com.example.baobang.threebird.annimator.VegaLayoutManager;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.bussinesslogic.OrderBL;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.MySupport;
import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private RecyclerView rcOrders;
    private List<Order> orders;
    private OrderAdapter orderAdapter;
    private LinearLayout layoutOrder, layoutNewOrder, layoutCancelOrder, layoutCompletedOrder;
    private int layoutSelected = 0;
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

        rcOrders = view.findViewById(R.id.rcOrders);
        orders = getOrderListByStatus(-1);
        orderAdapter = new OrderAdapter(getActivity(), orders, rcOrders);
        rcOrders.setLayoutManager(new VegaLayoutManager());
        rcOrders.setAdapter(orderAdapter);
//        lvOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                openOptionDialog(orders.get(i).getId());
//            }
//        });

        layoutOrder = view.findViewById(R.id.layoutOrder);
        layoutNewOrder = view.findViewById(R.id.layoutNewOrder);
        layoutCancelOrder = view.findViewById(R.id.layoutCancelOrder);
        layoutCompletedOrder = view.findViewById(R.id.layoutCompletedOrder);

        layoutCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackGround(layoutCancelOrder, 2);
                orders = getOrderListByStatus(Constants.CANCEL);
                orderAdapter = new OrderAdapter(getActivity(), orders, rcOrders);
                rcOrders.setAdapter(orderAdapter);
            }
        });

        layoutCompletedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackGround(layoutCompletedOrder, 3);
                orders = getOrderListByStatus(Constants.COMPLETED);
                orderAdapter = new OrderAdapter(getActivity(), orders, rcOrders);
                rcOrders.setAdapter(orderAdapter);
            }
        });

        layoutOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackGround(layoutOrder, 0);
                orders = getOrderListByStatus(-1);
                orderAdapter = new OrderAdapter(getActivity(), orders, rcOrders);
                rcOrders.setAdapter(orderAdapter);
            }
        });

        layoutNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackGround(layoutNewOrder, 1);
                orders = getOrderListByStatus(Constants.DELIVERY);
                orderAdapter = new OrderAdapter(getActivity(), orders, rcOrders);
                rcOrders.setAdapter(orderAdapter);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.ORDER_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            int orderId = -1;
            if(bundle != null){
                orderId = bundle.getInt(Constants.ORDER);
            }
            Order order = OrderBL.getOrder(orderId);
            int indexChange = checkOrders(orderId);
            if( indexChange == -1){
                orders.add(order);
            }else{
                orders.set(indexChange, order);
            }
//            orderAdapter.setTempObjects(orders);
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

//    private void setTextColorForViewChild(LinearLayout layout, int color){
//
//        for(int i = 0; i < layout.getChildCount(); i++){
//            TextView textView = (TextView) layout.getChildAt(i);
//            textView.setTextColor(color);
//        }
//    }

    private void openOptionDialog(final int orderId){
        final CharSequence[] items = { "Thêm", "Sửa", "Xem chi tiết", "Xóa"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Lựa chọn");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Thêm")) {
                    goToCreateOrderActivity(-1, Constants.ADD_OPTION);
                } else if (items[item].equals("Sửa")) {
                    goToCreateOrderActivity(orderId,Constants.EDIT_OPTION);
                }else if(items[item].equals("Xem chi tiết")){
                    goToCreateOrderActivity(orderId, Constants.DETAIL_OPTION);
                }
                else{
                    deleteOrder(orderId);
                }
            }
        });
        builder.show();
    }

    private void deleteOrder(int orderId) {
        Order order = OrderBL.getOrder(orderId);
        if(order != null){
            boolean res =OrderBL.deleteOrder(order);
            if(res){
                remove(order);
                updateListView();
                Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
            }else{
                MySupport.openDialog(getActivity(), "Có lỗi xảy ra, vui lòng thử lại");
            }
        }else{
            MySupport.openDialog(getActivity(), "Có lỗi xảy ra, vui lòng thử lại");
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
//        orderAdapter = new OrderAdapter(getActivity(), R.layout.item_order, orders);
//        lvOrders.setAdapter(orderAdapter);
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
