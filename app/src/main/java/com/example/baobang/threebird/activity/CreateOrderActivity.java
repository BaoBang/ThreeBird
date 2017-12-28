package com.example.baobang.threebird.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.bussinesslogic.ClientBL;
import com.example.baobang.threebird.model.bussinesslogic.OrderBL;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.MySupport;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ImageView imgAvatar;
    private EditText txtName, txtOrderId, txtPhone, txtAddress;
    private TextView txtCreatedAt, txtAmount, txtDeliveryDate;


    private Spinner spClient, spStatus, spProvince,
            spDistrict, spCommune,  spPayment;
    private ArrayList<String> statuses, provinces,
            districts, communes, payments;
    private ArrayList<Client> clients;
    private ArrayAdapter<String> adapterStatus, adapterProvince,
            adapterDistrict, adapterCommune, adapterPayment;
    private ArrayAdapter<Client> adapterClient;

    private ImageButton btnCreatedAt, btnAddProduct, btnDeliveryDate;

    private Order order = null;
    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activy_create_order);
        order = getOrder();
        addControlls();
        addEvents();
    }

    private void addControlls() {
        toolbar = findViewById(R.id.toolBarCreateOrder);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //
        imgAvatar = findViewById(R.id.imgAvatar);

        txtName = findViewById(R.id.txtName);
        txtOrderId = findViewById(R.id.txtOrderId);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);

        txtAmount = findViewById(R.id.txtAmount);
        txtCreatedAt = findViewById(R.id.txtCreatedAt);
        txtDeliveryDate = findViewById(R.id.txtDeliveryDate);

        btnCreatedAt = findViewById(R.id.btnCreatedAt);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnDeliveryDate = findViewById(R.id.btnDeliveryDate);

        addSpinnerClient();
        addSpinnerStatus();
        addSpinnerProvince();
        addSpinnerDistrict();
        addSpinnerCommune();
        addSpinnerPayment();

        if(order != null){
            setDataForInput();
        }
    }

    private void setDataForInput() {
        txtName.setText(order.getClientName());
        txtOrderId.setText(order.getId() + "");
        txtCreatedAt.setText(order.getCreatedAt().toString());
        spStatus.setSelection(order.getStatus() + 1);
        txtPhone.setText(order.getPhone());
        for(int i = 0; i < provinces.size(); i++){
            if(order.getAddress().getProvince().equals(provinces.get(i))){
                spProvince.setSelection(i);
                break;
            }
        }
        for(int i = 0; i < districts.size(); i++){
            if(order.getAddress().getDistrict().equals(districts.get(i))){
                spDistrict.setSelection(i);
                break;
            }
        }
        for(int i = 0; i < communes.size(); i++){
            if(order.getAddress().getCommune().equals(communes.get(i))){
                spCommune.setSelection(i);
                break;
            }
        }

        txtAmount.setText(order.getAmount());
        txtDeliveryDate.setText(order.getLiveryDate().toString());

        spPayment.setSelection(order.getPayments() + 1);
    }

    private void addSpinnerPayment() {
        spPayment = findViewById(R.id.spPayment);
        payments = getPayments();
        adapterPayment = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                payments);
        spPayment.setAdapter(adapterPayment);
    }

    private void addSpinnerCommune() {
        spCommune = findViewById(R.id.spCommune);
        communes = getCommunes();
        adapterCommune = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                communes);
        spCommune.setAdapter(adapterCommune);
    }

    private void addSpinnerDistrict() {
        spDistrict = findViewById(R.id.spDistrict);
        districts = getDistricts();
        adapterDistrict = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                districts);
        spDistrict.setAdapter(adapterDistrict);
    }

    private void addSpinnerProvince() {
        spProvince = findViewById(R.id.spProvince);
        provinces = getProvinces();
        adapterProvince = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                provinces);
        spProvince.setAdapter(adapterProvince);
    }

    private void addSpinnerStatus() {
        spStatus = findViewById(R.id.spStatus);
        statuses = getStatuses();
        adapterStatus = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                statuses);
        spStatus.setAdapter(adapterStatus);
    }

    private void addSpinnerClient() {
        spClient = findViewById(R.id.spClient);
        clients = getClients();
        adapterClient = new ArrayAdapter<Client>(
                this,
                android.R.layout.simple_list_item_1,
                clients);
        spClient.setAdapter(adapterClient);
    }

    private void addEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnCreatedAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySupport.getDate(CreateOrderActivity.this, txtCreatedAt);
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnDeliveryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySupport.getDate(CreateOrderActivity.this, txtDeliveryDate);
            }
        });
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
            addOrder();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addOrder() {
        String name = txtName.getText().toString();
        int id = Integer.parseInt(txtOrderId.getText().toString());
        String createdAtStr = txtCreatedAt.getText().toString();
        int status = spStatus.getSelectedItemPosition();
        String phone = txtPhone.getText().toString();
        int province = spProvince.getSelectedItemPosition();
        int district = spDistrict.getSelectedItemPosition();
        int commune = spCommune.getSelectedItemPosition();
        String address = txtAddress.getText().toString();
        String deliveryDateStr = txtDeliveryDate.getText().toString();
        int payment = spPayment.getSelectedItemPosition();

        if(MySupport.checkInput(name)){
            MySupport.openDialog(this, "Vui lòng nhập vào tên khách hàng");
            return;
        }

    }


    private ArrayList<String> getProvinces(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Tỉnh/Thành Phố...");
        list.add("An Giang");
        list.add("Bình Phước");
        list.add("Ninh Bình");
        list.add("Hồ Chí Minh");
        list.add("Hà Nội");
        list.add("Đà Nẵng");
        return list;
    }
    private ArrayList<String> getDistricts(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Quận/Huyện...");
        list.add("Ba Đình");
        list.add("Quận 1");
        list.add("Quận 9");
        list.add("Cầu Giấy");
        list.add("Quận Thủ Đức");
        list.add("Quận 3");
        return list;
    }
    private ArrayList<String> getCommunes(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Phường/Xã...");
        list.add("Xã 1");
        list.add("Phường 3");
        list.add("Phường Tăng Nhơn Phú A");
        list.add("Phường 5");
        return list;
    }
    private ArrayList<String> getStatuses(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Trạng thái");
        list.add("Hoàn thành");
        list.add("Hủy");
        list.add("Đang giao hàng");
        return list;
    }
    private ArrayList<String> getPayments(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Hình thức thanh toán");
        list.add("Tiền mặt");
        list.add("Chuyển khoản");
        list.add("Trả góp");
        return list;
    }
    private ArrayList<Client> getClients(){
        ArrayList<Client> list = ClientBL.getAllClient();
        return list;
    }

    public Order getOrder() {
        Bundle bundle = getIntent().getExtras();
        int orderId = bundle.getInt(Constants.ORDER);
        if(orderId == -1)
            return null;
        return OrderBL.getOrder(orderId);
    }
}


