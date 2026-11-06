package org.example;

public class UnitDTO {
    private int id;
    private String unitCode;
    private String name;
    private String description;

    public UnitDTO(int id, String unitCode, String name, String description) {
        this.id = id;
        this.unitCode = unitCode;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return name;
    }
}