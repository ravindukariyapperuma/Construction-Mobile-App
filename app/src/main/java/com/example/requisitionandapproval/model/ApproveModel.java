package com.example.requisitionandapproval.model;

public class ApproveModel {

    private String ITName;
    private boolean expanded;
    private String qty;
    private String price;

    public ApproveModel(String ITName,String qty,String price) {
        this.ITName = ITName;
        this.qty = qty;
        this.price = price;
        this.expanded = false;
    }

    public String getITName() {
        return ITName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setITName(String ITName) {
        this.ITName = ITName;
    }
    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
