package com.example.baobang.threebird.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.adapter.ClientAdapter;
import com.example.baobang.threebird.adapter.ProductAdapter;
import com.example.baobang.threebird.annimator.VegaLayoutManager;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.Commune;
import com.example.baobang.threebird.model.District;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.Payment;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.ProductOrder;
import com.example.baobang.threebird.model.Province;
import com.example.baobang.threebird.model.Status;
import com.example.baobang.threebird.model.helper.OrderHelper;
import com.example.baobang.threebird.presenter.imp.OrderPresenterImp;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.OrderView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class OrderActivity extends AppCompatActivity implements OrderView{

    private OrderPresenterImp orderPresenterImp;

    @BindView(R.id.toolBarCreateOrder)
    Toolbar toolbar;

    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;

    @BindView(R.id.txtName)
    ExtendedEditText txtName;

    @BindView(R.id.txtOrderId)
    ExtendedEditText txtOrderId;

    @BindView(R.id.txtPhone)
    ExtendedEditText txtPhone;

    @BindView(R.id.txtAddress)
    ExtendedEditText txtAddress;

    @BindView(R.id.txtCreatedAt)
    ExtendedEditText txtCreatedAt;

    @BindView(R.id.txtDeliveryDate)
    ExtendedEditText txtDeliveryDate;

    @BindView(R.id.txtAmount)
    TextView txtAmount;

    @BindView(R.id.btnAddClient)
    ImageButton btnAddClient;

    @BindView(R.id.btnCreatedAt)
    ImageButton btnCreatedAt;

    @BindView(R.id.btnAddProduct)
    ImageButton btnAddProduct;

    @BindView(R.id.btnDeliveryDate)
    ImageButton btnDeliveryDate;

    @BindView(R.id.layoutProduct)
    LinearLayout layoutProduct;

    @BindView(R.id.spStatus)
    Spinner spStatus;

    @BindView(R.id.spProvince)
    Spinner spProvince;

    @BindView(R.id.spDistrict)
    Spinner spDistrict;

    @BindView(R.id.spCommune)
    Spinner spCommune;

    @BindView(R.id.spPayment)
    Spinner spPayment;

    @BindView(R.id.tfbDeliveryDate)
    TextFieldBoxes tfbDeliveryDate;

    @BindView(R.id.tfbCreateAt)
    TextFieldBoxes tfbCreatedAt;

    @BindView(R.id.layoutRoot)
    LinearLayout layoutRoot;

    private ArrayList<Province> provinces;
    private ArrayList<District> districts;
    private ArrayList<Commune> communes;
    private ArrayList<Status> statuses;
    private ArrayList<Payment> payments;

    private Order order = null;
    private List<ProductOrder> productList = new ArrayList<>();
    private int option;
    private int clientSelectedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activy_order);

        ButterKnife.bind(this);

        Utils.hideKeyboardOutside(layoutRoot, this);

        orderPresenterImp = new OrderPresenterImp(this);

        Bundle bundle = getIntent().getExtras();
        order = orderPresenterImp.getOrderFromBundle(bundle);
        option = orderPresenterImp.getOptionFromBundle(bundle);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi", "VN"));
        Date date = new Date();
        try {
            date   = simpleDateFormat.parse("11/1/2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();

        Order order = OrderHelper.getOrder(0);
        order.setCreatedAt(date);
        OrderHelper.updateOrder(order);

        order = OrderHelper.getOrder(3);
        calendar.add(Calendar.MONTH, 2);
        order.setCreatedAt(calendar.getTime());
        OrderHelper.updateOrder(order);

        orderPresenterImp.init();
    }

    @Override
    public void addControls() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tfbCreatedAt.setEnabled(false);
        txtCreatedAt.setEnabled(false);

        tfbDeliveryDate.setEnabled(false);
        txtDeliveryDate.setEnabled(false);

        txtAmount.setText(String.valueOf(orderPresenterImp.getAmountAllProduct(productList)));

        orderPresenterImp.loadSpinnerData();

        if(order != null){
            productList = orderPresenterImp.getProductListFromOrder(order);
            orderPresenterImp.setData(this, order);
        }

        if(option == Constants.DETAIL_OPTION){
            setDisableInput();
        }
    }

    @Override
    public void setDisableInput() {
        TextFieldBoxes tfbName = findViewById(R.id.tfbName);
        tfbName.setEnabled(false);
        txtName.setEnabled(false);

        TextFieldBoxes tfbOrderId = findViewById(R.id.tfbOrderId);
        tfbOrderId.setEnabled(false);
        txtOrderId.setEnabled(false);

        TextFieldBoxes tfbPhone = findViewById(R.id.tfbPhone);
        tfbPhone.setEnabled(false);
        txtPhone.setEnabled(false);

        TextFieldBoxes tfbAddress = findViewById(R.id.tfbAddress);
        tfbAddress.setEnabled(false);
        txtAddress.setEnabled(false);

        txtAmount.setEnabled(false);
        txtCreatedAt.setEnabled(false);
        txtDeliveryDate.setEnabled(false);

        btnCreatedAt.setEnabled(false);
        btnAddProduct.setEnabled(false);
        btnDeliveryDate.setEnabled(false);
        btnAddClient.setEnabled(false);

        spCommune.setEnabled(false);
        spDistrict.setEnabled(false);
        spPayment.setEnabled(false);
        spProvince.setEnabled(false);
        spStatus.setEnabled(false);

        layoutProduct.setEnabled(false);
    }

    @Override
    public void setDataForInput(Bitmap bitmap, String name, int orderId,
                                Date createdAt, int statusPosition, String phone,
                                int province,int district, int commune,
                                String address,int amount, Date deliveryDate, int paymentPosition) {

        imgAvatar.setImageBitmap(Utils.getRoundedRectBitmap(bitmap));
        txtName.setText(name);
        txtOrderId.setText(String.valueOf(orderId));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi","VN"));
        txtCreatedAt.setText(simpleDateFormat.format(createdAt));

        spStatus.setSelection(statusPosition);

        txtPhone.setText(phone);

        orderPresenterImp.setProvinceSelected(provinces, province);
        orderPresenterImp.setDistrictSelected(districts, district);
        orderPresenterImp.setCommuneSelected(communes, commune);

        txtAddress.setText(address);
        txtAmount.setText(String.valueOf(amount));

        orderPresenterImp.addProductToLayout(productList);

        txtDeliveryDate.setText(simpleDateFormat.format(deliveryDate));

        spPayment.setSelection(paymentPosition);
    }

    @OnClick({R.id.btnCreatedAt,R.id.btnAddProduct,
            R.id.btnDeliveryDate,R.id.btnAddClient})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnCreatedAt:
                Utils.getDate(this, txtCreatedAt);
                break;
            case R.id.btnAddProduct:
                orderPresenterImp.showDialogProduct();
                break;
            case R.id.btnDeliveryDate:
                Utils.getDate(this, txtDeliveryDate);
                break;
            case R.id.btnAddClient:
                orderPresenterImp.showDialogClient();
                break;
        }
    }

    @OnTextChanged(value = {R.id.txtOrderId, R.id.txtPhone},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChanged(Editable editable){

        View view = getCurrentFocus();

        if(view == null) return;

        switch (view.getId()){

            case R.id.txtOrderId:
                if(!editable.toString().matches(Constants.NUMBER_REGUAR)){
                    showOrderIdWarning("Nhập vào kí tự số!");
                }
                break;
            case R.id.txtPhone:
                if(!editable.toString().matches(Constants.PHONE_REGULAR)){
                    showPhoneWarning("Số điện thoại bắt đầu với +84/0 và tiếp theo từ 9-10 kí tự số");
                }
                break;
        }
    }

    @Override
    public void addEvents() {
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    public void showSpinnerDistrict(ArrayList<District> districts) {

        this.districts = districts;
        ArrayAdapter<District> adapterDistrict = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                districts);
        spDistrict.setAdapter(adapterDistrict);
    }

    @Override
    public void showSpinnerProvince(ArrayList<Province> provinces) {

        this.provinces = provinces;
        ArrayAdapter<Province> adapterProvince = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                provinces);
        spProvince.setAdapter(adapterProvince);
    }

    @Override
    public void showSpinnerCommune(ArrayList<Commune> communes) {

        this.communes = communes;
        ArrayAdapter<Commune> adapterCommune = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                communes);
        spCommune.setAdapter(adapterCommune);
    }

    @Override
    public void showSpinnerPayment(ArrayList<Payment> payments) {

        this.payments = payments;
        ArrayAdapter<Payment> adapterPayment = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                payments);
        spPayment.setAdapter(adapterPayment);
    }

    @Override
    public void showSpinnerStatus(ArrayList<Status> statues) {
        this.statuses = statues;
        ArrayAdapter<Status> adapterStatus = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                statues);
        spStatus.setAdapter(adapterStatus);
    }

    @Override
    public void showDialogProduct(ArrayList<Product> products) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderActivity.this, R.drawable.background_color_gradient);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View convertView = inflater.inflate(R.layout.recycleview_dialog, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Danh sách sản phẩm");
        RecyclerView rcProudcts =  convertView.findViewById(R.id.recyclerView);
        final AlertDialog dialog = alertDialog.show();
        ProductAdapter adapter = new ProductAdapter(products, rcProudcts, item -> {
            Product product = (Product) item;
            productList = orderPresenterImp.getProductFromList(productList, product);
            txtAmount.setText(String.valueOf(orderPresenterImp.getAmountAllProduct(productList)));
            addProductToLayout(product);
            dialog.dismiss();
        });
        rcProudcts.setLayoutManager(new VegaLayoutManager());
        rcProudcts.setAdapter(adapter);
    }

    @Override
    public int checkLayoutProduct(int id){
        for(int i = 0; i < layoutProduct.getChildCount(); i++){
            LinearLayout layout = (LinearLayout) layoutProduct.getChildAt(i);
            if(id == layout.getId()){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void updateAmountOfProductInCard(int index, int productId){
        LinearLayout layout = (LinearLayout) layoutProduct.getChildAt(index);
        TextView textView = (TextView) layout.getChildAt(2);
        String text = "Số lượng: " + orderPresenterImp.getAmountProduct(productList, productId);
        textView.setText(text);
    }

    @Override
    public LinearLayout createLinearLayout(int id){
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setId(id);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        layoutParams.setMargins(0, 0, Constants.MARGIN_SMALL, 0);
        layout.setLayoutParams(layoutParams);
        return layout;
    }

    @Override
    public RelativeLayout createRelativeLayout(){
        RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams paramsRelativeLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayout.setLayoutParams(paramsRelativeLayout);
        return  relativeLayout;
    }

    @Override
    public ImageView createImageView(Product product){
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView imageView = new ImageView(this);
        Bitmap bitmap = orderPresenterImp.getImageFromProduct(this, product);
        imageView.setImageBitmap(bitmap);
        imageView.setLayoutParams(imageParams);
        imageView.getLayoutParams().height = Constants.IMAGE_HEIGHT * 2;
        imageView.getLayoutParams().width = Constants.IMAGE_WIDTH * 2;
//        imageParams.gravity = Gravity.CENTER;
        return  imageView;
    }

    @Override
    public ImageButton createImageRemoveButton(ImageView imgView){
        RelativeLayout.LayoutParams imageButtonParams = new RelativeLayout.LayoutParams(32, 32);
        imageButtonParams.setMargins(0, 10, 0, 0);
        imageButtonParams.addRule(RelativeLayout.END_OF, imgView.getId());
        ImageButton btnRemove = new ImageButton(imgView.getContext());
        btnRemove.setImageResource(R.drawable.ic_close_red);
        btnRemove.setLayoutParams(imageButtonParams);
        return  btnRemove;
    }

    @Override
    public void updateProductAmount(){
        txtAmount.setText(String.valueOf(orderPresenterImp.getAmountAllProduct(productList)));
    }

    @Override
    public void addProductToLayout(final Product product) {
        int index = checkLayoutProduct(product.getId());
        if(index != -1){
            updateAmountOfProductInCard(index, product.getId());
        }else{

            final LinearLayout layout =createLinearLayout(product.getId());
            //layout contain image product and button remove
            RelativeLayout relativeLayout = createRelativeLayout();
            // image product
            ImageView imageView = createImageView(product);
            // image button remove
            ImageButton imageButtonRemove = createImageRemoveButton(imageView);
            if(option == Constants.DETAIL_OPTION) imageButtonRemove.setEnabled(false);

            relativeLayout.addView(imageView);
            relativeLayout.addView(imageButtonRemove);
            imageButtonRemove.setOnClickListener(view -> {
                for(ProductOrder productOrder : productList){
                    if(productOrder.getProductId() == product.getId()){
                        productList.remove(productOrder);
                        break;
                    }
                }
                layoutProduct.removeView(layout);
                updateProductAmount();
            });

            layout.addView(relativeLayout);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            // add product name
            TextView txtName = new TextView(this);
            txtName.setLayoutParams(params);
            String text = "Tên sản phẩm: " + product.getName();
            txtName.setText(text);
            layout.addView(txtName);
            // add amount
            TextView txtAmount = new TextView(this);
            txtAmount.setLayoutParams(params);
            text = "Số lượng: "+orderPresenterImp.getAmountProduct(productList, product.getId());
            txtAmount.setText(text);
            layout.addView(txtAmount);
            // add price
            TextView txtPrice = new TextView(this);
            txtPrice.setLayoutParams(params);
            text = "Đơn giá: " + product.getPrice() + "đ";
            txtPrice.setText(text);
            layout.addView(txtPrice);

            layoutProduct.addView(layout);
        }
    }

    @Override
    public void showDialogClient(ArrayList<Client> clients) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderActivity.this, R.drawable.background_color_gradient);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View convertView = inflater.inflate(R.layout.recycleview_dialog, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Danh sách khách hàng");
        RecyclerView rcClients =  convertView.findViewById(R.id.recyclerView);
        final AlertDialog dialog = alertDialog.show();
        ClientAdapter adapter = new ClientAdapter(clients, rcClients, item -> {
            Client client = (Client) item;
            dialog.dismiss();
            clientSelectedId = orderPresenterImp.setClientData(this, client);
        });
        rcClients.setLayoutManager(new VegaLayoutManager());
        rcClients.setAdapter(adapter);
    }

    @Override
    public void addClientInfoFromListToView(Bitmap bitmap, String name, String phone,
                                            int province, int district, int commune,
                                            String address) {
        imgAvatar.setImageBitmap(Utils.getRoundedRectBitmap(bitmap));


        txtName.setText(name);
        txtPhone.setText(phone);
        orderPresenterImp.setProvinceSelected(provinces, province);
        orderPresenterImp.setDistrictSelected(districts, district);
        orderPresenterImp.setCommuneSelected(communes, commune);
        txtAddress.setText(address);

    }

    @Override
    public void setSpinnerPaymentSelectedPosition(int position) {
        spPayment.setSelection(position);
    }

    @Override
    public void setSpinnerProvincePosition(int position) {
        spProvince.setSelection(position);
    }

    @Override
    public void setSpinnerDistrictSelectedPosition(int position) {
        spDistrict.setSelection(position);
    }

    @Override
    public void setSpinnerCommuneSelectedPosition(int position) {
        spCommune.setSelection(position);
    }

    @Override
    public void showMessage(String message) {
        Utils.openDialog(this, message);
    }

    @Override
    public void showClientNameWarning(String message) {
        txtName.setError(message);
        txtName.requestFocus();
    }

    @Override
    public void showOrderIdWarning(String message) {
        txtOrderId.setError(message);
        txtOrderId.requestFocus();
    }

    @Override
    public void showPhoneWarning(String message) {
        txtPhone.setError(message);
        txtPhone.requestFocus();
    }

    @Override
    public void showAddressWarning(String message) {
        txtAddress.setError(message);
        txtAddress.requestFocus();
    }

    @Override
    public void changeActivity(int orderId) {
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ORDER,orderId);
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public void setSpinnerStatusSelectedPosition(int position) {
        spStatus.setSelection(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.actionBar_add){
            if(option != Constants.DETAIL_OPTION){

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi", "VN"));

                String name = txtName.getText().toString();

                String createdAtStr = txtCreatedAt.getText().toString();
                Date date = null;
                try {
                    date = createdAtStr.equals("") ? null : simpleDateFormat.parse(createdAtStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int position = spStatus.getSelectedItemPosition();
                Status status = position == 0 ? null : statuses.get(position);

                String phone = txtPhone.getText().toString();

                position = spProvince.getSelectedItemPosition();
                Province province = position > 0 ? provinces.get(position) : null;

                position = spDistrict.getSelectedItemPosition();
                District district = districts.get(position);

                position = spCommune.getSelectedItemPosition();
                Commune commune = communes.get(position);

                String address = txtAddress.getText().toString();

                String deliveryDateStr = txtDeliveryDate.getText().toString();
                Date deliveryDate = null;
                try {
                    deliveryDate = deliveryDateStr.equals("") ? null : simpleDateFormat.parse(deliveryDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                position = spPayment.getSelectedItemPosition();
                Payment payment = position == 0 ? null : payments.get(position);

                orderPresenterImp.clickAddOptionMenu(order, option, txtOrderId.getText().toString(), name,
                        date, status, phone,
                        province, district, commune,
                        address, productList, deliveryDate, payment);

            }else{
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}


