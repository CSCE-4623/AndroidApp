package com.mobile.finalproject.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Transaction {
    private String name,id;
    private Long  quantity, subTotal,price;



    public Transaction() {
    }

    public Transaction(String pid, String name, Long quantity, Long subTotal) {
        this.id = pid;
        this.name = name;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }



    public Transaction(String pid, String name, Long price, Long quantity, Long subTotal) {
        this.id = pid;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Long sutTotal) {
        this.subTotal = sutTotal;
    }
    public void addQty(){
        quantity++;}
    public void subtractQty(){
        quantity--;}

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("price", price);
        result.put("id", id);
        result.put("quantity", quantity);
        result.put("subTotal", subTotal);

        return result;
    }
}
