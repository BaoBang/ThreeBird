package com.example.baobang.threebird.utils;

import java.io.Serializable;


public class ItemFragmentModel implements Serializable {
    private int color;
    private String result;
    private long number1;
    private long number2;
    private long number3;

    public ItemFragmentModel(int color, String result, long number1, long number2, long number3) {
        this.color = color;
        this.result = result;
        this.number1 = number1;
        this.number2 = number2;
        this.number3 = number3;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getNumber1() {
        return number1;
    }

    public void setNumber1(long number1) {
        this.number1 = number1;
    }

    public long getNumber2() {
        return number2;
    }

    public void setNumber2(long number2) {
        this.number2 = number2;
    }

    public long getNumber3() {
        return number3;
    }

    public void setNumber3(long number3) {
        this.number3 = number3;
    }

    @Override
    public String toString() {
        return color + "-" + result + "-" + number1 + "-" + number2 + "-" + number3;
    }
}
