package com.example.requisitionandapproval.model;

public class getOrderedItemList {


    private String reqID;
    private String itemDescription;
    private String itemQty;
    private String itemPrice;


    public String getItemID() {
        return reqID;
    }

    public String getItem_Description() {
        return itemDescription;
    }

    public String getItem_Quantity() {
        return itemQty;
    }

    public String getItem_AgreedPrice() {
        return itemPrice;
    }

}
