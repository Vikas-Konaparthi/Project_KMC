package com.example.kmc;

public class Vendor {
    String agencyName;
    String vendorName;
    String vendorBankAcc;
    String vendorBankIFSC;
    String vendorBankName;

    public Vendor(){

    }

    public Vendor(String agencyName, String vendorName, String vendorBankAcc, String vendorBankIFSC, String vendorBankName) {
        this.agencyName = agencyName;
        this.vendorName = vendorName;
        this.vendorBankAcc = vendorBankAcc;
        this.vendorBankIFSC = vendorBankIFSC;
        this.vendorBankName = vendorBankName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorBankAcc() {
        return vendorBankAcc;
    }

    public void setVendorBankAcc(String vendorBnakAcc) {
        this.vendorBankAcc = vendorBnakAcc;
    }

    public String getVendorBankIFSC() {
        return vendorBankIFSC;
    }

    public void setVendorBankIFSC(String vendorBankIFSC) {
        this.vendorBankIFSC = vendorBankIFSC;
    }

    public String getVendorBankName() {
        return vendorBankName;
    }

    public void setVendorBankName(String vendorBankName) {
        this.vendorBankName = vendorBankName;
    }
}
