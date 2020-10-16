package com.example.requisitionandapproval.model;

public class SupplierOrderMdel {
    private String ItName ;
    private String qty;
    private String price;
    private Integer editbtn;
    private Integer deleteBtn;

    public SupplierOrderMdel(String itName, String qty, String price) {
        ItName = itName;
        this.qty = qty;
        this.price = price;
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
