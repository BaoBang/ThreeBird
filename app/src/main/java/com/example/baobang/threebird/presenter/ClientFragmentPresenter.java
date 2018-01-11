package com.example.baobang.threebird.presenter;

import android.app.Activity;
import android.view.View;

import com.example.baobang.threebird.model.Client;

import java.util.ArrayList;


public interface ClientFragmentPresenter {
    void init(View view);
    int checkClients(ArrayList<Client>clients, Client client);
    void deleteClient(Activity activity, ArrayList<Client>clients, Client client);
}
