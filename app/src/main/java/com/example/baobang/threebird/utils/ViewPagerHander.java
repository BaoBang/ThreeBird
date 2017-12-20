package com.example.baobang.threebird.utils;
import android.content.Context;
import android.support.v4.view.ViewPager;

import com.example.baobang.threebird.adapter.PagerAdapter;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by baobang on 11/28/17.
 */

public class ViewPagerHander {
    private Context context;
    private List<Model> models;
    private ViewPager viewPager;
    private int current = 0;
    private int scrollState = 0;
    private CircleIndicator circleIndicator;
    public ViewPagerHander(Context context, ViewPager viewPager, CircleIndicator circleIndicator,List<Model> models){
        this.models = models;
        this.viewPager = viewPager;
        this.context = context;
        this.circleIndicator = circleIndicator;
    }
    public void hander(){
        PagerAdapter pagerAdapter = new PagerAdapter(this.context, models);
        viewPager.setAdapter(pagerAdapter);

        circleIndicator.setViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                current = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                handleScrollState(state);
                scrollState = state;
            }
        });
    }

    private void handleScrollState(int state) {
        if(state == ViewPager.SCROLL_STATE_IDLE)
            setNextItemIfNeed();
    }

    private void setNextItemIfNeed() {
        if(!isScrollStateSettling()){
            handleNextItem();
        }
    }

    private void handleNextItem() {
        int lastItem = viewPager.getAdapter().getCount() - 1;
        if(current == 0)
            viewPager.setCurrentItem(lastItem, false);
        else if(current == lastItem)
            viewPager.setCurrentItem(0, false);

    }

    private boolean isScrollStateSettling() {
        return scrollState == ViewPager.SCROLL_STATE_SETTLING;
    }
}
