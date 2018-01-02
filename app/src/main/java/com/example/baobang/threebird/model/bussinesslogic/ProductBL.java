package com.example.baobang.threebird.model.bussinesslogic;

import android.util.Log;
import com.example.baobang.threebird.model.Product;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class ProductBL {
    public static boolean createProudct(Product product){
        try (Realm realm = Realm.getDefaultInstance()) {
            int nextID = 0;
            Number number = realm.where(Product.class).max("id");
            if (number != null)
                nextID = number.intValue() + 1;
            realm.beginTransaction();
            product.setId(nextID);
            realm.copyToRealm(product);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            Log.e("Lỗi: ", e.getMessage());
        }
        return false;
    }

    public static ArrayList<Product> getAllProduct(){
        List<Product> list;
        ArrayList<Product> products = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Product> results = realm.where(Product.class).findAll();
        list = realm.copyFromRealm(results);
        products.addAll(list);
        realm.close();
        return  products;
    }
    public static Product getProduct(int productId){
        Realm realm = Realm.getDefaultInstance();
        Product results = realm.where(Product.class).equalTo("id", productId).findFirst();
        Product product = results == null ? null : realm.copyFromRealm(results);
        realm.close();
        return  product;
    }

    public static ArrayList<Product> getListSortBy(int brandId, int categoryId){
        List<Product> list;
        ArrayList<Product> products = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Product> query = realm.where(Product.class);
        if(brandId != -1){
            query = query.equalTo("brandId", brandId);
        }
        if(categoryId  != -1){
            if(brandId != -1){
                query = query.and();
            }
            query = query.equalTo("categoryId", categoryId);
        }
        RealmResults<Product> results = query.findAll();

        list = realm.copyFromRealm(results);
        products.addAll(list);
        realm.close();
        return  products;
    }

    public static boolean updateProduct(Product product) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(product);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
