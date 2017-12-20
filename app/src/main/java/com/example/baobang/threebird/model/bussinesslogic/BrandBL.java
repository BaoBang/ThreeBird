package com.example.baobang.threebird.model.bussinesslogic;

import android.util.Log;

import com.example.baobang.threebird.model.Brand;
import com.example.baobang.threebird.model.Client;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by baobang on 12/18/17.
 */

public class BrandBL {
    public static boolean createBrand(Brand brand){
        Realm realm = Realm.getDefaultInstance();
        try{
            int nextID  = 0;
            Number number = realm.where(Brand.class).max("id");
            if(number != null)
                nextID =  number.intValue() + 1;
            realm.beginTransaction();
            brand.setId(nextID);
            Brand rBrand = realm.copyToRealm(brand);
            realm.commitTransaction();
            return true;
        }catch (Exception e){
            Log.e("Lỗi: ", e.getMessage());
        }finally {
            realm.close();
        }
        return false;
    }

    public static ArrayList<Brand> getAllClient(){
        List<Brand> list = null;
        ArrayList<Brand> brands = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Brand> results = realm.where(Brand.class).findAll();
        list = realm.copyFromRealm(results);
        for(Brand brand : list) brands.add(brand);
        realm.close();
        return  brands;
    }
}
