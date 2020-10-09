package com.example.requisitionandapproval.model;

public class GetReqDetailsByID {

    private String username;
    private String Item_Description;
    private String Item_Quantity;
    private String Item_AgreedPrice;



    public String getUserName() {
        return username;
    }

    public String getDes() {
        return Item_Description;
    }

    public String getQty() {
        return Item_Quantity;
    }

    public String getPrice() {
        return Item_AgreedPrice;
    }
}
