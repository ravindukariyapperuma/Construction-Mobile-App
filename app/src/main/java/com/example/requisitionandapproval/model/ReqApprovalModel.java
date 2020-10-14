package com.example.requisitionandapproval.model;

import java.util.ArrayList;

public class ReqApprovalModel {
    private String reqID;
    private String itemDescription;
    private String itemPrice;
    private String itemQty;
    private String status;

    public ReqApprovalModel( String reqID, String itemDescription, String itemPrice, String itemQty) {
        this.reqID = reqID;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemQty = itemQty;
    }

    public String getReqID() {
        return reqID;
    }

    public String getItemID() {
        return itemDescription;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemQty() {
        return itemQty;
    }
    public String getStatus() {
        return status;
    }
}
