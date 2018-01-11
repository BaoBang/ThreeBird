package com.example.baobang.threebird.model.helper;

import android.util.Log;

import com.example.baobang.threebird.model.Brand;
import com.example.baobang.threebird.model.Status;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class StatusHelper {
    public static boolean createStatus(Status status){
        try (Realm realm = Realm.getDefaultInstance()) {
            int nextID = 0;
            Number number = realm.where(Status.class).max("id");
            if (number != null)
                nextID = number.intValue() + 1;
            realm.beginTransaction();
            status.setId(nextID);
            realm.copyToRealm(status);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            Log.e("Lỗi: ", e.getMessage());
        }
        return false;
    }

    public static ArrayList<Status> getAllStatus(){
        List<Status> list;
        ArrayList<Status> statuses = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Status> results = realm.where(Status.class).findAll();
        list = realm.copyFromRealm(results);
        statuses.addAll(list);
        realm.close();
        return statuses;
    }
}
