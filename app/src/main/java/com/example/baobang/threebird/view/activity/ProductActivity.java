package com.example.baobang.threebird.view.activity;

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
import android.view.Gravity;
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

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Brand;
import com.example.baobang.threebird.model.Category;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.helper.BrandHelper;
import com.example.baobang.threebird.model.helper.CategoryHelper;
import com.example.baobang.threebird.model.helper.ProductHelper;
import com.example.baobang.threebird.presenter.ProductPresenter;
import com.example.baobang.threebird.presenter.ProductPresenterImp;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.ProductView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.RealmList;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class ProductActivity extends AppCompatActivity implements ProductView{

    ProductPresenterImp productPresenterImp;

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
    private HashMap<Integer,Bitmap> bitmaps = new HashMap<>();
    private int option;
    private int key = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productPresenterImp = new ProductPresenterImp(this);

        Bundle bundle = getIntent().getExtras();
        product = productPresenterImp.getProductFromBundle(bundle);
        option = productPresenterImp.getOptionFromBundle(bundle);

        productPresenterImp.init();
    }


    @Override
    public void setDisableInput() {
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
        layoutImage.setEnabled(false);
    }

    @Override
    public void showSpinnerCategory(ArrayList<Category> categories) {
        spCategory = findViewById(R.id.spProductCategory);
        this.categories = categories;
        for(Category category : categories){
            Log.e("cate: ",category.getId() + "-"+ category.getName());
        }
        ArrayAdapter<Category> categoryArrayAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        categories);
        spCategory.setAdapter(categoryArrayAdapter);
    }

    @Override
    public void showSpinnerBrand(ArrayList<Brand> brands) {
        spBrand = findViewById(R.id.spProductBrand);
        this.brands = brands;
        ArrayAdapter<Brand> brandArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, brands);
        spBrand.setAdapter(brandArrayAdapter);
    }

    @Override
    public void setError(ExtendedEditText extendedEditText, String message) {
        extendedEditText.setError(message);
        extendedEditText.requestFocus();
    }

    @Override
    public void setDataForInput() {

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
        for(String bitmapStr : product.getImages()){
            Bitmap bitmap = Utils.StringToBitMap(bitmapStr);
            bitmaps.put( key, bitmap);
            addImageView(layoutImage, key++, bitmap);
        }
    }

    @Override
    public void addControls() {
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
        // to save list product images

        if(product != null){
            setDataForInput();
        }

        if(option == Constants.DETAIL_OPTION){
            setDisableInput();
        }
    }

    @Override
    public void addEvents() {
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        btnPhoto.setOnClickListener(view -> Utils.galleryIntent(ProductActivity.this));

        btnCamera.setOnClickListener(view -> Utils.cameraIntent(ProductActivity.this));

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
                    setError(txtProductId, "Định dạng không đúng");
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
                    setError(txtProductPrice, "Định dạng không đúng");
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
                    setError(txtProductPriceInventory, "Định dạng không đúng");
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
                    setError(txtProductInventory, "Định dạng không đúng");
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
                    bitmap = Utils.getResizedBitmap(bitmap, Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT);
                    bitmaps.put(key, bitmap);
                    addImageView(layoutImage, key++, bitmap);
                }

            }else if(requestCode == Constants.SELECT_FILE){
                Uri selectedImageUri = data.getData();
                Bitmap bitmap;
                try {
                    bitmap = Utils.getBitmapFromUri(this,selectedImageUri);
                    bitmap = Utils.getResizedBitmap(bitmap, Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT);
                } catch (IOException e) {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);
                }
                bitmaps.put(key, bitmap);
                addImageView(layoutImage, key++, bitmap);
            }
        }
    }

    @Override
    public RelativeLayout createRelativeLayout(){
        RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams paramsRelativeLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayout.setGravity(Gravity.CENTER_VERTICAL);
        relativeLayout.setLayoutParams(paramsRelativeLayout);
        return  relativeLayout;
    }

    @Override
    public ImageButton createImageRemoveButton(ImageView imgView){
        RelativeLayout.LayoutParams imageButtonParams = new RelativeLayout.LayoutParams(20, 20);
        imageButtonParams.addRule(RelativeLayout.ALIGN_END, imgView.getId());

        ImageButton btnRemove = new ImageButton(imgView.getContext());
        btnRemove.setImageResource(R.drawable.ic_close_red);
        btnRemove.setLayoutParams(imageButtonParams);

        return  btnRemove;
    }

    @Override
    public Product getProductFromInput() {
        String name = txtProductName.getText().toString();
        int category = spCategory.getSelectedItemPosition();
        int brand = spBrand.getSelectedItemPosition();
        String productId = txtProductId.getText().toString();
        String inventoryStr = txtProductInventory.getText().toString();
        String priceInventoryStr = txtProductPriceInventory.getText().toString();
        String priceStr = txtProductPrice.getText().toString();
        String detail = txtDetail.getText().toString();

        if(Utils.checkInput(name)){
            setError(txtProductName, "Vui lòng nhập vào tên sản phẩm");
            return null;
        }
        if(spCategory.getSelectedItemPosition() ==0){
            Utils.openDialog(this, "Vui lòng chọn loại sản phẩm");
            return null;
        }
        if(spBrand.getSelectedItemPosition() ==0){
            Utils.openDialog(this, "Vui lòng chọn hãng sản phẩm");
            return null;
        }

        if(Utils.checkInput(productId)){
            setError(txtProductId, "Vui lòng nhập vào mã sản phẩm");
            return null;
        }

        if(Utils.checkInput(inventoryStr)){
            setError(txtProductInventory, "Vui lòng nhập vào số lượng tồn kho");
            return null;
        }
        if(Utils.checkInput(priceInventoryStr)){
            setError(txtProductPriceInventory, "Vui lòng nhập vào giá nhập kho");
            return null;
        }
        if(Utils.checkInput(priceStr)){
            setError(txtProductPrice, "Vui lòng nhập vào đơn giá sản phẩm");
            return null;
        }
        int id, inventory, priceInventory, price;
        try{
            id = Integer.parseInt(productId);
        }catch (Exception e){
            setError(txtProductId, "Định dạng không đúng");
            return null;
        }

        try{
            inventory = Integer.parseInt(inventoryStr);
        }catch (Exception e){

            setError(txtProductInventory, "Định dạng không đúng");
            return null;
        }
        try{
            priceInventory = Integer.parseInt(priceInventoryStr);
        }catch (Exception e){
            setError(txtProductPriceInventory, "Định dạng không đúng");
            return null;
        }

        try{
            price = Integer.parseInt(priceStr);
        }catch (Exception e){
            setError(txtProductPrice, "Định dạng không đúng");
            return null;
        }


        Product product = new Product(id,
                name,categories.get(category).getId(),
                brands.get(brand).getId(),
                inventory,
                priceInventory,
                price,
                detail);
        RealmList<String> bitmapStrs = new RealmList<>();
        for(Integer key : bitmaps.keySet()){
            Bitmap bitmap = bitmaps.get(key);
            bitmapStrs.add(Utils.BitMapToString(bitmap, 50));
        }
        product.setImages(bitmapStrs);
        return product;
    }

    @Override
    public void addImageView(final LinearLayout layoutImage, final int key, Bitmap bitmap){

        final RelativeLayout relativeLayout = createRelativeLayout();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(115, 115);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmap);
        imageView.setLayoutParams(params);
        imageView.setBackgroundResource(R.drawable.button_background_white);
        imageView.setPadding(15,15,15,15);

        ImageButton imageButton = createImageRemoveButton(imageView);
        if(option == Constants.DETAIL_OPTION) imageButton.setEnabled(false);
        imageButton.setOnClickListener(view -> {
            layoutImage.removeView(relativeLayout);
            bitmaps.remove(key);
        });

        relativeLayout.addView(imageView);
        relativeLayout.addView(imageButton);
        layoutImage.addView(relativeLayout);
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
                addProduct();
            }else{
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void addProduct(){

        Product product = getProductFromInput();
        if(product != null){
            int result;
            if(this.product == null){
                result =  productPresenterImp.addProduct(product);
            }else{
                result = productPresenterImp.updateProduct(product);
            }
            if(result != -1){
                Intent returnIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.PRODUCT,result);
                returnIntent.putExtras(bundle);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }else{
                Utils.openDialog(this, "Đã có lỗi xảy ra, vui lòng thử lại");
            }
        }

    }

}


