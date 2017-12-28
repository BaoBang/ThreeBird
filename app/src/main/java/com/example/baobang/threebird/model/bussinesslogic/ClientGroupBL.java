package com.example.baobang.threebird.model.bussinesslogic;

import android.util.Log;

import com.example.baobang.threebird.model.ClientGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by baobang on 12/28/17.
 */

public class ClientGroupBL {
    public static boolean createClientGroup(ClientGroup clientGroup){
        Realm realm = Realm.getDefaultInstance();
        try{
            int nextID  = 0;
            Number number = realm.where(ClientGroup.class).max("id");
            if(number != null)
                nextID =  number.intValue() + 1;
            realm.beginTransaction();
            clientGroup.setId(nextID);
            ClientGroup rClient = realm.copyToRealm(clientGroup);
            realm.commitTransaction();
            return true;
        }catch (Exception e){
            Log.e("Lỗi: ", e.getMessage());
        }finally {
            realm.close();
        }
        return false;
    }

    public static ArrayList<ClientGroup> getAllClientGroup(){
        List<ClientGroup> list = null;
        ArrayList<ClientGroup> clientGroups = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ClientGroup> results = realm.where(ClientGroup.class).findAll();
        list = realm.copyFromRealm(results);
        for(ClientGroup clientGroup : list) clientGroups.add(clientGroup);
        realm.close();
        return  clientGroups;
    }
    public static ClientGroup getClientById(int id){
        Realm realm = Realm.getDefaultInstance();
        ClientGroup result = realm.where(ClientGroup.class).equalTo("id", id).findFirst();
        ClientGroup clientGroup = realm.copyFromRealm(result);
        realm.close();
        return  clientGroup;
    }
}
