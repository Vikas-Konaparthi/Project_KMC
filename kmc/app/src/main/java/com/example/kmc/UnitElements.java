package com.example.kmc;

public class UnitElements {
    String unitName;
    String unitCount;
    String grounding;

    UnitElements(){}

    public UnitElements(String unitName, String unitCount, String grounding) {
        this.unitName = unitName;
        this.unitCount = unitCount;
        this.grounding = grounding;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(String unitCount) {
        this.unitCount = unitCount;
    }

    public String getGrounding() {
        return grounding;
    }

    public void setGrounding(String grounding) {
        this.grounding = grounding;
    }
}
