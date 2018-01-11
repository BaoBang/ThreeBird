package com.example.baobang.threebird.model.helper;

import android.util.Log;

import com.example.baobang.threebird.model.Payment;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by baobang on 1/11/18.
 */

public class PaymentHelper {
    public static boolean createStatus(Payment payment){
        try (Realm realm = Realm.getDefaultInstance()) {
            int nextID = 0;
            Number number = realm.where(Payment.class).max("id");
            if (number != null)
                nextID = number.intValue() + 1;
            realm.beginTransaction();
            payment.setId(nextID);
            realm.copyToRealm(payment);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            Log.e("Lỗi: ", e.getMessage());
        }
        return false;
    }

    public static ArrayList<Payment> getAllPayment(){
        List<Payment> list;
        ArrayList<Payment> payments = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Payment> results = realm.where(Payment.class).findAll();
        list = realm.copyFromRealm(results);
        payments.addAll(list);
        realm.close();
        return payments;
    }
}
