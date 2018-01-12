package com.example.baobang.threebird.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeFragmentView{

    @BindView(R.id.layoutStaticalOrder)
    LinearLayout layoutStaticalOrder;
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

        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);

        return view;
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
    public void showItemFragment(View view, ArrayList<ItemFragment> itemFragments) {

        ArrayList<LinearLayout> linearLayouts = new ArrayList<>();

        for(int i = 0; i < itemFragments.size(); i++){
            LinearLayout layout = createLinearLayout(i);
            linearLayouts.add(layout);
        }
        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager != null){
            for(int i = 1; i < itemFragments.size(); i++){
                getFragmentManager().beginTransaction().add(linearLayouts.get(i).getId(),itemFragments.get(i), "tag" + i).commit();
            }
        }
    }

    private LinearLayout createLinearLayout(int id){
        LinearLayout layout = new LinearLayout(getContext());
        layout.setId(id);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = R.dimen.margin_vertical;
        layoutStaticalOrder.addView(layout);
        return layout;
    }
}
