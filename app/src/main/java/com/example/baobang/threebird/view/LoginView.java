package com.example.baobang.threebird.view;

/**
 * Created by baobang on 1/9/18.
 */

public interface LoginView {

    void addControls();
    void addEvents();
    void showMessage(String message);
    void startMainActivity();
    void startRegisterActivity();
    void startForgetPasswordInActivity();
}
