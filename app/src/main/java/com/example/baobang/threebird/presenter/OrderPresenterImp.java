package com.example.baobang.threebird.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.ProductOrder;
import com.example.baobang.threebird.model.helper.OrderHelper;
import com.example.baobang.threebird.model.helper.ProductHelper;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.OrderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baobang on 1/8/18.
 */

public class OrderPresenterImp implements OrderPresenter{

    private OrderView orderView;

    public OrderPresenterImp(OrderView orderView) {
        this.orderView = orderView;
    }

    @Override
    public void init() {
        orderView.addControls();
        orderView.addEvents();
        orderView.showSpinnerCommune(getCommunes());
        orderView.showSpinnerDistrict(getDistricts());
        orderView.showSpinnerPayment(getPayments());
        orderView.showSpinnerStatus(getStatuses());
        orderView.showSpinnerProvince(getProvinces());
    }

    @Override
    public Order getOrderFromBundle(Bundle bundle) {
        int orderId = bundle != null ? bundle.getInt(Constants.ORDER) : -1;
        if(orderId == -1)
            return null;
        return OrderHelper.getOrder(orderId);
    }

    @Override
    public int getOptionFromBundle(Bundle bundle) {
        return bundle == null ? 0 : bundle.getInt(Constants.OPTION);
    }

    @Override
    public List<ProductOrder> getProductListFromOrder(Order order) {
        List<ProductOrder>  list = new ArrayList<>();
        list.addAll(order.getProducts());
        return list;
    }

    @Override
    public Bitmap getImageFromProduct(Activity activity, Product product) {
        if(product.getImages().size() == 0){
            return BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
        }else{
            return Utils.StringToBitMap(product.getImages().first());
        }
    }

    @Override
    public int getAmountProduct(List<ProductOrder> productList, int id) {
        int total = 1;
        for(ProductOrder productOrder : productList){
            if(productOrder.getProductId() == id){
                total = productOrder.getAmount();
            }
        }
        return total;
    }

    @Override
    public int getAmountAllProduct(List<ProductOrder> productList) {
        int total = 0;
        for(ProductOrder productOrder : productList){
            total += productOrder.getAmount();
        }
        return total;
    }

    @Override
    public List<ProductOrder> getProductFromList(List<ProductOrder> productList, Product product) {
        int index = checkProduct(productList, product);
        if(index != -1){
            productList.get(index).setAmount(productList.get(index).getAmount() + 1);
        }else{
            productList.add(new ProductOrder(product.getId(), 1));
        }
        return  productList;
    }

    @Override
    public int checkProduct(List<ProductOrder> productList, Product product) {
        for(int i = 0; i < productList.size(); i ++){
            if(productList.get(i).getProductId() == product.getId()){
                return i;
            }
        }
        return -1;
    }

    @Override
    public Product checkInventory(List<ProductOrder> productList) {
        for(ProductOrder productOrder : productList){
            Product product = ProductHelper.getProduct(productOrder.getProductId());
            if(product.getInvetory()  < productOrder.getAmount()){
                return product;
            }
        }
        return null;
    }

    @Override
    public void orderCompetition(List<ProductOrder> productList) {
        for(ProductOrder productOrder : productList){
            Product product = ProductHelper.getProduct(productOrder.getProductId());
            product.setInvetory(product.getInvetory() - productOrder.getAmount());
            ProductHelper.updateProduct(product);
        }
    }

    private ArrayList<String> getProvinces(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Tỉnh/Thành Phố...");
        list.add("An Giang");
        list.add("Bình Phước");
        list.add("Ninh Bình");
        list.add("Hồ Chí Minh");
        list.add("Hà Nội");
        list.add("Đà Nẵng");
        return list;
    }
    private ArrayList<String> getDistricts(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Quận/Huyện...");
        list.add("Ba Đình");
        list.add("Quận 1");
        list.add("Quận 9");
        list.add("Cầu Giấy");
        list.add("Quận Thủ Đức");
        list.add("Quận 3");
        return list;
    }
    private ArrayList<String> getCommunes(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Phường/Xã...");
        list.add("Xã 1");
        list.add("Phường 3");
        list.add("Phường Tăng Nhơn Phú A");
        list.add("Phường 5");
        return list;
    }
    private ArrayList<String> getStatuses(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Trạng thái");
        list.add("Hoàn thành");
        list.add("Hủy");
        list.add("Đang giao hàng");
        return list;
    }
    private ArrayList<String> getPayments(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Hình thức thanh toán");
        list.add("Tiền mặt");
        list.add("Chuyển khoản");
        list.add("Trả góp");
        return list;
    }
}
