package com.tracks.zrecipes.db;

public class ExtendedIngredient {
    private String name;
    private double amount;
    private String unit;
    private String unitShort;
    private String unitLong;
    private String originalString;
    private String[] metaInformation;

    public ExtendedIngredient(String name, double amount, String unit, String unitShort, String unitLong, String originalString, String[] metaInformation) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.unitShort = unitShort;
        this.unitLong = unitLong;
        this.originalString = originalString;
        this.metaInformation = metaInformation;
    }

   public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitShort() {
        return unitShort;
    }

    public void setUnitShort(String unitShort) {
        this.unitShort = unitShort;
    }

    public String getUnitLong() {
        return unitLong;
    }

    public void setUnitLong(String unitLong) {
        this.unitLong = unitLong;
    }

    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }

    public String[] getMetaInformation() {
        return metaInformation;
    }

    public void setMetaInformation(String[] metaInformation) {
        this.metaInformation = metaInformation;
    }
}
