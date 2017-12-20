package com.example.baobang.threebird.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by baobang on 12/14/17.
 */

public class User extends RealmObject{
    @PrimaryKey
    private String userName;
    private String passWord;

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
