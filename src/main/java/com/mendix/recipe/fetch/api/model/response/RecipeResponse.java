package com.mendix.recipe.fetch.api.model.response;

import com.mendix.recipe.fetch.api.model.Recipe;

import java.util.List;

public class RecipeResponse {
    private List<Recipe> recipeList;

    public RecipeResponse() {

    }

    public RecipeResponse(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "response=" + recipeList +
                '}';
    }
}
