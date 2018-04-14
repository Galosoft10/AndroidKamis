package com.example.jose.androidkamis.model;

import java.util.List;

/**
 * Created by josetrinidad on 05/03/18.
 */

public class Request {
    private String phone;
    private String name;
    private String address;
    private String total;
    private List<Order> products;

    public Request() {
    }

    public Request(String phone, String name, String address, String total, List<Order> products) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.products = products;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getProducts() {
        return products;
    }

    public void setProducts(List<Order> products) {
        this.products = products;
    }
}
