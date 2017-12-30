package com.example.baobang.threebird.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.User;
import com.example.baobang.threebird.model.bussinesslogic.UserBL;
import com.example.baobang.threebird.utils.MySupport;
import io.realm.Realm;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    private EditText txtUserName, txtPassword;
    private Button btnLogin;
    private TextView txtForgetPassword, txtSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Realm.init(this);
        addControlls();
        addEvents();
    }

    @Override
    protected void onDestroy() {
        System.exit(0);
        super.onDestroy();
    }

    private void addControlls() {
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtForgetPassword = findViewById(R.id.txtForgetPassword);
        txtSignIn = findViewById(R.id.txtSigniIn);
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

              doLogin();
            }
        });

        txtSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSignIn();
            }
        });

        txtForgetPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clickForgetPassword();
            }
        });
    }

    private void doLogin() {
        String userName = txtUserName.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        if(userName.equals("")){
            MySupport.openDialog(LoginActivity.this,
                    "Nhập vào tài khoản.");
            return;
        }
        if(password.equals("")){
            MySupport.openDialog(LoginActivity.this,
                    "Nhập vào mật khẩu.");
            return;
        }
        if(checkLogin(userName, password)){
            startMainActivity();
        }else{
            MySupport.openDialog(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng.");
        }
    }

    private void startMainActivity() {
        Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(mainActivity);
    }

    private void clickForgetPassword() {
        Intent registerActivity = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(registerActivity);
    }

    private void clickSignIn() {
        Intent registerActivity = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(registerActivity);
    }

    private boolean checkLogin(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setPassWord(password);
        return UserBL.checkUser( user);
    }

}

