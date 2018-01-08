package com.example.baobang.threebird.view.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.adapter.ProductAdapter;
import com.example.baobang.threebird.annimator.VegaLayoutManager;
import com.example.baobang.threebird.listener.OnItemRecyclerViewClickListener;
import com.example.baobang.threebird.model.Brand;
import com.example.baobang.threebird.model.Category;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.helper.BrandHelper;
import com.example.baobang.threebird.model.helper.CategoryHelper;
import com.example.baobang.threebird.model.helper.OrderHelper;
import com.example.baobang.threebird.model.helper.ProductHelper;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.activity.ProductActivity;

import java.util.ArrayList;
import java.util.List;


public class ProductFragment extends Fragment {

    private Spinner spCategory, spBrand;
    private List<Product> products;
    private ProductAdapter productAdapter;
    private List<Category> categories;
    private List<Brand> brands;
    private RecyclerView rcProducts;
    private OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;
    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolBarProduct);
        if (toolbar != null) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            if(appCompatActivity != null ){
                appCompatActivity.setSupportActionBar(toolbar);
            }
            toolbar.setTitle(null);
        }
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment


        rcProducts = view.findViewById(R.id.rcProduct);
        products = ProductHelper.getAllProduct();
        onItemRecyclerViewClickListener =new OnItemRecyclerViewClickListener() {
            @Override
            public void onItemClick(Object item) {
                Product product = (Product) item;
                openOptionDialog(product.getId());
            }
        };
        rcProducts.setLayoutManager(new VegaLayoutManager());
        updateRecyclerView();
        addSpinnerCategory(view);
        addSpinnerBrand(view);
        addSpinnerSortBy(view);
        return view;
    }

    private void openOptionDialog(final int productId) {
        final CharSequence[] items = { "Thêm", "Sửa", "Xem chi tiết", "Xóa"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Lựa chọn");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Thêm")) {
                    goToAddProductActivity(-1, Constants.ADD_OPTION);
                } else if (items[item].equals("Sửa")) {
                    goToAddProductActivity(productId,Constants.EDIT_OPTION);
                }else if(items[item].equals("Xem chi tiết")){
                    goToAddProductActivity(productId, Constants.DETAIL_OPTION);
                }
                else{
                    deleteProduct(productId);
                }
            }
        });
        builder.show();
    }

    private void deleteProduct(final int productId) {
        final Product product = ProductHelper.getProduct(productId);

        if(product != null && product.getInvetory() > 0){
            Utils.openDialog(getActivity(), "Sản phẩm " + product.getName() +" hiện còn " + product.getInvetory() + " sản phẩm, không thể xóa");
            return;
        }

        if(OrderHelper.checkProductOrdered(product.getId())){
            Utils.openDialog(getActivity(), "Sản phẩm đã được đặt hàng, không thể xóa");
            return;
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setCancelable(false);
        dialog.setTitle("Thông báo!");
        dialog.setMessage("Bạn có muốn xóa sản phẩm " + product.getName());

        dialog.setPositiveButton("Đồng ý", (dialog12, id) -> {
               boolean res = ProductHelper.deleteProduct(product);
               if(res){
                   removeProductFromList(product);
                   productAdapter.notifyDataSetChanged();
//                       updateRecyclerView();
                   Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
               }else{
                   Utils.openDialog(getActivity(), "Có lỗi xảy ra, vui lòng thử lại");
               }
        }).setNegativeButton("Hủy ", (dialog1, which) -> {
        });
        final AlertDialog alert = dialog.create();
        alert.show();
    }

    private void removeProductFromList(Product product){
        for(Product p : products){
            if(p.getId() == product.getId()){
                products.remove(p);
                return;
            }
        }
    }

    private void addSpinnerSortBy(View view) {
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
                doSortBy();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void doSortBy() {
        int brandPosition = spBrand.getSelectedItemPosition();
        int brandId = -1;
        if(brandPosition != 0){
            brandId = brands.get(brandPosition).getId();
        }
        int categoryPosition = spCategory.getSelectedItemPosition();
        int categoryId = -1;
        if(categoryPosition != 0){
            categoryId = categories.get(categoryPosition).getId();
        }
        products = ProductHelper.getListSortBy(brandId, categoryId);
        updateRecyclerView();
    }

    private void addSpinnerCategory(View view) {
        spCategory = view.findViewById(R.id.spCategory);
        spCategory = view.findViewById(R.id.spCategory);
        categories = getCategories();

        ArrayAdapter<Category> categoryArrayAdapter =
                new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_list_item_1,
                        categories);
        spCategory.setAdapter(categoryArrayAdapter);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                doSortBy();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addSpinnerBrand(View view) {
        spBrand = view.findViewById(R.id.spBrand);
        brands = getBrands();
        ArrayAdapter<Brand> brandArrayAdapter =
                new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_list_item_1,
                        brands);
        spBrand.setAdapter(brandArrayAdapter);
        spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                doSortBy();
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
            Log.e("Id", productId + "");
            Product product = ProductHelper.getProduct(productId);
            int indexChange = checkProducts(product);
            if( indexChange == -1){
                products.add(product);
            }else{
                products.set(indexChange, product);
            }
            productAdapter = new ProductAdapter(products, rcProducts, onItemRecyclerViewClickListener);
            rcProducts.setAdapter(productAdapter);
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

    private void updateRecyclerView(){
        productAdapter = new ProductAdapter(products, rcProducts, onItemRecyclerViewClickListener);
        rcProducts.setAdapter(productAdapter);
    }

    private List<Brand> getBrands(){
//        BrandHelper.createBrand(new Brand(0, "Chọn hãng..."));
//        BrandHelper.createBrand(new Brand(0, "Apple"));
//        BrandHelper.createBrand(new Brand(0, "SamSung"));
//        BrandHelper.createBrand(new Brand(0, "Oppo"));
        return BrandHelper.getAllBrand();
    }

    private List<Category> getCategories(){

//        CategoryHelper.createCategory(new Category(0, "Chọn loại sản phẩm"));
//        CategoryHelper.createCategory(new Category(1, "Phone"));
//        CategoryHelper.createCategory(new Category(2, "Laptop"));
//        CategoryHelper.createCategory(new Category(3, "Tablet"));
        return CategoryHelper.getAllCategory();
    }

    public List<String> getSortBies() {
        List<String> sortBies = new ArrayList<>();
        sortBies.add("Ngày tạo");
        sortBies.add("Tên sản phẩm");
        sortBies.add("Giá sản phẩm");
        return sortBies;
    }
}
