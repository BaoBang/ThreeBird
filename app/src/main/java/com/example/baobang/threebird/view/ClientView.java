package com.example.baobang.threebird.view;

import android.graphics.Bitmap;
import android.widget.Spinner;

import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.ClientGroup;

import java.util.ArrayList;
import java.util.List;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public interface ClientView {

    void addControls();
    void addEvents();
    void showSpinnerDistrict(ArrayList<String> districts);
    void showSpinnerProvince(ArrayList<String> provinces);
    void showSpinnerCommune(ArrayList<String> communes);
    void showSpinnerClientGroup(ArrayList<ClientGroup> clientGroups);
    void setDataForInput(Bitmap bitmap, String name, String phone,
                         String fax, String website, String email,
                         int groupId, String province, String district,
                         String commune, String address);
    void setDisableInput();
    void startCamera();
    boolean checkInput();
    Bitmap getAvatar();
    void setAvatar(Bitmap avatar);
    void setError(ExtendedEditText extendedEditText, String message);
    void setSpinnerClientGroupSelectedPosition(int position);
    void setSpinnerProvincePosition(int position);
    void setSpinnerDistrictSelectedPosition(int position);
    void setSpinnerCommuneSelectedPosition(int position);
}
