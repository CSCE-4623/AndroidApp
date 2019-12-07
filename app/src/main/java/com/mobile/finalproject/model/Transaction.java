package com.mobile.finalproject.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Transaction {
    private String pname;
    private Long itemID,  transactionQty, subTotal,price;



    public Transaction() {
    }

    public Transaction(Long pid, String pname, Long quantity, Long subTotal) {
        this.itemID = pid;
        this.pname = pname;
        this.transactionQty = quantity;
        this.subTotal = subTotal;
    }



    public Transaction(Long pid, String pname, Long price, Long quantity, Long subTotal) {
        this.itemID = pid;
        this.pname = pname;
        this.price = price;
        this.transactionQty = quantity;
        this.subTotal = subTotal;
    }

    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getTransactionQty() {
        return transactionQty;
    }

    public void setTransactionQty(Long quantity) {
        this.transactionQty = quantity;
    }

    public Long getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Long sutTotal) {
        this.subTotal = sutTotal;
    }
    public void addQty(){transactionQty++;}
    public void subtractQty(){transactionQty--;}

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("pname", pname);
        result.put("price", price);
        result.put("itemID", itemID);
        result.put("transactionQty", transactionQty);
        result.put("subTotal", subTotal);

        return result;
    }
}
