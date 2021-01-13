package com.mendix.recipe.fetch.api.model.response;

import java.util.List;

public class CategoryResponse {

    private List<String> categoryList;

    public CategoryResponse() {

    }

    public CategoryResponse(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "categoryList=" + categoryList +
                '}';
    }
}
