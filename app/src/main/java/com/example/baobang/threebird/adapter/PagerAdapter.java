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
import com.example.baobang.threebird.utils.SlideModel;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PagerAdapter extends android.support.v4.view.PagerAdapter {
    private List<SlideModel> models;
    private Context context;

    public PagerAdapter(Context context, List<SlideModel> models){
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
        SlideModel model = this.models.get(position);

        ViewHolder holder = new ViewHolder(view);

        holder.imageView.setImageResource(model.getImage());
        holder.txtTitle.setText(model.getTitle());
            holder.txtAmount.setText(String.valueOf(model.getAmount()));
        // add view to Viewpager
        container.addView(view);

        return view == null ? new View(context) : view;
    }

    public class ViewHolder{
        @BindView(R.id.img)
        ImageView imageView;

        @BindView(R.id.txtTitle)
        TextView txtTitle;

        @BindView(R.id.txtAmount)
        TextView txtAmount;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
