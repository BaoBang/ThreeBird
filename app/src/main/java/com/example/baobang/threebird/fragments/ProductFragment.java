package com.example.baobang.threebird.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.activity.ProductActivity;
import com.example.baobang.threebird.adapter.ProductAdapter;
import com.example.baobang.threebird.model.Brand;
import com.example.baobang.threebird.model.Category;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.bussinesslogic.BrandBL;
import com.example.baobang.threebird.model.bussinesslogic.CategoryBL;
import com.example.baobang.threebird.model.bussinesslogic.ProductBL;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.MySupport;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    private ListView lvProducts;
    private Spinner spCategory, spBrand;
    private List<Product> products;
    private ProductAdapter productAdapter;
    private List<String> sortBies;
    private List<Category> categories;
    private List<Brand> brands;

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
        products = ProductBL.getAllProduct();
        productAdapter = new ProductAdapter(getActivity(), R.layout.item_product, products);
        lvProducts.setAdapter(productAdapter);
        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productAdapter.setItemSelected(i);
                productAdapter.notifyDataSetChanged();
                openOptionDialog(products.get(i).getId());
            }
        });

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
                    deleteClient(productId);
                }
            }
        });
        builder.show();
    }

    private void deleteClient(int productId) {
        Product product = ProductBL.getProduct(productId);
        if(product != null){
            boolean res =ProductBL.deleteProduct(product);
            if(res){
                removeProductFromList(product);
                updateListView();
                Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
            }else{
                MySupport.openDialog(getActivity(), "Có lỗi xảy ra, vui lòng thử lại");
            }
        }else{
            MySupport.openDialog(getActivity(), "Có lỗi xảy ra, vui lòng thử lại");
        }
    }

    private boolean removeProductFromList(Product product){
        for(Product p : products){
            if(p.getId() == product.getId()){
                products.remove(p);
                return true;
            }
        }
        return false;
    }

    private void addSpinnerSortBy(View view) {
        Spinner spSortBy = view.findViewById(R.id.spSortBy);
        sortBies = getSortBies();
        ArrayAdapter<String> sortByAdapter =
                new ArrayAdapter<>(
                        getActivity(),
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
        products = ProductBL.getListSortBy(brandId, categoryId);
        updateListView();
    }

    private void addSpinnerCategory(View view) {
        spCategory = view.findViewById(R.id.spCategory);
        categories = getCategories();

        for(Category category : categories){
            Log.e("cate: ",category.getId() + "-"+ category.getName());
        }

        ArrayAdapter<Category> categoryArrayAdapter =
                new ArrayAdapter<>(
                        getActivity(),
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
                        getActivity(),
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
            Product product = ProductBL.getProduct(productId);
            int indexChange = checkProducts(product);
            if( indexChange == -1){
                products.add(product);
            }else{
                products.set(indexChange, product);
            }
            productAdapter.setTempObjects(products);
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

    private void updateListView(){
        productAdapter = new ProductAdapter(getActivity(), R.layout.item_product, products);
        lvProducts.setAdapter(productAdapter);
    }

    private List<Brand> getBrands(){
//        BrandBL.createBrand(new Brand(0, "Chọn hãng..."));
//        BrandBL.createBrand(new Brand(0, "Apple"));
//        BrandBL.createBrand(new Brand(0, "SamSung"));
//        BrandBL.createBrand(new Brand(0, "Oppo"));
        return BrandBL.getAllBrand();
    }

    private List<Category> getCategories(){

//        CategoryBL.createCategory(new Category(0, "Chọn loại sản phẩm"));
//        CategoryBL.createCategory(new Category(1, "Phone"));
//        CategoryBL.createCategory(new Category(2, "Laptop"));
//        CategoryBL.createCategory(new Category(3, "Tablet"));
        return CategoryBL.getAllCategory();
    }

    public List<String> getSortBies() {
        List<String> sortBies = new ArrayList<>();
        sortBies.add("Ngày tạo");
        sortBies.add("Tên sản phẩm");
        sortBies.add("Giá sản phẩm");
        return sortBies;
    }
}
