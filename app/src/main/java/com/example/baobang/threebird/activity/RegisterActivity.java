package com.example.baobang.threebird.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.User;
import com.example.baobang.threebird.model.bussinesslogic.UserBL;
import com.example.baobang.threebird.utils.MySupport;

import io.realm.Realm;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtUserName, txtPassword, txtPasswordConfirm;
    private Button btnSignUp;
    private TextView txtCancel;
    private Realm realm;
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
        realm = Realm.getDefaultInstance();
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword=  findViewById(R.id.txtPassword);
        txtPasswordConfirm = findViewById(R.id.txtPasswordConfirm);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtCancel = findViewById(R.id.txtCancel);
    }

    private void addEvents() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    doSignUp();
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void doSignUp() {
        String userName = txtUserName.getText().toString().trim();
        String passWord = txtPassword.getText().toString().trim();
        String passWordConfirm = txtPasswordConfirm.getText().toString().trim();

        if(MySupport.checkInput(userName)){
            MySupport.openDialog(this,
                    "Nhập vào tài khoản.");
            return;
        }
        if(MySupport.checkInput(passWord)){
            MySupport.openDialog(this,
                    "Nhập vào mật khẩu.");
            return;
        }
        if(MySupport.checkInput(passWordConfirm)){
            MySupport.openDialog(this,
                    "Nhập vào xác nhận mật khẩu.");
            return;
        }
        if(!passWord.equals(passWordConfirm)){
            MySupport.openDialog(this,
                    "Mật khẩu và xác nhận mật khẩu không trùng nhau");
            return;
        }
        if(UserBL.checkUser( userName)){
            MySupport.openDialog(this,
                    "Tài khoản đã tồn tại");
            return;
        }
        User user = new User();
        user.setUserName(userName);
        user.setPassWord(passWord);
        if(!UserBL.createUser(user)){
            MySupport.openDialog(this,
                    "Không thể tạo tài khoản");
        }else{
            MySupport.openDialog(this,
                    "Tạo tài khoản thành công");
        }

    }

}
