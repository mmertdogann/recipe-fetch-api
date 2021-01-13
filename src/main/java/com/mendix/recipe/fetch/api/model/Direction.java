package com.mendix.recipe.fetch.api.model;

public class Direction {
    private String step;

    public Direction() {

    }

    public Direction(String step) {
        this.step = step;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "Direction{" +
                "step=" + step +
                '}';
    }
}
