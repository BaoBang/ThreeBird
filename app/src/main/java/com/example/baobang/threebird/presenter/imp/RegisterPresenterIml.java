package com.example.baobang.threebird.presenter.imp;

import com.example.baobang.threebird.model.User;
import com.example.baobang.threebird.model.helper.UserHelper;
import com.example.baobang.threebird.presenter.RegisterPresenter;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.RegisterView;

import org.greenrobot.eventbus.EventBus;

public class RegisterPresenterIml implements RegisterPresenter {

    private RegisterView registerView;

    public RegisterPresenterIml(RegisterView registerView) {
        this.registerView = registerView;
    }

    /**
     *      When user click button Sign up, this method check input data and create a user
     *          - Empty input data.
     *          - Password and password confirm
     *          - User existed
     *
    **/

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
        boolean isCreated;
        if(!UserHelper.createUser(user)){
            registerView.showMessage("Không thể tạo tài khoản");
            isCreated = false;
        }else{
            EventBus.getDefault().postSticky(user);
            isCreated = true;
        }
        if (isCreated){
            registerView.finishActivity();
        }
    }

    /**
     *     finish active activity
    **/

    @Override
    public void clickCancel() {
        registerView.finishActivity();
    }
}
