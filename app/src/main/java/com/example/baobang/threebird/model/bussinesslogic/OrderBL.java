package com.example.baobang.threebird.model.bussinesslogic;

import android.util.Log;

import com.example.baobang.threebird.model.Order;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by baobang on 12/27/17.
 */

public class OrderBL {
    public static boolean createOrder(Order order){
        Realm realm = Realm.getDefaultInstance();
        try{
            int nextID  = 0;
            Number number = realm.where(Order.class).max("id");
            if(number != null)
                nextID =  number.intValue() + 1;
            realm.beginTransaction();
            order.setId(nextID);
            Order rOrder = realm.copyToRealm(order);
            realm.commitTransaction();
            return true;
        }catch (Exception e){
            Log.e("Lỗi: ", e.getMessage());
        }finally {
            realm.close();
        }
        return false;
    }

    public static ArrayList<Order> getAllOrder(){
        List<Order> list = null;
        ArrayList<Order> orders = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Order> results = realm.where(Order.class).findAll();
        list = realm.copyFromRealm(results);
        for(Order order : list) orders.add(order);
        realm.close();
        return  orders;
    }

    public static ArrayList<Order> getOrderByStatus(int status){
        List<Order> list = null;
        ArrayList<Order> orders = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Order> results = realm.where(Order.class).equalTo("status", status).findAll();
        list = realm.copyFromRealm(results);
        for(Order order : list) orders.add(order);
        realm.close();
        return  orders;
    }

    public static Order getOrder(int orderId) {
        Realm realm = Realm.getDefaultInstance();
        Order results = realm.where(Order.class).equalTo("id", orderId).findFirst();
        Order order = realm.copyFromRealm(results);
        realm.close();
        return  order;
    }

    public static boolean updateOrder(Order order) {
        Realm realm = Realm.getDefaultInstance();
        try{
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(order);
            realm.commitTransaction();
            return true;
        }catch (Exception e){
            return false;
        }finally {
            realm.close();
        }
    }
}
