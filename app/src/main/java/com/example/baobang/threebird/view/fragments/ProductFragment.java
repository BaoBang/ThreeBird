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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.adapter.ProductAdapter;
import com.example.baobang.threebird.annimator.VegaLayoutManager;
import com.example.baobang.threebird.listener.OnItemRecyclerViewClickListener;
import com.example.baobang.threebird.model.Brand;
import com.example.baobang.threebird.model.Category;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.presenter.imp.ProductFragmentPresenterImp;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.ProductFragmentView;
import com.example.baobang.threebird.view.activity.ProductActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductFragment extends Fragment implements ProductFragmentView {

    private ProductFragmentPresenterImp productFragmentPresenterImp;

    @BindView(R.id.toolBarProduct)
    Toolbar toolbar;

    @BindView(R.id.rcProduct)
    RecyclerView rcProducts;

    @BindView(R.id.spCategory)
    Spinner spCategory;

    @BindView(R.id.spBrand)
    Spinner spBrand;

    @BindView(R.id.layoutRoot)
    FrameLayout layoutRoot;

    private ArrayList<Product> products;
    private ProductAdapter productAdapter;
    private ArrayList<Category> categories;
    private ArrayList<Brand> brands;

    private OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        productFragmentPresenterImp = new ProductFragmentPresenterImp(this);
        productFragmentPresenterImp.init(view);
        return view;
    }

    private void openOptionDialog(int option, final int productId) {

         if(option == Constants.ADD_OPTION) {
             goToAddProductActivity(-1, Constants.ADD_OPTION);
         }
         else if(option == Constants.EDIT_OPTION) {
             goToAddProductActivity(productId,Constants.EDIT_OPTION);
         }
         else if(option == Constants.DETAIL_OPTION) {
             goToAddProductActivity(productId, Constants.DETAIL_OPTION);
         }
         else{
             productFragmentPresenterImp.deleteProduct(getActivity(), products, productId, onItemRecyclerViewClickListener);
         }
    }


    @Override
    public void addSpinnerSortBy(View view) {
        Spinner spSortBy = view.findViewById(R.id.spSortBy);
        List<String> sortBies = getSortBies();
        ArrayAdapter<String> sortByAdapter =
                new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_list_item_1,
                        sortBies);
        spSortBy.setAdapter(sortByAdapter);
        spSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Brand brand = spBrand.getSelectedItemPosition() == 0 ?
                        null : brands.get(spBrand.getSelectedItemPosition());

                Category category = spCategory.getSelectedItemPosition() == 0 ?
                        null : categories.get(spCategory.getSelectedItemPosition());
                productFragmentPresenterImp.doSort(brand, category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void addSpinnerCategory(View view, ArrayList<Category> categories) {
        this.categories = categories;
        ArrayAdapter<Category> categoryArrayAdapter =
                new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_list_item_1,
                        categories);
        spCategory.setAdapter(categoryArrayAdapter);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Brand brand = spBrand.getSelectedItemPosition() == 0 ?
                        null : brands.get(spBrand.getSelectedItemPosition());

                Category category = spCategory.getSelectedItemPosition() == 0 ?
                        null : categories.get(spCategory.getSelectedItemPosition());
                productFragmentPresenterImp.doSort(brand, category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void addSpinnerBrand(View view, ArrayList<Brand> brands) {
        this.brands = brands;
        ArrayAdapter<Brand> brandArrayAdapter =
                new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_list_item_1,
                        brands);
        spBrand.setAdapter(brandArrayAdapter);
        spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Brand brand = spBrand.getSelectedItemPosition() == 0 ?
                        null : brands.get(spBrand.getSelectedItemPosition());

                Category category = spCategory.getSelectedItemPosition() == 0 ?
                        null : categories.get(spCategory.getSelectedItemPosition());
                productFragmentPresenterImp.doSort(brand, category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.PRODUCT_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            int productId = bundle == null ? -1 : bundle.getInt(Constants.PRODUCT);
            productFragmentPresenterImp.addProduct(products, productId, onItemRecyclerViewClickListener);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      //  super.onCreateOptionsMenu(menu, inflater);
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
                productAdapter.getFilter().filter(s);
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.actionBar_add:
                goToAddProductActivity(-1, Constants.ADD_OPTION);
                break;
            case R.id.actionBar_search:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToAddProductActivity(int productId, int option) {
        Intent addProductActivity = new Intent(getActivity(), ProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.PRODUCT, productId);
        bundle.putInt(Constants.OPTION, option);
        addProductActivity.putExtras(bundle);
        startActivityForResult(addProductActivity, Constants.PRODUCT_REQUEST_CODE);

    }

    @Override
    public void updateRecyclerView(ArrayList<Product> products){
        this.products = products;
        productAdapter = new ProductAdapter(
                products,
                rcProducts,
                onItemRecyclerViewClickListener,
                true);
        rcProducts.setAdapter(productAdapter);
    }

    public List<String> getSortBies() {
        List<String> sortBies = new ArrayList<>();
        sortBies.add("Ngày tạo");
        sortBies.add("Tên sản phẩm");
        sortBies.add("Giá sản phẩm");
        return sortBies;
    }

    @Override
    public void addControls(View view) {
        Utils.hideKeyboardOutside(layoutRoot, getActivity());

    }

    @Override
    public void addToolBar(View view) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            if(appCompatActivity != null ){
                appCompatActivity.setSupportActionBar(toolbar);
            }
            toolbar.setTitle(null);
    }

    @Override
    public void showMessage(String message) {
        Utils.openDialog(getActivity(), message);
    }

    @Override
    public void showRecyclerViewProduct(ArrayList<Product> products) {
        this.products = products;
        onItemRecyclerViewClickListener = (option, item) -> {
            Product product = (Product) item;
            openOptionDialog(option, product.getId());
        };
        rcProducts.setLayoutManager(new VegaLayoutManager());
        updateRecyclerView(products);
    }
}
