package com.example.baobang.threebird.utils;

import java.io.Serializable;


public class SlideModel implements Serializable {
    private int image;
    private String title;
    private int amount;

    public SlideModel(int image, String title, int amount) {
        this.image = image;
        this.title = title;
        this.amount = amount;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
