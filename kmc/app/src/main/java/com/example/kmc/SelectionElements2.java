package com.example.kmc;

public class SelectionElements2 {
    String aadhar;
    String dbAccount;
    String approvalAmount;

    public SelectionElements2(String aadhar, String dbAccount, String approvalAmount) {
        this.aadhar = aadhar;
        this.dbAccount = dbAccount;
        this.approvalAmount = approvalAmount;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getDbAccount() {
        return dbAccount;
    }

    public void setDbAccount(String dbAccount) {
        this.dbAccount = dbAccount;
    }

    public String getApprovalAmount() {
        return approvalAmount;
    }

    public void setApprovalAmount(String approvalAmount) {
        this.approvalAmount = approvalAmount;
    }
}
