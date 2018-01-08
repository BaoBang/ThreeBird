package com.example.baobang.threebird.presenter;

import android.os.Bundle;

import com.example.baobang.threebird.model.Client;

/**
 * Created by baobang on 1/8/18.
 */

public interface ClientPresenter {
    void loadSpinnerData();
    boolean addClient(Client client);
    boolean updateClient(Client client);
    void init();
    Client getClientFromBundle(Bundle bundle);
    int getOptionFromBundle(Bundle bundle);
}
