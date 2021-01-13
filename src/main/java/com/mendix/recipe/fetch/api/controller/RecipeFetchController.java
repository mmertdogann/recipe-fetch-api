package com.mendix.recipe.fetch.api.controller;

import com.mendix.recipe.fetch.api.exception.CategoryIsNotExistException;
import com.mendix.recipe.fetch.api.exception.RecipeValidationException;
import com.mendix.recipe.fetch.api.exception.TitleAlreadyExistsException;
import com.mendix.recipe.fetch.api.model.Recipe;
import com.mendix.recipe.fetch.api.model.response.CategoryResponse;
import com.mendix.recipe.fetch.api.model.response.RecipeResponse;
import com.mendix.recipe.fetch.api.service.RecipeFetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
public class RecipeFetchController {

    private final RecipeFetchService recipeFetchService;

    @Autowired
    public RecipeFetchController(RecipeFetchService recipeFetchService) {
        this.recipeFetchService = recipeFetchService;
    }

    @GetMapping()
    public RecipeResponse getAllRecipes() {
        return recipeFetchService.getAllRecipes();
    }

    @GetMapping("/categories")
    public CategoryResponse getAllCategories() {
        return recipeFetchService.getAllCategories();
    }

    @GetMapping("/category/{category}")
    public RecipeResponse getRecipesByCategory(@PathVariable String category) throws CategoryIsNotExistException {
        return recipeFetchService.getRecipesByCategory(category);
    }

    @GetMapping(value = "/filter")
    public RecipeResponse getRecipeByParams(@RequestParam(value = "title", required = false) String title,
                                          @RequestParam(value = "category", required = false) String category,
                                          @RequestParam(value = "ingredients", required = false) String ingredients) {
        return recipeFetchService.getRecipeByParams(title, category, ingredients);
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public void createRecipe(@RequestBody Recipe recipe) throws TitleAlreadyExistsException, RecipeValidationException {
        recipeFetchService.createRecipe(recipe);
    }
}