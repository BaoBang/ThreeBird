package com.example.baobang.threebird.view;

import android.graphics.Bitmap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.baobang.threebird.model.Brand;
import com.example.baobang.threebird.model.Category;
import com.example.baobang.threebird.model.Product;

import java.util.ArrayList;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

/**
 * Created by baobang on 1/8/18.
 */

public interface ProductView {
    void addControls();
    void addEvents();
    void setDataForInput();
    void setDisableInput();
    void showSpinnerCategory(ArrayList<Category> categories);
    void showSpinnerBrand(ArrayList<Brand> brands);
    void setError(ExtendedEditText extendedEditText, String message);
    void addImageView(final LinearLayout layoutImage, final int key, Bitmap bitmap);
    RelativeLayout createRelativeLayout();
    ImageButton createImageRemoveButton(ImageView imgView);
    Product getProductFromInput();
}
