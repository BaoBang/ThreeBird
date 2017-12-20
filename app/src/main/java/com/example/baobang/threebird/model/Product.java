package com.example.baobang.threebird.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by baobang on 12/17/17.
 */

public class Product extends RealmObject implements Serializable{
    @PrimaryKey
    private int id;
    private String name;
    private Category category;
    private Brand brand;
    private int invetory;
    private int priceInventory;
    private int price;
    private String detail;
    private String image;

    public Product(int id, String name, Category category, Brand brand, int invetory, int priceInventory, int price, String detail) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.invetory = invetory;
        this.priceInventory = priceInventory;
        this.price = price;
        this.detail = detail;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
