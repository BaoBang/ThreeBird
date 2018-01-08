package com.example.baobang.threebird.model.helper;

import android.util.Log;

import com.example.baobang.threebird.model.Brand;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class BrandHelper {
    public static boolean createBrand(Brand brand){
        try (Realm realm = Realm.getDefaultInstance()) {
            int nextID = 0;
            Number number = realm.where(Brand.class).max("id");
            if (number != null)
                nextID = number.intValue() + 1;
            realm.beginTransaction();
            brand.setId(nextID);
            realm.copyToRealm(brand);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            Log.e("Lỗi: ", e.getMessage());
        }
        return false;
    }

    public static ArrayList<Brand> getAllBrand(){
        List<Brand> list;
        ArrayList<Brand> brands = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Brand> results = realm.where(Brand.class).findAll();
        list = realm.copyFromRealm(results);
        brands.addAll(list);
        realm.close();
        return  brands;
    }
}
