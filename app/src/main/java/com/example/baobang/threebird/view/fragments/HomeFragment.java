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
import com.example.baobang.threebird.utils.Model;
import com.example.baobang.threebird.utils.Model2;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.utils.ViewPagerHander;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

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
        //slide


        ViewPager viewPager = view.findViewById(R.id.pager);

        FrameLayout layoutRoot =  view.findViewById(R.id.layoutRoot);
        Utils.hideKeyboardOutside(layoutRoot, getActivity());

        CircleIndicator circleIndicator = view.findViewById(R.id.indicator);
        List<Model> models;
        models = new ArrayList<>();
        models.add(new Model(R.drawable.image1, "HOÀN THÀNH", 10));
        models.add(new Model(R.drawable.image2, "ĐANG CHỜ", 20));
        models.add(new Model(R.drawable.image3, "HỦY BỎ", 30));
        ViewPagerHander viewPagerHander = new ViewPagerHander(getContext(), viewPager, circleIndicator, models);
        viewPagerHander.hander();

        //chart


        LineChart mChart;mChart = view.findViewById(R.id.chart);
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(10f, 0));
        entries.add(new Entry(50f, 1));
        entries.add(new Entry(10f, 2));
        entries.add(new Entry(10f, 3));
        entries.add(new Entry(15f, 4));
        entries.add(new Entry(60f, 5));
        entries.add(new Entry(-10f, 6));

        ArrayList<String> lables = new ArrayList<>();
        lables.add("A");
        lables.add("B");
        lables.add("C");
        lables.add("D");
        lables.add("A");
        lables.add("B");
        lables.add("C");
        lables.add("D");
        com.example.baobang.threebird.utils.LineChart chart =
                new com.example.baobang.threebird.utils.LineChart("This is a line chart",mChart, entries, lables);
        chart.drawLineChart();

        Model2 model21  = new Model2(1, "HOÀN THÀNH", 100,20000,30000);
        Model2 model22  = new Model2(2, "HỦY", 100,20000,30000);
        Model2 model23  = new Model2(3, "MỚI", 100,20000,30000);

        ItemFragment fragment1 = ItemFragment.newInstance(model21);
        ItemFragment fragment2 = ItemFragment.newInstance(model22);
        ItemFragment fragment3 = ItemFragment.newInstance(model23);

        LinearLayout fragmentSuccess  =  view.findViewById(R.id.fragment_complete);
        LinearLayout fragmentCanCel  =  view.findViewById(R.id.fragment_cancle);
        LinearLayout fragmentNew =  view.findViewById(R.id.fragment_new);

        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager != null){
            getFragmentManager().beginTransaction().add(fragmentSuccess.getId(),fragment1, "tag1").commit();
            getFragmentManager().beginTransaction().add(fragmentCanCel.getId(),fragment2, "tag2").commit();
            getFragmentManager().beginTransaction().add(fragmentNew.getId(),fragment3, "tag3").commit();
        }

    }
}
