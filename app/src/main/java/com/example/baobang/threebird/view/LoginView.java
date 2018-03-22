package com.example.baobang.threebird.view;

/**
 * Created by baobang on 1/9/18.
 */

public interface LoginView {

    void showMessage(String message);

    void startMainActivity();

    void startRegisterActivity();

    void startForgetPasswordInActivity();

    void showUserToView(String userName, String passWord);
}
