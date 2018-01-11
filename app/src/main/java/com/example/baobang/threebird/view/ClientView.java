package com.example.baobang.threebird.view;

import android.graphics.Bitmap;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.ClientGroup;
import com.example.baobang.threebird.model.Commune;
import com.example.baobang.threebird.model.District;
import com.example.baobang.threebird.model.Province;

import java.util.ArrayList;

public interface ClientView {

    void addControls();

    void addEvents();

    void showSpinnerDistrict(ArrayList<District> districts);

    void showSpinnerProvince(ArrayList<Province> provinces);

    void showSpinnerCommune(ArrayList<Commune> communes);

    void showSpinnerClientGroup(ArrayList<ClientGroup> clientGroups);

    void setDataForInput(Bitmap bitmap, String name, String phone,
                         String fax, String website, String email,
                         int groupId, int provinceId, int districtId,
                         int communeId, String address);

    void setDisableInput();

    void startCamera();

    Bitmap getAvatar();

    void setAvatar(Bitmap avatar);

    void setSpinnerClientGroupSelectedPosition(int position);

    void setSpinnerProvincePosition(int position);

    void setSpinnerDistrictSelectedPosition(int position);

    void setSpinnerCommuneSelectedPosition(int position);

    void showMessage(String message);

    void showNameWarning(String message);

    void showPhoneWarning(String message);
    void showEmailWarning(String message);

    void showAddressWarning(String message);

    void changeActivity(Client client);

}
