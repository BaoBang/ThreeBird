package com.example.baobang.threebird.model.bussinesslogic;

import android.util.Log;
import com.example.baobang.threebird.model.Category;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class CategoryBL {
    public static boolean createCategory(Category category){
        try (Realm realm = Realm.getDefaultInstance()) {
            int nextID = 0;
            Number number = realm.where(Category.class).max("id");
            if (number != null)
                nextID = number.intValue() + 1;
            realm.beginTransaction();
            category.setId(nextID);
            realm.copyToRealm(category);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            Log.e("Lỗi: ", e.getMessage());
        }
        return false;
    }

    public static ArrayList<Category> getAllCategory(){
        List<Category> list;
        ArrayList<Category> categories = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Category> results = realm.where(Category.class).findAll();
        list = realm.copyFromRealm(results);
        categories.addAll(list);
        realm.close();
        return  categories;
    }
}
