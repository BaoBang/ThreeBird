package com.example.baobang.threebird.presenter.imp;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.helper.OrderHelper;
import com.example.baobang.threebird.presenter.OrderFragmentPresenter;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.OrderFragmentView;

import java.util.ArrayList;

public class OrderFragmentPresenterImp implements OrderFragmentPresenter {

    OrderFragmentView orderFragmentView;

    public OrderFragmentPresenterImp(OrderFragmentView orderFragmentView) {
        this.orderFragmentView = orderFragmentView;
    }

    @Override
    public void init(View view) {
        orderFragmentView.addControls(view);
        orderFragmentView.addEvents();
        orderFragmentView.showRecyclerViewOrder(OrderHelper.getAllOrderByStatusInDay());
        orderFragmentView.setupOrderInDay(getOrderListByStatus(-1).size(),getOrderListByStatus(Constants.DELIVERY).size(),
                getOrderListByStatus(Constants.CANCEL).size(),getOrderListByStatus(Constants.COMPLETED).size());
    }

    @Override
    public int checkOrders(ArrayList<Order> orders, int order) {
       for(int i = 0; i < orders.size(); i++){
           if(orders.get(i).getId() == order){
               return i;
           }
       }
        return -1;
    }

    @Override
    public void deleteOrder(Activity activity, ArrayList<Order> orders, int orderId) {
        Order order = OrderHelper.getOrder(orderId);
        if(order != null){
            boolean res = OrderHelper.deleteOrder(order);
            if(res){
               if(remove(orders, order)){
                   orderFragmentView.updateListView(orders);
                   Toast.makeText(activity, "Xóa thành công", Toast.LENGTH_SHORT).show();
               }else{
                   orderFragmentView.showMessage("Có lỗi xảy ra, vui lòng thử lại");
               }

            }else{
                orderFragmentView.showMessage("Có lỗi xảy ra, vui lòng thử lại");
            }
        }else{
            orderFragmentView.showMessage("Có lỗi xảy ra, vui lòng thử lại");
        }
        orderFragmentView.setupOrderInDay(getOrderListByStatus(-1).size(),getOrderListByStatus(Constants.DELIVERY).size(),
                getOrderListByStatus(Constants.CANCEL).size(),getOrderListByStatus(Constants.COMPLETED).size());
    }

    @Override
    public ArrayList<Order> getOrderListByStatus(int status) {
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

    @Override
    public void addOrder(ArrayList<Order> orders, int orderId) {
        Order order = OrderHelper.getOrder(orderId);
        int indexChange = checkOrders(orders, orderId);
        if( indexChange == -1){
            orders.add(order);
        }else{
            orders.set(indexChange, order);
        }
        orderFragmentView.updateListView(orders);
        orderFragmentView.setupOrderInDay(getOrderListByStatus(-1).size(),getOrderListByStatus(Constants.DELIVERY).size(),
                getOrderListByStatus(Constants.CANCEL).size(),getOrderListByStatus(Constants.COMPLETED).size());
    }

    private boolean remove(ArrayList<Order> orders, Order order){
        for(Order o : orders){
            if(o.getId() == order.getId()){
                orders.remove(o);
                return true;
            }
        }
        return false;
    }
}
