package com.example.kmc;

public class SelectionElements {
    String aadhar;
    String approvedAmount;

    public SelectionElements(String aadhar, String approvedAmount) {
        this.aadhar = aadhar;
        this.approvedAmount = approvedAmount;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(String approvedAmount) {
        this.approvedAmount = approvedAmount;
    }
}
