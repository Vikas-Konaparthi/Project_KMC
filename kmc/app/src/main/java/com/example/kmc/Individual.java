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
    String dbAccount;
    String sp_remarks;
    String so_remarks;
    String ctrApproved;
    String secOfficerUpload;
    String status;
    String secOfficerApproved;


    public String getSecOfficerUpload() {
        return secOfficerUpload;
    }

    public void setSecOfficerUpload(String secOfficerUpload) {
        this.secOfficerUpload = secOfficerUpload;
    }

    public Individual(){}

    public String getPsUpload() {
        return psUpload;
    }

    public void setPsUpload(String psUpload) {
        this.psUpload = psUpload;
    }

    public Individual(String secOfficerApproved,String name, String fatherName, String age, String houseNo, String aadhar, String phoneNo, String preferredUnit, String bankName, String bankAccNo, String psPDF, String spApproved, String village, String mandal, String district,String dbAccount,String sp_remarks,String so_remarks,String ctrApproved,String secOfficerUpload,String status) {
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
        this.dbAccount=dbAccount;
        this.sp_remarks=sp_remarks;
        this.so_remarks=so_remarks;
        this.ctrApproved=ctrApproved;
        this.secOfficerUpload=secOfficerUpload;
        this.status=status;
        this.secOfficerApproved=secOfficerApproved;

    }

    public String getSecOfficerApproved() {
        return secOfficerApproved;
    }

    public void setSecOfficerApproved(String secOfficerApproved) {
        this.secOfficerApproved = secOfficerApproved;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSp_remarks() {
        return sp_remarks;
    }

    public void setSp_remarks(String sp_remarks) {
        this.sp_remarks = sp_remarks;
    }

    public String getSo_remarks() {
        return so_remarks;
    }

    public void setSo_remarks(String so_remarks) {
        this.so_remarks = so_remarks;
    }

    public String getCtrApproved() {
        return ctrApproved;
    }

    public void setCtrApproved(String ctrApproved) {
        this.ctrApproved = ctrApproved;
    }

    public String getDbAccount() {
        return dbAccount;
    }

    public void setDbAccount(String dbAccount) {
        this.dbAccount = dbAccount;
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
