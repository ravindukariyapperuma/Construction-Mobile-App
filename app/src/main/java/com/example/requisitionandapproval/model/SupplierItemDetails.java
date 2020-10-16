package com.example.requisitionandapproval.model;

public class SupplierItemDetails {

        private String employeeName;
        private String itemDescription;
        private String itemQty;
        private String itemPrice;
        private String requiredDate;
        private String addressline1;
        private String addressline2;
        private String other;


    public String getRequiredDate() {
        return requiredDate;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public String getOther() {
        return other;
    }

    public String getUserName() {
            return employeeName;
        }

        public String getDes() {
            return itemDescription;
        }

        public String getQty() {
            return itemQty;
        }

        public String getPrice() {
            return itemPrice;
        }
}
