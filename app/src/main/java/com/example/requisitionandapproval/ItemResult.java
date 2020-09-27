package com.example.requisitionandapproval;

import com.google.gson.annotations.SerializedName;

public class ItemResult {
    private String username;
    private String ItemID;
    private String Item_Description;
    private String Item_Quantity;
    private String Item_AgreedPrice;

    public String getUsername() {
        return username;
    }

    public String getItemID() {
        return ItemID;
    }

    public String getItem_Description() {
        return Item_Description;
    }

    public String getItem_Quantity() {
        return Item_Quantity;
    }

    public String getItem_AgreedPrice() {
        return Item_AgreedPrice;
    }


}
