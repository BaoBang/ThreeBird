package com.example.baobang.threebird.model;

import java.io.Serializable;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Product extends RealmObject implements Serializable{
    @PrimaryKey
    private int id;
    private String name;
    private int categoryId;
    private int brandId;
    private int invetory;
    private int priceInventory;
    private int price;
    private String detail;
    private RealmList<String> images;

    public Product(int id, String name, int categoryId, int brandId, int invetory, int priceInventory, int price, String detail) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.invetory = invetory;
        this.priceInventory = priceInventory;
        this.price = price;
        this.detail = detail;
        images = new RealmList<>();
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return categoryId;
    }

    public void setCategory(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBrand() {
        return brandId;
    }

    public void setBrand(int brandId) {
        this.brandId = brandId;
    }

    public int getInvetory() {
        return invetory;
    }

    public void setInvetory(int invetory) {
        this.invetory = invetory;
    }

    public int getPriceInventory() {
        return priceInventory;
    }

    public void setPriceInventory(int priceInventory) {
        this.priceInventory = priceInventory;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public RealmList<String> getImages() {
        return images;
    }

    public void setImages(RealmList<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return id + "-" + name + "-" + price + "-" + invetory;
    }
}
