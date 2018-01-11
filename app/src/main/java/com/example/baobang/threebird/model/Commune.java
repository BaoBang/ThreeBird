package com.example.baobang.threebird.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Commune extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;
    private String name;
    private int parentId;

    public Commune() {
    }

    public Commune(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return name;
    }
}
