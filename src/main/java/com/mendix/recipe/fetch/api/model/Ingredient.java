package com.mendix.recipe.fetch.api.model;

public class Ingredient {
    private String item;
    private Amount amount;

    public Ingredient() {

    }

    public Ingredient(String item, Amount amount) {
        this.item = item;
        this.amount = amount;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "item='" + item + '\'' +
                ", amount=" + amount +
                '}';
    }
}
