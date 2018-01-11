package com.example.baobang.threebird.model.helper;

import com.example.baobang.threebird.model.Province;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class ProvinceHelper {

    public static boolean createProvince(Province province){
        try (Realm realm = Realm.getDefaultInstance()) {
            int nextID = 0;
            Number number = realm.where(Province.class).max("id");
            if (number != null)
                nextID = number.intValue() + 1;
            realm.beginTransaction();
            province.setId(nextID);
            realm.copyToRealm(province);
            realm.commitTransaction();
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    public static ArrayList<Province> getAllProvinces(){
        List<Province> list;
        ArrayList<Province> provinces = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Province> results = realm.where(Province.class).findAll();
        list = realm.copyFromRealm(results);
        provinces.addAll(list);
        realm.close();
        return  provinces;
    }

    public static Province getProvinceById(int id){
        Realm realm = Realm.getDefaultInstance();
        Province results = realm.where(Province.class).equalTo("id", id).findFirst();
        Province province = realm.copyFromRealm(results);
        realm.close();
        return province;
    }
}
