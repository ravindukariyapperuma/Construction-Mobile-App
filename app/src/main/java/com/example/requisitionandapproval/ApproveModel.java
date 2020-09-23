package com.example.requisitionandapproval;

public class ApproveModel {

    private String ITName;
    private boolean expanded;

    public ApproveModel(String ITName) {
        this.ITName = ITName;
        this.expanded = false;
    }

    public String getITName() {
        return ITName;
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
