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


    public Individual(){}

    public String getPsUpload() {
        return psUpload;
    }

    public void setPsUpload(String psUpload) {
        this.psUpload = psUpload;
    }

    public Individual(String name, String fatherName, String age, String houseNo, String aadhar, String phoneNo, String preferredUnit, String bankName, String bankAccNo, String psPDF, String spApproved) {
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
