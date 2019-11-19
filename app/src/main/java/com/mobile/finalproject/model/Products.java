package com.mobile.finalproject.model;

public class Products {

    private String name;
    private String description;
    private long price;
    private long quantity;
    private String fileLocation;
    private String Category;
    private long itemID;
    private String reservationStart;
    private String reservationEnd;

    public Products()
    {

    }

    public Products(String name, String description, long price, long quantity, String fileLocation, String category, long itemID, String reservationStart, String reservationEnd) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.fileLocation = fileLocation;
        this.Category = category;
        this.itemID = itemID;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        this.Category = category;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public String getReservationStart() {
        return reservationStart;
    }

    public void setReservationStart(String reservationStart) {
        this.reservationStart = reservationStart;
    }

    public String getReservationEnd() {
        return reservationEnd;
    }

    public void setReservationEnd(String reservationEnd) {
        this.reservationEnd = reservationEnd;
    }

    public String getJsonObject(){
        return "{ItemId:"+itemID+",name"+name+",price:"+price+",quantity:"+quantity+",Category:"+Category+
                ",description:"+description+",fileLocation:"+fileLocation+
                ",reservationStart:"+reservationStart+",reservationEnd:"+reservationEnd+"}";
    }
}
