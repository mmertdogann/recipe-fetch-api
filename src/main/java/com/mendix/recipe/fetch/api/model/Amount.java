package com.mendix.recipe.fetch.api.model;

public class Amount {
    private String quantity;
    private String unit;

    public Amount() {

    }

    public Amount(String quantity, String unit) {
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "quantity='" + quantity + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
