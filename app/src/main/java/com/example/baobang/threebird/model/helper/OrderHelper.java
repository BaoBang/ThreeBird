package com.example.baobang.threebird.model.helper;

import android.util.Log;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.ProductOrder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    public static ArrayList<Order> getAllOrder(int status){
        List<Order> list;
        ArrayList<Order> orders = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Order> results = realm.where(Order.class).equalTo("status", status).findAll();
        list = realm.copyFromRealm(results);
        orders.addAll(list);
        realm.close();
        return orders;
    }

    public static ArrayList<Order> getAllOrderByStatusInDay(){
        List<Order> list;
        ArrayList<Order> orders = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date dateBefore = calendar.getTime();
        calendar.add(Calendar.DATE, 2);
        Date dateAfter = calendar.getTime();
        RealmResults<Order> results = realm.where(Order.class).lessThan("createdAt", dateAfter).greaterThan("createdAt", dateBefore).findAll();
        list = realm.copyFromRealm(results);
        orders.addAll(list);
        realm.close();
        return orders;
    }

    public static ArrayList<Order> getOrderByStatusInDay(int status){
        List<Order> list;
        ArrayList<Order> orders = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date dateBefore = calendar.getTime();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Order> results = realm.where(Order.class).greaterThanOrEqualTo("createdAt", dateBefore).equalTo("status", status).findAll();
        list = realm.copyFromRealm(results);
        orders.addAll(list);
        realm.close();
        return orders;
    }

    public static ArrayList<Order> getOrderByStatusInMonth(int status, int month){
        List<Order> list;
        ArrayList<Order> orders = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("dd/MM/yyyy",
                        new Locale("vi", "VN"));

        calendar.set(Calendar.MONTH, month);

        calendar.set(Calendar.DATE, 1);
        Date firstDayInMonth = calendar.getTime();

        try {
            firstDayInMonth = simpleDateFormat.parse(simpleDateFormat.format(firstDayInMonth));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.add(Calendar.MONTH, 1);
        Date firstDayInNextMonth = calendar.getTime();

        try {
            firstDayInNextMonth = simpleDateFormat.parse(simpleDateFormat.format(firstDayInNextMonth));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Order> results = realm.where(Order.class).greaterThanOrEqualTo("createdAt", firstDayInMonth).lessThan("createdAt",firstDayInNextMonth).equalTo("status", status).findAll();
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
