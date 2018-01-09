package com.example.baobang.threebird.presenter;

/**
 * Created by baobang on 1/9/18.
 */

public interface LoginPresenter {

    void init();
    void clickLogin(String username, String password);
    void clickRegister();
    void clickForgetPassword();
    boolean checkLogin(String username, String password);
}
