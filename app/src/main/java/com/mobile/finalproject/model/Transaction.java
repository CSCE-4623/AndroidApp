package com.mobile.finalproject.model;

public class Transaction {
    private String pname, price;
    private Long itemID,  transactionQty, subTotal;



    public Transaction() {
    }

    public Transaction(Long pid, String pname, String price, Long quantity, Long subTotal) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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
}
