package com.example.kmc;

public class SPOfficer {
    String aadhar;
    String name;
    String password;
    String village1;
    String village2;

    SPOfficer(){}
    SPOfficer(String aadhar,String name,String password,String village1,String village2){
        this.aadhar=aadhar;
        this.name=name;
        this.password=password;
        this.village1=village1;
        this.village2=village2;
    }

    public String getAadhar() {
        return aadhar;
    }

    public String getPassword() {
        return password;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVillage1() {
        return village1;
    }

    public void setVillage1(String village1) {
        this.village1 = village1;
    }

    public String getVillage2() {
        return village2;
    }

    public void setVillage2(String village2) {
        this.village2 = village2;
    }
}
