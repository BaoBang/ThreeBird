package com.example.baobang.threebird.model;

import java.io.Serializable;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Client extends RealmObject implements Serializable{
    @PrimaryKey
    private int id;
    private String name;
    private int groupId;
    private String phone;
    private String fax;
    private String website;
    private String email;
    private String avatar;
    private Address address;

    public Client() {
    }

    public Client(int id, String name, int groupId, String phone, String fax, String website, String email, Address address) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
        this.phone = phone;
        this.fax = fax;
        this.website = website;
        this.email = email;
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public int getGroup() {
        return groupId;
    }

    public void setGroup(int groupId) {
        this.groupId = groupId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name + "-" + phone;
    }
}
