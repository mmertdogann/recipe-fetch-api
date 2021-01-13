package com.mendix.recipe.fetch.api.service;

import com.mendix.recipe.fetch.api.exception.CategoryIsNotExistException;
import com.mendix.recipe.fetch.api.exception.RecipeValidationException;
import com.mendix.recipe.fetch.api.exception.TitleAlreadyExistsException;
import com.mendix.recipe.fetch.api.helper.XmlHelper;
import com.mendix.recipe.fetch.api.model.Direction;
import com.mendix.recipe.fetch.api.model.Ingredient;
import com.mendix.recipe.fetch.api.model.Recipe;
import com.mendix.recipe.fetch.api.model.response.CategoryResponse;
import com.mendix.recipe.fetch.api.model.response.RecipeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeFetchService {

    private static final Logger logger = LoggerFactory.getLogger(RecipeFetchService.class);

    public RecipeResponse getAllRecipes() {

        return new RecipeResponse(XmlHelper.instance.getRecipes().stream()
                .sorted(Comparator.comparing(Recipe::getTitle))
                .collect(Collectors.toList()));
    }

    public CategoryResponse getAllCategories() {
        List<Recipe> recipes = XmlHelper.instance.getRecipes();
        Set<String> categories = new HashSet<>();

        for (Recipe recipe : recipes) {
            categories.addAll(recipe.getCategories());
        }

        List<String> tempList = new ArrayList<>(categories);
        Collections.sort(tempList);

        return new CategoryResponse(tempList);
    }

    public RecipeResponse getRecipesByCategory(String category) throws CategoryIsNotExistException {
        List<Recipe> recipes = XmlHelper.instance.getRecipes();
        List<Recipe> filteredRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            if (recipe.getCategories()
                    .stream().anyMatch(c -> c.equals(category))) {
                filteredRecipes.add(recipe);
            }
        }

        if (filteredRecipes.isEmpty()) {
            throw new CategoryIsNotExistException("No such category");
        }

        return new RecipeResponse(filteredRecipes);
    }

    public RecipeResponse getRecipeByParams(String title, String category, String ingredients) {
        List<Recipe> recipes = XmlHelper.instance.getRecipes();
        List<Recipe> filteredRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            if (Objects.nonNull(title) && !recipe.getTitle().equals(title)) {
                continue;
            }

            if (Objects.nonNull(category) && recipe.getCategories()
                    .stream().noneMatch(c -> c.equals(category))) {
                continue;
            }

            if (Objects.nonNull(ingredients) && recipe.getIngredients()
                    .stream().noneMatch(c -> c.getItem().contains(ingredients))) {
                continue;
            }

            filteredRecipes.add(recipe);
        }

        return new RecipeResponse(filteredRecipes);
    }

    public void createRecipe(Recipe recipe) throws TitleAlreadyExistsException, RecipeValidationException {
        if (isRecipeValid(recipe)) {
            List<Recipe> recipes = XmlHelper.instance.getRecipes();
            recipes.add(recipe);

            List<Recipe> sortedRecipes = recipes.stream()
                    .sorted(Comparator.comparing(Recipe::getTitle))
                    .collect(Collectors.toList());

            XmlHelper.instance.setRecipes(sortedRecipes);

            logger.info("A new recipe has been created.");
        }
    }

    private boolean isRecipeValid(Recipe recipe) throws TitleAlreadyExistsException, RecipeValidationException {
        String title = recipe.getTitle();
        String yield = recipe.getYield();
        List<Ingredient> ingredients = recipe.getIngredients();
        Set<String> categories = recipe.getCategories();
        Direction directions = recipe.getDirections();
        String message;

        //Checks if recipe has no title
        if (Objects.isNull(title) || title.isEmpty()) {
            message = "Recipe has no title.";
            logger.info(message);
            throw new RecipeValidationException(message);
        }

        //Checks if recipe title exists
        for (Recipe r : XmlHelper.instance.getRecipes()) {
            if (r.getTitle().equals(title)) {
                message = "Recipe title already exists.";
                logger.info(message);
                throw new TitleAlreadyExistsException(message);
            }
        }

        //Checks if yield exists
        if (Objects.isNull(yield) || yield.isEmpty()) {
            message = "Recipe has no yield.";
            logger.info(message);
            throw new RecipeValidationException(message);
        }

        //Sanitizes empty category input string
        if (Objects.nonNull(categories)) {
            categories.remove("");
        }

        //Checks if recipe has category
        if (Objects.isNull(categories) || categories.isEmpty()) {
            message = "A recipe must have at least one category.";
            logger.info(message);
            throw new RecipeValidationException(message);
        }

        //Sanitizes empty ingredient object
        if (Objects.nonNull(ingredients)) {
            ingredients.removeIf(ing -> (Objects.nonNull(ing.getItem()) && ing.getItem().isEmpty()) || Objects.isNull(ing.getItem()));
        }

        //Checks if recipe has ingredient
        if (Objects.isNull(ingredients) || ingredients.isEmpty()) {
            message = "A recipe must have at least one ingredient.";
            logger.info(message);
            throw new RecipeValidationException(message);
        }

        //Checks if recipe has direction
        if (Objects.isNull(directions) || Objects.isNull(directions.getStep()) || directions.getStep().isEmpty()) {
            message = "Recipe needs to have a direction.";
            logger.info(message);
            throw new RecipeValidationException(message);
        }

        return true;
    }
}