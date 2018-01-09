package com.example.baobang.threebird.model.helper;

import android.util.Log;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.ProductOrder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class OrderHelper {

    public static int createOrder(Order order){
        try (Realm realm = Realm.getDefaultInstance()) {
            int nextID = 0;
            Number number = realm.where(Order.class).max("id");
            if (number != null)
                nextID = number.intValue() + 1;
            realm.beginTransaction();
            order.setId(nextID);
            realm.copyToRealm(order);
            realm.commitTransaction();
            return nextID;
        } catch (Exception e) {
            Log.e("Lỗi: ", e.getMessage());
        }
        return -1;
    }



    public static ArrayList<Order> getAllOrderByStatusInDay(){
        List<Order> list;
        ArrayList<Order> orders = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi", "VN"));
        Date date = simpleDateFormat.getCalendar().getTime();
        RealmResults<Order> results = realm.where(Order.class).greaterThanOrEqualTo("createdAt", date).findAll();
        list = realm.copyFromRealm(results);
        orders.addAll(list);
        realm.close();
        return orders;
    }

    public static ArrayList<Order> getOrderByStatusInDay(int status){
        List<Order> list;
        ArrayList<Order> orders = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi", "VN"));
        Date date = simpleDateFormat.getCalendar().getTime();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Order> results = realm.where(Order.class).greaterThanOrEqualTo("createdAt", date).equalTo("status", status).findAll();
        list = realm.copyFromRealm(results);
        orders.addAll(list);
        realm.close();
        return orders;
    }

    public static Order getOrder(int orderId) {
        Realm realm = Realm.getDefaultInstance();
        Order results = realm.where(Order.class).equalTo("id", orderId).findFirst();
        Order order = results == null ? null : realm.copyFromRealm(results);
        realm.close();
        return  order;
    }

    public static int updateOrder(Order order) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(order);
            realm.commitTransaction();
            return order.getId();
        } catch (Exception e) {
            return -1;
        }
    }

    public static boolean deleteOrder(Order order){
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.where(Order.class).equalTo("id", order.getId()).findAll().deleteFirstFromRealm();
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkClientHasOreder(int clientId){
        boolean result = false;
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Order> results = realm.where(Order.class).findAll();
        for(Order order : results){
            if(order.getClientId() == clientId){
                result = true;
                break;
            }
        }
        return result;
    }

    public static boolean checkProductOrdered(int productId){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Order> results = realm.where(Order.class).findAll();
        for(Order order : results){
            RealmList<ProductOrder> products = order.getProducts();
            for(ProductOrder productOrder : products){
                if(productOrder.getProductId() == productId){
                    return true;
                }
            }
        }
        return false;
    }
}
