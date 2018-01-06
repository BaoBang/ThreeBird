package com.example.baobang.threebird.activity;

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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Address;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.ClientGroup;
import com.example.baobang.threebird.model.bussinesslogic.ClientBL;
import com.example.baobang.threebird.model.bussinesslogic.ClientGroupBL;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.MySupport;
import com.example.baobang.threebird.utils.SlideView;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class ClientActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout groupUserInfo, groupAddressInfo;
    private SwitchButton swbUserInfo, swbAddressInfo;
    private ImageView imgAvatar;

    ExtendedEditText txtName, txtPhone, txtFax, txtWebsite, txtEmail, txtAddress;

    Spinner spGroupClient, spProvince, spDistrict, spCommune;
    ArrayList<String> provinces, districts, communes;
    ArrayList<ClientGroup> groups;
    ArrayAdapter<String> provinceAdapter, districtAdapter, communeAdapter;
    ArrayAdapter<ClientGroup> groupAdapter;
    private Bitmap avartar = null;
    private Client client = null;
    private int option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Bundle bundle = getIntent().getExtras();
        client = (Client) (bundle != null ? bundle.getSerializable(Constants.CLIENT) : null);
        option = bundle == null ? 0 : bundle.getInt(Constants.OPTION);
//        ClientGroupBL.createClientGroup(new ClientGroup(0, "Admin"));
//        ClientGroupBL.createClientGroup(new ClientGroup(0, "Employee"));
//        ClientGroupBL.createClientGroup(new ClientGroup(0, "Membber"));
        addControls();
        addEvents();
    }

    private void addEvents() {

        swbUserInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    SlideView.expand(groupUserInfo);
                }else{
                    SlideView.collapse(groupUserInfo);
                }
            }
        });

        swbAddressInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    SlideView.expand(groupAddressInfo);
                }else{
                    SlideView.collapse(groupAddressInfo);
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
                    txtPhone.setError("Số điện thoại bắt đầu với +84/0 và tiếp theo từ 9-10 kí tự số");
                    txtPhone.requestFocus();
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
                    txtEmail.setError("Email định dạng không đúng");
                }
            }
        });
    }

    private void addControls() {
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
        addSpinnerClientGroup();
        addSpinnerProvince();
        addSpinnerDistrict();
        addSpinnerCommune();

        if(client != null){
            setDataForInput();
        }
        if(option == Constants.DETAIL_OPTION){
            setDisableInput();
        }
    }

    private void addSpinnerCommune() {

        spCommune = findViewById(R.id.spCommune);
        communes = getCommunes();

        communeAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                communes);
        spCommune.setAdapter(communeAdapter);
    }

    private void addSpinnerDistrict() {
        spDistrict = findViewById(R.id.spDistrict);
        districts = getDistricts();
        districtAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                districts );
        spDistrict.setAdapter(districtAdapter);
    }

    private void addSpinnerProvince() {
        spProvince = findViewById(R.id.spProvince);
        provinces = getProvinces();
        provinceAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                provinces);
        spProvince.setAdapter(provinceAdapter);

    }

    private void addSpinnerClientGroup() {
        spGroupClient = findViewById(R.id.spGroupClient);
        groups = getGroups();
        groupAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                groups);
        spGroupClient.setAdapter(groupAdapter);
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
        ClientGroup clientGroup =  ClientGroupBL.getClientById(client.getGroup());
        for(int i = 0; i < groups.size(); i ++){
            if(groups.get(i).getId() == clientGroup.getId()){
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

    private void setDisableInput() {
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

    private void startCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, Constants.CAMERA_PIC_REQUEST);
    }

    private void addClient() {
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

        if(MySupport.checkInput(name)){
            txtName.setError("Vui lòng nhập vào họ tên");
            txtName.requestFocus();
            return;
        }
        if(group ==0){
            MySupport.openDialog(this, "Vui lòng chọn nhóm thành viên");
            spGroupClient.requestFocus();
            return;
        }
        if(MySupport.checkInput(phone)){
            txtPhone.setError("Vui lòng nhập vào số điện thoại");
            txtPhone.requestFocus();
            return;
        }
        if(!phone.matches(Constants.PHONE_REGULAR)){
            txtPhone.setError("Số điện thoại bắt đầu với +84/0 và tiếp theo từ 9-10 kí tự số");
            txtPhone.requestFocus();
            return;
        }
//        if(MySupport.checkInput(fax)){
//            MySupport.openDialog(this, "Vui lòng nhập vào số fax");
//            return;
//        }
//        if(MySupport.checkInput(website)){
//            MySupport.openDialog(this, "Vui lòng nhập vào website");
//            return;
//        }
//        if(MySupport.checkInput(email)){
//            MySupport.openDialog(this, "Vui lòng nhập vào email");
//            return;
//        }
        if(email.length() > 0){
            if(!email.matches(Constants.EMAIL_REGULAR)){
                txtEmail.setError("Email định dạng không đúng");
                txtEmail.requestFocus();
                return;
            }

        }

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
            txtAddress.setError("Vui lòng nhập vào địa chỉ");
            txtAddress.requestFocus();
            return;
        }
        int clientId = -1;
        if(this.client != null){
            clientId = this.client.getId();
        }
        Client newClient = new Client(clientId,
                name,groups.get(group).getId(), phone,fax, website, email,
                new Address(province, district, commune,  address));
        if(avartar != null){
            newClient.setAvatar(MySupport.BitMapToString(avartar, Constants.AVATAR_HEIGHT));
        }else{
            newClient.setAvatar(null);
        }
        boolean res;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if (requestCode == Constants.CAMERA_PIC_REQUEST) {
                Bundle bundle =  data.getExtras();
                if(bundle != null){
                    avartar = (Bitmap) bundle.get("data");
                }
                imgAvatar.setImageBitmap(avartar);
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
            addClient();
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<ClientGroup> getGroups(){
        ArrayList<ClientGroup> list = new ArrayList<>();
        list.add(new ClientGroup(-1, "Nhóm khách hàng..."));
        ArrayList<ClientGroup> temp = ClientGroupBL.getAllClientGroup();
        list.addAll(temp);
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
