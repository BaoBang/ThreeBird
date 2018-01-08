package com.example.baobang.threebird.presenter;

import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.ClientGroup;
import com.example.baobang.threebird.model.helper.ClientGroupHelper;
import com.example.baobang.threebird.model.helper.ClientHelper;
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
    public void loadSpinnerData() {
        clientView.showSpinnerProvince(getProvinces());
        clientView.showSpinnerCommune(getCommunes());
        clientView.showSpinnerDistrict(getDistricts());
        clientView.showSpinnerClientGroup(getGroups());
    }

    @Override
    public boolean addClient(Client client) {
        return ClientHelper.createClient(client);
    }

    @Override
    public boolean updateClient(Client client) {
        return ClientHelper.updateClient(client);
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
