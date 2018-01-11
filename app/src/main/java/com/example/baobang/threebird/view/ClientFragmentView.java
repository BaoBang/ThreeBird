package com.example.baobang.threebird.view;

import android.view.View;

import com.example.baobang.threebird.model.Client;

import java.util.ArrayList;

/**
 * Created by baobang on 1/10/18.
 */

public interface ClientFragmentView {

    void addControls(View view);
    void addToolBar(View view);
    void showMessage(String message);
    void showRecyclerViewClient(ArrayList<Client> clients);
}
