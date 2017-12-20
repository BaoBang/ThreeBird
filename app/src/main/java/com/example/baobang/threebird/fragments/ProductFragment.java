package com.example.baobang.threebird.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.activity.AddProductActivity;
import com.example.baobang.threebird.activity.CreateOrderActivity;
import com.example.baobang.threebird.adapter.ProductAdapter;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.bussinesslogic.ProductBL;
import com.example.baobang.threebird.utils.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {


    private ListView lvProducts;
    private List<Product> products;
    private ProductAdapter productAdapter;

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolBarProduct);
        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            toolbar.setTitle(null);
        }
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment

        lvProducts = view.findViewById(R.id.lvProduct);
        products = ProductBL.getAllClient();
        productAdapter = new ProductAdapter(getActivity(), R.layout.item_product, products);
        lvProducts.setAdapter(productAdapter);
        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToAddProductActivity(products.get(i));
            }
        });
        for(Product product: products){
            Log.e("product: ", product.getId() + "-" + product.getBrand()+"-" + product.getCategory());
        }
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.PRODUCT_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            Product product = (Product) bundle.getSerializable(Constants.PRODUCT);
            int indexChange = checkProducts(product);
            if( indexChange == -1){
                products.add(product);
            }else{
                products.set(indexChange, product);
            }
            productAdapter.notifyDataSetChanged();
        }
    }

    private int checkProducts(Product product) {
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).getId() == product.getId()){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      //  super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.action_bar_menu, menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.actionBar_add:
                goToAddProductActivity(null);
                break;
            case R.id.actionBar_search:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToAddProductActivity(Product product) {
        Intent addProductActivity = new Intent(getActivity(), AddProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.PRODUCT, product);
        addProductActivity.putExtras(bundle);
        startActivityForResult(addProductActivity, Constants.PRODUCT_REQUEST_CODE);

    }
}
