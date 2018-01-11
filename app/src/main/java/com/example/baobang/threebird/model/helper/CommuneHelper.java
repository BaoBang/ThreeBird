package com.example.baobang.threebird.model.helper;

import com.example.baobang.threebird.model.Commune;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class CommuneHelper {
    public static boolean createCommune(Commune commune){
        try (Realm realm = Realm.getDefaultInstance()) {
            int nextID = 0;
            Number number = realm.where(Commune.class).max("id");
            if (number != null)
                nextID = number.intValue() + 1;
            realm.beginTransaction();
            commune.setId(nextID);
            realm.copyToRealm(commune);
            realm.commitTransaction();
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    public static ArrayList<Commune> getAllCommunes(){
        List<Commune> list;
        ArrayList<Commune> communes = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Commune> results = realm.where(Commune.class).findAll();
        list = realm.copyFromRealm(results);
        communes.addAll(list);
        realm.close();
        return  communes;
    }

    public static ArrayList<Commune> getCommuneByParent(int parentId){
        List<Commune> list;
        ArrayList<Commune> communes = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Commune> results = realm.where(Commune.class).equalTo("parentId", parentId).findAll();
        list = realm.copyFromRealm(results);
        communes.addAll(list);
        realm.close();
        return  communes;
    }


    public static Commune getCommuneById(int id){
        Realm realm = Realm.getDefaultInstance();
        Commune result = realm.where(Commune.class).equalTo("id", id).findFirst();
        Commune district = realm.copyFromRealm(result);
        realm.close();
        return district;
    }
}
