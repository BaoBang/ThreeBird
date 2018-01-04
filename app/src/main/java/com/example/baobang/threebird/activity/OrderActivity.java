package com.example.baobang.threebird.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.adapter.ClientAdapter;
import com.example.baobang.threebird.adapter.ProductAdapter;
import com.example.baobang.threebird.model.Address;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.ProductOrder;
import com.example.baobang.threebird.model.bussinesslogic.ClientBL;
import com.example.baobang.threebird.model.bussinesslogic.OrderBL;
import com.example.baobang.threebird.model.bussinesslogic.ProductBL;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.MySupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.RealmList;

public class OrderActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ImageView imgAvatar;
    private EditText txtName, txtOrderId, txtPhone, txtAddress;
    private TextView txtCreatedAt, txtAmount, txtDeliveryDate;

    private Spinner spStatus, spProvince,
            spDistrict, spCommune,  spPayment;
    private ArrayList<String>  provinces,
            districts, communes;


    private ImageButton btnAddClient, btnCreatedAt, btnAddProduct, btnDeliveryDate;
    private LinearLayout layoutProduct;

    private Order order = null;
    private List<ProductOrder> productList = new ArrayList<>();
    private int option;
    private int clientSelectedId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activy_order);
        Bundle bundle = getIntent().getExtras();
        order = getOrder(bundle);
        option = bundle == null ? 0 : bundle.getInt(Constants.OPTION);
        if(order != null){
            productList.addAll(order.getProducts());
        }
        addControlls();
        addEvents();
    }

    private void addControlls() {
        toolbar = findViewById(R.id.toolBarCreateOrder);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //
        imgAvatar = findViewById(R.id.imgAvatar);

        txtName = findViewById(R.id.txtName);
        txtOrderId = findViewById(R.id.txtOrderId);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);

        txtAmount = findViewById(R.id.txtAmount);
        txtAmount.setText(String.valueOf(getAmountAllProduct()));
        txtCreatedAt = findViewById(R.id.txtCreatedAt);
        txtDeliveryDate = findViewById(R.id.txtDeliveryDate);

        btnCreatedAt = findViewById(R.id.btnCreatedAt);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnDeliveryDate = findViewById(R.id.btnDeliveryDate);
        btnAddClient = findViewById(R.id.btnAddClient);

        layoutProduct = findViewById(R.id.layoutProduct);

        addSpinnerStatus();
        addSpinnerProvince();
        addSpinnerDistrict();
        addSpinnerCommune();
        addSpinnerPayment();

        if(order != null){
            setDataForInput();
            if(order.getClientId() != -1){
                Client client = ClientBL.getClient(order.getClientId());
                Bitmap bitmap = MySupport.StringToBitMap(client.getAvatar());
                imgAvatar.setImageBitmap(MySupport.getRoundedRectBitmap(bitmap));
            }
        }

        if(option == Constants.DETAIL_OPTION){
            setDisableInput();
        }
    }

    private void setDisableInput() {
        txtName.setEnabled(false);
        txtOrderId.setEnabled(false);
        txtPhone.setEnabled(false);
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
    }

    private void setDataForInput() {
        txtName.setText(order.getClientName());
        txtOrderId.setText(String.valueOf(order.getId()));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi","VN"));
        txtCreatedAt.setText(simpleDateFormat.format(order.getCreatedAt()));

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
        txtAddress.setText(order.getAddress().getAddress());
        txtAmount.setText(String.valueOf(order.getAmount()));

        for(ProductOrder productOrder : productList){
            Product product = ProductBL.getProduct(productOrder.getProductId());
            addProductToLayout(product);
        }

        txtDeliveryDate.setText(simpleDateFormat.format(order.getLiveryDate()));
        spPayment.setSelection(order.getPayments());
    }

    private void addSpinnerPayment() {
        spPayment = findViewById(R.id.spPayment);
        ArrayList<String> payments = getPayments();

        ArrayAdapter<String> adapterPayment = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                payments);
        spPayment.setAdapter(adapterPayment);
    }

    private void addSpinnerCommune() {
        spCommune = findViewById(R.id.spCommune);
        communes = getCommunes();

        ArrayAdapter<String> adapterCommune = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                communes);
        spCommune.setAdapter(adapterCommune);
    }

    private void addSpinnerDistrict() {
        spDistrict = findViewById(R.id.spDistrict);
        districts = getDistricts();
        ArrayAdapter<String> adapterDistrict = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                districts);
        spDistrict.setAdapter(adapterDistrict);
    }

    private void addSpinnerProvince() {
        spProvince = findViewById(R.id.spProvince);
        provinces = getProvinces();
        ArrayAdapter<String> adapterProvince = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                provinces);
        spProvince.setAdapter(adapterProvince);
    }

    private void addSpinnerStatus() {
        spStatus = findViewById(R.id.spStatus);
        ArrayList<String> statuses = getStatuses();
        ArrayAdapter<String> adapterStatus = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                statuses);
        spStatus.setAdapter(adapterStatus);
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
                MySupport.getDate(OrderActivity.this, txtCreatedAt);
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
                MySupport.getDate(OrderActivity.this, txtDeliveryDate);
            }
        });

        btnAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogClient();
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogProduct();
            }
        });
    }

    private void showDialogProduct() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderActivity.this, R.drawable.background_color_gradient);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.listview_dialog, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Danh sách sản phẩm");
        ListView lv =  convertView.findViewById(R.id.listView);
        final ArrayList<Product> products = ProductBL.getAllProduct();
        ProductAdapter adapter = new ProductAdapter(this, R.layout.item_product, products);
        lv.setAdapter(adapter);
        final AlertDialog dialog = alertDialog.show();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getProductFromList(products.get(i));
                txtAmount.setText(String.valueOf(getAmountAllProduct()));
                addProductToLayout(products.get(i));
                dialog.dismiss();
            }
        });
    }

    private int checkLayoutProduct(int id){
        for(int i = 0; i < layoutProduct.getChildCount(); i++){
            LinearLayout layout = (LinearLayout) layoutProduct.getChildAt(i);
            if(id == layout.getId()){
                return i;
            }
        }
        return -1;
    }

    private void addProductToLayout(Product product) {
        int index = checkLayoutProduct(product.getId());
        if(index != -1){
            LinearLayout layout = (LinearLayout) layoutProduct.getChildAt(index);
            TextView textView = (TextView) layout.getChildAt(2);
            String text = "Số lượng: " + getAmountProduct(product.getId());
            textView.setText(text);
        }else{
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setId(product.getId());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            layoutParams.setMargins(0, 0, Constants.MARGIN_SMALL, 0);
            layout.setLayoutParams(layoutParams);

            // ad image
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(MySupport.StringToBitMap(product.getImages().first()));
            imageView.setLayoutParams(imageParams);
            imageView.getLayoutParams().height = Constants.IMAGE_HEIGHT * 2;
            imageView.getLayoutParams().width = Constants.IMAGE_WIDTH * 2;
            imageParams.gravity = Gravity.CENTER;
            layout.addView(imageView);

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
            text = "Số lượng: "+getAmountProduct(product.getId());
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

    private int getAmountProduct(int id){
        int total = 1;
        for(ProductOrder productOrder : productList){
            if(productOrder.getProductId() == id){
                total = productOrder.getAmount();
            }
        }
        return total;
    }

    private int getAmountAllProduct(){
        int total = 0;
        for(ProductOrder productOrder : productList){
            total += productOrder.getAmount();
        }
        return total;
    }

    private void getProductFromList(Product product) {
        int index = checkProduct(product);
        if(index != -1){
            productList.get(index).setAmount(productList.get(index).getAmount() + 1);
        }else{
            productList.add(new ProductOrder(product.getId(), 1));
        }
    }

    private int checkProduct(Product product) {
        for(int i = 0; i < productList.size(); i ++){
            if(productList.get(i).getProductId() == product.getId()){
                return i;
            }
        }
        return -1;
    }

    private void showDialogClient() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderActivity.this, R.drawable.background_color_gradient);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.listview_dialog, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Danh sách khách hàng");
        ListView lv =  convertView.findViewById(R.id.listView);
        final ArrayList<Client> clients = ClientBL.getAllClient();
//        ClientAdapter adapter = new ClientAdapter(this, R.layout.item_client, clients);
//        lv.setAdapter(adapter);
        final AlertDialog dialog = alertDialog.show();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
                getClientFromList(clients.get(i));
                clientSelectedId = clients.get(i).getId();
            }
        });
    }

    private void getClientFromList(Client client) {
        txtName.setText(client.getName());
        if(client.getAvatar()!= null){
            Bitmap bitmap = MySupport.StringToBitMap(client.getAvatar());
            imgAvatar.setImageBitmap(MySupport.getRoundedRectBitmap(bitmap));
        }
        else{
            imgAvatar.setImageResource(R.drawable.noimage);
        }
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
       try {
           String name = txtName.getText().toString();
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
           if(MySupport.checkInput(createdAtStr)){
               MySupport.openDialog(this, "Vui lòng chọn ngày lập hóa đơn");
               return;
           }
           if(status == 0){
               MySupport.openDialog(this, "Vui lòng chọn trạng thái của hóa đơn");
               return;
           }
           if(MySupport.checkInput(phone)){
               MySupport.openDialog(this, "Vui lòng nhâp vào số điện thoại");
               return;
           }
           if(province == 0){
               MySupport.openDialog(this, "Vui lòng chọn tỉnh/thành phố");
               return;
           }
           if(district == 0){
               MySupport.openDialog(this, "Vui lòng chọn quận/huyện");
               return;
           }
           if(commune == 0){
               MySupport.openDialog(this, "Vui lòng chọn phường/xã");
               return;
           }
           if(MySupport.checkInput(address)){
               MySupport.openDialog(this, "Vui lòng nhâp vào địa chỉ");
               return;
           }
           if(MySupport.checkInput(deliveryDateStr)){
               MySupport.openDialog(this, "Vui lòng chọn ngày giao hàng");
               return;
           }
           Date date = new Date(createdAtStr);
           Date date2 = new Date(deliveryDateStr);
           if(date.after(date2)){
               MySupport.openDialog(this, "Ngày giao hàng không hợp lệ");
               return;
           }
           if(payment == 0){
               MySupport.openDialog(this, "Vui lòng chọn hình thức thanh toán");
               return;
           }
           RealmList<ProductOrder> products = new RealmList<>();
           products.addAll(productList);
           Order order = new Order(0, name,
                   date, status - 1, phone,
                   new Address(provinces.get(province),
                           districts.get(district),
                           communes.get(commune),
                           address),
                   products,
                   date2,
                   payment,
                   0);
           order.setClientId(clientSelectedId);
           boolean res;
           if(this.order == null){
               res =  OrderBL.createOrder(order);
           }else{
               order.setId(this.order.getId());
               res = OrderBL.updateOrder(order);
           }
           if(res){
               Intent returnIntent = new Intent();
               Bundle bundle = new Bundle();
               bundle.putInt(Constants.ORDER,order.getId());
               returnIntent.putExtras(bundle);
               setResult(Activity.RESULT_OK,returnIntent);
               finish();
           }else{
               MySupport.openDialog(this, "Đã có lỗi xảy ra, vui lòng thử lại");
           }


       }catch (Exception e){
           MySupport.openDialog(this, "Lỗi: " + e.getMessage());
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

//    private ArrayList<Client> getClients(){
//        return ClientBL.getAllClient();
//    }
    public Order getOrder(Bundle bundle) {
        int orderId = bundle != null ? bundle.getInt(Constants.ORDER) : -1;
        if(orderId == -1)
           return null;
        return OrderBL.getOrder(orderId);
    }
}


