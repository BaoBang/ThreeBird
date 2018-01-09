package com.example.baobang.threebird.presenter;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.baobang.threebird.model.Brand;
import com.example.baobang.threebird.model.Category;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.helper.BrandHelper;
import com.example.baobang.threebird.model.helper.CategoryHelper;
import com.example.baobang.threebird.model.helper.ProductHelper;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.ProductView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by baobang on 1/8/18.
 */

public class ProductPresenterImp implements ProductPresenter{

    private ProductView productView;
    private int key = 0;
    public ProductPresenterImp(ProductView productView) {
        this.productView = productView;
    }
    public void init(){
        productView.addControls();
        productView.addEvents();
    }

    @Override
    public void loadSpinnerData() {
        productView.showSpinnerBrand((ArrayList<Brand>) getBrands());
        productView.showSpinnerCategory((ArrayList<Category>) getCategories());
    }

    @Override
    public int addProduct(String name, int categoryId, int brandId,
                              int inventory, int priceInventory,int price,String detail,
                            HashMap<Integer,Bitmap> bitmaps) {
        RealmList<String> list = new RealmList<String>();
        for(Integer key : bitmaps.keySet()){
            Bitmap bitmap = bitmaps.get(key);
            list.add(Utils.BitMapToString(bitmap));
        }
        Product product = new Product(0, name, categoryId, brandId, inventory,
                priceInventory, price, detail);
        product.setImages(list);
        return ProductHelper.createProudct(product);
    }

    @Override
    public int updateProduct(Product product,String name, int categoryId, int brandId,
                                 int inventory, int priceInventory,int price,String detail
                 , HashMap<Integer,Bitmap> bitmaps) {
        RealmList<String> list = new RealmList<String>();
        for(Integer key : bitmaps.keySet()){
            Bitmap bitmap = bitmaps.get(key);
            list.add(Utils.BitMapToString(bitmap));
        }

        product = new Product(product.getId(), name, categoryId, brandId, inventory,
                priceInventory, price, detail);
        product.setImages(list);
        return ProductHelper.updateProduct(product);
    }

    @Override
    public Product getProductFromBundle(Bundle bundle) {
        int productId = bundle != null ? bundle.getInt(Constants.PRODUCT) : -1;
        if(productId == -1)
            return null;
        return ProductHelper.getProduct(productId);
    }

    @Override
    public int getOptionFromBundle(Bundle bundle) {
        return bundle == null ? Constants.ADD_OPTION : bundle.getInt(Constants.OPTION);
    }

    @Override
    public void setData(Product product) {
        productView.setDataForInput(product.getId(), product.getPriceInventory(), product.getPrice(),
                product.getInvetory(), product.getName(), product.getDetail(),
                product.getBrand(), product.getCategory(), new ArrayList<String>( product.getImages()));
    }

    @Override
    public void setBrandSelected(List<Brand> brands, int brandId) {
        productView.setBrandSelectedPosition(getBrandPosition(brands, brandId));
    }

    private int getBrandPosition(List<Brand> brands, int brandId){
        for(int i = 0; i < brands.size(); i++){
            if(brands.get(i).getId() == brandId){
                return i;
            }
        }
        return 0;
    }

    private int getCategoryPosition(List<Category> categories, int categoryId){
        for(int i = 0; i < categories.size(); i++){
            if(categories.get(i).getId() == categoryId){
                return i;
            }
        }
        return 0;
    }

    public HashMap<Integer,Bitmap> getHashMapImage(ArrayList<String> images){
        HashMap<Integer,Bitmap> bitmaps = new HashMap<>();
        for(String bitmapStr : images){
            Bitmap bitmap = Utils.StringToBitMap(bitmapStr);
            bitmaps.put( getNextKey(), bitmap);
        }
        return bitmaps;
    }
    public int getNextKey(){
        return ++key;
    }
    @Override
    public void setCategorySelected(List<Category> categories, int categoryId) {
        productView.setCategorySelectedPosition(getCategoryPosition(categories, categoryId));
    }

    private List<Brand> getBrands(){
        return BrandHelper.getAllBrand();
    }

    private List<Category> getCategories(){
        return CategoryHelper.getAllCategory();
    }
}
