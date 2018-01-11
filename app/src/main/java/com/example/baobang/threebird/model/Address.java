package com.example.baobang.threebird.model;

import com.example.baobang.threebird.model.helper.CommuneHelper;
import com.example.baobang.threebird.model.helper.DistrictHelper;
import com.example.baobang.threebird.model.helper.ProvinceHelper;

import java.io.Serializable;
import io.realm.RealmObject;

public class Address extends RealmObject implements Serializable {
    private int provinceId;
    private int districtId;
    private int communeId;
    private String address;

    public Address() {
    }

    public Address(int provinceId, int districtId, int communeId, String address) {
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.communeId = communeId;
        this.address = address;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getCommuneId() {
        return communeId;
    }

    public void setCommuneId(int communeId) {
        this.communeId = communeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        Province province = ProvinceHelper.getProvinceById(provinceId);
        Commune commune = CommuneHelper.getCommuneById(communeId);
        District district = DistrictHelper.getDistrictById(provinceId);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(province.toString());

        if(!district.toString().equals(""))
            stringBuilder.append(",")
                .append(district.toString());

        if(!commune.toString().equals(""))
            stringBuilder.append(",")
                    .append(commune.toString());

        if(!address.equals(""))
            stringBuilder.append(",")
                    .append(address);

        return stringBuilder.toString();
    }
}

