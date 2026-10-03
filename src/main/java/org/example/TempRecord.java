package org.example;



public class TempRecord {
    private int id;
    private double inputValue;
    private String inputUnit;
    private double convertedValue;
    private String convertedUnit;

    public TempRecord(int id, double inputValue, String inputUnit, double convertedValue, String convertedUnit) {
        this.id = id;
        this.inputValue = inputValue;
        this.inputUnit = inputUnit;
        this.convertedValue = convertedValue;
        this.convertedUnit = convertedUnit;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getInputValue() {
        return inputValue;
    }

    public void setInputValue(double inputValue) {
        this.inputValue = inputValue;
    }

    public String getInputUnit() {
        return inputUnit;
    }

    public void setInputUnit(String inputUnit) {
        this.inputUnit = inputUnit;
    }

    public double getConvertedValue() {
        return convertedValue;
    }

    public void setConvertedValue(double convertedValue) {
        this.convertedValue = convertedValue;
    }

    public String getConvertedUnit() {
        return convertedUnit;
    }

    public void setConvertedUnit(String convertedUnit) {
        this.convertedUnit = convertedUnit;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s = %.2f %s", inputValue, inputUnit, convertedValue, convertedUnit);
    }
}

