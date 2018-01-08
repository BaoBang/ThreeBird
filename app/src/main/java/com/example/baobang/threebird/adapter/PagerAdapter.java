package com.example.baobang.threebird.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.utils.Model;
import java.util.List;


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
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if(layoutInflater != null){
           view = layoutInflater.inflate(R.layout.item, container, false);
        }
        Model model = this.models.get(position);
        ImageView img = view == null ? null : (ImageView) view.findViewById(R.id.img);
        TextView txtTitle = view == null ? null : (TextView) view.findViewById(R.id.txtTitle);
        TextView txtAmount = view == null ? null : (TextView) view.findViewById(R.id.txtAmount);
        if(img != null){
            img.setImageResource(model.getImage());
        }
        if(txtTitle != null){
            txtTitle.setText(model.getTitle());
        }

        if(txtAmount != null){
            txtAmount.setText(String.valueOf(model.getAmount()));
        }

        // add view to Viewpager
        container.addView(view);

        return view == null ? new View(context) : view;
    }
}
