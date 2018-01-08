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
import com.example.baobang.threebird.utils.Utils;

public class RegisterActivity extends AppCompatActivity {

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

        addControlls();
        addEvents();
    }

    private void addControlls() {
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword=  findViewById(R.id.txtPassword);
        txtPasswordConfirm = findViewById(R.id.txtPasswordConfirm);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtCancel = findViewById(R.id.txtCancel);
    }

    private void addEvents() {
        btnSignUp.setOnClickListener(view -> doSignUp());

        txtCancel.setOnClickListener(view -> finish());
    }

    private void doSignUp() {
        String userName = txtUserName.getText().toString().trim();
        String passWord = txtPassword.getText().toString().trim();
        String passWordConfirm = txtPasswordConfirm.getText().toString().trim();

        if(Utils.checkInput(userName)){
            Utils.openDialog(this,
                    "Nhập vào tài khoản.");
            return;
        }
        if(Utils.checkInput(passWord)){
            Utils.openDialog(this,
                    "Nhập vào mật khẩu.");
            return;
        }
        if(Utils.checkInput(passWordConfirm)){
            Utils.openDialog(this,
                    "Nhập vào xác nhận mật khẩu.");
            return;
        }
        if(!passWord.equals(passWordConfirm)){
            Utils.openDialog(this,
                    "Mật khẩu và xác nhận mật khẩu không trùng nhau");
            return;
        }
        if(UserHelper.checkUser( userName)){
            Utils.openDialog(this,
                    "Tài khoản đã tồn tại");
            return;
        }
        User user = new User();
        user.setUserName(userName);
        user.setPassWord(passWord);
        if(!UserHelper.createUser(user)){
            Utils.openDialog(this,
                    "Không thể tạo tài khoản");
        }else{
            Utils.openDialog(this,
                    "Tạo tài khoản thành công");
        }

    }

}
