package com.example.baobang.threebird.model.bussinesslogic;

import android.util.Log;

import com.example.baobang.threebird.model.Product;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by baobang on 12/19/17.
 */

public class ProductBL {
    public static boolean createProudct(Product product){
        Realm realm = Realm.getDefaultInstance();
        try{
            int nextID  = 0;
            Number number = realm.where(Product.class).max("id");
            if(number != null)
                nextID =  number.intValue() + 1;
            realm.beginTransaction();
            product.setId(nextID);
            Product rBrand = realm.copyToRealm(product);
            realm.commitTransaction();
            return true;
        }catch (Exception e){
            Log.e("Lỗi: ", e.getMessage());
        }finally {
            realm.close();
        }
        return false;
    }

    public static ArrayList<Product> getAllClient(){
        List<Product> list = null;
        ArrayList<Product> products = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Product> results = realm.where(Product.class).findAll();
        list = realm.copyFromRealm(results);
        for(Product product : list) products.add(product);
        realm.close();
        return  products;
    }

    public static boolean updateProduct(Product product) {
        Realm realm = Realm.getDefaultInstance();
        try{
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(product);
            realm.commitTransaction();
            return true;
        }catch (Exception e){
            return false;
        }finally {
            realm.close();
        }
    }
}
