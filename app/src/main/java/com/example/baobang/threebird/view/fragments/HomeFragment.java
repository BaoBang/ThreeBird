package com.example.baobang.threebird.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.presenter.imp.HomeFragmentPresenterImp;
import com.example.baobang.threebird.utils.ItemFragmentModel;
import com.example.baobang.threebird.view.HomeFragmentView;
import com.example.baobang.threebird.utils.SlideModel;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.utils.ViewPagerHandler;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeFragmentView{

    private HomeFragmentPresenterImp homeFragmentPresenterImp;
    private  List<SlideModel> models;
    ArrayList<Entry> entries;
    ArrayList<String> lables;
    public HomeFragment() {
        // Required empty public constructor
    }
//
//    public static HomeFragment newInstance() {
//        Bundle args = new Bundle();
//        HomeFragment fragment = new HomeFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        homeFragmentPresenterImp = new HomeFragmentPresenterImp(this);
        homeFragmentPresenterImp.init(view);
    }

    @Override
    public void showSlide(View view, List<SlideModel> models) {
        //slide
        ViewPager viewPager = view.findViewById(R.id.pager);
        FrameLayout layoutRoot =  view.findViewById(R.id.layoutRoot);
        Utils.hideKeyboardOutside(layoutRoot, getActivity());
        CircleIndicator circleIndicator = view.findViewById(R.id.indicator);
        this.models = models;
        ViewPagerHandler viewPagerHandler = new ViewPagerHandler(getContext(), viewPager, circleIndicator, models);
        viewPagerHandler.handler();

    }

    @Override
    public void showLineChart(View view,  ArrayList<Entry> entries , ArrayList<String> lables) {

        LineChart mChart = view.findViewById(R.id.chart);
        this.entries = entries;
        this.lables = lables;
        com.example.baobang.threebird.utils.LineChart chart =
                new com.example.baobang.threebird.utils.LineChart("This is a line chart",mChart, this.entries, this.lables);
        chart.drawLineChart();
    }

    @Override
    public void showItemFragment(View view) {

        ItemFragmentModel model21  = new ItemFragmentModel(1, "HOÀN THÀNH", 100,20000,30000);
        ItemFragmentModel model22  = new ItemFragmentModel(2, "HỦY", 100,20000,30000);
        ItemFragmentModel model23  = new ItemFragmentModel(3, "MỚI", 100,20000,30000);

        ItemFragment fragment1 = ItemFragment.newInstance(model21);
        ItemFragment fragment2 = ItemFragment.newInstance(model22);
        ItemFragment fragment3 = ItemFragment.newInstance(model23);

        LinearLayout fragmentSuccess  =  view.findViewById(R.id.fragment_complete);
        LinearLayout fragmentCanCel  =  view.findViewById(R.id.fragment_cancel);
        LinearLayout fragmentNew =  view.findViewById(R.id.fragment_new);

        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager != null){
            getFragmentManager().beginTransaction().add(fragmentSuccess.getId(),fragment1, "tag1").commit();
            getFragmentManager().beginTransaction().add(fragmentCanCel.getId(),fragment2, "tag2").commit();
            getFragmentManager().beginTransaction().add(fragmentNew.getId(),fragment3, "tag3").commit();
        }
    }
}
