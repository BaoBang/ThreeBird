package com.example.baobang.threebird.presenter.imp;

import android.view.View;

import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.Status;
import com.example.baobang.threebird.model.helper.OrderHelper;
import com.example.baobang.threebird.model.helper.StatusHelper;
import com.example.baobang.threebird.presenter.HomeFragmentPresenter;
import com.example.baobang.threebird.utils.SlideModel;
import com.example.baobang.threebird.view.HomeFragmentView;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by baobang on 1/11/18.
 */

public class HomeFragmentPresenterImp implements HomeFragmentPresenter {
    private HomeFragmentView homeFragmentView;

    public HomeFragmentPresenterImp(HomeFragmentView homeFragmentView) {
        this.homeFragmentView = homeFragmentView;
    }

    @Override
    public void init(View view) {
        addSlide(view);
        addLineChart(view);
    }

    @Override
    public void addLineChart(View view) {

        Date date = new Date();
        int month = date.getMonth();
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> lables = new ArrayList<>();

        for(int i = 1; i <= month; i++){

        }

        entries.add(new Entry(10f, 0));
        entries.add(new Entry(50f, 1));
        entries.add(new Entry(10f, 2));
        entries.add(new Entry(10f, 3));
        entries.add(new Entry(15f, 4));
        entries.add(new Entry(60f, 5));
        entries.add(new Entry(-10f, 6));
        lables.add("A");
        lables.add("B");
        lables.add("C");
        lables.add("D");
        lables.add("A");
        lables.add("B");
        lables.add("C");
        lables.add("D");
        homeFragmentView.showLineChart(view, entries, lables);

    }

    @Override
    public void addSlide(View view) {
        List<SlideModel> slideModels = new ArrayList<>();

        List<Status> statuses = StatusHelper.getAllStatus();

        for(Status status : statuses){
           if(status.getId() != 0){
               ArrayList<Order> orders = OrderHelper.getOrderByStatusInDay(status.getId());
               SlideModel slideModel = new SlideModel(status.getImage(), status.getDescription(), orders.size());
               slideModels.add(slideModel);
           }
        }
        homeFragmentView.showSlide(view, slideModels);
    }
}
