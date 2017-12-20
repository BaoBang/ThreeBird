package com.example.baobang.threebird.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.activity.CreateOrderActivity;
import com.example.baobang.threebird.activity.LoginActivity;
import com.example.baobang.threebird.activity.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolBarOrder);
        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            toolbar.setTitle(null);
        }
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.actionBar_add:
                goToCreateOrderActivity();
                break;
            case R.id.actionBar_search:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToCreateOrderActivity() {
        Intent createOrderActivity = new Intent(getActivity(), CreateOrderActivity.class);
        startActivity(createOrderActivity);
    }
}
