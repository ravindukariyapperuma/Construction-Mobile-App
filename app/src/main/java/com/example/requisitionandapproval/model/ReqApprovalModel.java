package com.example.requisitionandapproval.model;

import java.util.ArrayList;

public class ReqApprovalModel {
    private String reqID;
    private String itemID;
    private String itemPrice;
    private String itemQty;

    public ReqApprovalModel(String reqID, String itemID, String itemPrice, String itemQty) {
        this.reqID = reqID;
        this.itemID = itemID;
        this.itemPrice = itemPrice;
        this.itemQty = itemQty;
    }

    public String getReqID() {
        return reqID;
    }

    public String getItemID() {
        return itemID;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemQty() {
        return itemQty;
    }
}
