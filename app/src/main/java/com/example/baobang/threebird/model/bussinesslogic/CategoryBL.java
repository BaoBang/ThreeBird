package com.example.baobang.threebird.model.bussinesslogic;

import android.util.Log;
import com.example.baobang.threebird.model.Category;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by baobang on 12/18/17.
 */

public class CategoryBL {
    public static boolean createBrand(Category category){
        Realm realm = Realm.getDefaultInstance();
        try{
            int nextID  = 0;
            Number number = realm.where(Category.class).max("id");
            if(number != null)
                nextID =  number.intValue() + 1;
            realm.beginTransaction();
            category.setId(nextID);
            Category rBrand = realm.copyToRealm(category);
            realm.commitTransaction();
            return true;
        }catch (Exception e){
            Log.e("Lỗi: ", e.getMessage());
        }finally {
            realm.close();
        }
        return false;
    }

    public static ArrayList<Category> getAllClient(){
        List<Category> list = null;
        ArrayList<Category> categories = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Category> results = realm.where(Category.class).findAll();
        list = realm.copyFromRealm(results);
        for(Category category : list) categories.add(category);
        realm.close();
        return  categories;
    }
}
