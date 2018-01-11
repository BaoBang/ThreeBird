package com.example.baobang.threebird.presenter.imp;

import com.example.baobang.threebird.model.User;
import com.example.baobang.threebird.model.helper.UserHelper;
import com.example.baobang.threebird.presenter.LoginPresenter;
import com.example.baobang.threebird.view.LoginView;

/**
 * Created by baobang on 1/9/18.
 */

public class LoginPresenterImp implements LoginPresenter {

    private LoginView loginView;

    public LoginPresenterImp(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void init() {
        loginView.addControls();
        loginView.addEvents();
    }

    @Override
    public void clickLogin(String username, String password) {
        if(username.equals("")){
            loginView.showMessage("Nhập vào tài khoản.");
            return;
        }
        if(password.equals("")){
            loginView.showMessage("Nhập vào mật khẩu.");
            return;
        }
        if(checkLogin(username, password)){
            loginView.startMainActivity();
        }else{
            loginView.showMessage("Tài khoản hoặc mật khẩu không đúng.");
        }
    }

    @Override
    public void clickRegister() {
        loginView.startRegisterActivity();
    }

    @Override
    public void clickForgetPassword() {
        loginView.startForgetPasswordInActivity();
    }

    @Override
    public boolean checkLogin(String username, String password) {
        User user = new User();
        user.setUserName(username);
        user.setPassWord(password);
        return UserHelper.checkUser( user);
    }
}
