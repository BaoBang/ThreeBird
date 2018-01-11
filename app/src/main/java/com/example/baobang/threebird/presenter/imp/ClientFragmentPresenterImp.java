package com.example.baobang.threebird.presenter.imp;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.helper.ClientHelper;
import com.example.baobang.threebird.model.helper.OrderHelper;
import com.example.baobang.threebird.presenter.ClientFragmentPresenter;
import com.example.baobang.threebird.view.ClientFragmentView;

import java.util.ArrayList;



public class ClientFragmentPresenterImp implements ClientFragmentPresenter {

    ClientFragmentView clientFragmentView;

    public ClientFragmentPresenterImp(ClientFragmentView clientFragmentView) {
        this.clientFragmentView = clientFragmentView;
    }

    @Override
    public void init(View view) {
        clientFragmentView.addToolBar(view);
        clientFragmentView.addControls(view);
        clientFragmentView.showRecyclerViewClient(ClientHelper.getClientOn30Days());
    }

    @Override
    public int checkClients(ArrayList<Client> clients, Client client) {
        for(int i = 0; i < clients.size(); i++){
            if(client.getId() == clients.get(i).getId()){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void deleteClient(Activity activity, ArrayList<Client>clients, Client client) {
        if(OrderHelper.checkClientHasOreder(client.getId())){
            clientFragmentView.showMessage("Khách hàng đã lập hóa đơn không thể xóa.");
            return;
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setCancelable(false);
        dialog.setTitle("Thông báo!");
        dialog.setMessage("Bạn có muốn xóa khách hàng " + client.getName());
        dialog.setPositiveButton("Đồng ý", (dialog1, id) -> {
            boolean res = ClientHelper.deleteClient(client);
            if(res){

                clients.remove(client);
                clientFragmentView.showRecyclerViewClient(clients);
//                clientAdapter.notifyDataSetChanged();
                Toast.makeText(activity, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }else{
                clientFragmentView.showMessage("Có lỗi xảy ra, vui lòng thử lại");
            }
        }).setNegativeButton("Hủy ", (dialog12, which) -> {
        });
        final AlertDialog alert = dialog.create();
        alert.show();
    }
}
