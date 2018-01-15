package com.example.baobang.threebird.presenter.imp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Address;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.ClientGroup;
import com.example.baobang.threebird.model.Commune;
import com.example.baobang.threebird.model.District;
import com.example.baobang.threebird.model.Province;
import com.example.baobang.threebird.model.helper.ClientGroupHelper;
import com.example.baobang.threebird.model.helper.ClientHelper;
import com.example.baobang.threebird.model.helper.CommuneHelper;
import com.example.baobang.threebird.model.helper.DistrictHelper;
import com.example.baobang.threebird.model.helper.ProvinceHelper;
import com.example.baobang.threebird.presenter.ClientPresenter;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.ClientView;

import java.util.ArrayList;

public class ClientPresenterImp implements ClientPresenter {

    private ClientView clientView;

    public ClientPresenterImp(ClientView clientView) {
        this.clientView = clientView;
    }

    @Override
    public void init() {
        clientView.addControls();
        clientView.addEvents();
    }

    @Override
    public Client getClientFromBundle(Bundle bundle) {
        return (bundle != null ? (Client) bundle.getSerializable(Constants.CLIENT) : null);
    }

    @Override
    public int getOptionFromBundle(Bundle bundle) {
        return bundle == null ? Constants.ADD_OPTION : bundle.getInt(Constants.OPTION);
    }

    @Override
    public void setData(Activity activity, Client client) {
        Bitmap bitmap;
        if(client.getAvatar() != null && client.getAvatar().length() > 0){
            bitmap = Utils.StringToBitMap(client.getAvatar());
        }else{
            bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
        }
        clientView.setDataForInput(bitmap, client.getName(), client.getPhone(),
                client.getFax(), client.getWebsite(), client.getEmail(),
                client.getGroupId(), client.getAddress().getProvinceId(),
                client.getAddress().getDistrictId(), client.getAddress().getCommuneId(),
                client.getAddress().getAddress());
    }

    @Override
    public void setAvatar(Activity activity, Client client) {
        Bitmap bitmap;
        if(client.getAvatar() != null && client.getAvatar().length() > 0){
            bitmap = Utils.StringToBitMap(client.getAvatar());
        }else{
            bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
        }
        clientView.setAvatar(bitmap);
    }

    @Override
    public void setGroupClientSelected(ArrayList<ClientGroup> groups, int groupId) {
       int position = 0;
        for(int i = 0; i < groups.size(); i++){
           if(groups.get(i).getId() == groupId){
                position = i;
                break;
           }
       }
       clientView.setSpinnerClientGroupSelectedPosition(position);
    }

    @Override
    public void setDistrictSelected(ArrayList<District> districts, int districtId) {
        int position = 0;
        for(int i = 0; i < districts.size(); i++){
            if(districts.get(i).getId() == districtId){
                position = i;
                break;
            }
        }
        clientView.setSpinnerDistrictSelectedPosition(position);
    }

    @Override
    public void setProvinceSelected(ArrayList<Province> provinces, int provinceId) {
        int position = 0;
        for(int i = 0; i < provinces.size(); i++){
            if(provinces.get(i).getId() == provinceId){
                position = i;
                break;
            }
        }
        clientView.setSpinnerProvincePosition(position);
    }

    @Override
    public void setCommuneSelected(ArrayList<Commune> communes, int communeId) {
        int position = 0;
        for(int i = 0; i < communes.size(); i++){
            if(communes.get(i).getId() == communeId){
                position = i;
                break;
            }
        }
        clientView.setSpinnerCommuneSelectedPosition(position);
    }

    @Override
    public void loadSpinnerData() {
        clientView.showSpinnerProvince(getProvinces());
        clientView.showSpinnerCommune(getCommunes());
        clientView.showSpinnerDistrict(getDistricts());
        clientView.showSpinnerClientGroup(getGroups());
    }

    @Override
    public void clickAddOptionMenu(Client client, int option, String name,
                                   int group, ClientGroup clientGroup,
                                   String phone, String fax, String website,
                                   String email, Province province,
                                   District district, Commune commune,
                                   String address) {
        if(Utils.checkInput(name)){
            clientView.showNameWarning("Vui lòng nhập vào họ tên");
            return;
        }
        if(group ==0){
            clientView.showMessage("Vui lòng chọn nhóm thành viên");
            return;
        }
        if(Utils.checkInput(phone)){
            clientView.showPhoneWarning("Vui lòng nhập vào số điện thoại");
            return;
        }
        if(!phone.matches(Constants.PHONE_REGULAR)){
            clientView.showPhoneWarning("Số điện thoại bắt đầu với +84/0 và tiếp theo từ 9-10 kí tự số");
            return;
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
                clientView.showEmailWarning("Email định dạng không đúng");
                return;
            }

        }

        if(province == null){
            clientView.showMessage("Vui lòng chọn tỉnh/thành phố");
            return;
        }
        if(district == null){
            clientView.showMessage("Vui lòng chọn quận/huyện");
            return;
        }
        if(commune == null){
            clientView.showMessage("Vui lòng chọn phường/xã");
            return;
        }
        if(Utils.checkInput(address)){
            clientView.showAddressWarning("Vui lòng nhập vào địa chỉ");
            return;
        }
        if(option == Constants.ADD_OPTION){
            client =  addClient(name,clientGroup , phone,
                    fax, website, email,
                    province, district, commune, address);
        }else if(option == Constants.EDIT_OPTION){
            client =  updateClient(client ,name, clientGroup,
                    phone, fax, website,
                    email, province, district,
                    commune, address);
        }

        if(client != null){
            clientView.changeActivity(client);
        }else{
            clientView.showMessage("Đã có lỗi xảy ra, vui lòng thử lại");
        }
    }

    @Override
    public Client addClient(String name, ClientGroup group, String phone,
                            String fax, String website, String email,
                            Province province, District district,
                            Commune commune, String address) {
        Client newClient = new Client(0,
                name,group.getId(), phone,fax, website, email,
                new Address(province.getId(), district.getId(), commune.getId(),  address));
        if(clientView.getAvatar() != null){
            newClient.setAvatar(Utils.BitMapToString(clientView.getAvatar(), Constants.AVATAR_HEIGHT));
        }else{
            newClient.setAvatar("");
        }
        return ClientHelper.createClientReturnObject(newClient);
    }

    @Override
    public Client updateClient(Client client, String name, ClientGroup group,
                               String phone, String fax, String website,
                               String email, Province province, District district,
                               Commune commune, String address) {
        if(client == null)
             return null;

        client.setName(name);
        client.setGroupId(group.getId());
        client.setPhone(phone);
        client.setFax(fax);
        client.setWebsite(website);
        client.setEmail(email);
        client.getAddress().setProvinceId(province.getId());
        client.getAddress().setDistrictId(district.getId());
        client.getAddress().setCommuneId(commune.getId());
        client.getAddress().setAddress(address);
        if(clientView.getAvatar() != null){
            client.setAvatar(Utils.BitMapToString(clientView.getAvatar(), Constants.AVATAR_HEIGHT));
        }else{
            client.setAvatar("");
        }

        return ClientHelper.updateClientReturnObject(client);
    }


    private ArrayList<ClientGroup> getGroups(){
        return ClientGroupHelper.getAllClientGroup();
    }
    private ArrayList<Province> getProvinces(){
        return ProvinceHelper.getAllProvinces();
    }

    private ArrayList<District> getDistricts(){
        return DistrictHelper.getAllDistricts();
    }
    private ArrayList<Commune> getCommunes(){
        return CommuneHelper.getAllCommunes();
    }


}
