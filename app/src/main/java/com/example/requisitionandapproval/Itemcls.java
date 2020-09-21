package com.example.requisitionandapproval;

public class Itemcls {

    private String ItName ;
    private String qty;
    private String price;
    private Integer editbtn;
    private Integer deleteBtn;

    public Itemcls(String itName, String qty, String price, Integer editbtn, Integer deleteBtn) {
        ItName = itName;
        this.qty = qty;
        this.price = price;
        this.editbtn = editbtn;
        this.deleteBtn = deleteBtn;
    }

    public String getItName() {
        return ItName;
    }

    public void setItName(String itName) {
        ItName = itName;
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

    public Integer getEditbtn() {
        return editbtn;
    }

    public void setEditbtn(Integer editbtn) {
        this.editbtn = editbtn;
    }

    public Integer getDeleteBtn() {
        return deleteBtn;
    }

    public void setDeleteBtn(Integer deleteBtn) {
        this.deleteBtn = deleteBtn;
    }
}
