package com.mendix.recipe.fetch.api.controller;

import com.mendix.recipe.fetch.api.exception.CategoryIsNotExistException;
import com.mendix.recipe.fetch.api.exception.RecipeValidationException;
import com.mendix.recipe.fetch.api.exception.TitleAlreadyExistsException;
import com.mendix.recipe.fetch.api.model.Recipe;
import com.mendix.recipe.fetch.api.model.response.CategoryResponse;
import com.mendix.recipe.fetch.api.model.response.RecipeResponse;
import com.mendix.recipe.fetch.api.service.RecipeFetchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecipeFetchControllerTest {

    @InjectMocks
    private RecipeFetchController recipeFetchController;

    @Mock
    private RecipeFetchService recipeFetchService;

    @Test
    public void it_should_get_all_recipes() {
        //Given
        RecipeResponse recipeResponseMock = mock(RecipeResponse.class);

        when(recipeFetchService.getAllRecipes()).thenReturn(recipeResponseMock);

        //When
        RecipeResponse recipeResponse = recipeFetchController.getAllRecipes();

        //Then
        assertThat(recipeResponse).isEqualTo(recipeResponseMock);
    }

    @Test
    public void it_should_get_all_categories() {
        //Given
        CategoryResponse categoryResponseMock = mock(CategoryResponse.class);

        when(recipeFetchService.getAllCategories()).thenReturn(categoryResponseMock);

        //When
        CategoryResponse allCategories = recipeFetchController.getAllCategories();

        //Then
        assertThat(allCategories).isEqualTo(categoryResponseMock);
    }

    @Test
    public void it_should_get_recipes_by_category() throws CategoryIsNotExistException {
        //Given
        RecipeResponse recipeResponseMock = mock(RecipeResponse.class);
        String category = "Cakes";

        when(recipeFetchService.getRecipesByCategory(category)).thenReturn(recipeResponseMock);

        //When
        RecipeResponse recipesByCategory = recipeFetchController.getRecipesByCategory(category);

        //Then
        assertThat(recipesByCategory).isEqualTo(recipeResponseMock);
    }

    @Test
    public void it_should_get_recipes_by_params() {
        //Given
        RecipeResponse recipeResponse = mock(RecipeResponse.class);
        String title = "Amaretto Cake";
        String category = "Cakes";
        String ingredients = "Eggs";

        when(recipeFetchService.getRecipeByParams(title, category, ingredients)).thenReturn(recipeResponse);

        //When
        RecipeResponse recipeByParams = recipeFetchController.getRecipeByParams(title, category, ingredients);

        //Then
        assertThat(recipeByParams).isEqualTo(recipeResponse);
    }

    @Test
    public void it_should_throw_exception_when_creating_recipe_by_given_invalid_input() throws TitleAlreadyExistsException, RecipeValidationException {
        //Given
        Recipe recipe = new Recipe();
        recipe.setTitle("Sample Title");

        //When
        recipeFetchController.createRecipe(recipe);

        //Then
        Mockito.verify(recipeFetchService).createRecipe(recipe);
    }
}