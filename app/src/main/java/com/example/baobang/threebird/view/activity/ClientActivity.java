package com.example.baobang.threebird.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.ClientGroup;
import com.example.baobang.threebird.model.helper.ClientGroupHelper;
import com.example.baobang.threebird.presenter.ClientPresenterImp;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.utils.SlideView;
import com.example.baobang.threebird.view.ClientView;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class ClientActivity extends AppCompatActivity implements ClientView{

    private ClientPresenterImp clientPresenterImp;

    private Toolbar toolbar;
    private LinearLayout groupUserInfo, groupAddressInfo;
    private SwitchButton swbUserInfo, swbAddressInfo;

    private ImageView imgAvatar;
    private ExtendedEditText txtName, txtPhone, txtFax,
            txtWebsite, txtEmail, txtAddress;

    private Spinner spGroupClient, spProvince, spDistrict, spCommune;
    private ArrayList<String> provinces, districts, communes;
    private ArrayList<ClientGroup> groups;

    private Bitmap avatar = null;
    private Client client = null;
    private int option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        clientPresenterImp = new ClientPresenterImp(this);

        Bundle bundle = getIntent().getExtras();
        client = clientPresenterImp.getClientFromBundle(bundle);
        option = clientPresenterImp.getOptionFromBundle(bundle);

        clientPresenterImp.init();

//        ClientGroupHelper.createClientGroup(new ClientGroup(0, "Admin"));
//        ClientGroupHelper.createClientGroup(new ClientGroup(0, "Employee"));
//        ClientGroupHelper.createClientGroup(new ClientGroup(0, "Membber"));
    }
    @Override
    public void addControls() {
        // start setting toolbar
        toolbar = findViewById(R.id.toolBarUserDetail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        // end setting toolbar
        groupAddressInfo = findViewById(R.id.groupAddressInfo);
        groupUserInfo = findViewById(R.id.groupUserInfo);
        swbAddressInfo = findViewById(R.id.swbAddressInfo);
        swbUserInfo = findViewById(R.id.swbUserInfo);
        imgAvatar = findViewById(R.id.imgAvatar);
        // input
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtFax = findViewById(R.id.txtFax);
        txtWebsite = findViewById(R.id.txtWebsite);
        txtEmail = findViewById(R.id.txtEmail);
        txtAddress = findViewById(R.id.txtAddress);
        // spinner
        clientPresenterImp.loadSpinnerData();

        if(client != null){
            clientPresenterImp.setAvatar(this, client);
            clientPresenterImp.setData(this, client);
        }
        if(option == Constants.DETAIL_OPTION){
            setDisableInput();
        }
    }
    @Override
    public void addEvents() {
//
        swbUserInfo.setOnCheckedChangeListener(
                (compoundButton,  isChecked) ->{
                    if(isChecked){SlideView.expand(groupUserInfo);}
                         else{SlideView.collapse(groupUserInfo);}});

        swbAddressInfo.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if(isChecked){
                SlideView.expand(groupAddressInfo);
            }else{
                SlideView.collapse(groupAddressInfo);
            }
        });

        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        imgAvatar.setOnClickListener(view -> startCamera());

        txtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().matches(Constants.PHONE_REGULAR)){
                    setError(txtPhone, "Số điện thoại bắt đầu với +84/0 và tiếp theo từ 9-10 kí tự số");
                }
            }
        });

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().matches(Constants.EMAIL_REGULAR)){
                    setError(txtEmail, "Email định dạng không đúng");
                }
            }
        });
    }
    @Override
    public void setDataForInput(Bitmap bitmap, String name, String phone,
                                String fax, String website, String email,
                                int groupId, String province, String district,
                                String commune, String address) {

        imgAvatar.setImageBitmap(bitmap);
        txtName.setText(name);
        txtPhone.setText(phone);
        txtFax.setText(fax);
        txtWebsite.setText(website);
        txtEmail.setText(email);
        txtAddress.setText(address);
        //

        clientPresenterImp.setGroupClientSelected(groups, groupId);
        clientPresenterImp.setProvinceSelected(provinces, province);
        clientPresenterImp.setDistrictSelected(districts, district);
        clientPresenterImp.setCommuneSelected(communes, commune);

    }
    @Override
    public void setDisableInput() {
        imgAvatar.setEnabled(false);
        // input
        TextFieldBoxes tfbName = findViewById(R.id.tfbName);
        tfbName.setEnabled(false);
        txtName.setEnabled(false);

        TextFieldBoxes tfbPhone = findViewById(R.id.tfbPhone);
        tfbPhone.setEnabled(false);
        txtPhone.setEnabled(false);

        TextFieldBoxes tfbFax = findViewById(R.id.tfbFax);
        tfbFax.setEnabled(false);
        txtFax.setEnabled(false);

        TextFieldBoxes tfbWebsite = findViewById(R.id.tfbWebsite);
        tfbWebsite.setEnabled(false);
        txtWebsite.setEnabled(false);


        TextFieldBoxes tfbEmail = findViewById(R.id.tfbEmail);
        tfbEmail.setEnabled(false);
        txtEmail.setEnabled(false);

        TextFieldBoxes tfbAddress = findViewById(R.id.tfbAddress);
        tfbAddress.setEnabled(false);
        txtAddress.setEnabled(false);


        spGroupClient.setEnabled(false);
        spCommune.setEnabled(false);
        spProvince.setEnabled(false);
        spDistrict.setEnabled(false);
    }
    @Override
    public void startCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, Constants.CAMERA_PIC_REQUEST);
    }
    @Override
    public boolean checkInput() {
        String name = txtName.getText().toString();
        int group = spGroupClient.getSelectedItemPosition();
        String phone = txtPhone.getText().toString();
        String fax = txtFax.getText().toString();
        String website = txtWebsite.getText().toString();
        String email = txtEmail.getText().toString();
        String province = spProvince.getSelectedItem().toString();
        String district = spDistrict.getSelectedItem().toString();
        String commune = spCommune.getSelectedItem().toString();
        String address = txtAddress.getText().toString();

        if(Utils.checkInput(name)){
            setError(txtName, "Vui lòng nhập vào họ tên");
            return false;
        }
        if(group ==0){
            Utils.openDialog(this, "Vui lòng chọn nhóm thành viên");
            return false;
        }
        if(Utils.checkInput(phone)){
            setError(txtPhone, "Vui lòng nhập vào số điện thoại");
            return false;
        }
        if(!phone.matches(Constants.PHONE_REGULAR)){
            setError(txtPhone, "Số điện thoại bắt đầu với +84/0 và tiếp theo từ 9-10 kí tự số");
            return false;
        }
//        if(Utils.checkInput(fax)){
//            Utils.openDialog(this, "Vui lòng nhập vào số fax");
//            return;
//        }
//        if(Utils.checkInput(website)){
//            Utils.openDialog(this, "Vui lòng nhập vào website");
//            return;
//        }
//        if(Utils.checkInput(email)){
//            Utils.openDialog(this, "Vui lòng nhập vào email");
//            return;
//        }
        if(email.length() > 0){
            if(!email.matches(Constants.EMAIL_REGULAR)){
                setError(txtEmail, "Email định dạng không đúng");
                return false;
            }

        }

        if(spProvince.getSelectedItemPosition() ==0){
            Utils.openDialog(this, "Vui lòng chọn tỉnh/thành phố");
            return false;
        }
        if(spDistrict.getSelectedItemPosition() ==0){
            Utils.openDialog(this, "Vui lòng chọn quận/huyện");
            return false;
        }
        if(spCommune.getSelectedItemPosition() ==0){
            Utils.openDialog(this, "Vui lòng chọn phường/xã");
            return false;
        }
        if(Utils.checkInput(address)){
            txtAddress.setError("Vui lòng nhập vào địa chỉ");
            txtAddress.requestFocus();
            return false;
        }
        return true;
    }
    @Override
    public Bitmap getAvatar() {
        return avatar;
    }
    @Override
    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }
    @Override
    public void setError(ExtendedEditText extendedEditText, String message) {
        extendedEditText.setError(message);
        extendedEditText.requestFocus();
    }

    @Override
    public void setSpinnerClientGroupSelectedPosition(int position) {
        spGroupClient.setSelection(position);
    }

    @Override
    public void setSpinnerProvincePosition(int position) {
        spProvince.setSelection(position);
    }

    @Override
    public void setSpinnerDistrictSelectedPosition(int position) {
      spDistrict.setSelection(position);
    }

    @Override
    public void setSpinnerCommuneSelectedPosition(int position) {
        spCommune.setSelection(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if (requestCode == Constants.CAMERA_PIC_REQUEST) {
                Bundle bundle =  data.getExtras();
                if(bundle != null){
                    avatar = (Bitmap) bundle.get("data");
                }
                imgAvatar.setImageBitmap(avatar);
            }
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
            if(option != Constants.DETAIL_OPTION){
                if(checkInput()){
                    String name = txtName.getText().toString();
                    int group = spGroupClient.getSelectedItemPosition();
                    String phone = txtPhone.getText().toString();
                    String fax = txtFax.getText().toString();
                    String website = txtWebsite.getText().toString();
                    String email = txtEmail.getText().toString();
                    String province = spProvince.getSelectedItem().toString();
                    String district = spDistrict.getSelectedItem().toString();
                    String commune = spCommune.getSelectedItem().toString();
                    String address = txtAddress.getText().toString();

                    Client client = null;
                    if(option == Constants.ADD_OPTION){
                        client =  clientPresenterImp.addClient(name, groups.get(group), phone, fax, website, email, province, district, commune, address);
                    }else if(option == Constants.EDIT_OPTION){
                        client =  clientPresenterImp.updateClient(this.client,name, groups.get(group), phone, fax, website, email, province, district, commune, address);
                    }

                    if(client != null){
                        Intent returnIntent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.CLIENT,client);
                        returnIntent.putExtras(bundle);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }else{
                        Utils.openDialog(this, "Đã có lỗi xảy ra, vui lòng thử lại");
                    }
                }
            }
            else{
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void showSpinnerDistrict(ArrayList<String> districts) {
            spDistrict = findViewById(R.id.spDistrict);
            this.districts = districts;
            ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    districts );
            spDistrict.setAdapter(districtAdapter);
    }
    @Override
    public void showSpinnerProvince(ArrayList<String> provinces) {
            spProvince = findViewById(R.id.spProvince);
            this.provinces = provinces;
            ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    provinces);
            spProvince.setAdapter(provinceAdapter);
    }
    @Override
    public void showSpinnerCommune(ArrayList<String> communes) {
            spCommune = findViewById(R.id.spCommune);
            this.communes = communes;

            ArrayAdapter<String> communeAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    communes);
            spCommune.setAdapter(communeAdapter);
    }
    @Override
    public void showSpinnerClientGroup(ArrayList<ClientGroup> clientGroups) {
            spGroupClient = findViewById(R.id.spGroupClient);
            this.groups = clientGroups;
            ArrayAdapter<ClientGroup> groupAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    groups);
            spGroupClient.setAdapter(groupAdapter);
    }
}
