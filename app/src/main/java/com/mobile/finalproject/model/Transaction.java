package com.mobile.finalproject.model;

public class Transaction {
    private String itemID, pname, price, transactionQty, subTotal;



    public Transaction() {
    }

    public Transaction(String pid, String pname, String price, String quantity, String subTotal) {
        this.itemID = pid;
        this.pname = pname;
        this.price = price;
        this.transactionQty = quantity;
        this.subTotal = subTotal;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
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

    public String getTransactionQty() {
        return transactionQty;
    }

    public void setTransactionQty(String quantity) {
        this.transactionQty = quantity;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String sutTotal) {
        this.subTotal = sutTotal;
    }
}
