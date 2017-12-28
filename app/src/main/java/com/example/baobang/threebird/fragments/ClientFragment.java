package com.example.baobang.threebird.fragments;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.activity.UserDetailsActivity;
import com.example.baobang.threebird.adapter.ClientApdapter;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.bussinesslogic.ClientBL;
import com.example.baobang.threebird.utils.Constants;
import java.util.ArrayList;
/**
 * A simple {@link Fragment} subclass.
 */
public class ClientFragment extends Fragment {

    private ListView lvClients;
    private ArrayList<Client> clients;
    private ClientApdapter clientApdapter;

    public ClientFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = addControlls(inflater,container);
        return view;
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
        lvClients = view.findViewById(R.id.lvClients);
        clients = ClientBL.getAllClient();
        //
        clientApdapter = new ClientApdapter(getActivity(), R.layout.item_client, clients);
        lvClients.setAdapter(clientApdapter);
        for(Client client : clients){
            Log.e("Client ", client.getId() + "");
        }
        lvClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
                clientApdapter.getFilter().filter(s);
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
                Client client = (Client) bundle.getSerializable(Constants.CLIENT);
                int indexChange = checkClients(client);
                if( indexChange == -1){
                    clients.add(client);
                }else{
                    clients.set(indexChange, client);
                }
                clientApdapter.setTempObjects(clients);
                clientApdapter.notifyDataSetChanged();
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
        Intent userDetailActivity = new Intent(getActivity(), UserDetailsActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.CLIENT,client);
        userDetailActivity.putExtras(bundle);
        startActivityForResult(userDetailActivity, Constants.CLIENT_REQUEST_CODE);
    }
}
