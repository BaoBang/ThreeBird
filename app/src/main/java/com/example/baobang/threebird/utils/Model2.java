package com.example.baobang.threebird.utils;

import java.io.Serializable;

/**
 * Created by baobang on 11/29/17.
 */

public class Model2 implements Serializable {
    private int color;
    private String result;
    private int number1;
    private int number2;
    private int number3;

    public Model2(int color, String result, int number1, int number2, int number3) {
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

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    public int getNumber3() {
        return number3;
    }

    public void setNumber3(int number3) {
        this.number3 = number3;
    }
}
