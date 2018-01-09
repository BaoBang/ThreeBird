package com.example.baobang.threebird.model.helper;

import android.util.Log;

import com.example.baobang.threebird.model.Client;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class ClientHelper {

    public static boolean createClient(Client client){
        try (Realm realm = Realm.getDefaultInstance()) {
            int nextID = 0;
            Number number = realm.where(Client.class).max("id");
            if (number != null)
                nextID = number.intValue() + 1;
            realm.beginTransaction();
            client.setId(nextID);
            realm.copyToRealm(client);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            Log.e("Lỗi: ", e.getMessage());
        }
        return false;
    }

    public static Client createClientReturnObject(Client client){
        try (Realm realm = Realm.getDefaultInstance()) {
            int nextID = 0;
            Number number = realm.where(Client.class).max("id");
            if (number != null)
                nextID = number.intValue() + 1;
            realm.beginTransaction();
            client.setId(nextID);
            realm.copyToRealm(client);
            realm.commitTransaction();
            return client;
        } catch (Exception e) {
            Log.e("Lỗi: ", e.getMessage());
        }
        return null;
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
        List<Client> list;
        ArrayList<Client> clients = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Client> results = realm.where(Client.class).findAll();
        list = realm.copyFromRealm(results);
        clients.addAll(list);
        realm.close();
        return  clients;
    }

    public static ArrayList<Client> getClientOn30Days(){
        List<Client> list;
        ArrayList<Client> clients = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        Date end = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.add(Calendar.DATE, -30);
        Date start = calendar.getTime();
        RealmResults<Client> results = realm.where(Client.class).
                greaterThanOrEqualTo("createdAt", start).
                lessThanOrEqualTo("createdAt", end).
                findAll();
        list = realm.copyFromRealm(results);
        clients.addAll(list);
        realm.close();
        return  clients;
    }

    public static Client getClient(int id){
        Realm realm = Realm.getDefaultInstance();
        Client result = realm.where(Client.class).equalTo("id", id).findFirst();
        Client client = result == null ? null : realm.copyFromRealm(result);
        realm.close();
        return  client;
    }

    public static ArrayList<Client> getClientByName(String name){
        List<Client> list;
        ArrayList<Client> clients = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Client> results = realm.where(Client.class).contains("name", name).findAll();
        list = realm.copyFromRealm(results);
        clients.addAll(list);
        realm.close();
        return  clients;
    }

    public static boolean updateClient(Client client){
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(client);
            realm.commitTransaction();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Client updateClientReturnObject(Client client){
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(client);
            realm.commitTransaction();
            return client;
        } catch (Exception e) {
        }
        return null;

    }

    public static boolean deleteClient(Client client){
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.where(Client.class).equalTo("id", client.getId()).findAll().deleteFirstFromRealm();
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
