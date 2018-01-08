package com.example.baobang.threebird.presenter;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.baobang.threebird.model.Client;

/**
 * Created by baobang on 1/8/18.
 */

public interface ClientPresenter {
    void loadSpinnerData();
    boolean addClient(Client client);
    boolean updateClient(Client client);
}
