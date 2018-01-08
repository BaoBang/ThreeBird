package com.example.baobang.threebird.view.fragments;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.adapter.ClientAdapter;
import com.example.baobang.threebird.annimator.VegaLayoutManager;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.helper.ClientHelper;
import com.example.baobang.threebird.model.helper.OrderHelper;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.activity.ClientActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientFragment extends Fragment {

    private ArrayList<Client> clients;
    private ClientAdapter clientAdapter;
    private RecyclerView rcClients;
    public ClientFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return addControls(inflater,container);

    }

    private View addControls(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_client, container, false);
        // add toolbar
        Toolbar toolbar = view.findViewById(R.id.toolBarClient);
        if (toolbar != null) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            if(appCompatActivity != null){
                appCompatActivity.setSupportActionBar(toolbar);
            }
            toolbar.setTitle(null);
        }
        setHasOptionsMenu(true);
        // add views

        rcClients = view.findViewById(R.id.rcClients);
//        rcClients.setHasFixedSize(true);
        clients = ClientHelper.getClientOn30Days();
        //
        clientAdapter = new ClientAdapter(clients, rcClients, item -> openOptionDialog((Client)item));
        rcClients.setLayoutManager(new VegaLayoutManager());
        rcClients.setAdapter(clientAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.action_bar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.actionBar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                clientAdapter.getFilter().filter(s);
                return false;
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.actionBar_add:
                goToUserDetailActivity(null, Constants.ADD_OPTION);
                break;
            case R.id.actionBar_search:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.CLIENT_REQUEST_CODE && resultCode == Activity.RESULT_OK){
                Bundle bundle = data.getExtras();
                Client client = bundle == null ? null : (Client) bundle.getSerializable(Constants.CLIENT);
                int indexChange = checkClients(client);
                if( indexChange == -1){
                    clients.add(client);
                }else{
                    clients.set(indexChange, client);
                }
                clientAdapter.notifyDataSetChanged();
        }
    }

    private void openOptionDialog(final Client client){
        final CharSequence[] items = { "Thêm", "Sửa", "Xem chi tiết", "Xóa"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Lựa chọn");
        builder.setItems(items, (dialog, item) -> {

            if (items[item].equals("Thêm")) {
                goToUserDetailActivity(null, Constants.ADD_OPTION);
            } else if (items[item].equals("Sửa")) {
                goToUserDetailActivity(client,Constants.EDIT_OPTION);
            }else if(items[item].equals("Xem chi tiết")){
                goToUserDetailActivity(client, Constants.DETAIL_OPTION);
            }
            else{
                deleteClient(client);
            }
        });
        builder.show();
    }

    private void deleteClient(final Client client) {
        if(OrderHelper.checkClientHasOreder(client.getId())){
            Utils.openDialog(getActivity(), "Khách hàng đã lập hóa đơn không thể xóa.");
            return;
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setCancelable(false);
        dialog.setTitle("Thông báo!");
        dialog.setMessage("Bạn có muốn xóa khách hàng " + client.getName());

        dialog.setPositiveButton("Đồng ý", (dialog1, id) -> {
                boolean res = ClientHelper.deleteClient(client);
                if(res){
                    clients.remove(client);
                    clientAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                }else{
                    Utils.openDialog(getActivity(), "Có lỗi xảy ra, vui lòng thử lại");
                }
        }).setNegativeButton("Hủy ", (dialog12, which) -> {

        });
        final AlertDialog alert = dialog.create();
        alert.show();



    }

    private int checkClients(Client client){
        for(int i = 0; i < clients.size(); i++){
            if(client.getId() == clients.get(i).getId()){
                return i;
            }
        }
        return -1;
    }

    private void goToUserDetailActivity(Client client, int option) {
        Intent userDetailActivity = new Intent(getActivity(), ClientActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.CLIENT,client);
        bundle.putInt(Constants.OPTION, option);
        userDetailActivity.putExtras(bundle);
        startActivityForResult(userDetailActivity, Constants.CLIENT_REQUEST_CODE);
    }
}
