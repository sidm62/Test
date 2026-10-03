package org.example;

public class TemperatureUnit {
    private int id;
    private String unitCode;
    private String unitName;

    public TemperatureUnit(int id, String unitCode, String unitName) {
        this.id = id;
        this.unitCode = unitCode;
        this.unitName = unitName;
    }

    public int getId() {
        return id;
    }
    public String getUnitCode() {
        return unitCode;
    }
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public String toString() {
        return unitName != null ? unitName : unitCode;
    }

}
