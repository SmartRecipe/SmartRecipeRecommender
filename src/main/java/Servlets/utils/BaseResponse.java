package Servlets.utils;

import Beans.User;
import Beans.Recipe;
import java.util.List;
import java.io.Serializable;

public class BaseResponse implements Serializable {
    private User user;

    private Recipe recipe;

    private String filters[];

    private String message;

    private List<Recipe> recipes;

    public void BaseResponse() {
        this.user = new User();
        this.recipe = new Recipe();
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String[] getFilters() {
        return this.filters;
    }

    public void setFilters(String[] filters) {
        this.filters = filters;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Recipe> getRecipes() {
        return this.recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}   