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
import android.widget.FrameLayout;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.adapter.ClientAdapter;
import com.example.baobang.threebird.annimator.VegaLayoutManager;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.presenter.imp.ClientFragmentPresenterImp;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.ClientFragmentView;
import com.example.baobang.threebird.view.activity.ClientActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientFragment extends Fragment implements ClientFragmentView{

    ClientFragmentPresenterImp clientFragmentPresenterImp;

    private Unbinder unbinder;

    @BindView(R.id.toolBarClient)
    Toolbar toolbar;

    @BindView(R.id.layoutRoot)
    FrameLayout layoutRoot;

    @BindView(R.id.rcClients)
    RecyclerView rcClients;

    private ArrayList<Client> clients;
    private ClientAdapter clientAdapter;
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

        unbinder = ButterKnife.bind(this, view);

        clientFragmentPresenterImp = new ClientFragmentPresenterImp(this);
        setHasOptionsMenu(true);
        // add views
        clientFragmentPresenterImp.init(view);
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
                int indexChange = clientFragmentPresenterImp.checkClients(clients,client);
                if( indexChange == -1){
                    clients.add(client);
                }else{
                    clients.set(indexChange, client);
                }
                clientAdapter.notifyDataSetChanged();
        }
    }

    private void openOptionDialog(int option, final Client client){

         if (option == Constants.ADD_OPTION) {
                goToUserDetailActivity(null, Constants.ADD_OPTION);
         } else if (option == Constants.EDIT_OPTION) {
                goToUserDetailActivity(client,Constants.EDIT_OPTION);
         }else if(option == Constants.DETAIL_OPTION){
                goToUserDetailActivity(client, Constants.DETAIL_OPTION);
         }else{
                clientFragmentPresenterImp
                        .deleteClient(
                                getActivity(),
                                clients,
                                client);
         }
    }

    private void goToUserDetailActivity(Client client, int option) {
        Intent userDetailActivity = new Intent(getActivity(), ClientActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.CLIENT,client);
        bundle.putInt(Constants.OPTION, option);
        userDetailActivity.putExtras(bundle);
        startActivityForResult(userDetailActivity, Constants.CLIENT_REQUEST_CODE);
    }

    @Override
    public void addControls(View view) {
        Utils.hideKeyboardOutside(layoutRoot, getActivity());

    }

    @Override
    public void addToolBar(View view) {
        if (toolbar != null) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            if(appCompatActivity != null){
                appCompatActivity.setSupportActionBar(toolbar);
            }
            toolbar.setTitle(null);
        }
    }

    @Override
    public void showMessage(String message) {
        Utils.openDialog(getActivity(), message);
    }

    @Override
    public void showRecyclerViewClient( ArrayList<Client> clients) {
        this.clients = clients;
        clientAdapter = new ClientAdapter(clients,
                rcClients,
                (option, item) -> openOptionDialog(option, (Client)item),
                true);
        rcClients.setLayoutManager(new VegaLayoutManager());
        rcClients.setAdapter(clientAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
