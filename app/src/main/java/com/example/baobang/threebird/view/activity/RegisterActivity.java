package com.example.baobang.threebird.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.presenter.imp.RegisterPresenterIml;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.RegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterView{

    private RegisterPresenterIml registerPresenterIml;

    @BindView(R.id.txtUserName)
    EditText txtUserName;

    @BindView(R.id.txtPassword)
    EditText txtPassword;

    @BindView(R.id.txtPasswordConfirm)
    EditText txtPasswordConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        LinearLayout layoutRoot = findViewById(R.id.layoutRoot);
        Utils.hideKeyboardOutside(layoutRoot, this);
        registerPresenterIml = new RegisterPresenterIml(this);
    }

    @OnClick({R.id.btnSignUp, R.id.txtCancel})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnSignUp:
                registerPresenterIml.clickSignUp(txtUserName.getText().toString(),
                        txtPassword.getText().toString(),
                        txtPasswordConfirm.getText().toString());
                break;
            case R.id.txtCancel:
                registerPresenterIml.clickCancel();
                break;
        }
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
