package com.example.baobang.threebird.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.presenter.imp.LoginPresenterImp;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.LoginView;

import io.realm.Realm;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginView{

    private LoginPresenterImp loginPresenterImp;

    private EditText txtUserName, txtPassword;
    private Button btnLogin;
    private TextView txtForgetPassword, txtSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Realm.init(this);

        RelativeLayout layoutRoot = findViewById(R.id.layoutRoot);
        Utils.hideKeyboardOutside(layoutRoot, this);

        loginPresenterImp = new LoginPresenterImp(this);
        loginPresenterImp.init();
    }

    @Override
    protected void onDestroy() {
        System.exit(0);
        super.onDestroy();
    }

    @Override
    public void addControls() {
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtForgetPassword = findViewById(R.id.txtForgetPassword);
        txtSignIn = findViewById(R.id.txtSigniIn);
    }

    @Override
    public void addEvents() {
        btnLogin.setOnClickListener(view -> loginPresenterImp.clickLogin(txtUserName.getText().toString(), txtPassword.getText().toString()));

        txtSignIn.setOnClickListener(view -> loginPresenterImp.clickRegister());

        txtForgetPassword.setOnClickListener(view -> loginPresenterImp.clickForgetPassword());
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

