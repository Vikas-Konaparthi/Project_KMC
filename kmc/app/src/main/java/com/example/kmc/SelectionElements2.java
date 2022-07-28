package com.example.kmc;

public class SelectionElements2 {
    String aadhar;
    String dbAccount;
    String approvalAmount;
    String soApprovalAmount;
    String status;
    String benAccountAmount;

    public SelectionElements2(String aadhar, String dbAccount, String approvalAmount,String soApprovalAmount,String status,String benAccountAmount) {
        this.aadhar = aadhar;
        this.dbAccount = dbAccount;
        this.approvalAmount = approvalAmount;
        this.soApprovalAmount=soApprovalAmount;
        this.status=status;
        this.benAccountAmount=benAccountAmount;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getSoApprovalAmount() {
        return soApprovalAmount;
    }

    public void setSoApprovalAmount(String soApprovalAmount) {
        this.soApprovalAmount = soApprovalAmount;
    }

    public String getStatus() {
        return status;
    }

    public String getBenAccountAmount() {
        return benAccountAmount;
    }

    public void setBenAccountAmount(String benAccountAmount) {
        this.benAccountAmount = benAccountAmount;
    }

    public void setStatus(String status) {
        this.status = status;
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
