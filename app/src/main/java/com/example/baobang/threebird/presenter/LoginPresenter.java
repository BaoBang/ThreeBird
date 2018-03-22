package com.example.baobang.threebird.presenter;

import com.example.baobang.threebird.model.User;

/**
 * Created by baobang on 1/9/18.
 */

public interface LoginPresenter {

    void clickLogin(String username, String password);
    void clickRegister();
    void clickForgetPassword();
    boolean checkLogin(String username, String password);

    void addUserToView(User user);
}
