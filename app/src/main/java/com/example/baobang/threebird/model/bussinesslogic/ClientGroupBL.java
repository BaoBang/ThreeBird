package com.example.baobang.threebird.model.bussinesslogic;

import android.util.Log;

import com.example.baobang.threebird.model.ClientGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ClientGroupBL {

    public static boolean createClientGroup(ClientGroup clientGroup){
        try (Realm realm = Realm.getDefaultInstance()) {
            int nextID = 0;
            Number number = realm.where(ClientGroup.class).max("id");
            if (number != null)
                nextID = number.intValue() + 1;
            realm.beginTransaction();
            clientGroup.setId(nextID);
            realm.copyToRealm(clientGroup);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            Log.e("Lỗi: ", e.getMessage());
        }
        return false;
    }

    public static ArrayList<ClientGroup> getAllClientGroup(){
        List<ClientGroup> list;
        ArrayList<ClientGroup> clientGroups = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ClientGroup> results = realm.where(ClientGroup.class).findAll();
        list = realm.copyFromRealm(results);
        clientGroups.addAll(list);
        realm.close();
        return  clientGroups;
    }
    public static ClientGroup getClientById(int id){
        Realm realm = Realm.getDefaultInstance();
        ClientGroup result = realm.where(ClientGroup.class).equalTo("id", id).findFirst();
        ClientGroup clientGroup = result == null ? null : realm.copyFromRealm(result);
        realm.close();
        return  clientGroup;
    }
}
