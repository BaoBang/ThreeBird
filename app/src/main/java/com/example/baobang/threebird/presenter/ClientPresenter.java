package com.example.baobang.threebird.presenter;

import android.app.Activity;
import android.os.Bundle;

import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.ClientGroup;
import com.example.baobang.threebird.model.Commune;
import com.example.baobang.threebird.model.District;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.Province;

import java.util.ArrayList;

/**
 * Created by baobang on 1/8/18.
 */

public interface ClientPresenter {
    void init();
    void loadSpinnerData();

    void clickAddOptionMenu(Client client, int option, String name,
                            int group, ClientGroup clientGroup,
                            String phone, String fax, String website,
                            String email, Province province,
                            District district, Commune commune,
                            String address);

    Client addClient(String name, ClientGroup group, String phone,
                     String fax, String website, String email,
                     Province province, District district,
                     Commune commune, String address);
    Client updateClient(Client client, String name, ClientGroup group,
                        String phone, String fax, String website,
                        String email, Province province, District district,
                        Commune commune, String address);

    Client getClientFromBundle(Bundle bundle);
    int getOptionFromBundle(Bundle bundle);

    void setData(Activity activity, Client client);
    void setAvatar(Activity activity,Client client);

    void setGroupClientSelected(ArrayList<ClientGroup> groups, int groupId);
    void setDistrictSelected(ArrayList<District> districts, int districtId);
    void setProvinceSelected(ArrayList<Province> provinces, int provinceId);
    void setCommuneSelected(ArrayList<Commune> communes, int communeId);

}
