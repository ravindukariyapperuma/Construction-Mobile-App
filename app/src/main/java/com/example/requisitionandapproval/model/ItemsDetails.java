package com.example.requisitionandapproval.model;

import java.util.ArrayList;

public class ItemsDetails {
    private String itemID;
    private String itemQty;
    private String itemPrice;

    public ItemsDetails(String itemID, String itemQty, String itemPrice) {
        this.itemID = itemID;
        this.itemQty = itemQty;
        this.itemPrice = itemPrice;
    }

    public String getItemID() {
        return itemID;
    }

    public String getItemQty() {
        return itemQty;
    }

    public String getItemPrice() {
        return itemPrice;
    }
}
