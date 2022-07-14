package com.example.kmc;

public class SPElements {
    String name;
    String village1;
    String village2;
    String pending;
    SPElements(){}
    public SPElements(String name, String village1, String village2, String pending){
        this.name=name;
        this.village1=village1;
        this.village2=village2;
        this.pending=pending;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }
}
