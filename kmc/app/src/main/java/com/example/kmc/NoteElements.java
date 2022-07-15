package com.example.kmc;

public class NoteElements {
    int totalAmount;
    int noOfBen;
    NoteElements(){}

    public NoteElements(int totalAmount, int noOfBen) {
        this.totalAmount = totalAmount;
        this.noOfBen = noOfBen;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getNoOfBen() {
        return noOfBen;
    }

    public void setNoOfBen(int noOfBen) {
        this.noOfBen = noOfBen;
    }
}
