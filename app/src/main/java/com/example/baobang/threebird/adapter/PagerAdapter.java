package com.example.baobang.threebird.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.utils.Model;

import java.util.List;

/**
 * Created by baobang on 11/28/17.
 */

public class PagerAdapter extends android.support.v4.view.PagerAdapter {
    private List<Model> models;
    private Context context;

    public PagerAdapter(Context context, List<Model> models){
        this.context = context;
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item, container, false);
        Model model = this.models.get(position);
        ImageView img = view.findViewById(R.id.img);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtAmount = view.findViewById(R.id.txtAmount);
        img.setImageResource(model.getImage());
        txtTitle.setText(model.getTitle());
        txtAmount.setText(model.getAmount() + "");

        // add view to Viewpager
        ((ViewPager)container).addView(view);

        return view;
    }
}
