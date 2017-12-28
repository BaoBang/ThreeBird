package com.example.baobang.threebird.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by baobang on 12/14/17.
 */

public class Address extends RealmObject implements Serializable {
    private String province;
    private String district;
    private String commune;
    private String address;

    public Address() {
    }

    public Address(String province, String district, String commune, String address) {
        this.province = province;
        this.district = district;
        this.commune = commune;
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return address
                + ", " + commune
                + ", " + district
                + ", " + province;
    }
}

