package com.example.baobang.threebird.model.bussinesslogic;

import android.app.Activity;
import android.util.Log;

import com.example.baobang.threebird.activity.LoginActivity;
import com.example.baobang.threebird.model.User;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by baobang on 12/14/17.
 */

public class UserBL {

    public static boolean createUser(User user){
        Realm realm = Realm.getDefaultInstance();
        try{
           realm.beginTransaction();
           User rUser = realm.copyToRealm(user);
           realm.commitTransaction();
           return true;
       }catch (Exception e){
          Log.e("Lỗi: ", e.getMessage());
       }finally {
           realm.close();
        }
        return false;
    }

    public static boolean checkUser(User user){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> results = realm.where(User.class)
                                        .equalTo("userName", user.getUserName())
                                        .equalTo("passWord", user.getPassWord())
                                        .findAll();
        int size = results.size();
        realm.close();
        return  size > 0;
    }
    public static boolean checkUser(String userName){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> results = realm.where(User.class)
                .equalTo("userName", userName)
                .findAll();
        realm.close();
        return results.size() > 0;
    }

    public static boolean updateUser( User user){
        Realm realm = Realm.getDefaultInstance();
        try{
            realm.beginTransaction();

            realm.copyToRealmOrUpdate(user);

            realm.commitTransaction();

            return true;
        }catch (Exception e){
            return false;
        }finally {
            realm.close();
        }
    }
}
