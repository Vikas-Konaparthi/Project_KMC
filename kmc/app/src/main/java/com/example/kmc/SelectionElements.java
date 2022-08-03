package com.example.kmc;

public class SelectionElements {
    String aadhar;
    String approvedAmount;
    String status;
    String dbAccountAmount;
    String benAmount;
    String creditedToDB;

    public SelectionElements(String aadhar, String approvedAmount,String status,String dbAccountAmount,String benAmount,String creditedToDB) {
        this.aadhar = aadhar;
        this.approvedAmount = approvedAmount;
        this.dbAccountAmount=dbAccountAmount;
        this.status=status;
        this.benAmount=benAmount;
        this.creditedToDB=creditedToDB;
    }

    public String getCreditedToDB() {
        return creditedToDB;
    }

    public void setCreditedToDB(String creditedToDB) {
        this.creditedToDB = creditedToDB;
    }

    public String getDbAccountAmount() {
        return dbAccountAmount;
    }

    public String getBenAmount() {
        return benAmount;
    }

    public void setBenAmount(String benAmount) {
        this.benAmount = benAmount;
    }

    public void setDbAccountAmount(String dbAccountAmount) {
        this.dbAccountAmount = dbAccountAmount;
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
