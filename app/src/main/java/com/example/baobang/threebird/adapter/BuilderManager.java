package com.example.baobang.threebird.adapter;

import com.example.baobang.threebird.R;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.Util;

/**
 * Created by baobang on 1/25/18.
 */

public class BuilderManager {
    private static int[] imageResources = new int[]{
            R.drawable.ic_clock,
            R.drawable.ic_clock,
            R.drawable.ic_clock,
            R.drawable.ic_clock,
            R.drawable.ic_clock,
            R.drawable.ic_clock,
            R.drawable.ic_clock,
            R.drawable.ic_clock,
            R.drawable.ic_clock,
            R.drawable.ic_clock,
            R.drawable.ic_clock,
            R.drawable.ic_clock,
            R.drawable.ic_clock,
            R.drawable.ic_clock,
            R.drawable.ic_clock,
            R.drawable.ic_clock
    };

    private static int imageResourceIndex = 0;

    static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }

    static SimpleCircleButton.Builder getSimpleCircleButtonBuilder() {
        return new SimpleCircleButton.Builder()
                .normalImageRes(getImageResource());
    }

    static SimpleCircleButton.Builder getSquareSimpleCircleButtonBuilder() {
        return new SimpleCircleButton.Builder()
                .isRound(false)
                .shadowCornerRadius(Util.dp2px(20))
                .buttonCornerRadius(Util.dp2px(20))
                .normalImageRes(getImageResource());
    }
}
