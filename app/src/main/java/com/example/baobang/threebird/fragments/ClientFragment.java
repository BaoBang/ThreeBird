package com.example.baobang.threebird.fragments;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.activity.ClientActivity;
import com.example.baobang.threebird.adapter.ClientAdapter;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.bussinesslogic.ClientBL;
import com.example.baobang.threebird.utils.Constants;
import java.util.ArrayList;
/**
 * A simple {@link Fragment} subclass.
 */
public class ClientFragment extends Fragment {

    private ArrayList<Client> clients;
    private ClientAdapter clientAdapter;

    public ClientFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return addControlls(inflater,container);

    }

    private View addControlls(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_client, container, false);
        // add toolbar
        Toolbar toolbar = view.findViewById(R.id.toolBarClient);
        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            toolbar.setTitle(null);
        }
        setHasOptionsMenu(true);
        // add views

        ListView lvClients; lvClients = view.findViewById(R.id.lvClients);
        clients = ClientBL.getClientOn30Days();
        //
        clientAdapter = new ClientAdapter(getActivity(), R.layout.item_client, clients);
        lvClients.setAdapter(clientAdapter);
        lvClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clientAdapter.setItemSelected(i);
                clientAdapter.notifyDataSetChanged();
                goToUserDetailActivity(clients.get(i));
            }
        });
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
                goToUserDetailActivity(null);
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
                clientAdapter.setTempObjects(clients);
                clientAdapter.notifyDataSetChanged();
        }
    }

    private int checkClients(Client client){
        for(int i = 0; i < clients.size(); i++){
            if(client.getId() == clients.get(i).getId()){
                return i;
            }
        }
        return -1;
    }

    private void goToUserDetailActivity(Client client) {
        Intent userDetailActivity = new Intent(getActivity(), ClientActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.CLIENT,client);
        userDetailActivity.putExtras(bundle);
        startActivityForResult(userDetailActivity, Constants.CLIENT_REQUEST_CODE);
    }
}
