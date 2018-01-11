package com.example.baobang.threebird.view;

import android.app.Activity;
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

public interface ProductView{
    void addControls();

    void addEvents();

    void setDataForInput(int id, int priceInventory, int price, int inventory,
                         String productName, String detail, int brandId,
                         int categoryId, ArrayList<String> images);
    void setDisableInput();

    void showSpinnerCategory(ArrayList<Category> categories);

    void showSpinnerBrand(ArrayList<Brand> brands);

    RelativeLayout createImageViewAndImageButtonRemove(final int key, Bitmap bitmap);

    RelativeLayout createRelativeLayout();

    ImageButton createImageRemoveButton(ImageView imgView);

    void setBrandSelectedPosition(int position);

    void setCategorySelectedPosition(int position);

    void changeActivity(int productId);

    void showMessage(String message);

    void showProductNameWarning(String message);

    void showProductIdWarning(String message);

    void showProductPriceWarning(String message);

    void showProductPriceInventoryWarning(String message);

    void showProductInventoryWarning(String message);
}
