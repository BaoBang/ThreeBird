package com.example.baobang.threebird.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Address;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.ClientGroup;
import com.example.baobang.threebird.model.helper.ClientGroupHelper;
import com.example.baobang.threebird.model.helper.ClientHelper;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.ClientView;

import java.util.ArrayList;

/**
 * Created by baobang on 1/8/18.
 */

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
        Bitmap bitmap = null;
        if(client.getAvatar() != null && client.getAvatar().length() > 0){
            bitmap = Utils.StringToBitMap(client.getAvatar());
        }else{
            bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
        }
        clientView.setDataForInput(bitmap, client.getName(), client.getPhone(),
                client.getFax(), client.getWebsite(), client.getEmail(),
                client.getGroupId(), client.getAddress().getProvince(),
                client.getAddress().getDistrict(), client.getAddress().getCommune(),
                client.getAddress().getAddress());
    }

    @Override
    public void setAvatar(Activity activity, Client client) {
        Bitmap bitmap = null;
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
    public void setDistrictSelected(ArrayList<String> districts, String district) {
        int position = 0;
        for(int i = 0; i < districts.size(); i++){
            if(districts.get(i).equals(district)){
                position = i;
                break;
            }
        }
        clientView.setSpinnerDistrictSelectedPosition(position);
    }

    @Override
    public void setProvinceSelected(ArrayList<String> provinces, String province) {
        int position = 0;
        for(int i = 0; i < provinces.size(); i++){
            if(provinces.get(i).equals(province)){
                position = i;
                break;
            }
        }
        clientView.setSpinnerProvincePosition(position);
    }

    @Override
    public void setCommuneSelected(ArrayList<String> communes, String commune) {
        int position = 0;
        for(int i = 0; i < communes.size(); i++){
            if(communes.get(i).equals(commune)){
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
    public void clickAddOptionMenu(Client client, int option, String name, int group, ClientGroup clientGroup,
                                   String phone,String fax, String website,
                                   String email, int province, String provinceStr,
                                   int district, String districtStr,int commune,
                                   String communeStr, String address) {
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

        if(province ==0){
            clientView.showMessage("Vui lòng chọn tỉnh/thành phố");
            return;
        }
        if(district == 0){
            clientView.showMessage("Vui lòng chọn quận/huyện");
            return;
        }
        if(commune==0){
            clientView.showMessage("Vui lòng chọn phường/xã");
            return;
        }
        if(Utils.checkInput(address)){
            clientView.showAddressWarning("Vui lòng nhập vào địa chỉ");
            return;
        }
        if(option == Constants.ADD_OPTION){
            client =  addClient(name,clientGroup , phone, fax, website, email, provinceStr, districtStr, communeStr, address);
        }else if(option == Constants.EDIT_OPTION){
            client =  updateClient(client ,name, clientGroup, phone, fax, website, email, provinceStr, districtStr, communeStr, address);
        }

        if(client != null){
            clientView.changeActivity(client);
        }else{
            clientView.showMessage("Đã có lỗi xảy ra, vui lòng thử lại");
        }
    }

    @Override
    public Client addClient(String name, ClientGroup group, String phone, String fax, String website, String email, String province, String district, String commune, String address) {
        Client newClient = new Client(0,
                name,group.getId(), phone,fax, website, email,
                new Address(province, district, commune,  address));
        if(clientView.getAvatar() != null){
            newClient.setAvatar(Utils.BitMapToString(clientView.getAvatar(), Constants.AVATAR_HEIGHT));
        }else{
            newClient.setAvatar(null);
        }
        return ClientHelper.createClientReturnObject(newClient);
    }

    @Override
    public Client updateClient(Client client, String name, ClientGroup group,
                               String phone, String fax, String website,
                               String email, String province, String district,
                               String commune, String address) {
        if(client == null)
             return null;

        client.setName(name);
        client.setGroupId(group.getId());
        client.setPhone(phone);
        client.setFax(fax);
        client.setWebsite(website);
        client.setEmail(email);
        client.getAddress().setProvince(province);
        client.getAddress().setDistrict(district);
        client.getAddress().setCommune(commune);
        client.getAddress().setAddress(address);
        if(clientView.getAvatar() != null){
            client.setAvatar(Utils.BitMapToString(clientView.getAvatar(), Constants.AVATAR_HEIGHT));
        }else{
            client.setAvatar(null);
        }

        return ClientHelper.updateClientReturnObject(client);
    }


    private ArrayList<ClientGroup> getGroups(){
        ArrayList<ClientGroup> list = new ArrayList<>();
        list.add(new ClientGroup(-1, "Nhóm khách hàng..."));
        ArrayList<ClientGroup> temp = ClientGroupHelper.getAllClientGroup();
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
