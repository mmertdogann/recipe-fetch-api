package com.mendix.recipe.fetch.api.model;

import java.util.List;
import java.util.Set;

public class Recipe {

    private String title;
    private String yield;
    private Set<String> categories;
    private List<Ingredient> ingredients;
    private Direction directions;

    public Recipe() {

    }

    public Recipe(String title, String yield, Set<String> categories, List<Ingredient> ingredients, Direction directions) {
        this.title = title;
        this.yield = yield;
        this.categories = categories;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYield() {
        return yield;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Direction getDirections() {
        return directions;
    }

    public void setDirections(Direction directions) {
        this.directions = directions;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", yield=" + yield +
                ", categories=" + categories +
                ", ingredients=" + ingredients +
                ", directions=" + directions +
                '}';
    }
}
