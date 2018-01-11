package com.example.baobang.threebird.view.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.utils.ItemFragmentModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {

    public ItemFragment() {
        // Required empty public constructor
    }

    public static ItemFragment newInstance(ItemFragmentModel model2) {

        Bundle args = new Bundle();
        args.putSerializable("MODEL2", model2);
        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        model2 = (ItemFragmentModel) savedInstanceState.getSerializable("MODEL2");
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        TextView txtResult  = view.findViewById(R.id.txtResult);
        TextView txtNumber1 = view.findViewById(R.id.txtNumber1);
        TextView txtNumber2 = view.findViewById(R.id.txtNumber2);
        TextView txtNumber3 = view.findViewById(R.id.txtNumber3);

        LinearLayout layout = view.findViewById(R.id.layout);
        ItemFragmentModel model2 = (ItemFragmentModel)  getArguments().getSerializable("MODEL2");
       // ItemFragmentModel model2  = new ItemFragmentModel(3, "MỚI", 100,20000,30000);

        txtResult.setText(model2 == null ? null : model2.getResult());
        txtNumber1.setText(String.valueOf(model2 == null ? null : model2.getNumber1()));
        txtNumber2.setText(new StringBuilder(model2 == null ? null : model2.getNumber2() + "đ"));
        txtNumber3.setText(new StringBuilder(model2 == null ? null : model2.getNumber3() + "đ"));
        switch (model2 == null ? Color.BLUE : model2.getColor()){
            case 1:
                txtNumber3.setTextColor(Color.GREEN);
                layout.setBackgroundResource(R.drawable.background_border_left_green);
                break;
            case 2:
                txtNumber3.setTextColor(Color.RED);
                layout.setBackgroundResource(R.drawable.background_border_left_red);
                break;
            case 3:
                txtNumber3.setTextColor(Color.BLUE);
                layout.setBackgroundResource(R.drawable.background_border_left_blue);
                break;
        }
    }
}
