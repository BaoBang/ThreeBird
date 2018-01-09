package com.example.baobang.threebird.presenter;

import android.app.Activity;
import android.os.Bundle;

import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.ClientGroup;
import com.example.baobang.threebird.model.Order;

import java.util.ArrayList;

/**
 * Created by baobang on 1/8/18.
 */

public interface ClientPresenter {
    void init();
    void loadSpinnerData();

    void clickAddOptionMenu(Client client, int option, String name, int group, ClientGroup clientGroup,
                            String phone, String fax, String website,
                            String email, int province, String provinceStr,
                            int district, String districtStr, int commune,
                            String communeStr, String address);

    Client addClient(String name, ClientGroup group, String phone,
                     String fax, String website, String email,
                     String province, String district, String commune, String address);
    Client updateClient(Client client, String name, ClientGroup group,
                        String phone, String fax, String website,
                        String email, String province, String district,
                        String commune, String address);

    Client getClientFromBundle(Bundle bundle);
    int getOptionFromBundle(Bundle bundle);

    void setData(Activity activity, Client client);
    void setAvatar(Activity activity,Client client);

    void setGroupClientSelected(ArrayList<ClientGroup> groups, int groupId);
    void setDistrictSelected(ArrayList<String> districts, String district);
    void setProvinceSelected(ArrayList<String> provinces, String province);
    void setCommuneSelected(ArrayList<String> communes, String commune);

}
