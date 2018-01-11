package com.example.baobang.threebird.view;

import android.view.View;

import com.example.baobang.threebird.utils.SlideModel;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baobang on 1/11/18.
 */

public interface HomeFragmentView {

    void showSlide(View view, List<SlideModel> models);
    void showLineChart(View view, ArrayList<Entry> entries , ArrayList<String> lables);
    void showItemFragment(View view);
}
