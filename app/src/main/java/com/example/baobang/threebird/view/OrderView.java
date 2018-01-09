package com.example.baobang.threebird.view;

import android.graphics.Bitmap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.Product;

import java.util.ArrayList;
import java.util.Date;


public interface OrderView {
    void addControls();
    void addEvents();
    void showDialogClient(ArrayList<Client> clients);
    void showDialogProduct(ArrayList<Product> products);
    void showSpinnerDistrict(ArrayList<String> districts);
    void showSpinnerProvince(ArrayList<String> provinces);
    void showSpinnerCommune(ArrayList<String> communes);
    void showSpinnerPayment(ArrayList<String> payments);
    void showSpinnerStatus(ArrayList<String> statues);
    void setDataForInput(Bitmap bitmap, String name, int orderId,
                         Date createdAt, int status, String phone, String province,
                         String district, String commune, String address,
                         int amount, Date deliveryDate, int payment);
    void setDisableInput();
    void updateAmountOfProductInCard(int index, int productId);
    int checkLayoutProduct(int id);
    LinearLayout createLinearLayout(int id);
    RelativeLayout createRelativeLayout();
    ImageView createImageView(Product product);
    ImageButton createImageRemoveButton(ImageView imgView);
    void updateProductAmount();
    void addProductToLayout(final Product product);
    void addClientInfoFromListToView(Bitmap bitmap, String name, String phone,
                                     String province, String district, String commune,
                                     String address);

    void setSpinnerPaymentSelectedPosition(int position);
    void setSpinnerProvincePosition(int position);
    void setSpinnerDistrictSelectedPosition(int position);
    void setSpinnerCommuneSelectedPosition(int position);

    boolean checkInput();
}
