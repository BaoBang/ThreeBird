package com.example.baobang.threebird.model.bussinesslogic;

import android.util.Log;
import com.example.baobang.threebird.model.User;
import io.realm.Realm;
import io.realm.RealmResults;

public class UserBL {

    public static boolean createUser(User user){
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.copyToRealm(user);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            Log.e("Lỗi: ", e.getMessage());
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
        boolean result = results.size() > 0;
        realm.close();
        return result;
    }

    public static boolean updateUser( User user){
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(user);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
