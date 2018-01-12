package com.example.baobang.threebird.model;

import com.example.baobang.threebird.model.helper.ProductHelper;

import java.io.Serializable;

import io.realm.RealmObject;


public  class ProductOrder extends RealmObject implements Serializable{

    private int productId;
    private int amount;

    public ProductOrder(int productId, int amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public ProductOrder() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getTotalPrice(){
        Product product = ProductHelper.getProduct(productId);
        int total = 0;
        if(product != null){
            total = product.getPrice() * amount;
        }
        return total;
    }

    public long getToTalInventoryPrice(){
        Product product = ProductHelper.getProduct(productId);
        int total = 0;
        if(product != null){
            total = product.getPriceInventory() * amount;
        }
        return total;
    }
}
