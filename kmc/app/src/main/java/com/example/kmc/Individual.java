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
    String approvalAmount;
    String bankIFSC;
    String vendorName;
    String vendorAccountNo;
    String vendorIFSC;
    String vendorAgency;
    String vendorBankName;
    String groundingStatus;
    String grounding_img;
    String individualAmountRequired;
    String spApproved2;
    String spAmountApproved;
    String psRequestedAmountToBeneficiary;
    String quotationImage;
    String DbBankName;
    String dbBankAccNo;
    String DbBankIFSC;
    String spApproved3;
    String soApproved;
    String so_quotation_amount;
    String ctrApproved2;
    String psApproved;
    String psApproved2;
    String psApproved3;
    String occupation;
    String rationcardnumber;
    String option_selected;

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

    public Individual(String option_selected, String grounding_img,String vendorName,String vendorAccountNo,String vendorIFSC,String groundingStatus,String bankIFSC,String approvalAmount,String secOfficerApproved,String name, String fatherName, String age, String houseNo, String aadhar, String phoneNo, String preferredUnit, String bankName, String bankAccNo, String psPDF, String spApproved, String village, String mandal, String district,String dbAccount,String sp_remarks,String so_remarks,String ctrApproved,String secOfficerUpload,String status,String individualAmountRequired,String spApproved2,String spAmountApproved,String psRequestedAmountToBeneficiary,String vendorAgency,String vendorBankName,String quotationImage, String DbBankName, String dbBankAccNo, String DbBankIFSC,String spApproved3,String soApproved,String so_quotation_amount,String ctrApproved2,String psApproved, String psApproved2, String psApproved3,String occupation,String rationcardnumber) {
        this.option_selected = option_selected;
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
        this.approvalAmount=approvalAmount;
        this.bankIFSC=bankIFSC;
        this.vendorAccountNo=vendorAccountNo;
        this.vendorIFSC=vendorIFSC;
        this.vendorName=vendorName;
        this.groundingStatus=groundingStatus;
        this.grounding_img=grounding_img;
        this.individualAmountRequired=individualAmountRequired;
        this.spApproved2=spApproved2;
        this.spAmountApproved=spAmountApproved;
        this.psRequestedAmountToBeneficiary=psRequestedAmountToBeneficiary;
        this.vendorAgency=vendorAgency;
        this.vendorBankName=vendorBankName;
        this.quotationImage=quotationImage;
        this.dbBankAccNo=dbBankAccNo;
        this.DbBankName=DbBankName;
        this.DbBankIFSC=DbBankIFSC;
        this.spApproved3=spApproved3;
        this.soApproved=soApproved;
        this.so_quotation_amount=so_quotation_amount;
        this.ctrApproved2=ctrApproved2;
        this.psApproved = psApproved;
        this.psApproved2 = psApproved2;
        this.psApproved3 = psApproved3;
        this.occupation=occupation;
        this.rationcardnumber=rationcardnumber;

    }

    public String getOption_selected() {
        return option_selected;
    }

    public String getPsApproved() {
        return psApproved;
    }

    public void setPsApproved(String psApproved) {
        this.psApproved = psApproved;
    }

    public String getPsApproved2() {
        return psApproved2;
    }

    public void setPsApproved2(String psApproved2) {
        this.psApproved2 = psApproved2;
    }

    public String getPsApproved3() {
        return psApproved3;
    }

    public void setPsApproved3(String psApproved3) {
        this.psApproved3 = psApproved3;
    }

    public String getDbBankAccNo() {
        return dbBankAccNo;
    }

    public void setDbBankAccNo(String dbBankAccNo) {
        this.dbBankAccNo = dbBankAccNo;
    }

    public String getSo_quotation_amount() {
        return so_quotation_amount;
    }

    public String getCtrApproved2() {
        return ctrApproved2;
    }

    public void setCtrApproved2(String ctrApproved2) {
        this.ctrApproved2 = ctrApproved2;
    }

    public void setSo_quotation_amount(String so_quotation_amount) {
        this.so_quotation_amount = so_quotation_amount;
    }

    public String getSpApproved3() {
        return spApproved3;
    }

    public void setSpApproved3(String spApproved3) {
        this.spApproved3 = spApproved3;
    }

    public String getSoApproved() {
        return soApproved;
    }

    public void setSoApproved(String soApproved) {
        this.soApproved = soApproved;
    }

    public String getDbBankName() {
        return DbBankName;
    }

    public void setDbBankName(String dbBankName) {
        DbBankName = dbBankName;
    }

    public String getDbBankIFSC() {
        return DbBankIFSC;
    }

    public void setDbBankIFSC(String dbBankIFSC) {
        DbBankIFSC = dbBankIFSC;
    }


    public String getVendorAgency() {
        return vendorAgency;
    }

    public void setVendorAgency(String vendorAgency) {
        this.vendorAgency = vendorAgency;
    }

    public String getVendorBankName() {
        return vendorBankName;
    }

    public String getQuotationImage() {
        return quotationImage;
    }

    public String getPsRequestedAmountToBeneficiary() {
        return psRequestedAmountToBeneficiary;
    }

    public void setPsRequestedAmountToBeneficiary(String psRequestedAmountToBeneficiary) {
        this.psRequestedAmountToBeneficiary = psRequestedAmountToBeneficiary;
    }

    public void setQuotationImage(String quotationImage) {
        this.quotationImage = quotationImage;
    }

    public void setVendorBankName(String vendorBankName) {
        this.vendorBankName = vendorBankName;
    }

    public String getPsApprovedAmount() {
        return psRequestedAmountToBeneficiary;
    }

    public void setPsApprovedAmount(String psApprovedAmount) {
        this.psRequestedAmountToBeneficiary = psApprovedAmount;
    }

    public String getSpApproved2() {
        return spApproved2;
    }

    public void setSpApproved2(String spApproved2) {
        this.spApproved2 = spApproved2;
    }

    public String getSpAmountApproved() {
        return spAmountApproved;
    }

    public void setSpAmountApproved(String spAmountApproved) {
        this.spAmountApproved = spAmountApproved;
    }

    public String getIndividualAmountRequired() {
        return individualAmountRequired;
    }

    public void setIndividualAmountRequired(String individualAmountRequired) {
        this.individualAmountRequired = individualAmountRequired;
    }

    public String getGrounding_img() {
        return grounding_img;
    }

    public void setGrounding_img(String grounding_img) {
        this.grounding_img = grounding_img;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorAccountNo() {
        return vendorAccountNo;
    }

    public void setVendorAccountNo(String vendorAccountNo) {
        this.vendorAccountNo = vendorAccountNo;
    }

    public String getVendorIFSC() {
        return vendorIFSC;
    }

    public void setVendorIFSC(String vendorIFSC) {
        this.vendorIFSC = vendorIFSC;
    }

    public String getGroundingStatus() {
        return groundingStatus;
    }

    public void setGroundingStatus(String groundingStatus) {
        this.groundingStatus = groundingStatus;
    }

    public String getBankIFSC() {
        return bankIFSC;
    }

    public void setBankIFSC(String bankIFSC) {
        this.bankIFSC = bankIFSC;
    }

    public String getApprovalAmount() {
        return approvalAmount;
    }

    public void setApprovalAmount(String approvalAmount) {
        this.approvalAmount = approvalAmount;
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

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getRationcardnumber() {
        return rationcardnumber;
    }

    public void setRationcardnumber(String rationcardnumber) {
        this.rationcardnumber = rationcardnumber;
    }
}