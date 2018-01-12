package com.example.baobang.threebird.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Province;
import com.example.baobang.threebird.model.helper.ProvinceHelper;
import com.example.baobang.threebird.presenter.imp.LoginPresenterImp;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.LoginView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginView{

    private LoginPresenterImp loginPresenterImp;

    @BindView(R.id.txtUserName)
    EditText txtUserName;

    @BindView(R.id.txtPassword)
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Realm.init(this);

        ButterKnife.bind(this);

        RelativeLayout layoutRoot = findViewById(R.id.layoutRoot);
        Utils.hideKeyboardOutside(layoutRoot, this);

        loginPresenterImp = new LoginPresenterImp(this);

        ArrayList<Province> provinces = ProvinceHelper.getAllProvinces();
        if(provinces.size() == 0){
            loginPresenterImp.initData();
        }
    }

    @OnClick({R.id.btnLogin, R.id.txtForgetPassword, R.id.txtSigniIn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnLogin:
                loginPresenterImp.clickLogin(
                        txtUserName.getText().toString(),
                        txtPassword.getText().toString());
                break;
            case R.id.txtForgetPassword:
                loginPresenterImp.clickRegister();
                break;
            case R.id.txtSigniIn:
                loginPresenterImp.clickForgetPassword();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        System.exit(0);
        super.onDestroy();
    }

    @Override
    public void showMessage(String message) {
        Utils.openDialog(LoginActivity.this,
                message);
    }


    @Override
    public void startMainActivity() {
        Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(mainActivity);
    }

    @Override
    public void startRegisterActivity() {
        Intent registerActivity = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(registerActivity);
    }

    @Override
    public void startForgetPasswordInActivity() {
        Intent registerActivity = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(registerActivity);
    }

}

