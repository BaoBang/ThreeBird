package com.example.baobang.threebird.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.fragments.ClientFragment;
import com.example.baobang.threebird.model.Address;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.bussinesslogic.ClientBL;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.MySupport;
import com.example.baobang.threebird.utils.SlideView;

import java.util.ArrayList;
import java.util.Arrays;

public class UserDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout groupUserInfo, groupAddressInfo;
    private ImageButton btnUserInfo, btnAddressInfo;
    private ImageView imgAvatar;

    EditText txtName, txtPhone, txtFax, txtWebsite, txtEmail, txtAddress;

    Spinner spGroupClient, spProvince, spDistrict, spCommune;
    ArrayList<String> groups, provinces, districts, communes;
    ArrayAdapter<String> groupAdapter, provinceAdapter, districtAdapter, communeAdapter;

    private Bitmap avartar = null;
    private Client client = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        Bundle bundle = getIntent().getExtras();
        client = (Client) bundle.getSerializable(Constants.CLIENT);
        addControlls();
        addEvents();
    }

    private void addControlls() {
        // start setting toolbar
        toolbar = findViewById(R.id.toolBarUserDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // end setting toolbar
        groupAddressInfo = findViewById(R.id.groupAddressInfo);
        groupUserInfo = findViewById(R.id.groupUserInfo);
        btnAddressInfo = findViewById(R.id.btnAddressInfo);
        btnUserInfo = findViewById(R.id.btnUserInfo);
        imgAvatar = findViewById(R.id.imgAvatar);
        // input
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtFax = findViewById(R.id.txtFax);
        txtWebsite = findViewById(R.id.txtWebsite);
        txtEmail = findViewById(R.id.txtEmail);
        txtAddress = findViewById(R.id.txtAddress);
        // spinner
        spGroupClient = findViewById(R.id.spGroupClient);
        spProvince = findViewById(R.id.spProvince);
        spDistrict = findViewById(R.id.spDistrict);
        spCommune = findViewById(R.id.spCommune);

        groups = getGroups();
        provinces = getProvinces();
        districts = getDistricts();
        communes = getCommunes();

        groupAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                groups);
        provinceAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                provinces);
        districtAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                districts );
        communeAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                communes);

        spGroupClient.setAdapter(groupAdapter);
        spProvince.setAdapter(provinceAdapter);
        spDistrict.setAdapter(districtAdapter);
        spCommune.setAdapter(communeAdapter);

        if(client != null){
            setDataForInput();
        }

    }

    private void setDataForInput() {
        if(client.getAvatar() != null && client.getAvatar().length() > 0){
            avartar = MySupport.StringToBitMap(client.getAvatar());
            imgAvatar.setImageBitmap(avartar);
        }else{
            imgAvatar.setImageResource(R.drawable.noimage);
        }
        txtName.setText(client.getName());
        txtPhone.setText(client.getPhone());
        txtFax.setText(client.getFax());
        txtWebsite.setText(client.getWebsite());
        txtEmail.setText(client.getEmail());
        txtAddress.setText(client.getAddress().getAddress());
        //
        for(int i = 0; i < groups.size(); i ++){
            if(groups.get(i).equals(client.getGroup())){
                spGroupClient.setSelection(i);
                break;
            }
        }

        for(int i = 0; i < provinces.size(); i ++){
            if(provinces.get(i).equals(client.getAddress().getProvince())){
                spProvince.setSelection(i);
                break;
            }
        }

        for(int i = 0; i < districts.size(); i ++){
            if(districts.get(i).equals(client.getAddress().getDistrict())){
                spDistrict.setSelection(i);
                break;
            }
        }

        for(int i = 0; i < communes.size(); i ++){
            if(communes.get(i).equals(client.getAddress().getCommune())){
                spCommune.setSelection(i);
                break;
            }
        }
    }

    private void addEvents() {
        btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(groupUserInfo.getVisibility() == View.VISIBLE){
                    SlideView.collapse(groupUserInfo);
                    btnUserInfo.setImageResource(R.drawable.ic_right_arrow);
                }else if(groupUserInfo.getVisibility() == View.INVISIBLE){
                    SlideView.expand(groupUserInfo);
                    btnUserInfo.setImageResource(R.drawable.ic_down_arrow);
                }
            }
        });

        btnAddressInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(groupAddressInfo.getVisibility() == View.VISIBLE){
                    SlideView.collapse(groupAddressInfo);
                    btnAddressInfo.setImageResource(R.drawable.ic_right_arrow);
                }else if(groupAddressInfo.getVisibility() == View.INVISIBLE){
                    SlideView.expand(groupAddressInfo);
                    btnAddressInfo.setImageResource(R.drawable.ic_down_arrow);
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCamera();
            }
        });
    }

    private void startCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, Constants.CAMERA_PIC_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CAMERA_PIC_REQUEST) {
            avartar = (Bitmap) data.getExtras().get("data");
            imgAvatar.setImageBitmap(avartar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.actionBar_add){
            addClient();
        }

        return super.onOptionsItemSelected(item);
    }

    private void addClient() {
        String name = txtName.getText().toString();
        String group = spGroupClient.getSelectedItem().toString();
        String phone = txtPhone.getText().toString();
        String fax = txtFax.getText().toString();
        String website = txtWebsite.getText().toString();
        String email = txtEmail.getText().toString();
        String province = spProvince.getSelectedItem().toString();
        String district = spDistrict.getSelectedItem().toString();
        String commune = spCommune.getSelectedItem().toString();
        String address = txtAddress.getText().toString();

        if(MySupport.checkInput(name)){
            MySupport.openDialog(this, "Vui lòng nhập vào họ tên");
            return;
        }
        if(spGroupClient.getSelectedItemPosition() ==0){
            MySupport.openDialog(this, "Vui lòng chọn nhóm thành viên");
            return;
        }
        if(MySupport.checkInput(phone)){
            MySupport.openDialog(this, "Vui lòng nhập vào số điện thoại");
            return;
        }
//        if(MySupport.checkInput(fax)){
//            MySupport.openDialog(this, "Vui lòng nhập vào số fax");
//            return;
//        } if(MySupport.checkInput(website)){
//            MySupport.openDialog(this, "Vui lòng nhập vào website");
//            return;
//        }
//        if(MySupport.checkInput(email)){
//            MySupport.openDialog(this, "Vui lòng nhập vào email");
//            return;
//        }
        if(spProvince.getSelectedItemPosition() ==0){
            MySupport.openDialog(this, "Vui lòng chọn tỉnh/thành phố");
            return;
        }
        if(spDistrict.getSelectedItemPosition() ==0){
            MySupport.openDialog(this, "Vui lòng chọn quận/huyện");
            return;
        }
        if(spCommune.getSelectedItemPosition() ==0){
            MySupport.openDialog(this, "Vui lòng chọn phường/xã");
            return;
        }
        if(MySupport.checkInput(address)){
           MySupport.openDialog(this, "Vui lòng nhập vào địa chỉ");
            return;
        }
        int clientId = -1;
        if(this.client != null){
            clientId = this.client.getId();
        }
        Client newClient = new Client(clientId,
                name,group, phone,fax, website, email,
                new Address(province, district, commune,  address));
        if(avartar != null){
            newClient.setAvatar(MySupport.BitMapToString(avartar));
        }else{
            newClient.setAvatar(null);
        }
        boolean res = false;
        if(this.client == null){
            res = ClientBL.createClient(newClient);
        }else{
            res = ClientBL.updateClient(newClient);
        }
        if(res){
            Intent returnIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.CLIENT,newClient);
            //returnIntent.putExtras(bundle);
            returnIntent.putExtras(bundle);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }else{
            MySupport.openDialog(this, "Đã có lỗi xảy ra, vui lòng thử lại");
        }


    }

    private ArrayList<String> getGroups(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Nhóm khách hàng...");
        list.add("Member");
        list.add("Admin");
        return list;
    }
    private ArrayList<String> getProvinces(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Tỉnh/Thành Phố...");
        list.add("An Giang");
        list.add("Bình Phước");
        list.add("Ninh Bình");
        list.add("Hồ Chí Minh");
        list.add("Hà Nội");
        list.add("Đà Nẵng");
        list.add("An Giang");
        list.add("Bình Phước");
        list.add("Ninh Bình");
        list.add("Hồ Chí Minh");
        list.add("Hà Nội");
        list.add("Đà Nẵng");
        list.add("An Giang");
        list.add("Bình Phước");
        list.add("Ninh Bình");
        list.add("Hồ Chí Minh");
        list.add("Hà Nội");
        list.add("Đà Nẵng");
        return list;
    }
    private ArrayList<String> getDistricts(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Quận/Huyện...");
        list.add("Ba Đình");
        list.add("Quận 1");
        list.add("Quận 9");
        list.add("Cầu Giấy");
        list.add("Quận Thủ Đức");
        list.add("Quận 3");
        return list;
    }
    private ArrayList<String> getCommunes(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Phường/Xã...");
        list.add("Xã 1");
        list.add("Phường 3");
        list.add("Phường Tăng Nhơn Phú A");
        list.add("Phường 5");
        return list;
    }
}
