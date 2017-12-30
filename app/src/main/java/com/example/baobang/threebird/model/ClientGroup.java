package com.example.baobang.threebird.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class ClientGroup extends RealmObject{
    @PrimaryKey
    private int id;
    private String name;

    public ClientGroup() {
    }

    public ClientGroup(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
