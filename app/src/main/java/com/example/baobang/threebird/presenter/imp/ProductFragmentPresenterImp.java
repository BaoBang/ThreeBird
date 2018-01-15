package com.example.baobang.threebird.presenter.imp;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.example.baobang.threebird.listener.OnItemRecyclerViewClickListener;
import com.example.baobang.threebird.model.Brand;
import com.example.baobang.threebird.model.Category;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.helper.BrandHelper;
import com.example.baobang.threebird.model.helper.CategoryHelper;
import com.example.baobang.threebird.model.helper.OrderHelper;
import com.example.baobang.threebird.model.helper.ProductHelper;
import com.example.baobang.threebird.presenter.ProductFragmentPresenter;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.ProductFragmentView;

import java.util.ArrayList;

public class ProductFragmentPresenterImp implements ProductFragmentPresenter{

    private ProductFragmentView productFragmentView;

    public ProductFragmentPresenterImp(ProductFragmentView productFragmentView) {
        this.productFragmentView = productFragmentView;
    }

    @Override
    public void init(View view) {
        productFragmentView.addToolBar(view);
        productFragmentView.addControls(view);
        productFragmentView.showRecyclerViewProduct(ProductHelper.getAllProduct());
        productFragmentView.addSpinnerCategory(view, CategoryHelper.getAllCategory());
        productFragmentView.addSpinnerBrand(view, BrandHelper.getAllBrand());
        productFragmentView.addSpinnerSortBy(view);
    }

    @Override
    public int checkClients(ArrayList<Product> products, Product product) {
        for(int i = 0; i < products.size(); i++){
            if(product.getId() == products.get(i).getId()){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void deleteProduct(Activity activity, ArrayList<Product> products, int productId, OnItemRecyclerViewClickListener onItemRecyclerViewClickListener) {
        final Product product = ProductHelper.getProduct(productId);

        if(product != null && product.getInvetory() > 0){
            productFragmentView.showMessage("Sản phẩm " + product.getName() +" hiện còn " + product.getInvetory() + " sản phẩm, không thể xóa");
            return;
        }

        if(product != null && OrderHelper.checkProductOrdered(product.getId())){
            productFragmentView.showMessage("Sản phẩm đã được đặt hàng, không thể xóa");
            return;
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setCancelable(false);
        dialog.setTitle("Thông báo!");
        if(product != null)
            dialog.setMessage("Bạn có muốn xóa sản phẩm " +  product.getName());

        dialog.setPositiveButton("Đồng ý", (dialog12, id) -> {
            boolean res = ProductHelper.deleteProduct(product);
            if(res){
                removeProductFromList(products, product);
                productFragmentView.updateRecyclerView(products);
                Toast.makeText(activity, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }else{
                Utils.openDialog(activity, "Có lỗi xảy ra, vui lòng thử lại");
            }
        }).setNegativeButton("Hủy ", (dialog1, which) -> {
        });
        final AlertDialog alert = dialog.create();
        alert.show();
    }

    @Override
    public void doSort(Brand brand, Category category) {
        int brandId = -1;
        if(brand != null){
            brandId = brand.getId();
        }
        int categoryId = -1;
        if(category != null){
            categoryId = category.getId();
        }
        ArrayList<Product> products = ProductHelper.getListSortBy(brandId, categoryId);
        productFragmentView.updateRecyclerView(products);
    }

    @Override
    public void addProduct(ArrayList<Product> products, int productId, OnItemRecyclerViewClickListener onItemRecyclerViewClickListener) {
        Product product = ProductHelper.getProduct(productId);
        int indexChange = checkClients(products, product);
        if( indexChange == -1){
            products.add(product);
        }else{
            products.set(indexChange, product);
        }
        productFragmentView.updateRecyclerView(products);
    }

    private void removeProductFromList(ArrayList<Product> products, Product product){
        for(Product p : products){
            if(p.getId() == product.getId()){
                products.remove(p);
                return;
            }
        }
    }
}
