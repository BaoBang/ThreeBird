package com.example.baobang.threebird.model.bussinesslogic;

import android.util.Log;

import com.example.baobang.threebird.model.Client;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by baobang on 12/14/17.
 */

public class ClientBL {

    public static boolean createClient(Client client){
        Realm realm = Realm.getDefaultInstance();
        try{
            int nextID  = 0;
            Number number = realm.where(Client.class).max("id");
            if(number != null)
                nextID =  number.intValue() + 1;
            realm.beginTransaction();
            client.setId(nextID);
            Client rClient = realm.copyToRealm(client);
            realm.commitTransaction();
            return true;
        }catch (Exception e){
            Log.e("Lỗi: ", e.getMessage());
        }finally {
            realm.close();
        }
        return false;
    }

    public static boolean checkClient(Client client){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Client> results = realm.where(Client.class)
                .equalTo("userName", client.getId())
                .findAll();
        int size = results.size();
        realm.close();
        return  size > 0;
    }

    public static ArrayList<Client> getAllClient(){
        List<Client> list = null;
        ArrayList<Client> clients = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Client> results = realm.where(Client.class).findAll();
        list = realm.copyFromRealm(results);
        for(Client client : list) clients.add(client);
        realm.close();
        return  clients;
    }

    public static Client getClient(int id){
        Realm realm = Realm.getDefaultInstance();
        Client result = realm.where(Client.class).equalTo("id", id).findFirst();
        Client client = realm.copyFromRealm(result);
        realm.close();
        return  client;
    }

    public static ArrayList<Client> getClientByName(String name){
        List<Client> list = null;
        ArrayList<Client> clients = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Client> results = realm.where(Client.class).contains("name", name).findAll();
        list = realm.copyFromRealm(results);
        for(Client client : list) clients.add(client);
        realm.close();
        return  clients;
    }

    public static boolean updateClient(Client client){
        Realm realm = Realm.getDefaultInstance();
        try{
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(client);
            realm.commitTransaction();

            return true;
        }catch (Exception e){
            return false;
        }finally {
            realm.close();
        }
    }
}
