package com.example.baobang.threebird.model.helper;

import com.example.baobang.threebird.model.District;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by baobang on 1/10/18.
 */

public class DistrictHelper {
    public static boolean createDistrict(District district){
        try (Realm realm = Realm.getDefaultInstance()) {
            int nextID = 0;
            Number number = realm.where(District.class).max("id");
            if (number != null)
                nextID = number.intValue() + 1;
            realm.beginTransaction();
            district.setId(nextID);
            realm.copyToRealm(district);
            realm.commitTransaction();
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    public static ArrayList<District> getAllDistricts(){
        List<District> list;
        ArrayList<District> districts = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<District> results = realm.where(District.class).findAll();
        list = realm.copyFromRealm(results);
        districts.addAll(list);
        realm.close();
        return  districts;
    }

    public static ArrayList<District> getDistrictByParent(int parentId){
        List<District> list;
        ArrayList<District> districts = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<District> results = realm.where(District.class).equalTo("parentId", parentId).findAll();
        list = realm.copyFromRealm(results);
        districts.addAll(list);
        realm.close();
        return  districts;
    }


    public static District getDistrictById(int id){
        Realm realm = Realm.getDefaultInstance();
        District result = realm.where(District.class).equalTo("id", id).findFirst();
        District district = realm.copyFromRealm(result);
        realm.close();
        return district;
    }
}
