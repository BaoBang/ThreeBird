package com.example.baobang.threebird.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Brand;
import com.example.baobang.threebird.model.Category;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.bussinesslogic.BrandBL;
import com.example.baobang.threebird.model.bussinesslogic.CategoryBL;
import com.example.baobang.threebird.model.bussinesslogic.ProductBL;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.MySupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class ProductActivity extends AppCompatActivity {

    private ImageView btnCamera, btnPhoto;
    private LinearLayout layoutImage;
    private ExtendedEditText txtProductName, txtProductId,
            txtProductInventory, txtProductPriceInventory,
            txtProductPrice, txtDetail;

    private Spinner spBrand, spCategory;
    private List<Category> categories;
    private List<Brand> brands;


    private Product product = null;
    private Toolbar toolbar;
    private ArrayList<Bitmap> bitmaps;
    private int option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Bundle bundle = getIntent().getExtras();
        product = getProduct(bundle);
        option = bundle == null ? Constants.ADD_OPTION : bundle.getInt(Constants.OPTION);
        addControlls();
        addEvents();
    }

    private Product getProduct(Bundle bundle) {
        int productId = bundle != null ? bundle.getInt(Constants.PRODUCT) : -1;
        if(productId == -1)
            return null;
        return ProductBL.getProduct(productId);

    }

    private void addControlls() {
        // add tool bar
        toolbar = findViewById(R.id.toolBarAddProduct);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        // add views
        layoutImage = findViewById(R.id.layoutImage);
        txtDetail = findViewById(R.id.txtDetail);
        txtProductId = findViewById(R.id.txtProductId);
        txtProductInventory = findViewById(R.id.txtProductInventory);
        txtProductName = findViewById(R.id.txtProductName);
        txtProductPrice = findViewById(R.id.txtProductPrice);
        txtProductPriceInventory = findViewById(R.id.txtProductPriceInventory);
        // image button Camera and Photo
        btnCamera = findViewById(R.id.btnCamera);
        btnPhoto = findViewById(R.id.btnPhoto);
        // spinner brand and categoty
        addSpinnerBrand();
        addSpinnerCategory();
        // to save list product images
        bitmaps = new ArrayList<>();

        if(product != null){
            setDataForInput();
        }

        if(option == Constants.DETAIL_OPTION){
            setDisableInput();
        }
    }

    private void setDisableInput() {
        // add views
        TextFieldBoxes tfbDetail = findViewById(R.id.tfbDetail);
        tfbDetail.setEnabled(false);
        txtDetail.setEnabled(false);

        TextFieldBoxes tfbProductId = findViewById(R.id.tfbProductId);
        tfbProductId.setEnabled(false);
        txtProductId.setEnabled(false);

        TextFieldBoxes tfbProductInventory = findViewById(R.id.tfbProductInventory);
        tfbProductInventory.setEnabled(false);
        txtProductInventory.setEnabled(false);

        TextFieldBoxes tfbProductName = findViewById(R.id.tfbProductName);
        tfbProductName.setEnabled(false);
        txtProductName.setEnabled(false);

        TextFieldBoxes tfbProductPrice = findViewById(R.id.tfbProductPrice);
        tfbProductPrice.setEnabled(false);
        txtProductPrice.setEnabled(false);

        TextFieldBoxes tfbProductPriceInventory = findViewById(R.id.tfbProductPriceInventory);
        tfbProductPriceInventory.setEnabled(false);
        txtProductPriceInventory.setEnabled(false);
        // image button Camera and Photo
        btnCamera.setEnabled(false);
        btnPhoto.setEnabled(false);
        spBrand.setEnabled(false);
        spCategory.setEnabled(false);
    }

    private void addSpinnerCategory() {
        spCategory = findViewById(R.id.spProductCategory);
        categories = getCategories();
        for(Category category : categories){
            Log.e("cate: ",category.getId() + "-"+ category.getName());
        }
        ArrayAdapter<Category> categoryArrayAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        categories);
        spCategory.setAdapter(categoryArrayAdapter);
    }

    private void addSpinnerBrand() {
        spBrand = findViewById(R.id.spProductBrand);
        brands = getBrands();
        ArrayAdapter<Brand> brandArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, brands);
        spBrand.setAdapter(brandArrayAdapter);
    }

    private void setDataForInput() {

        txtProductId.setText(String.valueOf(product.getId()));
        txtProductPriceInventory.setText(String.valueOf(product.getPriceInventory()));
        txtProductPrice.setText(String.valueOf(product.getPrice()));
        txtProductName.setText(product.getName());
        txtDetail.setText(product.getDetail());
        txtProductInventory.setText(String.valueOf(product.getInvetory()));

        for(int i = 0; i < brands.size(); i ++){
            if(brands.get(i).getId() == product.getBrand()){
                spBrand.setSelection(i);
                break;
            }
        }

        for(int i = 0; i < categories.size(); i ++){
            if(categories.get(i).getId() == product.getCategory()){
                spCategory.setSelection(i);
                break;
            }
        }
        bitmaps = new ArrayList<>();
        for(String bitmapStr : product.getImages()){
            Bitmap bitmap = MySupport.StringToBitMap(bitmapStr);
            bitmaps.add(bitmap);
            addImageView(layoutImage, bitmap);
        }
    }

    private void addEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySupport.galleryIntent(ProductActivity.this);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySupport.cameraIntent(ProductActivity.this);
            }
        });

        txtProductId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().matches(Constants.NUMBER_REGUAR)){
                    txtProductId.setError("Định dạng không đúng");
                    txtProductId.requestFocus();
                }
            }
        });

        txtProductPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().matches(Constants.NUMBER_REGUAR)){
                    txtProductPrice.setError("Định dạng không đúng");
                    txtProductPrice.requestFocus();
                }
            }
        });

        txtProductPriceInventory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().matches(Constants.NUMBER_REGUAR)){
                    txtProductPriceInventory.setError("Định dạng không đúng");
                    txtProductPriceInventory.requestFocus();
                }
            }
        });

        txtProductInventory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().matches(Constants.NUMBER_REGUAR)){
                    txtProductInventory.setError("Định dạng không đúng");
                    txtProductInventory.requestFocus();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constants.CAMERA_PIC_REQUEST){

                Bundle bundle = data.getExtras();
                if(bundle != null){
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    bitmap = MySupport.getResizedBitmap(bitmap, Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT);
                    bitmaps.add(bitmap);
                    addImageView(layoutImage, bitmap);
                }

            }else if(requestCode == Constants.SELECT_FILE){
                Uri selectedImageUri = data.getData();
                Bitmap bitmap;
                try {
                    bitmap = MySupport.getBitmapFromUri(this,selectedImageUri);
                    bitmap = MySupport.getResizedBitmap(bitmap, Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT);
                } catch (IOException e) {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);
                }
                bitmaps.add(bitmap);
                addImageView(layoutImage, bitmap);
            }
        }
    }

    private void addImageView(LinearLayout layoutImage, Bitmap bitmap){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        params.setMargins(
                0,
                Constants.MARGIN * 2,
                0,
                0);

        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmap);
        imageView.setLayoutParams(params);
        imageView.setPadding(Constants.PADDING ,
                Constants.PADDING * 3,
                Constants.PADDING ,
                Constants.PADDING * 3);
        imageView.setBackgroundResource(R.drawable.button_background_white);

        layoutImage.addView(imageView);
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
            addProduct();
        }

        return super.onOptionsItemSelected(item);
    }

    private void addProduct(){
        String name = txtProductName.getText().toString();
        int category = spCategory.getSelectedItemPosition();
        int brand = spBrand.getSelectedItemPosition();
        String productId = txtProductId.getText().toString();
        String inventoryStr = txtProductInventory.getText().toString();
        String priceInventoryStr = txtProductPriceInventory.getText().toString();
        String priceStr = txtProductPrice.getText().toString();
        String detail = txtDetail.getText().toString();

        if(MySupport.checkInput(name)){
            txtProductName.setError("Vui lòng nhập vào tên sản phẩm");
            txtProductName.requestFocus();
            return;
        }
        if(spCategory.getSelectedItemPosition() ==0){
            MySupport.openDialog(this, "Vui lòng chọn loại sản phẩm");
            return;
        }
        if(spBrand.getSelectedItemPosition() ==0){
            MySupport.openDialog(this, "Vui lòng chọn hãng sản phẩm");
            return;
        }

        if(MySupport.checkInput(productId)){
            txtProductId.setError("Vui lòng nhập vào mã sản phẩm");
            txtProductId.requestFocus();
            return;
        }

        if(MySupport.checkInput(inventoryStr)){
            txtProductInventory.setError("Vui lòng nhập vào số lượng tồn kho");
            txtProductInventory.requestFocus();
            return;
        }
        if(MySupport.checkInput(priceInventoryStr)){
            txtProductPriceInventory.setError("Vui lòng nhập vào giá nhập kho");
            txtProductPriceInventory.requestFocus();
            return;
        }
        if(MySupport.checkInput(priceStr)){
            txtProductPrice.setError("Vui lòng nhập vào đơn giá sản phẩm");
            txtProductPrice.requestFocus();
            return;
        }
        int id, inventory, priceInventory, price;
        try{
            id = Integer.parseInt(productId);
        }catch (Exception e){
            txtProductId.setError("Định dạng không đúng");
            txtProductId.requestFocus();
            return;
        }

        try{
            inventory = Integer.parseInt(inventoryStr);
        }catch (Exception e){
            txtProductInventory.setError("Định dạng không đúng");
            txtProductInventory.requestFocus();
            return;
        }
        try{
            priceInventory = Integer.parseInt(priceInventoryStr);
        }catch (Exception e){
            txtProductPriceInventory.setError("Định dạng không đúng");
            txtProductPriceInventory.requestFocus();
            return;
        }

        try{
            price = Integer.parseInt(priceStr);
        }catch (Exception e){
            txtProductPrice.setError("Định dạng không đúng");
            txtProductPrice.requestFocus();
            return;
        }


        Product product = new Product(id,
                name,categories.get(category).getId(),
                brands.get(brand).getId(),
                inventory,
                priceInventory,
                price,
                detail);
        RealmList<String> bitmapStrs = new RealmList<>();
        for(Bitmap bitmap : bitmaps){
            bitmapStrs.add(MySupport.BitMapToString(bitmap, 50));
        }
        product.setImages(bitmapStrs);
        boolean res;
        if(this.product == null){
            res =  ProductBL.createProudct(product);
        }else{
            res = ProductBL.updateProduct(product);
        }
        if(res){
            Intent returnIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.PRODUCT,product.getId());
            returnIntent.putExtras(bundle);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }else{
            MySupport.openDialog(this, "Đã có lỗi xảy ra, vui lòng thử lại");
        }
    }

    private List<Brand> getBrands(){
        List<Brand> brands = BrandBL.getAllBrand();
        return brands;
    }

    private List<Category> getCategories(){
        List<Category> categories = CategoryBL.getAllCategory();
        return categories;
    }
}


