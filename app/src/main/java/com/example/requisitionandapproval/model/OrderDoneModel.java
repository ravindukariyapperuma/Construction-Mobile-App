package com.example.requisitionandapproval.model;

public class OrderDoneModel {

    public String reqID;
    public String[] itemDescription;
//    private String itemPrice;
//    private String itemQty;


    public OrderDoneModel(String reqID, String[] itemDescription) {
        this.reqID = reqID;
        this.itemDescription = itemDescription;
    }

    public String getReqID() {
        return reqID;
    }

    public String[] getItemDescription() {
        return itemDescription;
    }

//    public String getItemPrice() {
//        return itemPrice;
//    }
//
//    public String getItemQty() {
//        return itemQty;
//    }
}
