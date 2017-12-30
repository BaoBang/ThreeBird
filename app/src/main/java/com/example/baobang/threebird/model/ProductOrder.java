package com.example.baobang.threebird.model;

import com.example.baobang.threebird.model.bussinesslogic.ProductBL;

import java.io.Serializable;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


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

    private long getTotalPrice(){
        Product product = ProductBL.getProduct(productId);
        int total = 0;
        if(product != null){
            total = product.getPrice() * amount;
        }
        return total;
    }
}
