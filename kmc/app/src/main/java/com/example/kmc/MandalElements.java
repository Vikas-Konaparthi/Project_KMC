package com.example.kmc;

public class MandalElements {
    String mandalName;
    String totalRegistered;
    String totalSelected;
    String totalApprovedAmount;
    String dbAccountAmount;
    String grounding;
    String totalDbAmount;
    MandalElements(){}

    public MandalElements(String mandalName, String totalRegistered, String totalSelected, String totalApprovedAmount, String dbAccountAmount, String grounding, String totalDbAmount) {
        this.mandalName = mandalName;
        this.totalRegistered = totalRegistered;
        this.totalSelected = totalSelected;
        this.totalApprovedAmount = totalApprovedAmount;
        this.dbAccountAmount = dbAccountAmount;
        this.grounding = grounding;
        this.totalDbAmount = totalDbAmount;
    }

    public String getTotalDbAmount() {
        return totalDbAmount;
    }

    public String getMandalName() {
        return mandalName;
    }

    public void setMandalName(String mandalName) {
        this.mandalName = mandalName;
    }

    public String getTotalRegistered() {
        return totalRegistered;
    }

    public void setTotalRegistered(String totalRegistered) {
        this.totalRegistered = totalRegistered;
    }

    public String getTotalSelected() {
        return totalSelected;
    }

    public void setTotalSelected(String totalSelected) {
        this.totalSelected = totalSelected;
    }

    public String getTotalApprovedAmount() {
        return totalApprovedAmount;
    }

    public void setTotalApprovedAmount(String totalApprovedAmount) {
        this.totalApprovedAmount = totalApprovedAmount;
    }

    public String getDbAccountAmount() {
        return dbAccountAmount;
    }

    public void setDbAccountAmount(String dbAccountAmount) {
        this.dbAccountAmount = dbAccountAmount;
    }

    public String getGrounding() {
        return grounding;
    }

    public void setGrounding(String grounding) {
        this.grounding = grounding;
    }
}
