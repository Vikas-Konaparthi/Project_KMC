package com.example.kmc;

public class SelectionElements {
    String aadhar;
    String approvedAmount;
    String status;

    public SelectionElements(String aadhar, String approvedAmount,String status) {
        this.aadhar = aadhar;
        this.approvedAmount = approvedAmount;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
