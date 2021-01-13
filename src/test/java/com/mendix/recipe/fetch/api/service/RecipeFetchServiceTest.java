package com.mendix.recipe.fetch.api.service;

import com.mendix.recipe.fetch.api.exception.CategoryIsNotExistException;
import com.mendix.recipe.fetch.api.exception.RecipeValidationException;
import com.mendix.recipe.fetch.api.exception.TitleAlreadyExistsException;
import com.mendix.recipe.fetch.api.helper.XmlHelper;
import com.mendix.recipe.fetch.api.model.Amount;
import com.mendix.recipe.fetch.api.model.Direction;
import com.mendix.recipe.fetch.api.model.Ingredient;
import com.mendix.recipe.fetch.api.model.Recipe;
import com.mendix.recipe.fetch.api.model.response.CategoryResponse;
import com.mendix.recipe.fetch.api.model.response.RecipeResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RecipeFetchServiceTest {

    @InjectMocks
    private RecipeFetchService recipeFetchService;

    @Test
    public void it_should_get_all_recipes_without_additional_query_param() {
        //Given
        XmlHelper.instance = new XmlHelper();
        XmlHelper.instance.parseXml();

        //When
        RecipeResponse allRecipes = recipeFetchService.getAllRecipes();

        //Then
        assertThat(allRecipes.getRecipeList()).hasSize(3);
    }

    @Test
    public void it_should_get_all_categories_without_additional_query_param() {
        //Given
        XmlHelper.instance = new XmlHelper();
        XmlHelper.instance.parseXml();

        //When
        CategoryResponse allCategories = recipeFetchService.getAllCategories();

        //Then
        assertThat(allCategories.getCategoryList()).containsExactlyInAnyOrder("Main dish", "Chili", "Liquor", "Cakes", "Cake mixes", "Microwave", "Vegetables");
    }

    @Test
    public void it_should_get_all_recipes_by_category_query_param() throws CategoryIsNotExistException {
        //Given
        String category = "Cakes";

        XmlHelper.instance = new XmlHelper();
        XmlHelper.instance.parseXml();

        //When
        RecipeResponse recipesByCategory = recipeFetchService.getRecipesByCategory(category);

        //Then
        assertThat(recipesByCategory.getRecipeList()).hasSize(1);
    }

    @Test
    public void it_should_filter_recipes_by_specific_query_params() {
        //Given
        String title = "Amaretto Cake";
        String category = "Cakes";
        String ingredients = "Eggs";

        XmlHelper.instance = new XmlHelper();
        XmlHelper.instance.parseXml();

        //When
        RecipeResponse recipeByParams = recipeFetchService.getRecipeByParams(title, category, ingredients);

        //Then
        assertThat(recipeByParams.getRecipeList()).hasSize(1);
    }

    @Test
    public void it_should_create_recipe_by_given_model() throws TitleAlreadyExistsException, RecipeValidationException {
        //Given
        Recipe recipe = new Recipe();
        recipe.setTitle("Fettucine Alfredo");
        recipe.setYield("1");
        recipe.setCategories(Collections.singleton("Pasta"));
        recipe.setIngredients(Arrays.asList(new Ingredient("Black Pepper", new Amount("1", "pinch"))));
        recipe.setDirections(new Direction("In a large pot, heat water over high heat until boiling..."));

        XmlHelper.instance = new XmlHelper();
        XmlHelper.instance.parseXml();

        //When
        recipeFetchService.createRecipe(recipe);
        int size = XmlHelper.instance.getRecipes().size();

        //Then
        assertThat(size).isEqualTo(4);
    }

    @Test(expected = RecipeValidationException.class)
    public void it_should_not_create_recipe_without_title() throws RecipeValidationException, TitleAlreadyExistsException {
        //Given
        Recipe recipe = new Recipe();
        recipe.setYield("1");
        recipe.setCategories(Collections.singleton("Pasta"));
        recipe.setIngredients(Arrays.asList(new Ingredient("Black Pepper", new Amount("1", "pinch"))));
        recipe.setDirections(new Direction("In a large pot, heat water over high heat until boiling..."));

        XmlHelper.instance = new XmlHelper();
        XmlHelper.instance.parseXml();


        //When
        recipeFetchService.createRecipe(recipe);

        //Then catch the exception
    }

    @Test(expected = TitleAlreadyExistsException.class)
    public void it_should_not_create_recipe_if_given_title_exists() throws TitleAlreadyExistsException, RecipeValidationException {
        //Given
        Recipe recipe = new Recipe();
        recipe.setTitle("Amaretto Cake");
        recipe.setYield("1");
        recipe.setCategories(Collections.singleton("Cakes"));
        recipe.setIngredients(Collections.singletonList(new Ingredient("Vanilla instant pudding", new Amount("1", "package"))));
        recipe.setDirections(new Direction("Sprinkle 1 cup almonds into bottom of a well-greased..."));

        XmlHelper.instance = new XmlHelper();
        XmlHelper.instance.parseXml();


        //When
        recipeFetchService.createRecipe(recipe);

        //Then catch the exception
    }

    @Test(expected = RecipeValidationException.class)
    public void it_should_not_create_recipe_without_yield() throws TitleAlreadyExistsException, RecipeValidationException {
        //Given
        Recipe recipe = new Recipe();
        recipe.setTitle("Fettucine Alfredo");
        recipe.setCategories(Collections.singleton("Pasta"));
        recipe.setIngredients(Collections.singletonList(new Ingredient("Black Pepper", new Amount("1", "pinch"))));
        recipe.setDirections(new Direction("In a large pot, heat water over high heat until boiling..."));

        XmlHelper.instance = new XmlHelper();
        XmlHelper.instance.parseXml();


        //When
        recipeFetchService.createRecipe(recipe);

        //Then catch the exception
    }

    @Test(expected = RecipeValidationException.class)
    public void it_should_not_create_recipe_without_category() throws TitleAlreadyExistsException, RecipeValidationException {
        //Given
        Recipe recipe = new Recipe();
        recipe.setTitle("Fettucine Alfredo");
        recipe.setYield("1");
        recipe.setIngredients(Collections.singletonList(new Ingredient("Black Pepper", new Amount("1", "pinch"))));
        recipe.setDirections(new Direction("In a large pot, heat water over high heat until boiling..."));

        XmlHelper.instance = new XmlHelper();
        XmlHelper.instance.parseXml();


        //When
        recipeFetchService.createRecipe(recipe);

        //Then catch the exception
    }

    @Test(expected = RecipeValidationException.class)
    public void it_should_not_create_recipe_without_ingredient() throws TitleAlreadyExistsException, RecipeValidationException {
        //Given
        Recipe recipe = new Recipe();
        recipe.setTitle("Fettucine Alfredo");
        recipe.setYield("1");
        recipe.setCategories(Collections.singleton("Pasta"));
        recipe.setDirections(new Direction("In a large pot, heat water over high heat until boiling..."));

        XmlHelper.instance = new XmlHelper();
        XmlHelper.instance.parseXml();


        //When
        recipeFetchService.createRecipe(recipe);

        //Then catch the exception
    }

    @Test(expected = RecipeValidationException.class)
    public void it_should_not_create_recipe_without_direction() throws TitleAlreadyExistsException, RecipeValidationException {
        //Given
        Recipe recipe = new Recipe();
        recipe.setTitle("Fettucine Alfredo");
        recipe.setYield("1");
        recipe.setCategories(Collections.singleton("Pasta"));
        recipe.setIngredients(Arrays.asList(new Ingredient("Black Pepper", new Amount("1", "pinch"))));

        XmlHelper.instance = new XmlHelper();
        XmlHelper.instance.parseXml();


        //When
        recipeFetchService.createRecipe(recipe);

        //Then catch the exception
    }
}