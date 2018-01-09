package com.example.baobang.threebird.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.User;
import com.example.baobang.threebird.model.helper.UserHelper;
import com.example.baobang.threebird.presenter.RegisterPresenterIml;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.RegisterView;

public class RegisterActivity extends AppCompatActivity implements RegisterView{

    private RegisterPresenterIml registerPresenterIml;

    private EditText txtUserName, txtPassword, txtPasswordConfirm;
    private Button btnSignUp;
    private TextView txtCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        setContentView(R.layout.activity_register);
        registerPresenterIml = new RegisterPresenterIml(this);
        registerPresenterIml.init();
    }

    @Override
    public void addControls() {
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword=  findViewById(R.id.txtPassword);
        txtPasswordConfirm = findViewById(R.id.txtPasswordConfirm);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtCancel = findViewById(R.id.txtCancel);
    }

    @Override
    public void addEvents() {
        btnSignUp.setOnClickListener(view ->
                registerPresenterIml.clickSignUp(txtUserName.getText().toString(),
                        txtPassword.getText().toString(),
                        txtPasswordConfirm.getText().toString()));
        txtCancel.setOnClickListener(view -> registerPresenterIml.clickCancel());
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showMessage(String message) {
        Utils.openDialog(this, message);
    }

}
