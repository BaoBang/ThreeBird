package com.example.baobang.threebird.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Brand;
import com.example.baobang.threebird.model.Category;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.bussinesslogic.ClientBL;
import com.example.baobang.threebird.model.bussinesslogic.ProductBL;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.MySupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {

    private ImageView btnCamera, btnPhoto;
    private LinearLayout layoutImage;
    private EditText txtProductName, txtProductId,
            txtProductInventory, txtProductPriceInventory,
            txtProductPrice, txtDetail;

    private Spinner spBrand, spCategory;
    private List<Category> categories;
    private List<Brand> brands;
    private ArrayAdapter<Category> categoryArrayAdapter;
    private ArrayAdapter<Brand> brandArrayAdapter;

    private Product product = null;
    private Toolbar toolbar;
    private ArrayList<Bitmap> bitmaps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Bundle bundle = getIntent().getExtras();
        product = (Product) bundle.getSerializable(Constants.PRODUCT);
        addControlls();
        addEvents();
    }

    private void addControlls() {
        // add tool bar
        toolbar = findViewById(R.id.toolBarAddProduct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        spBrand = findViewById(R.id.spProductBrand);
        spCategory = findViewById(R.id.spProductCategory);
        // data of spinner
        categories = getCategories();
        brands = getBrands();
        // spinner adapter
        brandArrayAdapter = new ArrayAdapter<Brand>(this, android.R.layout.simple_list_item_1, brands);
        categoryArrayAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, categories);
        // set adapter for listview
        spBrand.setAdapter(brandArrayAdapter);
        spCategory.setAdapter(categoryArrayAdapter);
        // to save list product images
        bitmaps = new ArrayList<>();

        if(product != null){
            setDataForInput();
        }
    }

    private void setDataForInput() {
        txtProductId.setText(product.getId() +"");
        txtProductPriceInventory.setText(product.getPriceInventory() + "");
        txtProductPrice.setText(product.getPrice() + "");
        txtProductName.setText(product.getName());
        txtDetail.setText(product.getDetail());
        txtProductInventory.setText(product.getInvetory() + "");

        for(int i = 0; i < brands.size(); i ++){
            if(brands.get(i).getId() == product.getBrand().getId()){
                spBrand.setSelection(i);
                break;
            }
        }

        for(int i = 0; i < categories.size(); i ++){
            if(categories.get(i).getId() == product.getCategory().getId()){
                spCategory.setSelection(i);
                break;
            }
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
                MySupport.galleryIntent(AddProductActivity.this);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySupport.cameraIntent(AddProductActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CAMERA_PIC_REQUEST) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            bitmaps.add(bitmap);
            addImageView(layoutImage, bitmap);
        }else if(requestCode == Constants.SELECT_FILE){
            Uri selectedImageUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MySupport.getBitmapFromUri(this,selectedImageUri);
            } catch (IOException e) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);
            }
            bitmaps.add(bitmap);
            addImageView(layoutImage, bitmap);
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
            MySupport.openDialog(this, "Vui lòng nhập vào tên sản phẩm");
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
        if(MySupport.checkInput(priceInventoryStr)){
            MySupport.openDialog(this, "Vui lòng nhập vào giá nhập kho");
            return;
        }
        if(MySupport.checkInput(priceStr)){
            MySupport.openDialog(this, "Vui lòng nhập vào đơn giá sản phẩm");
            return;
        }
        int inventory = 0, priceInventory = 0, price = 0;

        try{
            inventory = Integer.parseInt(inventoryStr);
            priceInventory = Integer.parseInt(priceInventoryStr);
            price = Integer.parseInt(priceStr);
        }catch (Exception e){
            MySupport.openDialog(this, "Định dạng không đúng");
            return;
        }

        Product product = new Product(Integer.parseInt(productId),
                name,categories.get(category),
                brands.get(brand),
                inventory,
                priceInventory,
                price,
                detail);
//        if(avartar != null){
//            client.setAvatar(MySupport.BitMapToString(avartar));
//        }else{
//            client.setAvatar(null);
//        }
        boolean res = false;
        if(this.product == null){
            res =  ProductBL.createProudct(product);
        }else{
            res = ProductBL.updateProduct(product);
        }
        if(res){
            Intent returnIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.PRODUCT,product);
            //returnIntent.putExtras(bundle);
            returnIntent.putExtras(bundle);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }else{
            MySupport.openDialog(this, "Đã có lỗi xảy ra, vui lòng thử lại");
        }
    }

    private List<Brand> getBrands(){
        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(0,"Chọn hãng sản xuất"));
        brands.add(new Brand(1,"Apple"));
        brands.add(new Brand(2,"SamSung"));
        brands.add(new Brand(3,"Oppo"));
        return brands;
    }

    private List<Category> getCategories(){
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(0, "Chọn loại sản phẩm"));
        categories.add(new Category(1, "Phone"));
        categories.add(new Category(2, "Laptop"));
        categories.add(new Category(3, "Tablet"));
        return categories;
    }
}


