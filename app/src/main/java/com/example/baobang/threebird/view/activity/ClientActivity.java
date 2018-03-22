package com.example.baobang.threebird.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.ClientGroup;
import com.example.baobang.threebird.model.Commune;
import com.example.baobang.threebird.model.District;
import com.example.baobang.threebird.model.Province;
import com.example.baobang.threebird.presenter.imp.ClientPresenterImp;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.utils.SlideView;
import com.example.baobang.threebird.view.ClientView;
import com.kyleduo.switchbutton.SwitchButton;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class ClientActivity extends AppCompatActivity implements ClientView{


    @BindView(R.id.toolBarUserDetail)
    Toolbar toolbar;

    @BindView(R.id.groupUserInfo)
    LinearLayout groupUserInfo;

    @BindView(R.id.groupAddressInfo)
    LinearLayout groupAddressInfo;

    @BindView(R.id.swbUserInfo)
    SwitchButton swbUserInfo;

    @BindView(R.id.swbAddressInfo)
    SwitchButton swbAddressInfo;

    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;

    @BindView(R.id.txtName)
    ExtendedEditText txtName;

    @BindView(R.id.txtPhone)
    ExtendedEditText txtPhone;

    @BindView(R.id.txtFax)
    ExtendedEditText txtFax;

    @BindView(R.id.txtWebsite)
    ExtendedEditText txtWebsite;

    @BindView(R.id.txtEmail)
    ExtendedEditText txtEmail;

    @BindView(R.id.txtAddress)
    ExtendedEditText txtAddress;

    @BindView(R.id.spGroupClient)
    Spinner spGroupClient;

    @BindView(R.id.spProvince)
    Spinner spProvince;

    @BindView(R.id.spDistrict)
    Spinner spDistrict;

    @BindView(R.id.spCommune)
    Spinner spCommune;

    private ClientPresenterImp clientPresenterImp;

    private ArrayList<Province> provinces;
    private ArrayList<District> districts;
    private ArrayList<Commune> communes;
    private ArrayList<ClientGroup> groups;

    private Bitmap avatar = null;
    private Client client = null;
    private int option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        ButterKnife.bind(this);

        LinearLayout layoutRoot = findViewById(R.id.layoutRoot);
        Utils.hideKeyboardOutside(layoutRoot, this);

        clientPresenterImp = new ClientPresenterImp(this);

        Bundle bundle = getIntent().getExtras();
        client = clientPresenterImp.getClientFromBundle(bundle);
        option = clientPresenterImp.getOptionFromBundle(bundle);

        clientPresenterImp.init();


    }
    @Override
    public void addControls() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        clientPresenterImp.loadSpinnerData();

        if(client != null){
            clientPresenterImp.setAvatar(this, client);
            clientPresenterImp.setData(this, client);
        }
        if(option == Constants.DETAIL_OPTION){
            setDisableInput();
        }
    }

    @OnCheckedChanged({R.id.swbUserInfo, R.id.swbAddressInfo})
    public void onCheckedChange(CompoundButton button, boolean isChecked){
            View view = null;
            switch (button.getId()){
                case R.id.swbUserInfo:
                    view = groupUserInfo;
                    break;
                case R.id.swbAddressInfo:
                    view = groupAddressInfo;
                    break;
            }
        if(isChecked){
                SlideView.expand(view);
        }else{
            SlideView.collapse(view);
        }
    }

    @OnClick(R.id.imgAvatar)
    public void clickAvatar(View view){
        Utils.openDialogChosseImage(this);
    }

    @OnTextChanged(value = {R.id.txtPhone, R.id.txtEmail}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChanged(Editable editable){

        View view = getCurrentFocus();
        if(view != null){
            switch (view.getId()){
                case R.id.txtPhone:
                    if(!editable.toString().matches(Constants.PHONE_REGULAR)){
                        showPhoneWarning("Số điện thoại bắt đầu với +84/0 và tiếp theo từ 9-10 kí tự số");
                    }
                    break;
                case R.id.txtEmail:
                    if(editable.length() > 1
                            && !editable.toString().matches(Constants.EMAIL_REGULAR)){
                        showEmailWarning("Email định dạng không đúng");
                    }
                    break;
            }
        }
    }

    @Override
    public void addEvents() {
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

    }

    @Override
    public void setDataForInput(Bitmap bitmap, String name, String phone,
                                String fax, String website, String email,
                                int groupId, int provinceId, int districtId,
                                int communeId, String address) {

        imgAvatar.setImageBitmap(bitmap);
        txtName.setText(name);
        txtPhone.setText(phone);
        txtFax.setText(fax);
        txtWebsite.setText(website);
        txtEmail.setText(email);
        txtAddress.setText(address);

        clientPresenterImp.setGroupClientSelected(groups, groupId);
        clientPresenterImp.setProvinceSelected(provinces, provinceId);
        clientPresenterImp.setDistrictSelected(districts, districtId);
        clientPresenterImp.setCommuneSelected(communes, communeId);

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
    public Bitmap getAvatar() {
        return avatar;
    }

    @Override
    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
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
    public void showMessage(String message) {
        Utils.openDialog(this, message);
    }

    @Override
    public void showNameWarning(String message) {
        txtName.setError(message);
        txtName.requestFocus();
    }

    @Override
    public void showPhoneWarning(String message) {
        txtPhone.setError(message);
        txtPhone.requestFocus();
    }

    @Override
    public void showEmailWarning(String message) {
        txtEmail.setError(message);
        txtEmail.requestFocus();
    }

    @Override
    public void showAddressWarning(String message) {
        txtAddress.setError(message);
        txtAddress.requestFocus();
    }

    @Override
    public void changeActivity(Client client) {
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.CLIENT,client);
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
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
            }else if(requestCode == Constants.SELECT_FILE){
                Uri selectedImageUri = data.getData();
                try {
                    avatar = Utils.getBitmapFromUri(this,selectedImageUri);
                } catch (IOException e) {
                    avatar = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);
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
            if(option != Constants.DETAIL_OPTION) {
                String name = txtName.getText().toString();
                int group = spGroupClient.getSelectedItemPosition();
                String phone = txtPhone.getText().toString();
                String fax = txtFax.getText().toString();
                String website = txtWebsite.getText().toString();
                String email = txtEmail.getText().toString();

                int position = spProvince.getSelectedItemPosition();
                Province province = position > 0 ? provinces.get(position) : null;

                position = spDistrict.getSelectedItemPosition();
                District district = position > 0 ? districts.get(position) : null;

                position = spCommune.getSelectedItemPosition();
                Commune commune = position > 0 ? communes.get(position) : null;

                String address = txtAddress.getText().toString();

                clientPresenterImp.clickAddOptionMenu(client, option, name,
                        group, groups.get(group), phone, fax, website,
                        email, province, district, commune, address);
            }
            else{
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showSpinnerDistrict(ArrayList<District> districts) {
            this.districts = districts;
            ArrayAdapter<District> districtAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    districts );
            spDistrict.setAdapter(districtAdapter);
    }

    @Override
    public void showSpinnerProvince(ArrayList<Province> provinces) {

            this.provinces = provinces;
            ArrayAdapter<Province> provinceAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    provinces);
            spProvince.setAdapter(provinceAdapter);
    }

    @Override
    public void showSpinnerCommune(ArrayList<Commune> communes) {

            this.communes = communes;

            ArrayAdapter<Commune> communeAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    communes);
            spCommune.setAdapter(communeAdapter);
    }

    @Override
    public void showSpinnerClientGroup(ArrayList<ClientGroup> clientGroups) {

            this.groups = clientGroups;
            ArrayAdapter<ClientGroup> groupAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    groups);
            spGroupClient.setAdapter(groupAdapter);
    }
}
