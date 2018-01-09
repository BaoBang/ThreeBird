package com.example.baobang.threebird.presenter;

/**
 * Created by baobang on 1/9/18.
 */

public interface RegisterPresenter {
    void init();
    void clickSignUp(String username, String password, String passwordComfirm);
    void clickCancel();
}
