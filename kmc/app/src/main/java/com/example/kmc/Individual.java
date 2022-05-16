package com.example.kmc;

import com.google.android.material.textfield.TextInputLayout;
public class Individual {

    String name;
    String fatherName;
    String age;
    String houseNo;
    String aadhar;
    String phoneNo;
    String preferredUnit;
    String bankName;
    String bankAccNo;
    String psUpload;
    String spApproved;
    String village;
    String mandal;
    String district;


    public Individual(){}

    public String getPsUpload() {
        return psUpload;
    }

    public void setPsUpload(String psUpload) {
        this.psUpload = psUpload;
    }

    public Individual(String name, String fatherName, String age, String houseNo, String aadhar, String phoneNo, String preferredUnit, String bankName, String bankAccNo, String psPDF, String spApproved, String village, String mandal, String district) {
        this.name = name;
        this.fatherName = fatherName;
        this.age = age;
        this.houseNo = houseNo;
        this.aadhar = aadhar;
        this.phoneNo = phoneNo;
        this.preferredUnit = preferredUnit;
        this.bankName = bankName;
        this.bankAccNo = bankAccNo;
        this.psUpload = psPDF;
        this.spApproved=spApproved;
        this.village=village;
        this.mandal=mandal;
        this.district=district;

    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getMandal() {
        return mandal;
    }

    public void setMandal(String mandal) {
        this.mandal = mandal;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSpApproved() {
        return spApproved;
    }

    public void setSpApproved(String spApproved) {
        this.spApproved = spApproved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPreferredUnit() {
        return preferredUnit;
    }

    public void setPreferredUnit(String preferredUnit) {
        this.preferredUnit = preferredUnit;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccNo() {
        return bankAccNo;
    }

    public void setBankAccNo(String bankAccNo) {
        this.bankAccNo = bankAccNo;
    }
}
