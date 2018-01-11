package com.example.baobang.threebird.presenter.imp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.baobang.threebird.R;
import com.example.baobang.threebird.model.Address;
import com.example.baobang.threebird.model.Client;
import com.example.baobang.threebird.model.Commune;
import com.example.baobang.threebird.model.District;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.Payment;
import com.example.baobang.threebird.model.Product;
import com.example.baobang.threebird.model.ProductOrder;
import com.example.baobang.threebird.model.Province;
import com.example.baobang.threebird.model.Status;
import com.example.baobang.threebird.model.helper.ClientHelper;
import com.example.baobang.threebird.model.helper.CommuneHelper;
import com.example.baobang.threebird.model.helper.DistrictHelper;
import com.example.baobang.threebird.model.helper.OrderHelper;
import com.example.baobang.threebird.model.helper.PaymentHelper;
import com.example.baobang.threebird.model.helper.ProductHelper;
import com.example.baobang.threebird.model.helper.ProvinceHelper;
import com.example.baobang.threebird.model.helper.StatusHelper;
import com.example.baobang.threebird.presenter.OrderPresenter;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.Utils;
import com.example.baobang.threebird.view.OrderView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.RealmList;

/**
 * Created by baobang on 1/8/18.
 */

public class OrderPresenterImp implements OrderPresenter {

    private OrderView orderView;

    public OrderPresenterImp(OrderView orderView) {
        this.orderView = orderView;
    }

    @Override
    public void init() {
        orderView.addControls();
        orderView.addEvents();
    }

    @Override
    public void loadSpinnerData() {
        orderView.showSpinnerCommune(getCommunes());
        orderView.showSpinnerDistrict(getDistricts());
        orderView.showSpinnerPayment(getPayments());
        orderView.showSpinnerStatus(getStatuses());
        orderView.showSpinnerProvince(getProvinces());
    }

    @Override
    public void setData(Activity activity,Order order) {

        Client client = ClientHelper.getClient(order.getId());
        Bitmap bitmap;
        if(client != null && client.getAvatar().length() > 0){
            bitmap = Utils.StringToBitMap(client.getAvatar());
        }else{
            bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
        }

        List<ProductOrder>productOrders = new ArrayList<>(order.getProducts());
        int amount = getAmountAllProduct(productOrders);

        int statusPostion = getStatusSelectedPosition(StatusHelper.getAllStatus(), order.getStatus());
        int paymentPosition = getPaymentSelectedPosition(PaymentHelper.getAllPayment(), order.getPayments());

        orderView.setDataForInput(bitmap, order.getClientName(), order.getId(), order.getCreatedAt(),
                statusPostion, order.getPhone(), order.getAddress().getProvinceId(),
                order.getAddress().getDistrictId(), order.getAddress().getCommuneId(),
                order.getAddress().getAddress(), amount, order.getLiveryDate(), paymentPosition);
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

    @Override
    public int getStatusSelectedPosition(ArrayList<Status> statuses, int statusId) {
        int position = 0;
        for(int i = 0; i < statuses.size(); i++){
            if(statuses.get(i).getId() == statusId){
                position = i;
                break;
            }
        }
        return position;
    }

    @Override
    public int getPaymentSelectedPosition(ArrayList<Payment> payments, int paymentId) {
        int position = 0;
        for(int i = 0; i < payments.size(); i++){
            if(payments.get(i).getId() == paymentId){
                position = i;
                break;
            }
        }
       return position;
    }

    @Override
    public void setDistrictSelected(ArrayList<District> districts, int districtId) {
        int position = 0;
        for(int i = 0; i < districts.size(); i++){
            if(districts.get(i).getId() == districtId){
                position = i;
                break;
            }
        }
        orderView.setSpinnerDistrictSelectedPosition(position);
    }

    @Override
    public void setProvinceSelected(ArrayList<Province> provinces, int provinceId) {
        int position = 0;
        for(int i = 0; i < provinces.size(); i++){
            if(provinces.get(i).getId() == provinceId){
                position = i;
                break;
            }
        }
        orderView.setSpinnerProvincePosition(position);
    }

    @Override
    public void setCommuneSelected(ArrayList<Commune> communes, int communeId) {
        int position = 0;
        for(int i = 0; i < communes.size(); i++){
            if(communes.get(i).getId() == communeId){
                position = i;
                break;
            }
        }
        orderView.setSpinnerCommuneSelectedPosition(position);
    }

    @Override
    public void addProductToLayout(List<ProductOrder> productList) {
        for(ProductOrder productOrder : productList){
            Product product = ProductHelper.getProduct(productOrder.getProductId());
            orderView.addProductToLayout(product);
        }
    }

    @Override
    public int addOrder(int clientId, String name, Date date,
                        Status status, String phone, Province province,
                        District district, Commune commune, String address,
                        List<ProductOrder> products, Date deliveryDate, Payment payment) {

        RealmList<ProductOrder> productList = new RealmList<>();
        productList.addAll(products);
        Order order = new Order(0, name, date,
                status.getId(), phone, new Address(
                        province.getId(), district.getId(), commune.getId(), address),
                productList, deliveryDate, payment.getId(), 0);
        order.setClientId(clientId);

        return OrderHelper.createOrder(order);
    }

    @Override
    public int updateOrder(Order order, int clientId, String name,
                           Date date, Status status, String phone,
                           Province province, District district, Commune commune,
                           String address, List<ProductOrder> products,
                           Date deliveryDate, Payment payment) {
        RealmList<ProductOrder> productList = new RealmList<>();
        productList.addAll(products);
        boolean isPayment = order.isPayment();
        order = new Order(order.getId(), name, date,
                status.getId(), phone, new Address(
                province.getId(), district.getId(), commune.getId(), address),
                productList, deliveryDate, payment.getId(), 0);
        order.setClientId(clientId);
        order.setPayment(isPayment);
        return OrderHelper.updateOrder(order);
    }

    @Override
    public void clickAddOptionMenu(Order order,int option, String clientIdStr, String name,
                                   Date date, Status status, String phone,
                                   Province province, District district, Commune commune,
                                   String address, List<ProductOrder> products,
                                   Date deliveryDate, Payment payment) {
        try {

            if(Utils.checkInput(name)){
                orderView.showClientNameWarning("Vui lòng nhập vào tên khách hàng");
                return;
            }
            if(Utils.checkInput(clientIdStr)){
                orderView.showOrderIdWarning("Vui lòng nhập vào số hóa đơn");
                return;
            }
            if(date == null){
                orderView.showMessage("Vui lòng chọn ngày lập hóa đơn");
                return;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi", "VN"));
            if(date.before(simpleDateFormat.getCalendar().getTime())){
                orderView.showMessage("Ngày đặt hàng không hợp lệ");
                return;
            }

            if(status == null){
                orderView.showMessage("Vui lòng chọn trạng thái của hóa đơn");
                return;
            }
            if(Utils.checkInput(phone)){
                orderView.showPhoneWarning("Vui lòng nhâp vào số điện thoại");
                return;
            }
            if(!phone.matches(Constants.PHONE_REGULAR)){
                orderView.showPhoneWarning("Số điện thoại không đúng");
                return;
            }
            if(province == null){
                orderView.showMessage("Vui lòng chọn tỉnh/thành phố");
                return;
            }
            if(district == null){
                orderView.showMessage("Vui lòng chọn quận/huyện");
                return;
            }
            if(commune == null){
                orderView.showMessage("Vui lòng chọn phường/xã");
                return;
            }
            if(Utils.checkInput(address)){
                orderView.showAddressWarning("Vui lòng nhâp vào địa chỉ");
                return;
            }

            if(products.size() == 0){
                orderView.showMessage("Vui lòng chọn sản phẩm");
                return;
            }

            if(deliveryDate == null){
                orderView.showMessage("Vui lòng chọn ngày giao hàng");
                return;
            }

            if(date.after(deliveryDate)){
                orderView.showMessage("Ngày giao hàng không hợp lệ");
                return;
            }
            if(payment == null){
                orderView.showMessage("Vui lòng chọn hình thức thanh toán");
                return;
            }



            int result = -1;
            if(option == Constants.ADD_OPTION){
                result = addOrder(Integer.parseInt(clientIdStr), name,date,
                        status, phone, province, district,
                        commune, address, products, deliveryDate, payment);
            }else if(option == Constants.EDIT_OPTION){
                result = updateOrder(order, Integer.parseInt(clientIdStr), name, date,
                        status, phone, province, district,
                        commune, address, products, deliveryDate, payment);
            }

            if(result != -1){
                Order orderCompleted = OrderHelper.getOrder(result);
                if(order != null && !order.isPayment() && order.getStatus() == Constants.COMPLETED){
                    if(!orderCompleted.isPayment()){
                        orderCompetition(products);
                        orderCompleted.setPayment(true);
                        OrderHelper.updateOrder(orderCompleted);
                    }
                }
                orderView.changeActivity(result);

            }else{
                orderView.showMessage("Đã có lỗi xảy ra, vui lòng thử lại");
            }


        }catch (Exception e){
            orderView.showMessage("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public int setClientData(Activity activity, Client client) {

        Bitmap  bitmap;
        if(client == null){
            bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
            bitmap = Utils.getRoundedRectBitmap(bitmap);
        }else{
            bitmap = Utils.StringToBitMap(client.getAvatar());
        }
        if(client != null)
        orderView.addClientInfoFromListToView(bitmap, client.getName(), client.getPhone(),
                client.getAddress().getProvinceId(), client.getAddress().getDistrictId(),
                client.getAddress().getCommuneId(), client.getAddress().getAddress());

        return client == null ? -1 : client.getId();
    }

    @Override
    public void showDialogClient() {
        orderView.showDialogClient(ClientHelper.getAllClient());
    }

    @Override
    public void showDialogProduct() {
        orderView.showDialogProduct(ProductHelper.getAllProduct());
    }

    private ArrayList<Province> getProvinces(){
        return ProvinceHelper.getAllProvinces();
    }

    private ArrayList<District> getDistricts(){
        return DistrictHelper.getAllDistricts();
    }
    private ArrayList<Commune> getCommunes(){
        return CommuneHelper.getAllCommunes();
    }

    private ArrayList<Status> getStatuses(){

        return StatusHelper.getAllStatus();
    }
    private ArrayList<Payment> getPayments(){

        return PaymentHelper.getAllPayment();
    }
}
