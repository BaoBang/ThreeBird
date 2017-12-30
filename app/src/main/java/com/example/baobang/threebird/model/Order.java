package com.example.baobang.threebird.model;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Order extends RealmObject implements Serializable {
    @PrimaryKey
    private int id;
    private String clientName;
    private Date createdAt;
    private int status;
    private String phone;
    private Address address;
    private RealmList<ProductOrder> products = new RealmList<>();
    private Date liveryDate;
    private int payments;
    private int paymentValue;

    public Order(int id, String clientName, Date createdAt, int status, String phone, Address address, RealmList<ProductOrder> products, Date liveryDate, int payments, int paymentValue) {
        this.id = id;
        this.clientName = clientName;
        this.createdAt = createdAt;
        this.status = status;
        this.phone = phone;
        this.address = address;
        this.products = products;
        this.liveryDate = liveryDate;
        this.payments = payments;
        this.paymentValue = paymentValue;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public RealmList<ProductOrder> getProducts() {
        return products;
    }

    public void setProducts(RealmList<ProductOrder> products) {
        this.products = products;
    }

    public Date getLiveryDate() {
        return liveryDate;
    }

    public void setLiveryDate(Date liveryDate) {
        this.liveryDate = liveryDate;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public int getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(int paymentValue) {
        this.paymentValue = paymentValue;
    }

    public long getToal(){
        long total = 0;
        for(ProductOrder productOrder : products){
            total += productOrder.getTotalPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return id + "-" + clientName + "-" + phone;
    }

    public int getAmount(){
        int total = 0;
        for(ProductOrder productOrder : products){
            total += productOrder.getAmount();
        }
        return total;
    }
}
