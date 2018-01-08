package com.example.baobang.threebird.view;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.ClientGroup;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.Product;

import java.util.ArrayList;

/**
 * Created by baobang on 1/8/18.
 */

public interface OrderView {
    void addControls();
    void addEvents();
    void showSpinnerDistrict(ArrayList<String> districts);
    void showSpinnerProvince(ArrayList<String> provinces);
    void showSpinnerCommune(ArrayList<String> communes);
    void showSpinnerPayment(ArrayList<String> payments);
    void showSpinnerStatus(ArrayList<String> statues);
    void setDataForInput();
    void setDisableInput();
    void showDialogProduct();
    void updateAmountOfProductInCard(int index, int productId);
    int checkLayoutProduct(int id);
    LinearLayout createLinearLayout(int id);
    RelativeLayout createRelativeLayout();
    ImageView createImageView(Product product);
    ImageButton createImageRemoveButton(ImageView imgView);
    void updateProductAmount();
    void addProductToLayout(final Product product);
    void showDialogClient();
    void addClientInfoFromListToView(Client client);
}
