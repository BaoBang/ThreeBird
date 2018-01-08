package com.example.baobang.threebird.view;

import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.ClientGroup;

import java.util.ArrayList;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

/**
 * Created by baobang on 1/8/18.
 */

public interface ClientView {
    void addControls();
    void addEvents();
    void showSpinnerDistrict(ArrayList<String> districts);
    void showSpinnerProvince(ArrayList<String> provinces);
    void showSpinnerCommune(ArrayList<String> communes);
    void showSpinnerClientGroup(ArrayList<ClientGroup> clientGroups);
    void setDataForInput();
    void setDisableInput();
    void startCamera();
    Client getClientFromInput();
    void setError(ExtendedEditText extendedEditText, String message);
}
