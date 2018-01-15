package com.example.baobang.threebird.view.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.fragments.ClientFragment;
import com.example.baobang.threebird.view.fragments.HomeFragment;
import com.example.baobang.threebird.view.fragments.OrderFragment;
import com.example.baobang.threebird.view.fragments.ProductFragment;
import com.gw.swipeback.SwipeBackLayout;
import com.gw.swipeback.WxSwipeBackLayout;
import com.gw.swipeback.tools.WxSwipeBackActivityManager;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private static final String SELECTED_ITEM = "arg_selected_item";
    private BottomNavigationView navigation;
    private int mSelectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WxSwipeBackActivityManager.getInstance().init(getApplication());

        Realm.init(this);


        LinearLayout layoutRoot = findViewById(R.id.container);
        Utils.hideKeyboardOutside(layoutRoot, this);

        addControlls(savedInstanceState);
        addEvents();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = navigation.getMenu().getItem(0);
        if(mSelectedItem != homeItem.getItemId()){
            selectedFragment(homeItem);
        }else{
            super.onBackPressed();
        }
    }

    private void selectedFragment(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_order:
                fragment = new OrderFragment();
                break;
            case R.id.navigation_product:
                fragment = new ProductFragment();
                break;
            case R.id.navigation_client:
                fragment = new ClientFragment();
                break;
        }
      // update selected item
        mSelectedItem = item.getItemId();
        updateToolBarText(item.getTitle());
        if(fragment != null){
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment).commit();
        }
    }

    private void updateToolBarText(CharSequence title) {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
        }
    }
    private void addEvents() {
        navigation.setOnNavigationItemSelectedListener(item -> {
            selectedFragment(item);
            return true;
        });
    }
    private void addControlls(Bundle savedInstanceState) {
        navigation = findViewById(R.id.navigation);
        MenuItem selectedItem;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            selectedItem = navigation.getMenu().findItem(mSelectedItem);
        } else {
            selectedItem = navigation.getMenu().getItem(0);
        }
        selectedFragment(selectedItem);
    }


}
