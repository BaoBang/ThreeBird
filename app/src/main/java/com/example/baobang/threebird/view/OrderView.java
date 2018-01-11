package com.example.baobang.threebird.view;

import android.graphics.Bitmap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.Commune;
import com.example.baobang.threebird.model.District;
import com.example.baobang.threebird.model.Payment;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.Province;
import com.example.baobang.threebird.model.Status;

import java.util.ArrayList;
import java.util.Date;


public interface OrderView {
    void addControls();

    void addEvents();

    void showDialogClient(ArrayList<Client> clients);

    void showDialogProduct(ArrayList<Product> products);

    void showSpinnerDistrict(ArrayList<District> districts);

    void showSpinnerProvince(ArrayList<Province> provinces);

    void showSpinnerCommune(ArrayList<Commune> communes);

    void showSpinnerPayment(ArrayList<Payment> payments);

    void showSpinnerStatus(ArrayList<Status> statues);

    void setDataForInput(Bitmap bitmap, String name, int orderId,
                         Date createdAt, int status, String phone,
                         int province, int district, int commune,
                         String address, int amount,
                         Date deliveryDate, int payment);

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
                                     int province, int district, int commune,
                                     String address);

    void setSpinnerPaymentSelectedPosition(int position);

    void setSpinnerProvincePosition(int position);

    void setSpinnerDistrictSelectedPosition(int position);

    void setSpinnerCommuneSelectedPosition(int position);

    void showMessage(String message);

    void showClientNameWarning(String message);

    void showOrderIdWarning(String message);

    void showPhoneWarning(String message);

    void showAddressWarning(String message);

    void changeActivity(int orderId);

    void setSpinnerStatusSelectedPosition(int position);
}
