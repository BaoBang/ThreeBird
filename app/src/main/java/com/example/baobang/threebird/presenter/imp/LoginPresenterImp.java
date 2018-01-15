package com.example.baobang.threebird.presenter.imp;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Brand;
import com.example.baobang.threebird.model.Category;
import com.example.baobang.threebird.model.ClientGroup;
import com.example.baobang.threebird.model.Commune;
import com.example.baobang.threebird.model.District;
import com.example.baobang.threebird.model.Payment;
import com.example.baobang.threebird.model.Province;
import com.example.baobang.threebird.model.Status;
import com.example.baobang.threebird.model.User;
import com.example.baobang.threebird.model.helper.BrandHelper;
import com.example.baobang.threebird.model.helper.CategoryHelper;
import com.example.baobang.threebird.model.helper.ClientGroupHelper;
import com.example.baobang.threebird.model.helper.CommuneHelper;
import com.example.baobang.threebird.model.helper.DistrictHelper;
import com.example.baobang.threebird.model.helper.PaymentHelper;
import com.example.baobang.threebird.model.helper.ProvinceHelper;
import com.example.baobang.threebird.model.helper.StatusHelper;
import com.example.baobang.threebird.model.helper.UserHelper;
import com.example.baobang.threebird.presenter.LoginPresenter;
import com.example.baobang.threebird.view.LoginView;

public class LoginPresenterImp implements LoginPresenter {

    private LoginView loginView;

    public LoginPresenterImp(LoginView loginView) {
        this.loginView = loginView;
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

    public void initData(){
        ProvinceHelper.createProvince(new Province(0, "Tỉnh/Thành phố..."));
        ProvinceHelper.createProvince(new Province(1, "An Giang"));
        ProvinceHelper.createProvince(new Province(2, "Bình Phước"));
        ProvinceHelper.createProvince(new Province(3, "Ninh Bình"));
        ProvinceHelper.createProvince(new Province(4, "Hồ Chí Minh"));
        ProvinceHelper.createProvince(new Province(5, "Hà Nội"));
        ProvinceHelper.createProvince(new Province(6, "Đà Nẵng"));

        DistrictHelper.createDistrict(new District(0, "Quận/Huyện", 0));
        DistrictHelper.createDistrict(new District(1, "Ba Đình", 1));
        DistrictHelper.createDistrict(new District(2, "Ba Đình", 2));
        DistrictHelper.createDistrict(new District(3, "Quận 1", 2));
        DistrictHelper.createDistrict(new District(4, "Quận 9", 1));
        DistrictHelper.createDistrict(new District(5, "Cầu Giấy", 2));
        DistrictHelper.createDistrict(new District(6, "Quận Thủ Đức", 3));
        DistrictHelper.createDistrict(new District(7, "Quận 3", 4));

        CommuneHelper.createCommune(new Commune(-1, "Phường/Xã...", 0));
        CommuneHelper.createCommune(new Commune(0, "Xã 1", 1));
        CommuneHelper.createCommune(new Commune(0, "Phường 3", 1));
        CommuneHelper.createCommune(new Commune(0, "Phường Tăng Nhơn Phú A", 1));
        CommuneHelper.createCommune(new Commune(0, "Phường 5", 3));


        ClientGroupHelper.createClientGroup(new ClientGroup(0, "Nhóm khách hàng..."));
        ClientGroupHelper.createClientGroup(new ClientGroup(1, "Admin"));
        ClientGroupHelper.createClientGroup(new ClientGroup(2, "Employee"));
        ClientGroupHelper.createClientGroup(new ClientGroup(3, "Membber"));

        CategoryHelper.createCategory(new Category(0, "Loại sản phẩm..."));
        CategoryHelper.createCategory(new Category(0, "Điện thoại"));
        CategoryHelper.createCategory(new Category(0, "Laptop"));
        CategoryHelper.createCategory(new Category(0, "Tablet"));

        BrandHelper.createBrand(new Brand(0, "Thương hiệu..."));
        BrandHelper.createBrand(new Brand(0, "Apple"));
        BrandHelper.createBrand(new Brand(0, "SamSung"));
        BrandHelper.createBrand(new Brand(0, "Oppo"));

        StatusHelper.createStatus(new Status(0,"Trạng thái đơn hàng...", R.drawable.noimage));
        StatusHelper.createStatus(new Status(0,"Hoàn thành", R.drawable.image1));
        StatusHelper.createStatus(new Status(0,"Hủy", R.drawable.image2));
        StatusHelper.createStatus(new Status(0,"Đang giao hàng", R.drawable.image3));

        PaymentHelper.createStatus(new Payment(0, "Hình thức thanh toán"));
        PaymentHelper.createStatus(new Payment(0, "Tiền mặt"));
        PaymentHelper.createStatus(new Payment(0, "Chuyển khoản"));



    }
}
