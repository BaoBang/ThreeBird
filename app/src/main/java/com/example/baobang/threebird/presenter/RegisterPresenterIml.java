package com.example.baobang.threebird.presenter;

import com.example.baobang.threebird.model.User;
import com.example.baobang.threebird.model.helper.UserHelper;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.RegisterView;

/**
 * Created by baobang on 1/9/18.
 */

public class RegisterPresenterIml implements RegisterPresenter {

    private RegisterView registerView;

    public RegisterPresenterIml(RegisterView registerView) {
        this.registerView = registerView;
    }

    @Override
    public void init() {
        registerView.addControls();
        registerView.addEvents();
    }

    @Override
    public void clickSignUp(String username, String password, String passwordConfirm) {

        if(Utils.checkInput(username)){
            registerView.showMessage("Nhập vào tài khoản.");
            return;
        }
        if(Utils.checkInput(password)){
            registerView.showMessage("Nhập vào mật khẩu.");
            return;
        }
        if(Utils.checkInput(passwordConfirm)){
            registerView.showMessage("Nhập vào xác nhận mật khẩu.");
            return;
        }
        if(!password.equals(passwordConfirm)){
            registerView.showMessage("Mật khẩu và xác nhận mật khẩu không trùng nhau");
            return;
        }
        if(UserHelper.checkUser(username)){
            registerView.showMessage("Tài khoản đã tồn tại");
            return;
        }
        User user = new User();
        user.setUserName(username);
        user.setPassWord(password);
        if(!UserHelper.createUser(user)){
            registerView.showMessage("Không thể tạo tài khoản");
        }else{
            registerView.showMessage("Tạo tài khoản thành công");
        }
    }

    @Override
    public void clickCancel() {
        registerView.finishActivity();
    }
}
