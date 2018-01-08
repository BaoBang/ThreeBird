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
import com.example.baobang.threebird.listener.OnItemRecyclerViewClickListener;
import com.example.baobang.threebird.model.Address;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.ProductOrder;
import com.example.baobang.threebird.model.helper.ClientHelper;
import com.example.baobang.threebird.model.helper.OrderHelper;
import com.example.baobang.threebird.model.helper.ProductHelper;
import com.example.baobang.threebird.presenter.OrderPresenter;
import com.example.baobang.threebird.presenter.OrderPresenterImp;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.OrderView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.RealmList;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class OrderActivity extends AppCompatActivity implements OrderView{

    private OrderPresenterImp orderPresenterImp;

    private Toolbar toolbar;
    private ImageView imgAvatar;
    private ExtendedEditText txtName, txtOrderId, txtPhone, txtAddress, txtCreatedAt, txtDeliveryDate;
    private TextView txtAmount;

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

        orderPresenterImp = new OrderPresenterImp(this);

        Bundle bundle = getIntent().getExtras();
        order = orderPresenterImp.getOrderFromBundle(bundle);
        option = orderPresenterImp.getOptionFromBundle(bundle);
        if(order != null){
            productList = orderPresenterImp.getProductListFromOrder(order);
        }

        orderPresenterImp.init();
    }

    @Override
    public void addControls() {
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

        TextFieldBoxes tfbCreatedAt = findViewById(R.id.tfbCreateAt);
        tfbCreatedAt.setEnabled(false);
        txtCreatedAt = findViewById(R.id.txtCreatedAt);
        txtCreatedAt.setEnabled(false);

        TextFieldBoxes tfbDeliveryDate = findViewById(R.id.tfbDeliveryDate);
        tfbDeliveryDate.setEnabled(false);
        txtDeliveryDate = findViewById(R.id.txtDeliveryDate);
        txtDeliveryDate.setEnabled(false);

        txtAmount = findViewById(R.id.txtAmount);
        txtAmount.setText(String.valueOf(orderPresenterImp.getAmountAllProduct(productList)));

        btnCreatedAt = findViewById(R.id.btnCreatedAt);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnDeliveryDate = findViewById(R.id.btnDeliveryDate);
        btnAddClient = findViewById(R.id.btnAddClient);

        layoutProduct = findViewById(R.id.layoutProduct);

        if(order != null){
            setDataForInput();
            if(order.getClientId() != -1){
                Client client = ClientHelper.getClient(order.getClientId());
                Bitmap bitmap = Utils.StringToBitMap(client.getAvatar());
                imgAvatar.setImageBitmap(Utils.getRoundedRectBitmap(bitmap));
            }
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
    public void setDataForInput() {
        Client client = ClientHelper.getClient(order.getClientId());
        if(client != null && client.getAvatar().length() > 0){
            Bitmap bitmap = Utils.StringToBitMap(client.getAvatar());
            imgAvatar.setImageBitmap(Utils.getRoundedRectBitmap(bitmap));
        }
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
            Product product = ProductHelper.getProduct(productOrder.getProductId());
            addProductToLayout(product);
        }

        txtDeliveryDate.setText(simpleDateFormat.format(order.getLiveryDate()));
        spPayment.setSelection(order.getPayments());
    }


    @Override
    public void addEvents() {
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        btnCreatedAt.setOnClickListener(view -> Utils.getDate(OrderActivity.this, txtCreatedAt));

        btnAddProduct.setOnClickListener(view -> {

        });

        btnDeliveryDate.setOnClickListener(view -> Utils.getDate(OrderActivity.this, txtDeliveryDate));

        btnAddClient.setOnClickListener(view -> showDialogClient());

        btnAddProduct.setOnClickListener(view -> showDialogProduct());

        txtOrderId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().matches(Constants.NUMBER_REGUAR)){
                    txtOrderId.setError("Nhập vào kí tự số!");
                    txtOrderId.requestFocus();
                }
            }
        });

        txtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().matches(Constants.PHONE_REGULAR)){
                    txtPhone.setError("Số điện thoại bắt đầu với +84/0 và tiếp theo từ 9-10 kí tự số");
                    txtPhone.requestFocus();
                }
            }
        });
    }

    @Override
    public void showSpinnerDistrict(ArrayList<String> districts) {
        spDistrict = findViewById(R.id.spDistrict);
        ArrayAdapter<String> adapterDistrict = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                districts);
        spDistrict.setAdapter(adapterDistrict);
    }

    @Override
    public void showSpinnerProvince(ArrayList<String> provinces) {
        spProvince = findViewById(R.id.spProvince);
        ArrayAdapter<String> adapterProvince = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                provinces);
        spProvince.setAdapter(adapterProvince);
    }

    @Override
    public void showSpinnerCommune(ArrayList<String> communes) {
        spCommune = findViewById(R.id.spCommune);
        ArrayAdapter<String> adapterCommune = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                communes);
        spCommune.setAdapter(adapterCommune);
    }

    @Override
    public void showSpinnerPayment(ArrayList<String> payments) {
        spPayment = findViewById(R.id.spPayment);
        ArrayAdapter<String> adapterPayment = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                payments);
        spPayment.setAdapter(adapterPayment);
    }

    @Override
    public void showSpinnerStatus(ArrayList<String> statues) {
        spStatus = findViewById(R.id.spStatus);
        ArrayAdapter<String> adapterStatus = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                statues);
        spStatus.setAdapter(adapterStatus);
    }

    @Override
    public void showDialogProduct() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderActivity.this, R.drawable.background_color_gradient);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View convertView = inflater.inflate(R.layout.recycleview_dialog, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Danh sách sản phẩm");
        RecyclerView rcProudcts =  convertView.findViewById(R.id.recyclerView);
        final ArrayList<Product> products = ProductHelper.getAllProduct();
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
//        lv.setOnItemClickListener(new AdapterView.OnItemRecyclerViewClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                getProductFromList(products.get(i));
//                txtAmount.setText(String.valueOf(getAmountAllProduct()));
//                addProductToLayout(products.get(i));
//                dialog.dismiss();
//            }
//        });
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



//            LinearLayout layout = new LinearLayout(this);
//            layout.setOrientation(LinearLayout.VERTICAL);
//            layout.setId(product.getId());
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
//            layoutParams.setMargins(0, 0, Constants.MARGIN_SMALL, 0);
//            layout.setLayoutParams(layoutParams);
//
//            // ad image
//            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            ImageView imageView = new ImageView(this);
//            if(product.getImages().size() == 0){
//                imageView.setImageResource(R.drawable.noimage);
//            }else{
//                Bitmap bitmap = Utils.StringToBitMap(product.getImages().first());
//                imageView.setImageBitmap(Utils.getRoundedRectBitmap(bitmap));
//            }
//            imageView.setLayoutParams(imageParams);
//            imageView.getLayoutParams().height = Constants.IMAGE_HEIGHT * 2;
//            imageView.getLayoutParams().width = Constants.IMAGE_WIDTH * 2;
//            imageParams.gravity = Gravity.CENTER;
//            layout.addView(imageView);
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            // add product name
//            TextView txtName = new TextView(this);
//            txtName.setLayoutParams(params);
//            String text = "Tên sản phẩm: " + product.getName();
//            txtName.setText(text);
//            layout.addView(txtName);
//            // add amount
//            TextView txtAmount = new TextView(this);
//            txtAmount.setLayoutParams(params);
//            text = "Số lượng: "+getAmountProduct(product.getId());
//            txtAmount.setText(text);
//            layout.addView(txtAmount);
//            // add price
//            TextView txtPrice = new TextView(this);
//            txtPrice.setLayoutParams(params);
//            text = "Đơn giá: " + product.getPrice() + "đ";
//            txtPrice.setText(text);
//            layout.addView(txtPrice);
//
//            layoutProduct.addView(layout);
        }


    }

    @Override
    public void showDialogClient() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderActivity.this, R.drawable.background_color_gradient);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View convertView = inflater.inflate(R.layout.recycleview_dialog, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Danh sách khách hàng");
        RecyclerView rcClients =  convertView.findViewById(R.id.recyclerView);
        final ArrayList<Client> clients = ClientHelper.getAllClient();
        final AlertDialog dialog = alertDialog.show();
        ClientAdapter adapter = new ClientAdapter(clients, rcClients, item -> {
            Client client = (Client) item;
            dialog.dismiss();
            addClientInfoFromListToView(client);
            clientSelectedId = client.getId();
        });
        rcClients.setLayoutManager(new VegaLayoutManager());
        rcClients.setAdapter(adapter);
//        lv.setOnItemClickListener(new AdapterView.OnItemRecyclerViewClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                dialog.dismiss();
//                getClientFromList(clients.get(i));
//                clientSelectedId = clients.get(i).getId();
//            }
//        });
    }

    @Override
    public void addClientInfoFromListToView(Client client) {
        txtName.setText(client.getName());
        if(client.getAvatar()!= null){
            Bitmap bitmap = Utils.StringToBitMap(client.getAvatar());
            imgAvatar.setImageBitmap(Utils.getRoundedRectBitmap(bitmap));
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
            if(option != Constants.DETAIL_OPTION){
                addOrder();
            }else{
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void addOrder() {
       try {
           String name = txtName.getText().toString();
           String orderId = txtOrderId.getText().toString();
           String createdAtStr = txtCreatedAt.getText().toString();
           int status = spStatus.getSelectedItemPosition();
           String phone = txtPhone.getText().toString();
           int province = spProvince.getSelectedItemPosition();
           int district = spDistrict.getSelectedItemPosition();
           int commune = spCommune.getSelectedItemPosition();
           String address = txtAddress.getText().toString();
           String deliveryDateStr = txtDeliveryDate.getText().toString();
           int payment = spPayment.getSelectedItemPosition();

           if(Utils.checkInput(name)){
               txtName.setError("Vui lòng nhập vào tên khách hàng");
               txtName.requestFocus();
               return;
           }
           if(Utils.checkInput(orderId)){
               txtOrderId.setError("Vui lòng nhập vào số hóa đơn");
               txtOrderId.requestFocus();
               return;
           }
           if(Utils.checkInput(createdAtStr)){
               Utils.openDialog(this, "Vui lòng chọn ngày lập hóa đơn");
               return;
           }
           Date date = new Date(createdAtStr);
           SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi", "VN"));
           if(date.before(simpleDateFormat.getCalendar().getTime())){
               Utils.openDialog(this, "Ngày đặt hàng không hợp lệ");
               return;
           }

           if(status == 0){
               Utils.openDialog(this, "Vui lòng chọn trạng thái của hóa đơn");
               return;
           }
           if(Utils.checkInput(phone)){
               txtPhone.setError("Vui lòng nhâp vào số điện thoại");
               txtPhone.requestFocus();
               return;
           }
           if(!phone.matches(Constants.PHONE_REGULAR)){
               txtPhone.setError("Số điện thoại không đúng");
               txtPhone.requestFocus();
               return;
           }
           if(province == 0){
               Utils.openDialog(this, "Vui lòng chọn tỉnh/thành phố");
               return;
           }
           if(district == 0){
               Utils.openDialog(this, "Vui lòng chọn quận/huyện");
               return;
           }
           if(commune == 0){
               Utils.openDialog(this, "Vui lòng chọn phường/xã");
               return;
           }
           if(Utils.checkInput(address)){
               txtAddress.setError("Vui lòng nhâp vào địa chỉ");
               txtAddress.requestFocus();
               return;
           }

           if(productList.size() == 0){
               Utils.openDialog(this, "Vui lòng chọn sản phẩm");
               return;
           }

           if(Utils.checkInput(deliveryDateStr)){
               Utils.openDialog(this, "Vui lòng chọn ngày giao hàng");
               return;
           }

           Date date2 = new Date(deliveryDateStr);

           if(date.after(date2)){
               Utils.openDialog(this, "Ngày giao hàng không hợp lệ");
               return;
           }
           if(payment == 0){
               Utils.openDialog(this, "Vui lòng chọn hình thức thanh toán");
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

           if(option == Constants.ADD_OPTION || option == Constants.EDIT_OPTION){
               Product product = orderPresenterImp.checkInventory(productList);
               if(product != null){
                   Utils.openDialog(this, "Sản phẩm " + product.getName() +" hiện còn " + product.getInvetory() + " sản phẩm");
                   return;
               }
           }

           if(this.order == null){
               res =  OrderHelper.createOrder(order);
           }else{
               order.setId(this.order.getId());
               res = OrderHelper.updateOrder(order);
           }
           if(res){
               if(status - 1 == Constants.COMPLETED){
                    orderPresenterImp.orderCompetition(productList);
               }
               Intent returnIntent = new Intent();
               Bundle bundle = new Bundle();
               bundle.putInt(Constants.ORDER,order.getId());
               returnIntent.putExtras(bundle);
               setResult(Activity.RESULT_OK,returnIntent);
               finish();
           }else{
               Utils.openDialog(this, "Đã có lỗi xảy ra, vui lòng thử lại");
           }


       }catch (Exception e){
           Utils.openDialog(this, "Lỗi: " + e.getMessage());
       }
    }


}


